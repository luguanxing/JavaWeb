package web.servlet;

import constant.Constant;
import domain.Index;
import domain.Project;
import service.IndexService;
import service.ProjectService;
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
			
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			List<Project> projects = service.findTheLastest(Constant.PROJECT_INDEX_COUNT);
			
			if (projects == null || projects.isEmpty())
				throw new Exception("查询项目不存在");
			//设置数据并转发
			request.setAttribute("projects", projects);
			return "/jsp/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取主页信息失败");
			return "/msg.jsp";
		}
	}

	public String readIndexContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IndexService service = (IndexService) BeanFactory.getBean("IndexService");
			Index index = service.getIndexContent();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(index.getContent());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取主页内容失败");
			return "/msg.jsp";
		}
	}
	
}
