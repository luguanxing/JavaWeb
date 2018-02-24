package news.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONObject;

import news.entity.Adminer;
import news.service.AdminerService;
import news.utils.Md5Utils;

@Controller
@RequestMapping("/admin/adminer")
public class AdminAdminerController {

	@Autowired
	private AdminerService adminerService;
	
	@RequestMapping(value="/modifyPassword", produces="application/json;charset=utf-8")
	@ResponseBody
	public String modifyPassword(String newPassword) {
		Adminer adminer = adminerService.getFirstAdminer();
		adminer.setPassword(Md5Utils.md5(newPassword, Md5Utils.SALT));
		Boolean result = adminerService.updateFirstAdminer(adminer);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	
}
