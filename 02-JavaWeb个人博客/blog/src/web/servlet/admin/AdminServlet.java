package web.servlet.admin;

import domain.Index;
import service.IndexService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/15.
 */
public class AdminServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/home.jsp";
	}

	public String editIndexUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IndexService service = (IndexService) BeanFactory.getBean("IndexService");
			Index index = service.getIndexContent();
			request.setAttribute("index", index);
			return "/admin/index/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取主页内容失败");
			return "/msg.jsp";
		}
	}

	public String editIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String content = request.getParameter("content");
			IndexService service = (IndexService) BeanFactory.getBean("IndexService");
			Index index = new Index();
			index.setContent(content);
			service.update(index);
			request.setAttribute("index", index);
			return "/index";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "修改主页内容失败");
			return "/msg.jsp";
		}
	}
	
}
