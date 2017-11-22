package cms.controller.admin;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import cms.entity.Article;
import cms.service.ArticleService;
import cms.service.impl.InitComponent;
import cms.utils.DateUtils;

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
		}
		if (result > 0) {
			//刷新缓存
			initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
			return "操作成功";
		} else {
			return "操作失败";
		}
	}
	
}
