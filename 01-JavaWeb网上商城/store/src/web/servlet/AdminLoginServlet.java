package web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/4.
 */
public class AdminLoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String captcha = (String)req.getSession().getAttribute("captcha");
		String inputCaptcha = req.getParameter("inputCaptcha");
		if (!captcha.equals(inputCaptcha)) {
			req.setAttribute("msg", "验证码错误");
			req.getRequestDispatcher("/admin.jsp").forward(req, resp);
			return;
		}
		
		if ("admin".equals(username) && "admin".equals(password)) {
			req.getSession().setAttribute("admin", "admin");
			resp.sendRedirect(req.getContextPath() + "/admin/home.jsp");
		} else {
			req.setAttribute("msg", "帐号/密码错误");
			req.getRequestDispatcher("/admin.jsp").forward(req, resp);
		}
		
	}
}
