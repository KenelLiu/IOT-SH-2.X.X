package com.iot.sh.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.sh.spring.config.UploadConfig;
import com.iot.sh.util.file.PathUtil;
import com.iot.sh.util.jackson.JsonObjectUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iot.sh.constants.IOTConstants;
import com.iot.sh.jackson.ObjectMappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Kenel Liu
 */
@Slf4j
public class BaseWeb<T> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected UploadConfig uploadConfig;
    protected T getSessionUser(){
        T user=(T)(getHttpSession().getAttribute(IOTConstants.Session.Session_User));
        if(user==null)
            log.warn("Session User is null...");
        return user;
    }
    protected void setSessionUser(T user){
        getHttpSession().setAttribute(IOTConstants.Session.Session_User,user);
    }
    protected void clearSessionUser(){
        getHttpSession().removeAttribute(IOTConstants.Session.Session_User);
    }
    protected HttpSession getHttpSession(){
        return request.getSession();
    }
    /**
     * 读取url中的QueryString 查询
     * @return
     */
    private ObjectNode readQueryUrl(){
        String urlQuery=request.getQueryString();
        ObjectMapper objectMapper=null;
        ObjectNode queryJson=null;
        if(urlQuery!=null && urlQuery.trim().length()>0){
            String[] keyPairs=urlQuery.split("&");
            for(String keyValue:keyPairs){
                String[] values=keyValue.split("=");
                if(values.length!=2 ||
                        values[0]==null || values[1]==null ){
                    // key==null 或value==null 忽略
                    continue;
                }
                if(queryJson==null){
                    objectMapper= ObjectMappers.JSON_MAPPER;
                    queryJson=objectMapper.createObjectNode();
                }
                putValue(values[0],request.getParameter(values[0]),queryJson,objectMapper);
            }
        }
        return queryJson;
    }

    private  void putValue(String key,String value,ObjectNode  queryJson,ObjectMapper objectMapper){
        int idxDot=key.indexOf(".");
        if(idxDot<0){
            queryJson.put(key,value);
            return;
        }
        String parentKey=key.substring(0,idxDot);
        JsonNode jsonNode=queryJson.get(parentKey);
        ObjectNode dataJson=null;
        if(jsonNode!=null){
            dataJson=(ObjectNode)jsonNode;
        }else {
            dataJson=objectMapper.createObjectNode();
        }
        queryJson.put(parentKey,dataJson);
        String subKey=key.substring(idxDot+1);
        putValue(subKey,value,dataJson,objectMapper);
    }

    /**
     * ObjectMappers.JSON_MAPPER.treeToValue(jsonNode, MyClass.class);
     * @return
     */
    protected  ObjectNode readBody(){
        if(request==null)
            return null;
        // 读取http中的（）数据流
        BufferedReader bufferReader=null;
        StringBuilder body =null;
        try{
            ObjectNode queryJson=readQueryUrl();
            String method=request.getMethod().toUpperCase().trim();
            switch(method){
                case "GET":
                    return queryJson;
                case "POST":
                    InputStream inputStream=request.getInputStream();
                    bufferReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    body= new StringBuilder(512);
                    char[] cbuf=new char[1024];
                    int len=0;
                    while((len=bufferReader.read(cbuf))>0){
                        body.append(cbuf, 0, len);
                    }
                    String bodyValue=body.toString().trim();
                    if(queryJson==null)
                        return bodyValue.equals("") ? null:(ObjectNode)(ObjectMappers.JSON_MAPPER.readTree(bodyValue));

                    ObjectNode bodyJson= (ObjectNode)(ObjectMappers.JSON_MAPPER.readTree(bodyValue));
                    return JsonObjectUtil.merge(bodyJson, queryJson);
            }
            return null;
        }catch(Throwable ex){
            log.error("read body cause error:"+ex.getMessage(),ex);
            return null;
        }finally{
            try{if(bufferReader!=null) bufferReader.close();}catch(Exception ex){}
        }
    }

    protected String getFileRootPath(){
        String path=uploadConfig.getPath();
        try{
            if(path==null || path.startsWith("./")){
                path=PathUtil.getFilePath(path);
            }else{
                path=PathUtil.getFilePath(path,null);
            }
            return path;
        }catch (IOException ex){
            log.error(ex.getMessage(),ex);
        }
        return path;
    }
}
