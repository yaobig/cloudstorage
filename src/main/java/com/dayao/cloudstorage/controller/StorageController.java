package com.dayao.cloudstorage.controller;

import com.dayao.cloudstorage.entity.UserEntity;
import com.dayao.cloudstorage.service.SwiftStorageService;
import com.dayao.cloudstorage.utill.UtilTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
@RestController
@RequestMapping("/Storage")
@Api("对Storage进行处理")
public class StorageController extends BaseController{

    @Autowired
    private SwiftStorageService swiftStorageService;
    @PostMapping("/home")
    @ApiOperation(value = "查询用户所有文件")
    public ModelAndView home(HttpServletRequest request,
                             HttpServletResponse response, String path) {
        ModelAndView view = new ModelAndView();
        // 获取用户
        UserEntity user = getSessionUser(request);
        path = UtilTools.converStr(path);
        List list = swiftStorageService.getAllStoredList(user.getUsername(),path);
        view.addObject("path", path);
        view.addObject("search", "false");
        view.addObject("list", list);
        view.setViewName("/main");
        view.addObject("type", 0);
        return view;
    }
}
