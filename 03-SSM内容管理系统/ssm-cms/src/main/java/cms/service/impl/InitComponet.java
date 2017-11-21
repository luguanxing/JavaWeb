package cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cms.entity.ArcType;
import cms.entity.Article;
import cms.entity.Link;
import cms.service.ArcTypeService;
import cms.service.ArticleService;
import cms.service.LinkService;

/**
 * 初始化组件
 * @author LGX
 *
 */
@Component("initComponet")
public class InitComponet implements ApplicationContextAware, ServletContextListener {

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
		LinkService linkService = (LinkService) applicationContext.getBean("linkService");
		ArcTypeService arcTypeService = (ArcTypeService) applicationContext.getBean("arcTypeService");
		ArticleService articleService = (ArticleService) applicationContext.getBean("articleService");
		
		List<Link> linkList = linkService.list(null);
		List<ArcType> arcTypeList = arcTypeService.list(null);
		List<Article> newestArticleList = articleService.getNewest();
		List<Article> recommendArticleList = articleService.getRecommend();
		List<Article> slideArticleList = articleService.getSlide();
		List<List<Article>> subArticleLists = new ArrayList();
		if (arcTypeList != null && !arcTypeList.isEmpty()) {
			for (int i = 0; i < arcTypeList.size(); i++) {
				Integer typeId = arcTypeList.get(i).getId();
				List<Article> subArticleList = articleService.getIndex(typeId);
				subArticleLists.add(subArticleList);
			}
		}
		
		application.setAttribute("linkList", linkList);
		application.setAttribute("arcTypeList", arcTypeList);
		application.setAttribute("newestArticleList", newestArticleList);
		application.setAttribute("recommendArticleList", recommendArticleList);
		application.setAttribute("slideArticleList", slideArticleList);
		application.setAttribute("subArticleLists", subArticleLists);
	}
	
}
