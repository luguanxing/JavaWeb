package blog.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import blog.entity.Blog;
import blog.entity.Blogger;
import blog.entity.Blogtype;
import blog.entity.Link;
import blog.service.BlogService;
import blog.service.BloggerService;
import blog.service.BlogtypeService;
import blog.service.LinkService;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private LinkService linkService;

	@Autowired
	private BlogtypeService blogtypeService;

	@Autowired
	private BlogService blogService;

	@Value("${SEARCH_BLOGS_NUM}")
	private Integer SEARCH_BLOGS_NUM;
	
	@RequestMapping("/{keyword}")
	public ModelAndView showBlogs(@PathVariable("keyword") String keyword) {
		return showBlogsPages(keyword, "1");
	}
	
	@RequestMapping("/{keyword}/{page}")
	public ModelAndView showBlogsPages(@PathVariable("keyword") String keyword, @PathVariable("page") String page) {
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
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setPassword(null);	//不传输密码保护安全
		List<Link> links = linkService.getLinksBySortNo();
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		List<Blog> priorityBlogs = blogService.getPriorityBlogs();
		Integer blogsCount = blogService.getSearchBlogsCount(keyword);
		Integer totalPages = (blogsCount % SEARCH_BLOGS_NUM == 0) ? (blogsCount / SEARCH_BLOGS_NUM)
				: (blogsCount / SEARCH_BLOGS_NUM) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<Blog> blogs = blogService.getSearchBlogs(keyword, (pageNum-1)*SEARCH_BLOGS_NUM, SEARCH_BLOGS_NUM);	//先保证页数正确才能查blogs
		mav.addObject("blogger", blogger);
		mav.addObject("links", links);
		mav.addObject("blogtypes", blogtypes);
		mav.addObject("priorityBlogs", priorityBlogs);
		mav.addObject("blogs", blogs);
		mav.addObject("indexTitle", "搜索关键字 : " + keyword);
		mav.addObject("htmlTitle", "搜索");
		mav.addObject("pageUrl", "search/"+keyword);
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", SEARCH_BLOGS_NUM);
		mav.setViewName("index");
		return mav;
	}
	
}
