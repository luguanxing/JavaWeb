package blog.controller;

import java.util.List;

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

/**
 * 主页的controller
 * 
 * @author Administrator
 *
 */
@Controller
public class IndexController {

	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private LinkService linkService;

	@Autowired
	private BlogtypeService blogtypeService;

	@Autowired
	private BlogService blogService;

	@Value("${NEWEST_BLOGS_NUM}")
	private Integer NEWEST_BLOGS_NUM;

	@RequestMapping("/index")
	public ModelAndView index() {
		// 首页，获取第一页博客
		return indexPages("1");
	}

	@RequestMapping("/index/{page}")
	public ModelAndView indexPages(@PathVariable("page") String page) {
		// 获取第page页博客
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
		Integer blogsCount = blogService.getBlogsCount();
		Integer totalPages = (blogsCount % NEWEST_BLOGS_NUM == 0) ? (blogsCount / NEWEST_BLOGS_NUM)
				: (blogsCount / NEWEST_BLOGS_NUM) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<Blog> blogs = blogService.getBlogs(pageNum, NEWEST_BLOGS_NUM);	//先保证页数正确才能查blogs
		mav.addObject("blogger", blogger);
		mav.addObject("links", links);
		mav.addObject("blogtypes", blogtypes);
		mav.addObject("priorityBlogs", priorityBlogs);
		mav.addObject("blogs", blogs);
		mav.addObject("indexTitle", "最新博客");
		mav.addObject("htmlTitle", "主页");
		mav.addObject("pageUrl", "index");
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", NEWEST_BLOGS_NUM);
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping("/login")
	public String loginUI() {
		return "login";
	}

}
