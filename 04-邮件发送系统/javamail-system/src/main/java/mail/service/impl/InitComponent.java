package mail.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import mail.entity.EmailSource;
import mail.entity.Target;
import mail.service.EmailSourceService;
import mail.service.TargetService;



/**
 * 初始化组件
 * @author LGX
 *
 */
@Component("initComponent")
public class InitComponent implements ApplicationContextAware, ServletContextListener {

	//静态共享对象,储存缓存,让spring启动时和servlet容器启动时的对象共有,用于管理bean等
	private static ApplicationContext applicationContext;
	
	//spring初始化时获取applicationContext
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//servlet容器启动时获取application并刷新
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		refreshSystem(application);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	/**
	 * 刷新application缓存方法
	 * @param application
	 */
	public void refreshSystem(ServletContext application) {
		 EmailSourceService mailSourceService = (EmailSourceService) applicationContext.getBean("mailSourceService");
		 TargetService targetService = (TargetService) applicationContext.getBean("targetService");
		 
		 List<EmailSource> mailSourceList = mailSourceService.list(null);
		 List<Target> targetList = targetService.list(null);
		 
		 application.setAttribute("mailSourceList", mailSourceList);
		 application.setAttribute("targetList", targetList);
	}
	
}
