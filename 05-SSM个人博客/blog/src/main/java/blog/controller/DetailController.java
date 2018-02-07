package blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class DetailController {
	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private LinkService linkService;

	@Autowired
	private BlogtypeService blogtypeService;

	@Autowired
	private BlogService blogService;
	
	@Value("${TYPE_BLOGS_NUM}")
	private Integer TYPE_BLOGS_NUM;
	
	@RequestMapping("/detail")
	public ModelAndView showBlogger() {
		ModelAndView mav = new ModelAndView();
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setPassword(null);	//不传输密码保护安全
		List<Link> links = linkService.getLinksBySortNo();
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		List<Blog> priorityBlogs = blogService.getPriorityBlogs();
		mav.addObject("blogger", blogger);
		mav.addObject("links", links);
		mav.addObject("blogtypes", blogtypes);
		mav.addObject("priorityBlogs", priorityBlogs);
		mav.addObject("indexTitle", "关于本站");
		mav.addObject("htmlTitle", "关于本站");
		mav.setViewName("detail");
		return mav;
	}
}
