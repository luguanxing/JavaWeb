package mail.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import mail.entity.MyEmailFormat;
import mail.entity.Schedule;
import mail.entity.Target;
import mail.service.EmailService;
import mail.service.EmailSourceService;
import mail.service.ScheduleService;
import mail.service.TargetService;
import mail.utils.TimeUtils;

/**
 * 
 * @author LGX
 *
 */
@Component("haha")
public class Timer implements ApplicationContextAware, ServletContextListener {

	private ScheduleService scheduleService;
	
	private EmailSourceService emailSourceService;
	
	private TargetService targetService;
	
	private EmailService emailService;
	
	public void startSender() {
		Runnable runnable = new Runnable() {
			public void run() {
				List<Schedule> scheduleList = scheduleService.list(null);
				for (Schedule schedule : scheduleList) {
					String lastsendtime = schedule.getLastsendtime();
					String currentTime = TimeUtils.getCurrentTime();
					if (lastsendtime==null || lastsendtime.trim().isEmpty()) {
						//需要初始化
						schedule.setLastsendtime(currentTime);
						scheduleService.update(schedule);
						System.out.println("初始化schedule.id="+schedule.getId());
					} else {
						Integer intervaltimeMinute = schedule.getIntervaltime();
						try {
							//判断是否到时间
							long currentSeconds = TimeUtils.getSecondsFromTimeString(currentTime);
							long lastSeconds = TimeUtils.getSecondsFromTimeString(lastsendtime);
							if (Math.abs((currentSeconds-lastSeconds)-intervaltimeMinute*60) < 10) {
								//判断时间需要解决误差问题，发送邮件也可以使用线程防止阻塞
								//到时间了获取信息封装邮件发送,发送后更新
								Target target = targetService.findById(schedule.getTargetid());
								MyEmailFormat myEmailFormat = new MyEmailFormat();
								myEmailFormat.setTarget(target.getEmail());
								myEmailFormat.setEmailSourceid(schedule.getSourceid());
								myEmailFormat.setTitle(schedule.getDescription());
								myEmailFormat.setContent(schedule.getSendcontent());
								myEmailFormat.setFujian(null);
								emailService.send(myEmailFormat);
								System.out.println("发送了schedule.id="+schedule.getId());
								schedule.setLastsendtime(TimeUtils.getCurrentTime());
								scheduleService.update(schedule);
							} else {
								//没到时间不发送
								System.out.println("没到时间schedule.id="+schedule.getId());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println();
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		
		//延迟5秒开始执行,每30秒(1分钟)次执行1次
		service.scheduleAtFixedRate(runnable, 30, 60, TimeUnit.SECONDS);
	}

	//静态共享对象,储存缓存,让spring启动时和servlet容器启动时的对象共有,用于管理bean等
	private static ApplicationContext applicationContext;
	
	//spring初始化时获取applicationContext
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//servlet容器启动时获初始化
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		scheduleService = (ScheduleService) applicationContext.getBean("scheduleService");
		emailSourceService = (EmailSourceService) applicationContext.getBean("mailSourceService");
		targetService = (TargetService) applicationContext.getBean("targetService");
		emailService = (EmailService) applicationContext.getBean("emailService");
		scheduleService.deleteTime();
		startSender();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
