package com.jean.community.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE ="like:user";
    private static final String PREFIX_FOLLOWEE ="followee";// 关注者目标
    private static final String PREFIX_FOLLOWER ="follower";// 关注
    private static final String PREFIX_KAPTCHA="kaptcha";
    private static final String PREFIX_TICKET="ticket";
    private static final String PREFIX_USER="user";
    private static final String PREFIX_UA="ua";
    private static final String PREFIX_DAU="dau";
    private static final String PREFIX_POST="post";

    // 某个实体的赞：
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {
            return PREFIX_USER_LIKE + SPLIT + userId;
    }

    // 某个用户关注的实体
    // followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId +SPLIT +entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static  String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType +SPLIT +entityId;
    }

    // 登录验证码
    public static String getKapthchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录的凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 用户
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

    // 单日UV
    public static String getUVKey(String data) {
        return PREFIX_UA + SPLIT + data;
    }
    // 区间UV
    public static String getUVKey(String startData, String endData) {
        return PREFIX_UA + SPLIT +startData + SPLIT +endData;
    }

    // 单日活跃用户
    public static String getDAUKey(String data) {
        return PREFIX_DAU + SPLIT + data;
    }

    // 区间活跃用户
    public static String getDAUKey(String startData, String endData) {
        return PREFIX_DAU + SPLIT +startData + SPLIT +endData;
    }

    // 帖子分数
    public static String getPostScoreKey() {
        return PREFIX_POST +SPLIT +"score";
    }
}
