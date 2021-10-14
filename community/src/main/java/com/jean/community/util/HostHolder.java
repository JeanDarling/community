package com.jean.community.util;

import com.jean.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author The High Priestess
 * @date 2021/10/13 21:16
 * 用于代替session 对象
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
