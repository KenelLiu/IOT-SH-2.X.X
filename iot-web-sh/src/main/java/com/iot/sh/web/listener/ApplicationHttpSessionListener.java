package com.iot.sh.web.listener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public abstract  class ApplicationHttpSessionListener implements HttpSessionListener{


	@Override
	public abstract  void sessionCreated(HttpSessionEvent se);

	@Override
	public abstract  void sessionDestroyed(HttpSessionEvent se);


}
