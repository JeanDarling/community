package com.jean.community.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

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

    //
}
