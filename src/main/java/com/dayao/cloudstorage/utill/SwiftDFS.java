package com.dayao.cloudstorage.utill;

import com.dayao.cloudstorage.entity.FileBean;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.ContainerListOptions;
import org.openstack4j.model.storage.object.options.ObjectListOptions;
import org.openstack4j.model.storage.object.options.ObjectLocation;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * mail: dayaojiang@foxmail.com
 * Create by dayao on 2019/9/21
 */
public class SwiftDFS {

    private OSClient os;
    public SwiftDFS() {
        os = UtilTools.getConnection();
    }
    // 创建用户
    public boolean createContainer(String rootPath){
        List containers = os.objectStorage().containers().list(ContainerListOptions.create().startsWith(rootPath));
        if(containers.isEmpty())
        {
            os.objectStorage().containers().create(rootPath);
            return true;
        }
        for(Iterator iterator = containers.iterator(); iterator.hasNext();)
        {
            SwiftContainer container = (SwiftContainer)iterator.next();
            if(!rootPath.equals(container.getName()))
            {
                os.objectStorage().containers().create(rootPath);
                return true;
            }
        }

        return false;
    }

    /**
     * 创建文件夹
     * @param rootPath dayao
     * @param path xx/
     * @return
     */
    public boolean createDir(String rootPath, String path)
    {
        os.objectStorage().containers().createPath(rootPath, path);
        return true;
    }
    private String getName(String path)
    {
        if(path != null && "/".equals(path.substring(path.length() - 1)))
            path = path.substring(0, path.length() - 1);
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public boolean isDirectory(String name)
    {
        return name != null && "/".equals(name.substring(name.length() - 1));
    }

    // 获取文件
    public List getFile(String rpath)
    {
        Map mappath = getSplitPath(rpath);
        String rootPath = mappath.get("rootPath").toString();
        String path = mappath.get("path").toString();
        List objs = list(rootPath, path);
        List list = new ArrayList();
        FileBean fb;
        for(Iterator iterator = objs.iterator(); iterator.hasNext(); list.add(fb))
        {
            SwiftObject obj = (SwiftObject)iterator.next();
            fb = new FileBean();
            String filePath = obj.getName();
            fb.setPath(filePath);
            fb.setName(getName(filePath));
            boolean flag = isDirectory(filePath);
            fb.setIsdirectory(flag);
            if(!flag)
                fb.setLength(obj.getSizeInBytes());
            fb.setLastmodified(UtilTools.cstToHMS(obj.getLastModified()));
        }

        return list;
    }

    private List list(String rootPath, String path)
    {
        return os.objectStorage().objects().list(rootPath, ObjectListOptions.create().path(path));
    }

    private Map getSplitPath(String path)
    {
        String strPath[] = path.split("/");
        String rootPath = "";
        path = "";
        for(int i = 0; i < strPath.length; i++)
            if(i == 0)
                rootPath = strPath[0];
            else
            if(i == strPath.length - 1)
            {
                if(strPath[i].contains("."))
                    path = (new StringBuilder(String.valueOf(path))).append(strPath[i]).toString();
                else
                    path = (new StringBuilder(String.valueOf(path))).append(strPath[i]).append("/").toString();
            } else
            {
                path = (new StringBuilder(String.valueOf(path))).append(strPath[i]).append("/").toString();
            }

        Map map = new HashMap();
        map.put("rootPath", rootPath);
        map.put("path", path);
        return map;
    }

    public boolean setMetadata(String rootPath, String path)
    {
        Map myMetadataMap = new HashMap();
        try
        {
            path = URLEncoder.encode(path, "UTF-8");
            path = path.replaceAll("\\+", "%20");
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        myMetadataMap.put("Oldpath", path);
        os.objectStorage().objects().updateMetadata(ObjectLocation.create(rootPath, path), myMetadataMap);
        return true;
    }

    public void deleteFile(String rootPath, String srcPath, String name, boolean isDir)
    {
        if(isDir)
        {
            deletefiles(rootPath, srcPath, (new StringBuilder(String.valueOf(name))).append("/").toString());
            deletedirfile(rootPath, srcPath, (new StringBuilder(String.valueOf(name))).append("/").toString());
        } else
        {
            deletefiles(rootPath, srcPath, name);
        }
    }

    public void deletefiles(String rootPath, String srcPath, String destPath)
    {
        boolean flag = setMetadata(rootPath, srcPath);
        if(!flag)
            System.out.println((new StringBuilder("********\u91CD\u8981\uFF1A\u8BBE\u7F6EMetadata\u5931\u8D25 name-->")).append(srcPath).toString());
        copy(rootPath, (new StringBuilder("garbage_")).append(rootPath).toString(), srcPath, destPath);
        delete(rootPath, srcPath);
    }

    private void copy(String srcContainer, String destContainer, String srcPath, String destPath)
    {
        ObjectLocation srcLocation = ObjectLocation.create(srcContainer, srcPath);
        ObjectLocation destLocation = ObjectLocation.create(destContainer, destPath);
        os.objectStorage().objects().copy(srcLocation, destLocation);
    }

    private void delete(String rootPath, String srcPath)
    {
        os.objectStorage().objects().delete(rootPath, srcPath);
    }

    private void deletedirfile(String rootPath, String path, String destPath)
    {
        List list = list(rootPath, path);
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            SwiftObject so = (SwiftObject)iterator.next();
            String filePath = so.getName();
            String fileName = getName(filePath);
            if(isDirectory(filePath))
            {
                deletefiles(rootPath, filePath, (new StringBuilder(String.valueOf(destPath))).append(fileName).append("/").toString());
                deletedirfile(rootPath, filePath, (new StringBuilder(String.valueOf(destPath))).append(fileName).append("/").toString());
            } else
            {
                deletefiles(rootPath, filePath, (new StringBuilder(String.valueOf(destPath))).append(fileName).toString());
            }
        }

    }
}
