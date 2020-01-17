package com.wjk.blog.service;

import com.wjk.blog.po.User;

public interface UserSerivce {
    User checkLogin(String username,String password);
}
