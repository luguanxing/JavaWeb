package web.servlet;

import domain.Product;
import service.CategoryService;
import service.ProductService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 * 后台商品管理模块
 */
public class AdminProductServlet extends BaseServlet {
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = service.findAll();
			request.setAttribute("list", list);
			return "/admin/product/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取商品列表失败");
			return "jsp/msg.jsp";
		}
	}

	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			List list = service.findList();
			request.setAttribute("list", list);
			return "/admin/product/add.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取商品页面失败");
			return "jsp/msg.jsp";
		}
	}
	
}
