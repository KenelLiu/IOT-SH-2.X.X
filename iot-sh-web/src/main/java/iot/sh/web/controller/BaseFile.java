package iot.sh.web.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Kenel Liu
 */
public class BaseFile<T> extends BaseWeb<T>{
    @RequestMapping(value = "/download",method ={RequestMethod.GET})
    public ResponseEntity<InputStreamSource> download(){
        String fileUrl=request.getParameter("uri");
        File target = new File(getFileRootPath()+fileUrl);
        //=====构建响应体====//
        if (target.exists()) {
            FileSystemResource resource = new FileSystemResource(target);
            String titleVal=target.getName();
            try {
                titleVal = new String(titleVal.getBytes(), "ISO8859-1");
            } catch (Exception e) {
            }
            return ResponseEntity.ok()
                                 .header("Content-Type","application/octet-stream")
                                 .header("Content-Disposition", "attachment;filename="+titleVal)
                                 .body(resource);
        }else{
            // 如果文件不存在，返回404响应
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/preview",method ={RequestMethod.GET})
    public ResponseEntity<InputStreamSource> preview(WebRequest request){
        String fileUrl=request.getParameter("uri");
        File target = new File(getFileRootPath()+fileUrl);
        //=====构建响应体====//
        if (target.exists()) {
            // 获取文件的最后修改时间
            long lastModified = target.lastModified();
            if (request.checkNotModified(lastModified)) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
            // 获取文件扩展名
            String ext = fileUrl.substring(fileUrl.lastIndexOf(".")+1).toLowerCase();
            // 根据文件扩展名获取mediaType
            String mediaType =uploadConfig.getPicMap().get(ext);
            // 如果没有找到对应的扩展名，使用默认的mediaType
            if (Objects.isNull(mediaType)) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
            FileSystemResource resource = new FileSystemResource(target);
            return ResponseEntity.ok()
                                 .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                                 .lastModified(lastModified)
                                 .header("Content-Type", mediaType)// 指定文件的contentType
                                 .body(resource);
        }else{
            // 如果文件不存在，返回404响应
            return ResponseEntity.notFound().build();
        }
    }
}
