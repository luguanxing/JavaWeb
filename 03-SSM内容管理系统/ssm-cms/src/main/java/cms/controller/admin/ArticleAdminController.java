package cms.controller.admin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;


import cms.entity.Article;
import cms.entity.PageBean;
import cms.service.ArticleService;
import cms.service.impl.InitComponent;
import cms.utils.DateJsonUtils;
import cms.utils.DateUtils;
import cms.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 管理员文章Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleAdminController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private InitComponent initComponent;
	
	/**
	 * 添加或修改文章内容
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save", produces="application/json;charset=utf-8")
	@ResponseBody
	public String save(@RequestParam("slideImageFile") MultipartFile slideImageFile, Article article, HttpServletRequest request) throws Exception {
		int result = 0;
		if (!slideImageFile.isEmpty()) {
			String rootPath = request.getServletContext().getRealPath("/");
			String imageName = DateUtils.getCurrentDateStr() + "." + slideImageFile.getOriginalFilename().split("\\.")[1];
			slideImageFile.transferTo(new File(rootPath + "static/userImages/" + imageName));
			article.setSlideImage(imageName);
		}
		if (article.getId() == null) {
			//添加
			article.setPublishDate(new Date());
			result = articleService.add(article);
		} else {
			//修改
			result = articleService.update(article);
		}
		if (result > 0) {
			//刷新缓存
			initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
			return "操作成功";
		} else {
			return "操作失败";
		}
	}
	
	/**
	 * 根据条件分页查询文章信息
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(Article searchArticle, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize, HttpServletResponse response) {
		PageBean pageBean = new PageBean(page, pageSize);
		Map<String, Object> map = new HashMap();
		map.put("title", StringUtils.formatLike(searchArticle.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Article> articleList = articleService.list(map);
		Long total = articleService.getTotal(map);
		JSONObject json = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonUtils("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(articleList, jsonConfig);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	/**
	 * 通过id返回文章实体JSON
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findById", produces="application/json;charset=utf-8")
	@ResponseBody
	public String findById(@RequestParam(value="id") Integer id) {
		Article article = articleService.getById(id);
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(article);
		return json.toString();
	}
	
	/**
	 * 删除文章
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String ids) {
		String[] idsStr = ids.split(",");
		JSONObject json = new JSONObject();
		for (String idStr : idsStr) {
			articleService.delete(Integer.parseInt(idStr));
		}
		json.put("success", true);
		//刷新缓存
		initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
		return json.toString();
	}
}
