package com.dayao.cloudstorage.entity;

import com.dayao.cloudstorage.utill.UtilTools;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
public class FileBean {
    private boolean isdirectory;
    private boolean haschild;
    private String name;
    private String path;
    private String length;
    private String lastmodified;
    private String filepath;

    public boolean isIsdirectory() {
        return isdirectory;
    }

    public void setIsdirectory(boolean isdirectory) {
        this.isdirectory = isdirectory;
    }

    public boolean isHaschild() {
        return haschild;
    }

    public void setHaschild(boolean haschild) {
        this.haschild = haschild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLength() {
        return length;
    }

    public void setLength(long length)
    {
        this.length = UtilTools.convertFileSize(length);
    }

    public String getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(String lastmodified) {
        this.lastmodified = lastmodified;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

}
