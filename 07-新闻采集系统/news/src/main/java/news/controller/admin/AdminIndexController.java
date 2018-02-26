package news.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import news.entity.New;
import news.service.CrawlerService;
import news.service.NewService;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	private static Logger logger = Logger.getLogger(AdminIndexController.class);
	
	@Autowired
	private NewService newService;
	
	@Autowired
	private CrawlerService crawlerService;
	
	@RequestMapping("/main")
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login.html";
	}
	
	@RequestMapping("/manageNew")
	public String manageNew() {
		return "admin/manageNew";
	}
	
	@RequestMapping("/editNew")
	public ModelAndView editNew(Integer id) {
		ModelAndView mav = new ModelAndView();
		New newObj = newService.getNewById(id);
		mav.addObject("newObj", newObj);
		mav.setViewName("admin/editNew");
		return mav;
	}
	
	@RequestMapping("/importRedis")
	@ResponseBody
	public String importRedis() {
		Boolean result = newService.importRedis();
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	@RequestMapping("/importSolr")
	@ResponseBody
	public String importSolr() {
		Boolean result = newService.importSolr();
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	
	@RequestMapping("/crawler")
	@ResponseBody
	public String crawler() {
		//居然直接注入MyTimingCrawler然后调用方法失败，只能把方法抄过来
		logger.warn("手动执行爬虫");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.warn(sdf.format(new Date()) + " 进行爬取");
		List<New> news = crawlerService.getNewsUrlsAndComments();
		Collections.reverse(news);	//让最后抓取的最先保存
		for (New newObj : news) {
			logger.warn("================");
			logger.warn("获取" + newObj.getUrl());
			newObj = crawlerService.getNewFromUrlAndComment(newObj);
			if (newObj == null) {
				logger.warn("不是正常的新闻格式，进行忽略");
			} else {
				logger.warn("保存或更新" + newObj.getUrl());
				crawlerService.saveNewToLocal(newObj);
			}
			logger.warn("================");
			logger.warn("");
		}
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json.toString();
	}
	
	
}
