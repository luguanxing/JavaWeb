package web.servlet;

import domain.Category;
import service.CategoryService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 * 主页模块
 * 
 */
public class IndexServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("index", 1);
			return "WEB-INF/jsp/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取主页信息失败");
			return "/msg.jsp";
		}
	}
	
}
