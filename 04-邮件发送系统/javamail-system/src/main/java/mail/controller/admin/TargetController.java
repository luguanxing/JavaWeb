package mail.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import mail.entity.PageBean;
import mail.entity.Target;
import mail.service.TargetService;
import mail.service.impl.InitComponent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 目标后台管理Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin/target")
public class TargetController {

	@Autowired
	private TargetService targetService;
	
	@Autowired
	private InitComponent initComponent;
	
	/**
	 * 根据条件分页查询目标
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
		List<Target> targetList = targetService.list(map);
		Long total = targetService.getTotal(map);
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(targetList);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	/**
	 * 添加或者修改目标
	 * @param link
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Target target, HttpServletRequest request) throws Exception{
		int result = 0;
		if (target.getId() == null) { // 添加
			result = targetService.add(target);
		} else { // 修改
			result = targetService.update(target);
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
	 * 删除目标
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
			targetService.delete(Integer.parseInt(idsStr[i]));
		}
		initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
		json.put("success", true);
		return json.toString();
	}
	
}
