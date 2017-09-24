package web.servlet;

import service.CategoryService;
import service.impl.CategoryServiceImpl;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/24.
 * 前台分类模块
 */
public class CategoryServlet extends BaseServlet {
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//查询所有分类
		try {
			//1.调用service查询分类返回json
			CategoryService service = new CategoryServiceImpl();
			String value = service.findAll();

			//2.写回数据
			response.setContentType("text/html;charset=utf-8");	//设置响应编码：不能放在过滤器放行后再写否则太迟，所以写在内部
			response.getWriter().write(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
