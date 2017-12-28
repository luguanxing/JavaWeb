package e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;

import e3mall.common.utils.E3Result;
import e3mall.pojo.TbUser;
import e3mall.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor {

	private static final String TbUser = null;
	@Autowired
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//执行handler前处理,返回true放行,false拦截
		String token = CookieUtils.getCookieValue(request, "token");
		if (StringUtils.isBlank(token))
			return true;
		E3Result e3Result = tokenService.getUserByToken(token);
		if (e3Result.getStatus() != 200)
			return true;
		//有用户信息则取
		TbUser user = (TbUser)e3Result.getData();
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//执行handler后,返回modelAndView之前
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//完成处理,返回modelAndView之后,可以处理异常
		
	}

}
