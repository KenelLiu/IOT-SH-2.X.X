package com.iot.example.boot;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.iot.sh.spring.context.SpringBootIotApplication;
import com.iot.sh.spring.context.SpringContextManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
@ComponentScan(basePackages={"com.iot.example.boot"})
@ServletComponentScan(basePackages={"com.iot.example.boot.filter"})
@Slf4j
public class CloudApplication extends SpringBootIotApplication {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ApplicationContext applicationContext= SpringApplication.run(CloudApplication.class, args);
		SpringContextManager.getInstance().setApplicationContext(applicationContext);
		//org.springframework.boot.autoconfigure.EnableAutoConfiguration
		log.info("============启动完成========");
	}

	@Bean
	public SchduleStart start() {
		return new SchduleStart();
	}
	@Order(value=1)
	@Component
	 class SchduleStart implements CommandLineRunner {
		@Override
		public void run(String... args){
			//SchduleManager.getInstance().start();
		}
	}

}
