package controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.BolggerService;

/**
 * 后台管理员博主控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	private BolggerService bolggerService;
	
}
