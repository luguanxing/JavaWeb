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

			//请求转发到order_info.jsp
			request.setAttribute("bean", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "订单保存出错");
			return "/jsp/msg.jsp";
		}
	}
	
}
