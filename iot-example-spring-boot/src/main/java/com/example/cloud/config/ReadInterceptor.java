package com.example.cloud.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@AutoConfigureAfter(SpringTransactionManager.class)
public class ReadInterceptor {

	//========local 事务管理=======//
	@Resource(name="txReadManager")
	private TransactionManager transactionManager;
	//============事务拦截器===========//
	static final String TX_INTERCEPTOR_NAME="txReadInterceptor";
	/**
	 * 默认的只读事务,txReadInterceptor的拦截那些方法
	 */
	private static final String[] DEFAULT_ReadOnly_TransactionAttributes= {
		"get*" ,
		"find*" ,
		"query*" ,
		"select*" ,
		"list*" ,
		"*" ,
	};
	/**
	 * 自定义方法名的事务属性相关联,可以使用通配符(*)字符关联相同的事务属性的设置方法; 只读事务
	 */
	private  final static  String[] CUSTOM_ReadOnly_TransactionAttributes= {};
	
	/**
	 * 配置事务拦截器
	 *
	 * @param transactionManager : 事务管理器
	 */
	@Bean(TX_INTERCEPTOR_NAME)
	public TransactionInterceptor customTransactionInterceptor() {
		NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();
		RuleBasedTransactionAttribute       readOnly = this.readOnlyTransactionRule();
		// 默认的只读事务配置
		for (String methodName : DEFAULT_ReadOnly_TransactionAttributes) {
			transactionAttributeSource.addTransactionalMethod( methodName , readOnly );
		}
		// 定制的只读事务配置
		for ( String methodName : CUSTOM_ReadOnly_TransactionAttributes ) {
			transactionAttributeSource.addTransactionalMethod( methodName , readOnly );
		}
		return new TransactionInterceptor(transactionManager , transactionAttributeSource);
	}
	/**
	 * 只读事务
	 * {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED}
	 */
	private RuleBasedTransactionAttribute readOnlyTransactionRule() {
		RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
		readOnly.setReadOnly( true );
		readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
		readOnly.setTimeout(30);
		return readOnly;
	}	
}
