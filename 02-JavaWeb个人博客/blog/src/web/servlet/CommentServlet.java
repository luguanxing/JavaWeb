package web.servlet;

import constant.Constant;
import domain.Comment;
import domain.PageBean;
import service.CommentService;
import utils.BeanFactory;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/10/6.
 * 主页模块
 * 
 */
public class CommentServlet extends BaseServlet {

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
			//加载页面评论的数据
			CommentService service = (CommentService) BeanFactory.getBean("CommentService");
			PageBean<Comment> bean = service.findByPage(pageNumber, Constant.COMMENT_PAGE_COUNT);
			if (bean == null || pageNumber > bean.getTotalPage())
				throw new Exception("查询内容页不存在");
			//设置数据并转发
			request.setAttribute("pagebean", bean);
			request.setAttribute("index", 5);
			return "WEB-INF/jsp/comment.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取评论信息失败");
			return "/msg.jsp";
		}
	}
	
	public String addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String comment = request.getParameter("comment");
			String nickname = request.getParameter("nickname");
			String inputCaptcha = request.getParameter("inputCaptcha");
			String captcha = (String) request.getSession().getAttribute("captcha");
			CommentService service = (CommentService) BeanFactory.getBean("CommentService");
			
			if (captcha == null || !captcha.equals(inputCaptcha)) {
				//验证码错误，显示失败信息
				request.setAttribute("errorcomment", "验证码错误");
				request.setAttribute("comment", comment);
				request.setAttribute("nickname", nickname);
				index(request, response);
				return "WEB-INF/jsp/comment.jsp";
			} else {
				String result = service.checkformat(comment, nickname, inputCaptcha);
				if (!Constant.COMMENT_SUCCESS.equals(result)) {
					//内容格式错误，显示失败信息
					request.setAttribute("errorcomment", result);
					request.setAttribute("comment", comment);
					request.setAttribute("nickname", nickname);
					index(request, response);
					return "WEB-INF/jsp/comment.jsp";
				} else {
					TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
					String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					Comment newcomment = new Comment();
					newcomment.setComment(comment);
					newcomment.setDate(date);
					newcomment.setNickname(nickname);
					service.addComment(newcomment);
				}
			}
			
			//添加评论成功
			response.sendRedirect("/comment");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加评论失败");
			return "/msg.jsp";
		}
	}
}
