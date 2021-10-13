package com.jean.community.util;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author The High Priestess
 * @date 2021/10/8 22:55
 */
public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
    * @Description:  默认状态登录凭证超时时间；
    * @Param:
    * @return:
    * @Author: The High Priestess
    * @Date: 2021/10/12
    */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
    * @Description: 记住状态的登录凭证超时时间100天
    * @Param:
    * @return:
    * @Author: The High Priestess
    * @Date: 2021/10/12
    */

    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 *100;
}
