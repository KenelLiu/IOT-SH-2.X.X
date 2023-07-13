package iot.sh.spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Kenel Liu
 */
public class SpringContextManager implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private static final SpringContextManager instance=new SpringContextManager();
    private SpringContextManager(){
    }
    public static  SpringContextManager getInstance(){
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextManager.applicationContext=applicationContext;
    }

    public  ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public  Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public  <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    //通过name,以及Clazz返回指定的Bean
    public  <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
