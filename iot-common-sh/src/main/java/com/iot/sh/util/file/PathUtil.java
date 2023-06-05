package com.iot.sh.util.file;

import java.io.File;
import java.io.IOException;

/**
 * @author Kenel Liu
 */
public class PathUtil {
    public static String getFilePath(String path) throws IOException {
        return getFilePath(null,path);
    }

    public static String getFilePath(String parentPath,String subPath) throws IOException {
        if(parentPath==null) parentPath=".";
        File parent= new File(parentPath);
        if(!parent.exists())
            throw new IOException("文件["+parent+"]不存在.");
        String path=parent.getCanonicalPath();
        path=path.replaceAll("\\\\","/");
        if(subPath==null) subPath="";
        subPath=subPath.replaceAll("\\\\","/");
        if(subPath.equalsIgnoreCase("")){
            return path;
        }
        if(path.endsWith("/")){
            if(subPath.startsWith("./")){
                path=path+subPath.substring(2);
            }else if(subPath.startsWith("/")){
                path=path+subPath.substring(1);
            }else {
                path=path+subPath;
            }
        }else{
            if(subPath.startsWith("./")){
                path=path+subPath.substring(1);
            }else if(subPath.startsWith("/")){
                path=path+subPath;
            }else {
                path=path+"/"+subPath;
            }
        }
        return path;
    }

}
