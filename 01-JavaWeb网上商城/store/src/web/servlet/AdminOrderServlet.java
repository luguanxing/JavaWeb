package web.servlet;

import domain.Order;
import domain.OrderItem;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import service.OrderService;
import utils.BeanFactory;
import utils.JsonUtil;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/10/4.
 * 后台订单模块
 */
public class AdminOrderServlet extends BaseServlet {
	
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取state
			String state = request.getParameter("state");

			//调用service查询
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			List<Order> list = service.findAllByState(state);

			//转发
			request.setAttribute("list", list);
			return "/admin/order/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取订单失败");
			return "/jsp/msg.jsp";
		}
	}
	
	public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=utf-8");
			String oid = request.getParameter("oid");
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			Order order = service.getById(oid);
			if (order != null) {
				List<OrderItem> list = order.getItems();
				if (list != null && list.size() > 0) {
					//排除多余字段
					JsonConfig config = JsonUtil.configJson(new String[]{"order", "subtotal", "itemid", "pdate",
							"is_hot", "pdesc", "pflag", "market_price", "class", "shop_price", "pid", "category"});
					response.getWriter().println(JSONArray.fromObject(list, config));
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取订单详情失败");
			return "/jsp/msg.jsp";
		}
	}
	
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取oid, toState
			String oid = request.getParameter("oid");
			String toState = request.getParameter("toState");

			//调用serivce获取订单
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			Order order = service.getById(oid);

			//设置状态更新
			order.setState(Integer.parseInt(toState));
			service.update(order);

			//重定向
			response.sendRedirect(request.getContextPath() + "/adminOrder?method=findAllByState&state=" + toState);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "修改订单状态失败");
			return "/jsp/msg.jsp";
		}
	}
}
