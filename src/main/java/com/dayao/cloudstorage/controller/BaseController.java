package com.dayao.cloudstorage.controller;

import com.dayao.cloudstorage.entity.UserEntity;
import com.dayao.cloudstorage.utill.Constants;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
public class BaseController {
    /**
     * 获取保存在Session中的用户对象
     *
     * @param request
     * @return
     */
    protected UserEntity getSessionUser(HttpServletRequest request) {
        UserEntity user = (UserEntity)request.getSession().getAttribute(Constants.USER_CONTEXT);
        return user;
    }

    /**
     * 保存用户对象到Session中
     * @param request
     * @param user
     */
    protected void setSessionUser(HttpServletRequest request,UserEntity  user) {
        request.getSession().setAttribute(Constants.USER_CONTEXT, user);
    }
}
