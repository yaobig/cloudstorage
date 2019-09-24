package com.dayao.cloudstorage.service.serviceImpl;

import com.dayao.cloudstorage.entity.UserEntity;
import com.dayao.cloudstorage.mapper.UserEntityMapper;
import com.dayao.cloudstorage.service.UserService;
import com.dayao.cloudstorage.utill.MD5;
import com.dayao.cloudstorage.utill.UtilTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/19
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserEntityMapper userMapper;
    @Autowired
    public UserServiceImpl(UserEntityMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserEntity> userList() {
        return userMapper.userList();
    }

    /**
     * 添加用户
     * @param username
     * @param realname
     * @param email
     * @param password
     * @return
     */
    @Override
    public UserEntity addUser(String username,String realname, String email,String password) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        MD5 md5 = new MD5();
        user.setPassword(md5.getMD5String(password));
        user.setUsername(username);
        user.setRealname(realname);
        user.setJoindate(UtilTools.timeTostrHMS(new Date()));
        user.setLastdate(UtilTools.timeTostrHMS(new Date()));
        userMapper.addUser(user);
        return user;
    }


    /**
     * 根据名字获取用户信息
     * @param username
     * @return
     */
    @Override
    public UserEntity getUserByname(String username) {
        UserEntity user = new UserEntity();
        user = userMapper.getUserByname(username);
        return user;
    }
}
