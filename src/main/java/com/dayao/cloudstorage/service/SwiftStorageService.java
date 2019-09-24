package com.dayao.cloudstorage.service;

import com.dayao.cloudstorage.utill.ComparatorFile;
import com.dayao.cloudstorage.utill.Constants;
import com.dayao.cloudstorage.utill.SwiftDFS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
@Service
@Transactional
public class SwiftStorageService {
    /**
     * 创建用户对应容器和垃圾站
     * @param username
     */
    public void createContainer(String username){
        SwiftDFS swiftdfs= new SwiftDFS();
        swiftdfs.createContainer(username);
        swiftdfs.createContainer(Constants.GARBAGE_PREFIX + username);
    }

    /**
     * 获取所有文件
     * @param username
     * @param path
     * @return
     */
    public List getAllStoredList(String username, String path){
        SwiftDFS swiftdfs = new SwiftDFS();
        String up =null;
        up = username+"/"+path;
        List list =swiftdfs.getFile(up);
        ComparatorFile comparator = new ComparatorFile();
        if (!list.isEmpty()) {
            synchronized (list) {
                Collections.sort(list, comparator);
            }

        }
        return list;
    }

    /**
     * 创建文件夹
     *
     * @param name
     * @return
     */
    public boolean createDir(String username, String path, String name) {
        SwiftDFS swiftdfs = new SwiftDFS();
        path += name + "/";
        swiftdfs.createDir(username, path);
        return true;
    }

    // 删除
    public void deleteFile(String rootPath, String srcPath, String name, boolean isDir){
        SwiftDFS swiftDFS = new SwiftDFS();
        swiftDFS.deleteFile(rootPath, srcPath, name, isDir);
    }

}
