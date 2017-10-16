package web.servlet;

import domain.Admin;
import service.AdminService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/15.
 */
public class AdminLoginServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//去登录界面
		return "/admin/adminLoginUI.jsp";
	}

	public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//登录的方法
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String captcha = (String) request.getSession().getAttribute("captcha");
			String inputCaptcha = request.getParameter("inputCaptcha");
			if (!captcha.equals(inputCaptcha)) {
				request.setAttribute("msg", "验证码错误");
				return "/admin/adminLoginUI.jsp";
			}
			AdminService service = (AdminService) BeanFactory.getBean("AdminService");
			Boolean isAdmin = service.isAdmin(username, password);
			if (isAdmin) {
				request.getSession().setAttribute("admin", "admin");
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				request.setAttribute("msg", "帐号/密码错误");
				return "/admin/adminLoginUI.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "管理员登录失败");
			return "/msg.jsp";
		}
		return null;
	}
	
}
