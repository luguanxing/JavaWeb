package web.servlet;

import constant.Constant;
import domain.Article;
import domain.PageBean;
import service.ArticleService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/6.
 * 主页模块
 * 
 */
public class ArticleServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				//设置pagesize，默认1
				pageNumber = 1;
			}

			//加载页面文章的数据
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			PageBean<Article> bean = service.findByPage(pageNumber, Constant.ARTICLE_PAGE_COUNT);
			
			if (bean == null || pageNumber > bean.getTotalPage())
				throw new Exception("查询内容页不存在");
			//设置数据并转发
			request.setAttribute("pagebean", bean);
			request.setAttribute("index", 3);
			return "WEB-INF/jsp/article.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取文章信息失败");
			return "/msg.jsp";
		}
	}

	public String read(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String aid = request.getParameter("aid");
			if (aid == null)
				throw new Exception("查询详情不存在");
			
			//加载页面文章的数据
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			Article article = service.getById(aid);
			
			if (article == null)
				throw new Exception("文章不存在");

			request.setAttribute("article", article);
			request.setAttribute("index", 3);
			return "WEB-INF/jsp/articleInfo.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取文章详情失败");
			return "/msg.jsp";
		}
	}

	public String readContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String aid = request.getParameter("aid");
			if (aid == null)
				throw new Exception("查询详情不存在");

			//加载页面文章的数据
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			Article article = service.getById(aid);

			if (article == null)
				throw new Exception("文章不存在");

			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(article.getContent());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取文章详情失败");
			return "/msg.jsp";
		}
	}
	
}
