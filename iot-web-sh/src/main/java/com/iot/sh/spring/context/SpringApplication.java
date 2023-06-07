package com.iot.sh.spring.context;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Kenel Liu
 */
public  abstract  class SpringApplication extends SpringBootServletInitializer{

    protected   void initBeforeRunSpring(BeforeSpringConfig beforeSpringConfig){
        BeforeSpringManager.getInstance().setBeforeSpringConfig(beforeSpringConfig);
    }
}
