package blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/blogtype")
public class BlogtypeController {

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

	@RequestMapping("/{typeId}")
	public ModelAndView showBlogtype(@PathVariable("typeId") String typeId) {
		// 首页，获取第一页typeId博客
		return showBlogtypePages(typeId, "1");
	}

	@RequestMapping("/{typeIdOrTypeName}/{page}")
	public ModelAndView showBlogtypePages(@PathVariable("typeIdOrTypeName") String typeIdOrTypeName, @PathVariable("page") String page) {
		// 获取第page页typeId博客
		Integer typeIdNum;
		Integer pageNum; 
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
			pageNum = 1;
		}
		Blogtype blogtype = blogtypeService.getBlogtypeByTypeName(typeIdOrTypeName);
		if (blogtype == null) {	//判断有没有该名字或类别id的博客
			//两个try catch不能写在一起，要单独写，否则后面受前面影响
			try {
				typeIdNum = Integer.parseInt(typeIdOrTypeName);
			} catch (Exception e) {
				e.printStackTrace();
				typeIdNum = -1;	//错误的typeId类型会查不到数据
			}
			blogtype = blogtypeService.getBlogtypeById(typeIdNum);
			if (blogtype == null) {
				return new ModelAndView("redirect:/index.html");
			}
		} else {
			typeIdNum = blogtype.getId();
		}
		ModelAndView mav = new ModelAndView();
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setPassword(null);	//不传输密码保护安全
		List<Link> links = linkService.getLinksBySortNo();
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		List<Blog> priorityBlogs = blogService.getPriorityBlogs();
		Integer blogsCount = blogService.getBlogsCount(typeIdNum);
		Integer totalPages = (blogsCount % TYPE_BLOGS_NUM == 0) ? (blogsCount / TYPE_BLOGS_NUM)
				: (blogsCount / TYPE_BLOGS_NUM) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<Blog> blogs = blogService.getBlogsBytypeId(typeIdNum, pageNum, TYPE_BLOGS_NUM);	//先保证页数正确才能查blogs
		mav.addObject("blogger", blogger);
		mav.addObject("links", links);
		mav.addObject("blogtypes", blogtypes);
		mav.addObject("priorityBlogs", priorityBlogs);
		mav.addObject("blogs", blogs);
		mav.addObject("indexTitle", "博客类型 : " + blogtype.getTypeName());
		mav.addObject("htmlTitle", blogtype.getTypeName());
		mav.addObject("pageUrl", "blogtype/" + blogtype.getTypeName());
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", TYPE_BLOGS_NUM);
		mav.setViewName("index");
		return mav;
	}

}
