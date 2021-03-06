package web.servlet.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/9/20.
 * 通用的Servlet类
 */
public class BaseServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取方法名称(注意在没有method参数时默认设其为index)
			String methodName =  request.getParameter("method");
			if (methodName == null) {
				methodName = "index";
			}

			//2.通过反射获取BaseServlet方法对象(注意在找不到method对应方法时转到index)
			Method method;
			try {
				method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			} catch (NoSuchMethodException e) {
				method = this.getClass().getMethod("index", HttpServletRequest.class, HttpServletResponse.class);
			}
			
			//3.让方法执行，接收返回值
			String retPath = (String) method.invoke(this, request, response);
			
			//4.判断返回值，若不为空则转发
			if (retPath != null) {
				request.getRequestDispatcher(retPath).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println("没有找到对应的方法");
		return null;
	}
	
}
