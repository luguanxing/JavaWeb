package blog.controller.admin;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import blog.entity.Blog;
import blog.entity.Blogger;
import blog.entity.Blogtype;
import blog.service.BlogService;
import blog.service.BloggerService;
import blog.service.BlogtypeService;
import net.sf.json.JSONObject;

//用于页面转发和数据回显
@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private BlogtypeService blogtypeService;

	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/main")
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login.html";
	}
	
	@RequestMapping("/addBlog")
	public ModelAndView addBlog() {
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		ModelAndView mav = new ModelAndView();
		mav.addObject("blogtypes", blogtypes);
		mav.setViewName("admin/addBlog");
		return mav;
	}
	
	@RequestMapping("/blogManage")
	public ModelAndView blogManage() {
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		ModelAndView mav = new ModelAndView();
		mav.addObject("blogtypes", blogtypes);
		mav.setViewName("admin/blogManage");
		return mav;
	}
	
	@RequestMapping("/editBlog")
	public ModelAndView editBlog(Integer id) {
		Blog blog = blogService.getBlogById(id);
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		ModelAndView mav = new ModelAndView();
		mav.addObject("blog", blog);
		mav.addObject("blogtypes", blogtypes);
		mav.setViewName("admin/editBlog");
		return mav;
	}
	
	@RequestMapping("/bloggerManage")
	public ModelAndView bloggerManage() {
		Blogger blogger = bloggerService.getFirstBlogger();
		ModelAndView mav = new ModelAndView();
		mav.addObject("blogger", blogger);
		mav.setViewName("admin/bloggerManage");
		return mav;
	}
	
	@RequestMapping("/linkManage")
	public String linkManage() {
		return "admin/linkManage";
	}
	
	
	@RequestMapping("/commentManage")
	public String commentManage() {
		return "admin/commentManage";
	}
	
	@RequestMapping("/blogtypeManage")
	public String blogtypeManage() {
		return "admin/blogtypeManage";
	}

	@RequestMapping("/import")
	@ResponseBody
	public String importData() {
		Boolean result = blogService.importAllBlogs();
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
