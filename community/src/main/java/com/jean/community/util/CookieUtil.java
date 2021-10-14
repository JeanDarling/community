package com.jean.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author The High Priestess
 * @date 2021/10/13 20:59
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null ) {
                throw new IllegalArgumentException("参数为空！！");
        }

        Cookie[] cookies = request.getCookies(); // 得到所有cookie
        if (cookies != null ) {
            for (Cookie cookie : cookies) {
                // 判断每一个cookie的name是否等于传来的name
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
