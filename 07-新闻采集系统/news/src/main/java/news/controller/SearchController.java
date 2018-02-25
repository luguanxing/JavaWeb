package news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import news.service.NewService;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private NewService newService;
	
	
	
}
