package com.kitty.shiro.service;

import com.kitty.shiro.domain.User;

public interface UserService {
    User findByName(String name);
    User findById(Integer id);
}
