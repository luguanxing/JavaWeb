package blog.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import blog.entity.Link;
import blog.entity.PageBean;
import blog.service.LinkService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/link")
public class AdminLinkController {

	@Autowired
	private LinkService linkService;
	
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize) {
		PageBean pageBean = new PageBean(page, pageSize);
		List<Link> linkList = linkService.getLinks(page, pageSize);
		Integer total = linkService.getLinksCount();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(linkList);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Link link) {
		Boolean result = linkService.addLink(link);
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
	public String update(Link link) {
		Boolean result = linkService.updateLink(link);
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
			result = result & linkService.deleteLink(Integer.parseInt(idsStr[i]));
		}
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
