package controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.BolggerService;

/**
 * 前台博主控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BolggerService bolggerService;
	
}
