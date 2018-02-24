package news.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import news.entity.New;
import news.service.NewService;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	@Autowired
	private NewService newService;
	
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
	
}
