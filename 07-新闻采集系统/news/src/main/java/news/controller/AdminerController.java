package news.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import news.entity.Adminer;
import news.service.AdminerService;
import news.utils.Md5Utils;

/**
 * 博主前台controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/adminer")
public class AdminerController {

	@Autowired
	private AdminerService AdminerService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(String captcha, Adminer Adminer, HttpServletRequest request) {
		//管理员登录后台
		try {
			Subject subject = SecurityUtils.getSubject();
			if (!captcha.equals(subject.getSession().getAttribute("captcha"))) {
				request.setAttribute("errorInfo", "验证码错误");
				return "login";
			}
			UsernamePasswordToken token = new UsernamePasswordToken(Adminer.getUsername(), Md5Utils.md5(Adminer.getPassword(),  Md5Utils.SALT));
			subject.login(token);
			if (subject.isAuthenticated()) {
				//认证成功设置session
				subject.getSession().setAttribute("currentUser", Adminer);
			}
			return "redirect:/admin/main.html";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorInfo", "用户名或密码错误");
			return "login";
		}
	}
	
}
