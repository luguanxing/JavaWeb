package mail.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mail.entity.Manager;
import mail.service.ManagerService;
import mail.utils.Md5Utils;

/**
 * 管理员Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin/manager")
public class ManagerAdminController {

	@Autowired
	private ManagerService managerService;
	
	/**
	 * 修改密码
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("modifyPassword")
	@ResponseBody
	public String modifyPassword(String newPassword) {
		Manager manager = new Manager();
		manager.setPassword(Md5Utils.md5(newPassword, Md5Utils.SALT));
		int total = managerService.update(manager);
		JSONObject json = new JSONObject();
		if (total > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout() throws Exception {
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
	
}
