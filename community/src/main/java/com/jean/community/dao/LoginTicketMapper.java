package com.jean.community.dao;

import com.jean.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author The High Priestess
 * @date 2021/10/10 14:04
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id") //自动生成id,并注入给id
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectLoginTicket(String ticket);

    @Update({
            "<script> ",
            "update login_ticket set status=#{status} where ticket = #{ticket} ",
            "<if test=\"ticket != null\" >",
            "and 1=1",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}
