package com.example.cloud.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.cloud.listener.AppListener;
import com.example.cloud.listener.SessionListener;
@Configuration
//@EnableWebMvc
public class SpringWebConfig  implements WebMvcConfigurer {
	/**
	@Bean
	public FilterRegistrationBean<Filter> getOrderFilter(){
		OrderFilter filter=new OrderFilter();
		FilterRegistrationBean<Filter> registrationBean=new FilterRegistrationBean<Filter>();
		registrationBean.setFilter(filter);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(1);
		return registrationBean;
	}	
	
	@Bean
	public FilterRegistrationBean<Filter> getAuthFilter(){		
		AuthFilter filter=new AuthFilter();
		FilterRegistrationBean<Filter> registrationBean=new FilterRegistrationBean<Filter>();
		registrationBean.setFilter(filter);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(2);
		return registrationBean;
	}
	**/
	/**
	@Bean
	public ViewResolver jspViewResolver() {
	//application.properties  spring.mvc.view.prefix: /WEB-INF/views/   spring.mvc.view.suffix: .jsp
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		
		return bean;
	}
	
	 @Bean
	 public ThymeleafViewResolver viewResolver(){
		 //模板
		 ClassLoaderTemplateResolver templateResolver =new ClassLoaderTemplateResolver();
		 SpringResourceTemplateResolver templateResolver=new SpringResourceTemplateResolver();
		 templateResolver.setPrefix("/templates/");
		 templateResolver.setSuffix(".html");
		 templateResolver.setTemplateMode(TemplateMode.HTML);
		 templateResolver.setCharacterEncoding("utf-8");
		 // 创建模板引擎
		 SpringTemplateEngine engine = new SpringTemplateEngine();
		 engine.setTemplateResolver(templateResolver);
		 engine.setEnableSpringELCompiler(true);
		 // 创建模板解析器
		 ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		 viewResolver.setCharacterEncoding("utf-8");
		 viewResolver.setTemplateEngine(engine);
		 return viewResolver;
	 }
	 **/
	 /**
	 <bean id="velocityEngine" class="org.springframework.ui.velocity.VelocityEngineFactoryBean">
	 <property name="resourceLoaderPath" value="/WEB-INF/classes/templet" />
	 <property name="velocityProperties">
	 <props>
	 <prop key="velocimacro.library">*.vm</prop>
	 <prop key="default.contentType">text/html; charset=utf-8</prop>
	 <prop key="output.encoding">utf-8</prop>
	 <prop key="input.encoding">utf-8</prop>
	 </props>
	 </property>
	 </bean>
	 
	 <bean id="velocityEngine"
    class="org.apache.velocity.spring.VelocityEngineFactoryBean">
    <property name="velocityProperties">
        <props>
            <prop key="resource.loader">class</prop>
            <prop key="class.resource.loader.class">
                org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
            </prop>
        </props>
    </property>
</bean>

	 
	@Bean
	public ServletRegistrationBean<HttpServlet> getFileServlet(){
		FileServlet servlet=new FileServlet();		
		ServletRegistrationBean<HttpServlet> registrationBean=new ServletRegistrationBean<HttpServlet>();
		registrationBean.setServlet(servlet);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/servlet/file/*");//拦截路径，可以添加多个
		registrationBean.setUrlMappings(urlPatterns);
		registrationBean.setOrder(1);
		return registrationBean;
	}
	  **/
	@Bean
	public ServletListenerRegistrationBean<ServletContextListener>  getListener(){
		AppListener listener=new AppListener();
		ServletListenerRegistrationBean<ServletContextListener> registrationBean=new ServletListenerRegistrationBean<ServletContextListener>();
		registrationBean.setEnabled(true);
		registrationBean.setListener(listener);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionListener>  getHttpListener(){
		SessionListener listener=new SessionListener();
		ServletListenerRegistrationBean<HttpSessionListener>  registrationBean=new ServletListenerRegistrationBean<HttpSessionListener> ();
		registrationBean.setEnabled(true);
		registrationBean.setListener(listener);
		return registrationBean;
	}		
}
	
