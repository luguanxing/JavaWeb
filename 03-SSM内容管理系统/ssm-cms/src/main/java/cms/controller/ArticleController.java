package cms.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cms.entity.ArcType;
import cms.entity.Article;
import cms.service.ArticleService;
import cms.utils.HtmlUtils;
import cms.utils.StringUtils;

/**
 * 帖子Controller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/{id}")
	public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Article article = articleService.getById(id);
		ArcType arcType = article.getArcType();
		String keyWords = article.getKeyWords();
		mav.addObject("article", article);
		mav.addObject("navCode", HtmlUtils.getArticleNavigation(arcType.getTypeName(), arcType.getId(), article.getTitle(), request.getServletContext().getContextPath()));
		mav.addObject("pageCode", HtmlUtils.getUpAndDownCode(articleService.getLastArticle(id), articleService.getNextArticle(id), request.getServletContext().getContextPath()));
		if (StringUtils.isNotEmpty(keyWords)) {
			List<String> words = Arrays.asList(keyWords.split(" "));
			mav.addObject("keyWords", StringUtils.filter(words));
		} else {
			mav.addObject("keyWords", null);
		}
		mav.setViewName("article");
		article.setClick(article.getClick()+1);
		articleService.update(article);
		return mav;
	}
	
	
	
}
