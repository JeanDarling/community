package com.jean.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author The High Priestess
 * @date 2021/10/14 16:40
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";

    // 根节点
    private TireNode rootNode = new TireNode();

    //服务器启动的时候被调用
    @PostConstruct
    public void init() {
        try(
                InputStream is =  this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                ) {
            //通过reader 读取，敏感词
            String keyWord;
            while ((keyWord = reader.readLine())!=null) {
                // 添加到前缀树
                this.addKeyWord(keyWord);
            }
        }catch (IOException e){
            logger.error("加载敏感词文件失败: "+ e.getMessage());
        }
    }

    //将一个敏感词添加到前缀树中去
    private void addKeyWord (String keyWord) {
            TireNode tempNode = rootNode;
            for (int i = 0; i < keyWord.length(); i++) {
                char c = keyWord.charAt(i);
                TireNode subNode = tempNode.getSubNode(c);
                if (subNode == null ){
                    // 初始化子结点
                    subNode = new TireNode();
                    tempNode.addSubNode(c, subNode); // 把当前子节点挂到根节点下
                }
                // 指向子节点， 进入下一轮循环
                tempNode = subNode;

                // 设置结束标志
                if (i == keyWord.length() - 1) {
                    tempNode.setKeywordEnd(true);
                }
            }
    }
/** 
* @Description: 过滤敏感词
* @Param:
* @return:  过滤后的文本
* @Author: The High Priestess
* @Date: 2021/10/14 
*/ 
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
                return null;
        }

        // 指针1 指向树
        TireNode tempNode = rootNode;
        // 指针2
        int begin = 0;
        // 指针3
        int position = 0;
        // 结果
        StringBuilder sb = new StringBuilder();
        while (position < text.length()) {
                char c = text.charAt(position);
                // 跳过符号
            if (isSymbol(c)) {
                // 指针1处于根节点， 将此符号计入结果，让指针2向下一步走
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                //无论符号在开头或中间，指针3 都向下走一步
                position++;
                continue;
            }
            // 检查下一个节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null ) {
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            }else if(tempNode.isKeywordEnd()){
                // 发现敏感词,将begin-position的字符串替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                begin = ++position;
                // 重新指向根节点
                tempNode = rootNode;
            }else {
                // 检查下一个字符
                position++;
            }
        }
        // 将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    // 判断是否是符号
    private boolean isSymbol(Character c) {
        //CharUtils.isAsciiAlphanumeric(c)是不是普通字符 ，如果是返回true
        // 0x2E80 ~  0x9FFF 东亚文字范围 包括中文日文韩文。。。
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);  //!CharUtils.isAsciiAlphanumeric(c) 如果是特殊字符返回true
    }
    //前缀树
    private class TireNode {
        // 关键词结束的标识
        private boolean isKeywordEnd = false;

        // 子节点 ，一个子节点可能有多个子节点(key是下级字符，value是下级结点)
        private Map<Character, TireNode> subNode = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode (Character c, TireNode node){
            subNode.put(c, node);
        }

        //获取子节点
        public TireNode getSubNode(Character c) {
            return subNode.get(c);
        }
    }
}
