package com.jean.community.dao;

import com.jean.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author The High Priestess
 * @date 2021/10/6 16:42
 */

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPost(int userId, int offset, int limit);

//    parem给参数区别名，如果只有一个参数，并且在<if>；里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    //增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostByID(int id);

    int updateCommentCount(int id, int commentCount);
}
