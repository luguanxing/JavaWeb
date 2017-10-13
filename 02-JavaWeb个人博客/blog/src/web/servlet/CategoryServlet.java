package web.servlet;

import domain.Category;
import service.CategoryService;
import utils.BeanFactory;
import utils.JsonUtil;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/7.
 * 项目分类模块
 */
public class CategoryServlet extends BaseServlet {
	
	public String getCategoryListJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService cservice = (CategoryService) BeanFactory.getBean("CategoryService");
			String clist_json = cservice.getAllByJson();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(clist_json);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取项目分类模块失败");
			return "/msg.jsp";
		}
	}
	
}
