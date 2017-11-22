package cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cms.entity.Manager;
import cms.service.ManagerService;
import cms.utils.Md5Utils;

/**
 * 管理员Contoller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/management")
public class ManagerContoller {

	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(value="/login", produces="application/json;charset=utf-8")
	@ResponseBody
	public String login(Manager manager, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(manager.getUsername(), Md5Utils.md5(manager.getPassword(), Md5Utils.SALT));
		JSONObject json = new JSONObject();
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				//认证成功设置session
				request.getSession().setAttribute("currentUser", manager);
			}
			json.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", false);
			json.put("errMsg", "用户名或密码错误");
			e.printStackTrace();
		}
		return json.toString();
	}
	
}
