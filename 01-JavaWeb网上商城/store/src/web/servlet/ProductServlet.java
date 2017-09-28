package web.servlet;

import constant.Constant;
import domain.PageBean;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/24.
 * 前台商品模块
 */
public class ProductServlet extends BaseServlet {
	
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//商品详情
		try {
			//获取pid
			String pid = request.getParameter("pid");

			//调用service获取单个商品:pid 返回值:product
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			Product product = service.getById(pid);
			if (product == null)
				throw new Exception("商品不存在");
			
			//将product放入request域并转发
			request.setAttribute("product", product);
		} catch (Exception e) {
			request.setAttribute("msg", "查询失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_info.jsp";
	}

	
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//分类商品分页展示
		try {
			//获取pagenumber cid 设置pagesize，默认1
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				//设置pagesize，默认1
			}

			//调用service 分页查询商品 参数 ，返回pagebean
			String cid = request.getParameter("cid");
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> bean = service.findByPage(pageNumber, Constant.PRODUCT_PAGE_COUNT, cid);
			if (pageNumber > bean.getTotalPage())
				throw new Exception("查询页数不存在");
			
			//放pagebean入request并转发
			request.setAttribute("pagebean", bean);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "分页查询失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_list.jsp";
	}
}
