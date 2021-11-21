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

    /**
    * @Description:  实体类型,帖子
    * @Param:
    * @return:
    * @Author: The High Priestess
    * @Date: 2021/10/17
    */

    int ENTITY_TYPE_POST = 1;

    /**
    * @Description: 实体类型，评论
    * @Param:
    * @return:
    * @Author: The High Priestess
    * @Date: 2021/10/17
    */
    int ENTITY_TYPE_COMMENT = 2;

    /**
     * @author The High Priestess
     * @date 2021/11/3 10:43
     * @description : 实体用户类型，用户
     */
    int ENTITY_TYPE_USER = 3;

    /**
     * @author The High Priestess
     * @date 2021/11/4 21:35
     * @description : 主题：评论
     */
    String TOPIC_COMMENT = "comment";

    /**
     * @author The High Priestess
     * @date 2021/11/4 21:35
     * @description :主题：点赞
     */
    String TOPIC_LIKE = "like";

    /**
     * @author The High Priestess
     * @date 2021/11/4 21:36
     * @description :主题：关注
     */
    String TOPIC_FOLLOW = "follow";
/**
 * @author The High Priestess
 * @date 2021/11/18 16:56
 * @description : 主题：发帖
 */
    String TOPIC_PUBLISH = "publish";
/**
 * @author The High Priestess
 * @date 2021/11/4 21:47
 * @description : 系统用户ID
 */
    int SYSTEM_USER_ID= 1;
}
