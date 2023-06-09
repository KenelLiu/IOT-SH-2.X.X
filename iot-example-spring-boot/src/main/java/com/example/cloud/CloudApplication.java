package com.example.cloud;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.cloud.context.SpringContextUtil;
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
@ComponentScan(basePackages={"com.cdo.cloud","com.javadevjournal"})
@ServletComponentScan(basePackages={"com.cdo.cloud.filter"})
public class CloudApplication implements CommandLineRunner{
	private static Log logger=LogFactory.getLog(CloudApplication.class);


	public static void main(String[] args) throws FileNotFoundException, IOException {
		ApplicationContext applicationContext= SpringApplication.run(CloudApplication.class, args);
		new SpringContextUtil().setApplicationContext(applicationContext);
		//ThymeleafProperties
		//org.springframework.boot.autoconfigure.EnableAutoConfiguration
		logger.info("============启动完成========"+logger.getClass());
	}

	 public void run(String... strings) throws Exception {

	    }


}
