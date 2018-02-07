package blog.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import blog.entity.Blogtype;
import blog.entity.PageBean;
import blog.service.BlogtypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/blogtype")
public class AdminBlogtypeController {

	@Autowired
	private BlogtypeService blogtypeService;
	
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize) {
		PageBean pageBean = new PageBean(page, pageSize);
		List<Blogtype> blogtypes = blogtypeService.getBlogtypes(page, pageSize);
		Integer total = blogtypeService.getBlogtypesBySortNo().size();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(blogtypes);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Blogtype blogtype) {
		Boolean result = blogtypeService.addBlogtype(blogtype);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(Blogtype blogtype) {
		Boolean result = blogtypeService.updateBlogtype(blogtype);
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
			//注意deleteBlogtype中若发现有blog的typeId为该id则不能删除
			result = result & blogtypeService.deleteBlogtype(Integer.parseInt(idsStr[i]));
		}
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
