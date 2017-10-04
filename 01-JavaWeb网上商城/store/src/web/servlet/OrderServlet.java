package web.servlet;

import constant.Constant;
import domain.*;
import service.OrderService;
import utils.BeanFactory;
import utils.UUIDUtils;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/28.
 * 订单模块
 */
public class OrderServlet extends BaseServlet {
	
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取user
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				request.setAttribute("msg", "请先登录");
				return "/jsp/msg.jsp";
			}

			//封装订单对象
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if (cart == null || cart.getCartItems().isEmpty()) {
				request.setAttribute("msg", "请先添加购物项");
				return "/jsp/msg.jsp";
			}

			Order order = new Order();
			order.setOid(UUIDUtils.getId());
			order.setOrdertime(new Date());
			order.setTotal(cart.getTotal());
			order.setState(Constant.ORDER_UNPAID);
			order.setUser(user);
			for (CartItem cartItem : cart.getCartItems()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setItemid(UUIDUtils.getId());
				orderItem.setCount(cartItem.getCount());
				orderItem.setSubtotal(cartItem.getSubtotal());
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setOrder(order);
				order.getItems().add(orderItem);
			}

			//调用OrderService完成保存操作
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			service.save(order);
			cart.clearCart();

			//请求转发到order_info.jsp
			request.setAttribute("bean", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "订单保存出错");
			return "/jsp/msg.jsp";
		}
	}
	
	public String findMyOrderByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取pageNumber 设置pageSize 获取userid
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				//设置pagesize，默认1
			}
			int pageSize = Constant.ORDER_PAGE_COUNT;
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				request.setAttribute("msg", "请先登录");
				return "/jsp/msg.jsp";
			}

			//调用service获取当前页所有数据
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> bean = service.findMyOrderByPage(pageNumber, pageSize, user.getUid());

			//将pagebean放入request域中,请求转发到order_list.jsp
			request.setAttribute("bean", bean);

			return "/jsp/order_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取订单失败");
			return "/jsp/msg.jsp";
		}
	}
	
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取oid
			String oid = request.getParameter("oid");

			//调用service查询单个订单
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			Order order = service.getById(oid);
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				request.setAttribute("msg", "请先登录");
				return "/jsp/msg.jsp";
			} else if (!service.isMyOrder(order.getOid(), user.getUid())) {
				request.setAttribute("msg", "这不是你的订单");
				return "/jsp/msg.jsp";
			}

			//请求转发
			request.setAttribute("bean", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询订单详情失败");
			return "/jsp/msg.jsp";
		}
	}
	
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取收货人信息,oid
			String address = request.getParameter("address");
			String name = request.getParameter("name");
			String telephone = request.getParameter("telephone");
			String oid = request.getParameter("oid");

			//调用serivce修改订单信息
			OrderService service = (OrderService) BeanFactory.getBean("OrderService");
			Order order = service.getById(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			order.setState(Constant.ORDER_PAID);
			service.update(order);
			response.sendRedirect(request.getContextPath() + "order?method=getById&oid="+order.getOid());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<span style='color:green>'确认订单失败</span>");
			return "/jsp/msg.jsp";
		}
	}
}
