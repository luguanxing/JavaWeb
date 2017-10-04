package web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/1.
 */
public class AdminFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		//强转
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		//逻辑
		Object admin = request.getSession().getAttribute("admin");
		if (admin == null) {
			request.setAttribute("msg", "未登录");
			request.getRequestDispatcher("/admin.jsp").forward(request, response);
		} else {
			//放行
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}
	
}
