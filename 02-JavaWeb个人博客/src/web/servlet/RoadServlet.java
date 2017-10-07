package web.servlet;

import service.RoadService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/7.
 * 路线模块
 */
public class RoadServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("index", 2);
			RoadService service = (RoadService) BeanFactory.getBean("RoadService");
			Map map = service.getAllByMap();
			request.setAttribute("map", map);
			return "WEB-INF/jsp/road.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取路线信息失败");
			return "/msg.jsp";
		}
	}
	
}
