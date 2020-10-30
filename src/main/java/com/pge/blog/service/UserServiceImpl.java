package com.pge.blog.service;

import com.pge.blog.dao.UserRepository;
import com.pge.blog.po.User;
import com.pge.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public User getUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return  user;
    }
}
