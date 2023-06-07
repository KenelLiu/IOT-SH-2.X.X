package com.iot.sh.spring.datasource;

import com.iot.sh.spring.context.BeforeSpringConfig;
import com.iot.sh.spring.context.BeforeSpringManager;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration  
@AutoConfigureAfter(MybatisSessionFactory.class)  
public class MapperScan {
	  @Bean(name="writeMapperScanner")
	  public static MapperScannerConfigurer writeMapperScannerConfigurer() {
		    BeforeSpringConfig beforeSpringConfig=BeforeSpringManager.getInstance().getBeforeSpringConfig();
		    Objects.requireNonNull(beforeSpringConfig,"beforeSpringConfig is null");
			Objects.requireNonNull(beforeSpringConfig.getWriteMapperScannerBasePackage(),"beforeSpringConfig property[writeMapperScannerBasePackage] is null");
	        MapperScannerConfigurer writeMapperScannerConfigurer = new MapperScannerConfigurer();
	        writeMapperScannerConfigurer.setBasePackage(beforeSpringConfig.getWriteMapperScannerBasePackage());
	        writeMapperScannerConfigurer.setSqlSessionFactoryBeanName("writeFactory");
	        return writeMapperScannerConfigurer;
	    }
	  

}
