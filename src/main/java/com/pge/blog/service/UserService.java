package com.pge.blog.service;

import com.pge.blog.po.User;

public interface UserService {
    User getUser(String username,String password);
}
