package web.servlet;

import domain.Category;
import service.CategoryService;
import utils.BeanFactory;
import utils.UUIDUtils;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 * 后台分类管理模块
 */
public class AdminCategoryServlet extends BaseServlet {
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用serivce获取所有分类
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = service.findList();
			
			//将返回值放入到request域中
			request.setAttribute("list", list);
			
			//请求转发
			return "/admin/category/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台查询分类失败");
			return "jsp/msg.jsp";
		}
	}
	
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Category category = new Category();
			category.setCid(UUIDUtils.getId());
			String cname = request.getParameter("cname");
			if (cname == null || cname.isEmpty())
				throw new Exception("命名错误");
			category.setCname(cname);

			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			service.add(category);

			response.sendRedirect(request.getContextPath() + "adminCategory?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加分类失败");
			return "jsp/msg.jsp"; 
		}
		return null;
	}
	
}
