package mail.controller.admin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import mail.entity.MyEmailFormat;
import mail.service.EmailService;


/**
 * 邮件Contoller层
 * @author LGX
 *
 */
@Controller
@RequestMapping("/admin/email")
public class EmailSenderContoller {
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="send", produces="application/json;charset=utf-8")
	@ResponseBody
	public String sendEmail(@RequestParam("uploadFile") MultipartFile uploadFile, MyEmailFormat myEmailFormat, HttpServletRequest request) throws Exception {
		try {
			if (!uploadFile.isEmpty()) {
				String tempFolder = System.getProperty("java.io.tmpdir");
				String uploadFileName = uploadFile.getOriginalFilename();
				File fujian = new File(tempFolder + "\\" + uploadFileName);
				uploadFile.transferTo(fujian);
				myEmailFormat.setFujian(fujian);
			}
			emailService.send(myEmailFormat);
			return "操作成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败";
		}
	}
	
}
