package com.dayao.cloudstorage.utill;

import com.dayao.cloudstorage.entity.FileBean;

import java.util.Comparator;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
public class ComparatorFile implements Comparator<FileBean> {

    public ComparatorFile() {
    }

    public int compare(FileBean f1, FileBean f2) {

        boolean f1dir = f1.isIsdirectory();
        boolean f2dir = f2.isIsdirectory();

        if (f1dir && f2dir) {
            return 0;
        } else if (!f1dir && f2dir) {
            return 1;
        } else if (f1dir && !f2dir) {
            return -1;
        } else if (!f1dir && !f2dir) {
            return 0;
        }
        return 0;
    }

}
