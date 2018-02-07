package blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/article")
public class BlogController {

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
	
	@RequestMapping("/{blogId}")
	public ModelAndView showBlogtype(@PathVariable("blogId") String blogId) {
		// 获取blogId的博客
		Integer blogIdNum;
		try {
			blogIdNum = Integer.parseInt(blogId);
		} catch(Exception e) {
			e.printStackTrace();
			blogIdNum = -1;
		}
		Blog blog = blogService.getBlogById(blogIdNum);
		if (blog == null) {	//没有该blogId的博客
			return new ModelAndView("redirect:/index.html");
		}
		ModelAndView mav = new ModelAndView();
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setPassword(null);	//不传输密码保护安全
		List<Link> links = linkService.getLinksBySortNo();
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		Blogtype blogtype = blogtypeService.getBlogtypeById(blog.getTypeId());
		List<Blog> priorityBlogs = blogService.getPriorityBlogs();
		mav.addObject("blogger", blogger);
		mav.addObject("links", links);
		mav.addObject("blogtypes", blogtypes);
		mav.addObject("priorityBlogs", priorityBlogs);
		mav.addObject("blog", blog);
		mav.addObject("blogtypeName", blogtype.getTypeName());
		mav.addObject("indexTitle", "博客 : " + blog.getTitle());
		mav.addObject("htmlTitle", blog.getTitle());
		mav.setViewName("blog");
		return mav;
	}
	
}
