package web.filter;

import domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/1.
 */
public class PrivilegeFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		//强转
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		//逻辑
		User user = (User) request.getSession().getAttribute("user");
		if (user ==null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
		} else {
			//放行
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}
	
}
