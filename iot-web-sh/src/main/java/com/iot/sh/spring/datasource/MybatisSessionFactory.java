package com.iot.sh.spring.datasource;

import com.iot.sh.spring.context.BeforeSpringConfig;
import com.iot.sh.spring.context.BeforeSpringManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
@AutoConfigureAfter(DataSourceFactory.class)
@Slf4j
public class MybatisSessionFactory {

	  @Resource(name="write")	
	  private DataSource writeDataSource;
	  
	  
	  @Bean(name="writeFactory")
	  public SqlSessionFactory writeSessionFactory() throws Exception {
	        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	        factoryBean.setDataSource(writeDataSource);
	        org.springframework.core.io.Resource[] resources=resolveMapperLocations();
	        if(resources.length>0)
	        	factoryBean.setMapperLocations(resources);
	        return factoryBean.getObject();
	    }
	  
	  private org.springframework.core.io.Resource[] resolveMapperLocations(){
		  BeforeSpringConfig beforeSpringConfig= BeforeSpringManager.getInstance().getBeforeSpringConfig();
		  Objects.requireNonNull(beforeSpringConfig, "beforeSpringConfig is null");
		  Objects.requireNonNull(beforeSpringConfig.getWriteMapperLocations(),"beforeSpringConfig property[writeMapperLocations] is null");
		  List<String> mapperLocations=beforeSpringConfig.getWriteMapperLocations();
		  if(mapperLocations.size()==0)
			  log.warn("beforeSpringConfig property[writeMapperLocations] size=0");
		  ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
		  List<org.springframework.core.io.Resource> resources=new ArrayList<>();
		  for (String mapperLocation : mapperLocations){
			try{
				org.springframework.core.io.Resource[] mappers=resolver.getResources(mapperLocation);
				resources.addAll(Arrays.asList(mappers));
			}catch(IOException ex){
				log.error(ex.getMessage(),ex);
			}
		 }
		 return resources.toArray(new org.springframework.core.io.Resource[resources.size()]);
	  }
	  
	  @Bean(name="writeTemplate")
	  public SqlSessionTemplate writeSessionTemplate() throws Exception {
	        SqlSessionTemplate template = new SqlSessionTemplate(writeSessionFactory()); // 使用上面配置的Factory
	        return template;
	    }	

	  @Bean(name="writeJdbcTemplate")
	  public JdbcTemplate writeJdbcTemplate() {
		  	JdbcTemplate template = new JdbcTemplate(writeDataSource); 
	        return template;
	    }	  

}
