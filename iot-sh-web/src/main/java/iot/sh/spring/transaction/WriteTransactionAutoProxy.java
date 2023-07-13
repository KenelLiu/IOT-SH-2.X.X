package iot.sh.spring.transaction;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@AutoConfigureAfter(WriteInterceptor.class)  
public class WriteTransactionAutoProxy {
	/**
	 * 默认只对 "*Service" , "*ServiceImpl" Bean 进行事务处理,"*"表示模糊匹配, 比如 : userService,orderServiceImpl
	 */
	private static final String[] DEFAULT_TRANSACTION_BeanNames={ "*Service" , "*ServiceImpl" };	
	/**
	 * 自定义事务 BeanName 拦截
	 */
	private   final static String[]  CUSTOM_TRANSACTION_BeanNames= {};

	@Resource(name=WriteInterceptor.TX_INTERCEPTOR_NAME)
	private TransactionInterceptor txAdvice;
	/**
	 * 使用beeanName配置自动代理事务
	 * <p>
	 * {@link WriteInterceptor#customTransactionInterceptor()}
	 */
	@Bean
	public BeanNameAutoProxyCreator writeBeanNameAutoProxyCreator(){
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		// 设置定制的事务拦截器
		beanNameAutoProxyCreator.setInterceptorNames(WriteInterceptor.TX_INTERCEPTOR_NAME);
		List< String > transactionBeanNames = new ArrayList<>( DEFAULT_TRANSACTION_BeanNames.length + CUSTOM_TRANSACTION_BeanNames.length );
		// 默认
		transactionBeanNames.addAll(Arrays.asList(DEFAULT_TRANSACTION_BeanNames ) );
		// 定制
		transactionBeanNames.addAll(Arrays.asList(CUSTOM_TRANSACTION_BeanNames) );
		// 归集
		beanNameAutoProxyCreator.setBeanNames(transactionBeanNames.toArray(new String[transactionBeanNames.size()]));

		beanNameAutoProxyCreator.setProxyTargetClass( true );
				
		return beanNameAutoProxyCreator;
	}

	/**
	 * 暂时未使用
	 * 定义AOP拦截
	 */
	private static final String AOP_POINTCUT_EXPRESSION="execution(public * com.iot.waterfarm.service.*.*(..))";
	/**
	 * 使用定义AOP拦截配置自动代理事务[DefaultAdvisorAutoProxyCreator,DefaultPointcutAdvisor]
	 * 暂时未使用
	 * @return
	 */
	public DefaultAdvisorAutoProxyCreator writeAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator  advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setAdvisorBeanNamePrefix("com.iot.waterfarm.service");
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	
	public DefaultPointcutAdvisor writePointcutAdvisor(){
		DefaultPointcutAdvisor pointAdvisor=new DefaultPointcutAdvisor();
		pointAdvisor.setAdvice(txAdvice);
		AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		pointAdvisor.setPointcut(pointcut);		
		return pointAdvisor;
	}
	
	
}
