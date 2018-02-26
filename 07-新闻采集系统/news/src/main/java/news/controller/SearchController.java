package news.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import news.entity.New;
import news.service.NewService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Value("${SEARCH_NEWS_COUNT}")
	private Integer SEARCH_NEWS_COUNT;
	
	@Autowired
	private NewService newService;
	
	@RequestMapping("/{keyword}")
	public ModelAndView showNews(@PathVariable("keyword") String keyword) {
		return showNews(keyword, "1");
	}

	@RequestMapping("/{keyword}/{page}")
	public ModelAndView showNews(@PathVariable("keyword") String keyword, @PathVariable("page") String page) {
		//搜索空串
		if (StringUtils.isEmpty(keyword)) {
			return new ModelAndView("redirect:/index.html");
		}
		// 获取第page页搜索博客
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
			pageNum = 1;
		}
		ModelAndView mav = new ModelAndView();
		Integer newsCount = newService.getSearchNewsCount(keyword);
		Integer totalPages = (newsCount % SEARCH_NEWS_COUNT == 0) ? (newsCount / SEARCH_NEWS_COUNT)
				: (newsCount / SEARCH_NEWS_COUNT) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<New> searchNews = newService.getSearchNews(keyword, (pageNum-1)*SEARCH_NEWS_COUNT, SEARCH_NEWS_COUNT);	//先保证页数正确才能查news
		mav.addObject("keyword", keyword);
		mav.addObject("searchNews", searchNews);
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", SEARCH_NEWS_COUNT);
		mav.setViewName("search");
		return mav;
	}
	
	
}
