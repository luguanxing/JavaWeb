package cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cms.service.ManagerService;

/**
 * 管理员Contoller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin")
public class ManagerContoller {

	@Autowired
	private ManagerService managerService;
	
}
