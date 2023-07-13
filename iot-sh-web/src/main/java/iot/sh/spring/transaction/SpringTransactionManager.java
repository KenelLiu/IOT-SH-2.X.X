package iot.sh.spring.transaction;
import iot.sh.spring.datasource.DataSourceFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@AutoConfigureAfter(DataSourceFactory.class)
public class SpringTransactionManager {

	@Resource(name="write")
	private DataSource writeDataSource;
		
	@Bean(name="txWriteManager")	
	public PlatformTransactionManager writeTransaction(){	
		return new DataSourceTransactionManager(writeDataSource);
	}
	
}
