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

import mail.entity.EmailSource;
import mail.entity.PageBean;
import mail.service.EmailSourceService;
import mail.service.impl.InitComponent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 邮件源后台管理Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin/emailsource")
public class EmailSourceController {

	@Autowired
	private EmailSourceService emailSourceService;
	
	@Autowired
	private InitComponent initComponent;
	
	/**
	 * 根据条件分页查询邮件源
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
		List<EmailSource> emailSourceList = emailSourceService.list(map);
		Long total = emailSourceService.getTotal(map);
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(emailSourceList);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	/**
	 * 添加或者修改邮件源
	 * @param link
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(EmailSource emailSource, HttpServletRequest request) throws Exception{
		int result = 0;
		if (emailSource.getId() == null) { // 添加
			result = emailSourceService.add(emailSource);
		} else { // 修改
			result = emailSourceService.update(emailSource);
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
	 * 删除邮件源
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
			emailSourceService.delete(Integer.parseInt(idsStr[i]));
		}
		initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
		json.put("success", true);
		return json.toString();
	}
	
}
