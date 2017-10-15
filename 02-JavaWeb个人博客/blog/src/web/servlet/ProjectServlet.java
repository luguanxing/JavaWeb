package web.servlet;

import constant.Constant;
import domain.PageBean;
import domain.Project;
import service.ProjectService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/6.
 * 主页模块
 * 
 */
public class ProjectServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int pageNumber = 1;
			String type = "windows";
			
			//获取参数，注意不能写在一起否则可能获取不全
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				//设置pagesize，默认1
				pageNumber = 1;
			}
			try {
				type = request.getParameter("type");
			} catch (Exception e) {
				//设置默认类型为windows
				type = "windows";
			}
			

			//加载页面项目的数据
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			PageBean<Project> bean = service.findByPage(pageNumber, Constant.PROJECT_PAGE_COUNT, type);
			
			if (bean == null || pageNumber > bean.getTotalPage())
				throw new Exception("查询项目页不存在");
			//设置数据并转发
			request.setAttribute("pagebean", bean);
			request.setAttribute("index", 4);
			return "/jsp/project.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取项目信息失败");
			return "/msg.jsp";
		}
	}

	public String detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			if (pid == null)
				throw new Exception("查询详情不存在");

			//加载页面项目的数据
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			Project project = service.getById(pid);
			
			//添加点击量，防止刷新重复点击
			if (project == null)
				throw new Exception("项目不存在");
			String token = (String) request.getSession().getAttribute("token");
			if (token == null || !token.equals(project.getPid())) {
				request.getSession().setAttribute("token", project.getPid());
				project.setClick(project.getClick() + 1);
				service.update(project);
			}
			
			service.update(project);

			request.setAttribute("project", project);
			request.setAttribute("index", 4);
			return "/jsp/projectDetail.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取文章详情失败");
			return "/msg.jsp";
		}
	}
	
	public String readDescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			if (pid == null)
				throw new Exception("查询详情不存在");

			//加载页面项目的数据
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			Project project = service.getById(pid);


			if (project == null)
				throw new Exception("项目不存在");

			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(project.getDescription());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取文章详情失败");
			return "/msg.jsp";
		}
	}
	
}
