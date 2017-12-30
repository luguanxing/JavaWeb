package e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import e3mall.cart.service.CartService;
import e3mall.common.utils.E3Result;
import e3mall.order.pojo.OrderInfo;
import e3mall.order.service.OrderService;
import e3mall.pojo.TbItem;
import e3mall.pojo.TbUser;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		//根据id取用户收获列表,收货方式(静态数据实现)
		List<TbItem> cartList = cartService.getCartList(user.getId());
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		E3Result e3Result = orderService.createOrder(orderInfo);
		if (e3Result.getStatus() == 0) {
			cartService.clearCartItem(user.getId());
		}
		request.setAttribute("orderId", e3Result.getData());
		request.setAttribute("payment", orderInfo.getPayment());
		return "success";
	}
	
}
