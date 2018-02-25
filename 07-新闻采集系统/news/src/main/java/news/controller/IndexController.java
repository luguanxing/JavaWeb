package news.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import news.entity.New;
import news.service.NewService;


@Controller
public class IndexController {
	
	@Value("${INDEX_LATEST_NEWS_COUNT}")
	private Integer INDEX_LATEST_NEWS_COUNT;
	
	@Value("${INDEX_HOTEST_NEWS_COUNT}")
	private Integer INDEX_HOTEST_NEWS_COUNT;
	
	@Value("${LATEST_NEWS_COUNT}")
	private Integer LATEST_NEWS_COUNT;
	
	@Value("${HOTEST_NEWS_COUNT}")
	private Integer HOTEST_NEWS_COUNT;
	
	@Autowired
	private NewService newService;

	@RequestMapping("/login")
	public String loginUI() {
		return "login";
	}
	
	//主页，展示出网易当前新闻排行的新闻(按评论数)和历史评论数排行
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		List<New> latestNews = newService.getNewsByCrawlerDate(0, INDEX_LATEST_NEWS_COUNT);
		//首页的当前新闻应该按评论排序(仿网易评论)
		Collections.sort(latestNews, new Comparator<New>() {
			@Override
			public int compare(New n1, New n2) {
				return n2.getCommentCount() - n1.getCommentCount();
			}
		});
		List<New> hotestNews = newService.getHotNews(0, INDEX_HOTEST_NEWS_COUNT);
		mav.addObject("latestNews", latestNews);
		mav.addObject("hotestNews", hotestNews);
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/latest")
	public ModelAndView latest() {
		return latest("1");
	}

	//按发布日期选择
	@RequestMapping("/latest/{page}")
	public ModelAndView latest(@PathVariable("page") String page) {
		// 获取第page页新闻
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
			pageNum = 1;
		}
		ModelAndView mav = new ModelAndView();
		Integer newsCount = newService.getNewsCount();
		Integer totalPages = (newsCount % LATEST_NEWS_COUNT == 0) ? (newsCount / LATEST_NEWS_COUNT)
				: (newsCount / LATEST_NEWS_COUNT) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<New> latestNews = newService.getNewsByPublishDateAndSrc(pageNum, LATEST_NEWS_COUNT);	//先保证页数正确才能查news
		mav.addObject("latestNews", latestNews);
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", LATEST_NEWS_COUNT);
		mav.setViewName("latest");
		return mav;
	}
	
	@RequestMapping("/hotest")
	public ModelAndView hotest() {
		return hotest("1");
	}

	//按历史最热门选择
	@RequestMapping("/hotest/{page}")
	public ModelAndView hotest(@PathVariable("page") String page) {
		// 获取第page页新闻
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
			pageNum = 1;
		}
		ModelAndView mav = new ModelAndView();
		Integer newsCount = newService.getNewsCount();
		Integer totalPages = (newsCount % HOTEST_NEWS_COUNT == 0) ? (newsCount / HOTEST_NEWS_COUNT)
				: (newsCount / HOTEST_NEWS_COUNT) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<New> hotestNews = newService.getNewsByCommentCount(pageNum, HOTEST_NEWS_COUNT);	//先保证页数正确才能查news
		mav.addObject("hotestNews", hotestNews);
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", HOTEST_NEWS_COUNT);
		mav.setViewName("hotest");
		return mav;
	}

}
