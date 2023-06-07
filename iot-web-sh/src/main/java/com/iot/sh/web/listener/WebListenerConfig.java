package com.iot.sh.web.listener;

import com.iot.sh.spring.context.BeforeSpringConfig;
import com.iot.sh.spring.context.BeforeSpringManager;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;
import java.util.Objects;

/**
 * @author Kenel Liu
 */
public class WebListenerConfig {
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> getListener(){
        BeforeSpringConfig beforeSpringConfig= BeforeSpringManager.getInstance().getBeforeSpringConfig();
        Objects.requireNonNull(beforeSpringConfig, "beforeSpringConfig is null");

        ApplicationServletContextListener listener=beforeSpringConfig.getApplicationServletContextListener();
        ServletListenerRegistrationBean<ServletContextListener> registrationBean=new ServletListenerRegistrationBean<ServletContextListener>();
        if(listener!=null){
            registrationBean.setEnabled(true);
            registrationBean.setListener(listener);
            registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        }else{
            registrationBean.setEnabled(false);
        }
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener>  getHttpListener(){
        BeforeSpringConfig beforeSpringConfig= BeforeSpringManager.getInstance().getBeforeSpringConfig();
        Objects.requireNonNull(beforeSpringConfig, "beforeSpringConfig is null");

        ApplicationHttpSessionListener listener=beforeSpringConfig.getApplicationHttpSessionListener();
        ServletListenerRegistrationBean<HttpSessionListener>  registrationBean=new ServletListenerRegistrationBean<HttpSessionListener> ();
        if(listener!=null){
            registrationBean.setEnabled(true);
            registrationBean.setListener(listener);
        }else{
            registrationBean.setEnabled(false);
        }
        return registrationBean;
    }
}
