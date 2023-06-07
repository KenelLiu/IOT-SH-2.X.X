package com.iot.sh.spring.context;

import com.iot.sh.web.listener.ApplicationHttpSessionListener;
import com.iot.sh.web.listener.ApplicationServletContextListener;

import java.util.List;

/**
 * @author Kenel Liu
 */
public class BeforeSpringConfig {

    private String writeMapperScannerBasePackage;

    private List<String> writeMapperLocations;

    private ApplicationServletContextListener applicationServletContextListener;

    private ApplicationHttpSessionListener applicationHttpSessionListener;

    public BeforeSpringConfig(){}

    /**
     *  扫描mybatis的java文件 路径
     *  如:com.iot.waterfarm.mapper.write
     * @param writeMapperScannerBasePackage
     * @return
     */
    public BeforeSpringConfig setWriteMapperScannerBasePackage(String writeMapperScannerBasePackage){
        this.writeMapperScannerBasePackage=writeMapperScannerBasePackage;
        return this;
    }

    /**
     *  扫描mybatis的xml文件 路径
     *  如：
     *  writeMapperLocations.add("classpath*:com/iot/waterfarm/mapper/xml/'**'/*Mapper.xml");
     * @param writeMapperLocations
     * @return
     */
    public BeforeSpringConfig setWriteMapperLocations(List<String> writeMapperLocations){
        this.writeMapperLocations=writeMapperLocations;
        return this;
    }

    public String getWriteMapperScannerBasePackage(){
        return this.writeMapperScannerBasePackage;
    }

    public  List<String> getWriteMapperLocations(){
        return this.writeMapperLocations;
    }

    public BeforeSpringConfig setApplicationServletContextListener(ApplicationServletContextListener applicationServletContextListener){
        this.applicationServletContextListener=applicationServletContextListener;
        return this;
    }

    public BeforeSpringConfig setApplicationHttpSessionListener(ApplicationHttpSessionListener applicationHttpSessionListener){
        this.applicationHttpSessionListener=applicationHttpSessionListener;
        return this;
    }

    public ApplicationServletContextListener getApplicationServletContextListener(){
        return this.applicationServletContextListener;
    }

    public ApplicationHttpSessionListener getApplicationHttpSessionListener(){
        return this.applicationHttpSessionListener;
    }
}
