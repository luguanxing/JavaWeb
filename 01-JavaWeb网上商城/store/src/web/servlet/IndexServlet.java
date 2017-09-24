package web.servlet;

import domain.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 * 首页模块
 */
public class IndexServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//调用productservice查询热门和最新商品
			ProductService service = new ProductServiceImpl();
			List<Product> hotList = service.findHot();
			List<Product> newList = service.findNew();

			//将list放入request域中
			request.setAttribute("hotList", hotList);
			request.setAttribute("newList", newList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/jsp/index.jsp";
	}
	
}
