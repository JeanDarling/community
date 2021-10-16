package com.jean.community.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author The High Priestess
 * @date 2021/10/8 21:18
 */
public class CommunityUtil {

    // 生成随机字符串
    public static String gennerateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密， 特点:只能加密，不能解密
    //加盐，提高安全性
    public static String md5(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());//加盐加密返回16进制
    }

    //JSON
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        if (map != null ) {
                for (String key: map.keySet()) {
                    jsonObject.put(key, map.get(key));
                }
        }
        return  jsonObject.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

    //测试json
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "张三");
        map.put("sex","男");
        System.out.println(getJSONString(0,"ok",map));
    }
}
