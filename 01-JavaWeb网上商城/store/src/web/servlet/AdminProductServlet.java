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
			return "/jsp/msg.jsp";
		}
	}

	public String addProductUI(HttpServletRequest request, HttpServletResponse response) {
		try {
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			List list = service.findList();
			request.setAttribute("list", list);	//使得添加商品时有分类下拉框可选
			return "/admin/product/add.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取商品页面失败");
			return "jsp/msg.jsp";
		}
	}
	
	public String editProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			String pid = request.getParameter("pid");
			Product product = service.getById(pid);
			String cid = service.findCid(product);
			request.setAttribute("cid", cid);
			
			request.setAttribute("product", product);
			CategoryService cservice = (CategoryService) BeanFactory.getBean("CategoryService");
			
			List list = cservice.findList();
			request.setAttribute("list", list);
			
			return "/admin/product/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "跳转修改商品页面失败");
			return "/jsp/msg.jsp";
		}
	}

	public String deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			String pid = request.getParameter("pid");
			service.delete(pid);
			List<Product> list = service.findAll();
			request.setAttribute("list", list);
			return "/admin/product/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "删除商品失败");
			return "/jsp/msg.jsp";
		}
	}
}
