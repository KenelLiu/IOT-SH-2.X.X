package com.iot.sh.spring.context;

/**
 * @author Kenel Liu
 */
public class BeforeSpringManager {
    private static final BeforeSpringManager instance=new BeforeSpringManager();
    private BeforeSpringConfig beforeSpringConfig;

    private BeforeSpringManager(){
    }
    public static  BeforeSpringManager getInstance(){
        return instance;
    }

    public BeforeSpringManager setBeforeSpringConfig(BeforeSpringConfig beforeSpringConfig){
        this.beforeSpringConfig=beforeSpringConfig;
        return instance;
    }

    public BeforeSpringConfig getBeforeSpringConfig(){
        return this.beforeSpringConfig;
    }
}
