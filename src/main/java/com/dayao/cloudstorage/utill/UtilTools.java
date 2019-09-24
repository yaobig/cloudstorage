package com.dayao.cloudstorage.utill;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
public class UtilTools {
    public static OSClient getConnection(){
        OSClient os = OSFactory.builderV2()
                .endpoint("http://192.168.1.200:5000/v2.0")
                .credentials("admin", "000000")
                .tenantName("admin")
                .authenticate();
        return os;
    }
    public static String cstToHMS(Date cst) {
        SimpleDateFormat datefarmat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return datefarmat.format(cst).toString();
    }

    public static String timeTostrHMS(Date date) {
        String strDate = "";
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            strDate = format.format(date);
        }
        return strDate;
    }

    /**
     * 默认是utf-8编码
     *
     * @param str
     * @return
     */
    public static String converStr(String str) {
        return converStr(str, "UTF-8");
    }
    public static String converStr(String str, String encode) {
        if (str == null || str.equals("null")) {
            return "";
        }
        try {
            byte[] tmpbyte = str.getBytes("ISO8859_1");
            if (encode != null) {
                // 如果指定编码方式
                str = new String(tmpbyte, encode);
            } else {
                // 用系统默认的编码
                str = new String(tmpbyte);
            }
            return str;
        } catch (Exception e) {
        }
        return str;
    }

    public static String convertFileSize(long filesize)
    {
        String strUnit = "Bytes";
        String strAfterComma = "";
        int intDivisor = 1;
        if(filesize >= 1048576L)
        {
            strUnit = "MB";
            intDivisor = 1048576;
        } else
        if(filesize >= 1024L)
        {
            strUnit = "KB";
            intDivisor = 1024;
        }
        if(intDivisor == 1)
            return (new StringBuilder(String.valueOf(filesize))).append(" ").append(strUnit).toString();
        strAfterComma = (new StringBuilder()).append((100L * (filesize % (long)intDivisor)) / (long)intDivisor).toString();
        if(strAfterComma == "")
            strAfterComma = ".0";
        return (new StringBuilder(String.valueOf(filesize / (long)intDivisor))).append(".").append(strAfterComma).append(" ").append(strUnit).toString();
    }
}
