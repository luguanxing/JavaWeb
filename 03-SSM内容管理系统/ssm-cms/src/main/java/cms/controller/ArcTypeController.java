package cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cms.entity.ArcType;
import cms.entity.Article;
import cms.entity.PageBean;
import cms.service.ArcTypeService;
import cms.service.ArticleService;
import cms.utils.HtmlUtils;
import cms.utils.PropertiesUtils;



/**
 * 帖子类别Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/arcType")
public class ArcTypeController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArcTypeService arcTypeService;
	
	/**
	 * 根据类别查询帖子结果
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{id}")
	public ModelAndView list(@PathVariable("id") Integer id, @RequestParam(value="page", defaultValue="1") Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = new HashMap();
			PageBean pageBean = new PageBean(page, Integer.parseInt(PropertiesUtils.getValue("pageSize")));
			map.put("typeId", id);
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<Article> articleList = articleService.list(map);
			if (articleList.size() == 0)
				throw new Exception();
			Long total = articleService.getTotal(map);
			ArcType arcType = arcTypeService.findById(id);
			mav.addObject("articleList", articleList);
			mav.addObject("arcType", arcType);
			mav.addObject("navCode", HtmlUtils.getArticleListNavigation(arcType.getTypeName(), request.getServletContext().getContextPath()));
			mav.addObject("pageCode", HtmlUtils.getArctypeUpAndDownCode(total.intValue(), page, pageBean.getPageSize(), id, request.getServletContext().getContextPath()));
			mav.setViewName("articleList");
			return mav;
		} catch (Exception e) {
			response.sendRedirect(request.getServletContext().getContextPath());
			return null;
		}
	}
	
}
