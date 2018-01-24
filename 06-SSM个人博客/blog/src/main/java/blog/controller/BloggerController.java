package blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import blog.entity.Blogger;
import blog.service.BloggerService;
import blog.utils.Md5Utils;

/**
 * 博主前台展示controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Autowired
	private BloggerService bloggerService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(Blogger blogger, HttpServletRequest request) {
		//管理员登录后台
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUsername(), Md5Utils.md5(blogger.getPassword(),  Md5Utils.SALT));
			subject.login(token);
			if (subject.isAuthenticated()) {
				//认证成功设置session
				subject.getSession().setAttribute("currentUser", blogger);
			}
			return "redirect:/admin/main.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorInfo", "用户名或密码错误");
			return "login";
		}
	}
	
}
