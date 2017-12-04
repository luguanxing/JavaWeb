package mail.service;

import java.io.File;

import mail.entity.MyEmailFormat;

/**
 * 邮件服务Service接口
 * @author Administrator
 *
 */
public interface EmailService {

	public void send(MyEmailFormat myEmailFormat) throws Exception;
	
}
