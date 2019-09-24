package com.dayao.cloudstorage.service;

import com.dayao.cloudstorage.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/19
 */
public interface UserService {
    List<UserEntity> userList();
    UserEntity addUser(String username,String realname, String email,String password);
    UserEntity getUserByname(String username);
}
