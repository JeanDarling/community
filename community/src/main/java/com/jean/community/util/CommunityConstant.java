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
}
