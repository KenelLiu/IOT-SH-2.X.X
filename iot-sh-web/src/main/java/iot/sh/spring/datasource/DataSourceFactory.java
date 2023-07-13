package iot.sh.spring.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DataSourceFactory {

	@Bean(name="write")
	@ConfigurationProperties("spring.datasource")
	@Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
	public HikariDataSource writeDataSource(){
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

}
