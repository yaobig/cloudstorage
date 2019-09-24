package com.dayao.cloudstorage.controller;

import com.dayao.cloudstorage.entity.UserEntity;
import com.dayao.cloudstorage.service.SwiftStorageService;
import com.dayao.cloudstorage.service.UserService;
import com.dayao.cloudstorage.utill.Constants;
import com.dayao.cloudstorage.utill.MD5;
import com.dayao.cloudstorage.utill.UtilTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/19
 */
@RestController
@RequestMapping("/user")
@Api("对用户信息处理的类")
public class UserController extends BaseController{
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private SwiftStorageService swiftStorageService;

    // 查询所有用户
    @PostMapping("/userList")
    @ApiOperation(value = "查询所有用户")
    public List<UserEntity> getAll(){
        List<UserEntity> list = this.userService.userList();
        System.out.println(list.toString());
        return list;
    }

    // 添加新用户
    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户")
    public String addUser(@RequestBody UserEntity user){
        try {
            String flag = userService.getUserByname(user.getUsername()).getUsername();
            if (flag != null) {
                return "已存在";
            }
        }catch (Exception e){
            user = userService.addUser(user.getUsername(), user.getRealname(), user.getEmail(),user.getPassword());
        }
        swiftStorageService.createContainer(user.getUsername());
        return "创建成功";
    }

    // 登录
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public String Login(@RequestBody UserEntity user,HttpServletRequest request){
        user = userService.getUserByname(user.getUsername());
        if (user.getUsername() == null){
            return "用户不存在";
        }
        MD5 md5 = new MD5();
        String password = md5.getMD5String(user.getPassword());
        if (!password.equals(user.getPassword())){
            return "密码错误";
        }
        UtilTools.getConnection();
        setSessionUser(request, user);
        return "登录成功";
    }
}

