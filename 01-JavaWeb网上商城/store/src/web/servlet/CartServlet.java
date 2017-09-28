package web.servlet;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/26.
 * 购物车模块
 */
public class CartServlet extends BaseServlet {

	private Cart GetCartFromRequest(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	public String addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取pid count
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));

			//封装cartitem
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			Product product = service.getById(pid);
			CartItem item = new CartItem(product, count);

			//加入购物车
			Cart cart = GetCartFromRequest(request);
			cart.addToCart(item);

			response.sendRedirect("/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "加入购物车失败");
			return "jsp/msg.jsp";
		}
		return null;
	}
	
	public String removeFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取pid count
			String pid = request.getParameter("pid");

			//加入购物车
			Cart cart = GetCartFromRequest(request);
			cart.removeFromCart(pid);

			//重定向
			response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "购物车删除失败");
			return "jsp/msg.jsp";
		}
		return null;
	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//清空购物车
		Cart cart = GetCartFromRequest(request);
		cart.clearCart();

		//重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}
}
