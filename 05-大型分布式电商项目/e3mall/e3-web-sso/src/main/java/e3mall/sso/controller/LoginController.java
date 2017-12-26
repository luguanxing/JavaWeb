package e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.CookieUtils;

import e3mall.common.utils.E3Result;
import e3mall.sso.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	@RequestMapping("/page/login")
	public String showLoginUI() {
		return "login";
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public E3Result userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		E3Result e3Result = loginService.userLogin(username, password);
		//登录成功则写入cookie
		if (e3Result.getStatus() == 200) {
			String token = e3Result.getData().toString();
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}
		return e3Result;
	}
	
}
