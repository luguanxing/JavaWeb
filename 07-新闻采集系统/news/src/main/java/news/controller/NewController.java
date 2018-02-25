package news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import news.entity.New;
import news.service.NewService;

@Controller
@RequestMapping("/new")
public class NewController {
	
	@Autowired
	private NewService newService;
	
	@RequestMapping("/{newId}")
	public ModelAndView showNew(@PathVariable("newId") String newId) {
		// 获取newId的博客
		Integer newIdNum;
		try {
			newIdNum = Integer.parseInt(newId);
		} catch(Exception e) {
			e.printStackTrace();
			newIdNum = -1;
		}
		New newObj = newService.getNewById(newIdNum);
		if (newObj == null)
			new ModelAndView("redirect:/index.html");
		ModelAndView mav = new ModelAndView();
		mav.addObject("newObj", newObj);
		mav.setViewName("new");
		return mav;
	}
	
}
