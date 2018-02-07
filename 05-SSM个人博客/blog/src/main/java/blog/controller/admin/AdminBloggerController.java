package blog.controller.admin;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import blog.entity.Blogger;
import blog.service.BloggerService;
import blog.utils.Md5Utils;

@Controller
@RequestMapping("/admin/blogger")
public class AdminBloggerController {

	@Autowired
	private BloggerService bloggerService;
	
	@RequestMapping("/modifyBlogger")
	@ResponseBody
	public String modifyBlogger(Blogger newBlogger) {
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setUsername(newBlogger.getUsername());
		blogger.setImage(newBlogger.getImage());
		blogger.setNickname(newBlogger.getNickname());
		blogger.setProfile(newBlogger.getProfile());
		blogger.setSign(newBlogger.getSign());
		Boolean result = bloggerService.updateFirstBlogger(blogger);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	@RequestMapping(value="/modifyPassword", produces="application/json;charset=utf-8")
	@ResponseBody
	public String modifyPassword(String newPassword) {
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setPassword(Md5Utils.md5(newPassword, Md5Utils.SALT));
		Boolean result = bloggerService.updateFirstBlogger(blogger);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	
}
