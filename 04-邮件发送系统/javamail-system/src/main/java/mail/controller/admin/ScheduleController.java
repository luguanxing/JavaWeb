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
import mail.entity.Schedule;
import mail.entity.Target;
import mail.service.EmailSourceService;
import mail.service.ScheduleService;
import mail.service.TargetService;
import mail.service.impl.InitComponent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 计划后台管理Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private EmailSourceService emailSourceService;
	
	@Autowired
	private TargetService targetService;
	
	/**
	 * 根据条件分页查询计划
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
		List<Schedule> scheduleList = scheduleService.list(map);
		Long total = scheduleService.getTotal(map);
		for (Schedule schedule : scheduleList) {
			//由于在mapper关联两张表不方便，所以手动封装，也可以在service里面用dao封装
			EmailSource emailSource = emailSourceService.findById(schedule.getSourceid());
			Target target = targetService.findById(schedule.getTargetid());
			schedule.setEmailSource(emailSource);
			schedule.setTarget(target);
		}
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(scheduleList);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	/**
	 * 添加或者修改计划
	 * @param link
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Schedule schedule, HttpServletRequest request) throws Exception{
		int result = 0;
		if (schedule.getId() == null) { // 添加
			result = scheduleService.add(schedule);
		} else { // 修改
			result = scheduleService.update(schedule);
		}
		JSONObject json = new JSONObject();
		if (result > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	/**
	 * 删除计划
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
			scheduleService.delete(Integer.parseInt(idsStr[i]));
		}
		json.put("success", true);
		return json.toString();
	}

}
