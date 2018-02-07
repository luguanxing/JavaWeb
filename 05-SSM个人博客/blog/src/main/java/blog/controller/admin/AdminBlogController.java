package blog.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import blog.entity.Blog;
import blog.entity.PageBean;
import blog.service.BlogService;
import blog.service.BlogtypeService;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {

	@Autowired
	private BlogtypeService blogtypeService;
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Blog blog, String releaseDateInput) {
		//需要做一些属性的转换
		if (StringUtils.isEmpty(blog.getImage())) {
			blog.setImage(null);
		}
		Date releaseDate = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			releaseDate = sdf.parse(releaseDateInput);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		blog.setReleaseDate(releaseDate);
		Boolean result = blogService.addBlog(blog);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize) {
		PageBean pageBean = new PageBean(page, pageSize);
		List<Blog> blogs = blogService.getBlogs(page, pageSize);
		Integer total = blogService.getBlogsCount();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(blogs);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(Blog blog, String releaseDateInput) {
		//需要做一些属性的转换
		if (StringUtils.isEmpty(blog.getImage())) {
			blog.setImage(null);
		}
		Date releaseDate = new Date();
		try {
			//新增时获取的日期格式带秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			releaseDate = sdf.parse(releaseDateInput);	
		} catch (Exception e) {
			try {
				//修改时easyui控件获取的日期格式不带秒
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				releaseDate = sdf.parse(releaseDateInput);	
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		blog.setReleaseDate(releaseDate);
		Boolean result = blogService.updateBlog(blog);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		String[] idsStr = ids.split(",");
		Boolean result = true;
		for (int i = 0; i < idsStr.length; i++) {
			result = result & blogService.deleteBlog(Integer.parseInt(idsStr[i]));
		}
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
