package web.servlet.admin;

import constant.Constant;
import domain.*;
import service.*;
import utils.BeanFactory;
import utils.UUIDUtils;
import web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/15.
 */
public class AdminServlet extends BaseServlet {

	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/home.jsp";
	}

	//主页管理
	public String editIndexUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IndexService service = (IndexService) BeanFactory.getBean("IndexService");
			Index index = service.getIndexContent();
			request.setAttribute("index", index);
			return "/admin/index/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取主页内容失败");
			return "/msg.jsp";
		}
	}

	public String editIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String content = request.getParameter("content");
			IndexService service = (IndexService) BeanFactory.getBean("IndexService");
			Index index = new Index();
			index.setContent(content);
			service.update(index);
			request.setAttribute("index", index);
			return "/index";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "修改主页内容失败");
			return "/msg.jsp";
		}
	}

	

	//路线图管理
	public String listRoadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RoadService service = (RoadService) BeanFactory.getBean("RoadService");
			Map map = service.getAllByMap();
			request.setAttribute("map", map);
			return "/admin/road/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取路线信息失败");
			return "/msg.jsp";
		}
	}

	public String addRoadItemUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("method", "addRoadItem");
			request.setAttribute("type", "添加路线点");
			return "/admin/road/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取路线详情失败");
			return "/msg.jsp";
		}
	}

	public String addRoadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String date = request.getParameter("date");
			String year = request.getParameter("year");
			
			RoadItem roadItem = new RoadItem(UUIDUtils.getId(), title, content, new SimpleDateFormat("yyyy-MM-dd").parse(date), year);
			RoadService service = (RoadService) BeanFactory.getBean("RoadService");
			service.save(roadItem);
			response.sendRedirect("/admin?method=listRoadItem");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取路线详情失败");
			return "/msg.jsp";
		}
	}

	public String editRoadItemUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String rid = request.getParameter("rid");
			RoadService service = (RoadService) BeanFactory.getBean("RoadService");
			RoadItem roadItem = service.getById(rid);
			request.setAttribute("roadItem", roadItem);
			request.setAttribute("method", "editRoadItem");
			request.setAttribute("type", "修改路线点");
			return "/admin/road/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取路线详情失败");
			return "/msg.jsp";
		}
	}

	public String editRoadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String rid = request.getParameter("rid");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String date = request.getParameter("date");
			String year = request.getParameter("year");

			RoadItem roadItem = new RoadItem(rid, title, content, new SimpleDateFormat("yyyy-MM-dd").parse(date), year);
			RoadService service = (RoadService) BeanFactory.getBean("RoadService");
			service.update(roadItem);
			response.sendRedirect("/admin?method=listRoadItem");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取路线详情失败");
			return "/msg.jsp";
		}
	}

	public String deleteRoadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String rid = request.getParameter("rid");
			RoadService service = (RoadService) BeanFactory.getBean("RoadService");
			service.deleteById(rid);
			response.sendRedirect("/admin?method=listRoadItem");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台删除路线详情失败");
			return "/msg.jsp";
		}
	}

	

	//评论管理
	public String listComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			PageBean<Comment> bean = service.findByPage(pageNumber, Constant.COMMENT_ADMINPAGE_COUNT);
			if (bean == null || pageNumber > bean.getTotalPage())
				throw new Exception("查询内容页不存在");
			//设置数据并转发
			request.setAttribute("pagebean", bean);
			return "/admin/comment/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取评论信息失败");
			return "/msg.jsp";
		}
	}

	public String deleteComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CommentService service = (CommentService) BeanFactory.getBean("CommentService");
			service.deleteById(cid);
			response.sendRedirect("/admin?method=listComment");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台删除路线失败");
			return "/msg.jsp";
		}
	}

	
	
	//项目分类管理
	public String listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			List list =  service.getAll();
			request.setAttribute("list", list);
			return "/admin/category/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取项目分类失败");
			return "/msg.jsp";
		}
	}

	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("method", "addCategory");
			request.setAttribute("type", "添加项目分类");
			return "/admin/category/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取项目分类失败");
			return "/msg.jsp";
		}
	}

	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			String order = request.getParameter("order");
			Category category = new Category();
			category.setCid(UUIDUtils.getId());
			category.setName(name);
			category.setOrder(Integer.parseInt(order));

			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			service.save(category);
			response.sendRedirect("/admin?method=listCategory");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台增加项目分类失败");
			return "/msg.jsp";
		}
	}

	public String editCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			Category category = service.getById(cid);
			request.setAttribute("category", category);
			request.setAttribute("method", "editCategory");
			request.setAttribute("type", "修改项目分类");
			return "/admin/category/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台修改项目分类失败");
			return "/msg.jsp";
		}
	}

	public String editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			String name = request.getParameter("name");
			String order = request.getParameter("order");
			Category category = new Category();
			category.setCid(cid);
			category.setName(name);
			category.setOrder(Integer.parseInt(order));

			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			service.update(category);
			response.sendRedirect("/admin?method=listCategory");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台修改项目分类失败");
			return "/msg.jsp";
		}
	}

	public String deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			service.delete(cid);
			response.sendRedirect("/admin?method=listCategory");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台删除项目分类失败");
			return "/msg.jsp";
		}
	}



	//文章管理
	public String listArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			PageBean<Article> bean = service.findByPage(pageNumber, Constant.ARTICLE_ADMINPAGE_COUNT);
			if (bean == null || pageNumber > bean.getTotalPage())
				throw new Exception("查询内容页不存在");
			//设置数据并转发
			request.setAttribute("pagebean", bean);
			return "/admin/article/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取文章信息失败");
			return "/msg.jsp";
		}
	}
	
	public String addArticleUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("method", "addArticle");
			request.setAttribute("type", "添加文章");
			return "/admin/article/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取文章失败");
			return "/msg.jsp";
		}
	}
	
	public String addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String date = request.getParameter("date");
			String click = request.getParameter("click");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Article article = new Article(UUIDUtils.getId(),  date, Integer.parseInt(click), title, content);
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			service.save(article);
			response.sendRedirect("/admin?method=listArticle");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台添加文章失败");
			return "/msg.jsp";
		}
	}
	
	public String editArticleUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String aid = request.getParameter("aid");
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			Article article = service.getById(aid);
			request.setAttribute("article", article);
			request.setAttribute("method", "editArticle");
			request.setAttribute("type", "编辑文章");
			return "/admin/article/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取文章失败");
			return "/msg.jsp";
		}
	}
	
	public String editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String aid = request.getParameter("aid");
			String date = request.getParameter("date");
			String click = request.getParameter("click");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Article article = new Article(aid,  date, Integer.parseInt(click), title, content);
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			service.update(article);
			response.sendRedirect("/admin?method=listArticle");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台修改文章失败");
			return "/msg.jsp";
		}
	}
	
	public String deleteArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String aid = request.getParameter("aid");
			ArticleService service = (ArticleService) BeanFactory.getBean("ArticleService");
			service.delete(aid);
			response.sendRedirect("/admin?method=listArticle");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台删除文章失败");
			return "/msg.jsp";
		}
	}

	
	
	//项目管理
	public String listProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int pageNumber = 1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				//设置pagesize，默认1
				pageNumber = 1;
			}
			//加载页面文章的数据
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			PageBean<Project> bean = service.findByPage(pageNumber, Constant.PROJECT_ADMINPAGE_COUNT);
			if (bean == null || pageNumber > bean.getTotalPage())
				throw new Exception("查询内容页不存在");
			//设置数据并转发
			request.setAttribute("pagebean", bean);
			return "/admin/project/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取项目列表失败");
			return "/msg.jsp";
		}
	}
	
	public String addProjectUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("method", "addProject");
			request.setAttribute("type", "添加项目");
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			List list =  service.getAll();
			request.setAttribute("list", list);
			return "/admin/project/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取项目失败");
			return "/msg.jsp";
		}
	}
	
	public String addProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String date = request.getParameter("date");
			String click = request.getParameter("click");
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String imagepath = request.getParameter("imagepath");
			String type = request.getParameter("type");
			String link = request.getParameter("link");
			Project project = new Project(UUIDUtils.getId(), title, description, imagepath, date, type, Integer.parseInt(click), link);
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			service.save(project);
			response.sendRedirect("/admin?method=listProject");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台添加项目失败");
			return "/msg.jsp";
		}
	}
	
	public String editProjectUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");

			CategoryService cservice = (CategoryService) BeanFactory.getBean("CategoryService");
			List list =  cservice.getAll();
			ProjectService pservice = (ProjectService) BeanFactory.getBean("ProjectService");
			Project project = pservice.getById(pid);

			request.setAttribute("project", project);
			request.setAttribute("list", list);
			request.setAttribute("method", "editProject");
			request.setAttribute("type", "修改项目");
			return "/admin/project/edit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台获取项目失败");
			return "/msg.jsp";
		}
	}
	
	public String editProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			String date = request.getParameter("date");
			String click = request.getParameter("click");
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String imagepath = request.getParameter("imagepath");
			String type = request.getParameter("type");
			String link = request.getParameter("link");
			Project project = new Project(pid, title, description, imagepath, date, type, Integer.parseInt(click), link);
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			service.update(project);
			response.sendRedirect("/admin?method=listProject");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台修改项目失败");
			return "/msg.jsp";
		}
	}
	
	public String deleteProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			ProjectService service = (ProjectService) BeanFactory.getBean("ProjectService");
			service.delete(pid);
			response.sendRedirect("/admin?method=listProject");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "后台删除项目失败");
			return "/msg.jsp";
		}
	}
	
}
