package com.wjk.blog.service.impl;

import com.wjk.blog.dao.UserRepository;
import com.wjk.blog.po.User;
import com.wjk.blog.service.UserSerivce;
import com.wjk.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserSerivce {
    @Autowired
    private UserRepository userRepository;
    @Transactional
    @Override
    public User checkLogin(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Util.md5(password));
        return user;
    }
}
