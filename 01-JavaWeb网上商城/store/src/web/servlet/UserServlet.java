package web.servlet;

import constant.Constant;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.UUIDUtils;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/9/19.
 * 用户模块
 */
public class UserServlet extends BaseServlet {
	
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//转发路径，跳转到注册页面
		return "/jsp/register.jsp";
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//转发路径，跳转到登录页面
		return "/jsp/login.jsp";
	}
	
 	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户注册方法
		try {
			//1.封装对象，要手动封装uid,state，code
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			user.setUid(UUIDUtils.getId());
			user.setState(Constant.USER_IS_ACTIVE);
			
			UserService service = new UserServiceImpl();
			String checkResult = service.isRegistable(user);
			if (checkResult.equals("success")) {
				//2.调用service方法完成注册
				service.regist(user);
				//3.页面转发提示结果信息
				request.setAttribute("msg", "<span style='color:green'>恭喜，注册成功！</span>");
			} else {
				request.setAttribute("msg", "<span style='color:red'>注册失败:" + checkResult + "</span>");
				request.setAttribute("user", user);
				return "/jsp/register.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			//注册错误转发到msg.jsp
			request.setAttribute("msg", "<span style='color:red'>抱歉，注册失败！</span>");
			
		}
		return "/jsp/msg.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户登录方法
		try {
			//1.获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			//2.调用service完成登录操作
			UserService service = new UserServiceImpl();
			User user = service.login(username, password);

			//3.判断user结果生成提示信息
			if (user == null) {
				request.setAttribute("msg", "<span style='color:red'>用户名/密码错误</span>");
				return "/jsp/login.jsp";
			} else {
				if (user.getState() == Constant.USER_IS_NOT_ACTIVE) {
					request.setAttribute("msg", "<span style='color:red'>未激活</span>");
					return "/jsp/msg.jsp";
				} else {
					request.getSession().setAttribute("user", user);
					
					if (Constant.IS_SAVE_NAME.equals(request.getParameter("savename"))) {
						Cookie cookie = new Cookie("saveName", URLEncoder.encode(username, "utf-8"));
						cookie.setMaxAge(Integer.MAX_VALUE);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
					
					response.sendRedirect("/");
				}
			}
		} catch (Exception e) {
			request.setAttribute("msg", "<span style='color:red'>抱歉，登录失败！</span>");
			return "/jsp/msg.jsp";
		}
		return null;
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户退出方法
		request.getSession().invalidate();
		response.sendRedirect("/");
		return null;
	}

	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户激活方法
		try {
			//1.接收code
			String code = request.getParameter("code");

			//2.调用serivce完成激活，返回user
			UserService service = new UserServiceImpl();
			User user = service.active(code);

			//3.判断user，生成提示信息
			if (user == null) {
				request.setAttribute("msg", "<span style='color:red'>激活码对应用户不存在！</span>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//激活失败转发到msg.jsp
			request.setAttribute("msg", "<span style='color:red'>抱歉，激活失败！</span>");
		}

		request.setAttribute("msg", "<span style='color:red'>恭喜，激活成功！</span>");
		return "/jsp/msg.jsp";
	}

}
