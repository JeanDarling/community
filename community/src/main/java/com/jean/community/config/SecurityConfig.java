package com.jean.community.config;


import com.jean.community.util.CommunityConstant;
import com.jean.community.util.CommunityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by The High Priestess
 *
 * @description security的相关配置
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant {

    @Override
    public void configure(WebSecurity web) throws  Exception {
        // 忽略掉对静态资源的拦截
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权
        http.authorizeRequests()
                .antMatchers(
                        "/user/setting", // 不登录，不可以设置
                        "/user/upload", //如果不登录是不可以上传头像的
                        "/discuss/add", //如果不登录，不可以发送帖子
                        "/comment/add/**", //如果不登录，不可以评论 **代表下一级
                        "/message/letter/**", //如果不登录，私信下面的功能全部需要登录
                        "/message/notice/**", //如果不登录，通知下面的功能全部需要登录
                        "/like", //点赞需要登录
                        "/follow", //点赞需要登录
                        "/unfollow" //点赞需要登录
                )
                .hasAnyAuthority(
                        // 对于上面的路径，拥有以下任何权限就可以访问
                        AUTHORITY_ADMIN,
                        AUTHORITY_USER,
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/top",
                        "/discuss/wonderful"
                )
                .hasAnyAuthority(
                        // 版主能访问上述权限
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/delete"
                )
                .hasAnyAuthority(
                        // 管理员能删除
                        AUTHORITY_ADMIN
                )
                //除了这些请求外，其他请求统统允许
                .anyRequest().permitAll()
                .and().csrf().disable(); // 禁用csrf,不会增加csrf攻击的检查

        //权限不够的处理
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 没有登录
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {  // 说明这是一个异步请求
                            // 生命返回类型是普通字符串
                            response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403,"你还没有登录┗|｀O′|┛ 嗷~~"));

                        } else {
                            // 如果不是异步的处理，直接重定向
                            response.sendRedirect(request.getContextPath() + "/login");
                        }
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    // 权限不足
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {  // 说明这是一个异步请求
                            // 生命返回类型是普通字符串
                            response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403,"你没有访问此功能的权限┗|｀O′|┛ 嗷~~"));

                        } else {
                            // 如果不是异步的处理，直接重定向
                            response.sendRedirect(request.getContextPath() + "/denied");
                        }
                    }
                });
        // security 底层会默认拦截/logout请求，进行退出处理
        // 覆盖他默认的逻辑，才能执行我们自己的退出的代码
        http.logout().logoutUrl("/securitylogout");

    }



}
