package news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import news.service.AdminerService;

@Controller
public class IndexController {

	@Autowired
	private AdminerService AdminerService;

	@RequestMapping("/login")
	public String loginUI() {
		return "login";
	}

}
