package cms.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import cms.entity.Link;
import cms.entity.PageBean;
import cms.service.LinkService;
import cms.service.impl.InitComponent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 友情链接后台管理Controller层
 * @author user
 *
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

	@Resource
	private LinkService linkService;
	
	@Resource
	private InitComponent initComponent;
	
	/**
	 * 根据条件分页查询帖子友情链接
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize) {
		PageBean pageBean = new PageBean(page, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Link> linkList = linkService.list(map);
		Long total = linkService.getTotal(map);
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(linkList);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	/**
	 * 添加或者修改帖子友情链接
	 * @param link
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Link link, HttpServletRequest request) throws Exception{
		int result = 0;
		if (link.getId() == null) { // 添加
			result = linkService.add(link);
		} else { // 修改
			result = linkService.update(link);
		}
		JSONObject json = new JSONObject();
		if (result > 0) {
			initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	/**
	 * 删除友情链接
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String ids) {
		String[] idsStr = ids.split(",");
		JSONObject json = new JSONObject();
		for (int i = 0; i < idsStr.length; i++) {
			linkService.delete(Integer.parseInt(idsStr[i]));
		}
		initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
		json.put("success", true);
		return json.toString();
	}
}
