package iot.sh.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public  abstract  class ApplicationServletContextListener implements ServletContextListener {

	@Override
	public abstract  void contextInitialized(ServletContextEvent sce);

	@Override
	public abstract void contextDestroyed(ServletContextEvent sce);


}
