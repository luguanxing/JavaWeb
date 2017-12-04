package mail.service.impl;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mail.entity.EmailSource;
import mail.entity.MyEmailFormat;
import mail.service.EmailService;
import mail.service.EmailSourceService;
import mail.utils.PropertiesUtils;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailSourceService emailSourceService;
	
	@Override
	public void send(MyEmailFormat myEmailFormat) throws Exception {
		//根据邮件格式加载必要的信息和获取邮件源后配置环境
		EmailSource emailSource = emailSourceService.findById(myEmailFormat.getEmailSourceid());
		String email = emailSource.getEmail();
		String password = emailSource.getPassword();
		Properties props = PropertiesUtils.getProperties(emailSource.getProperty());
		Session session = Session.getInstance(
				props, 
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, password);
					}
				}
			);
		//包装邮件
		Message msg = packetingMessageContent(session, myEmailFormat, emailSource);
		//发送邮件
		Transport.send(msg);
	}

	public static Message packetingMessageContent(Session session, MyEmailFormat myEmailFormat, EmailSource emailSource) throws Exception {
		Message msg = new MimeMessage(session);
		if (myEmailFormat.getFujian() != null) {	//无附件
			File fujian = myEmailFormat.getFujian();
			MimeMultipart mmp = new MimeMultipart("mixed");
			MimeBodyPart mbp_data = new MimeBodyPart();
			DataSource ds1 = new FileDataSource(fujian);
			DataHandler dh1 = new DataHandler(ds1);
			mbp_data.setDataHandler(dh1);
			mbp_data.setFileName(fujian.getName());
			mmp.addBodyPart(mbp_data);
			MimeBodyPart mbp_fujian = new MimeBodyPart();
			mbp_fujian.setContent(myEmailFormat.getContent(), "text/html;charset=utf-8");
			mmp.addBodyPart(mbp_fujian);
			msg.setContent(mmp, "text/html; charset=utf-8");
			msg.saveChanges();
		} else {	//有附件
			msg.setContent(myEmailFormat.getContent(), "text/html;charset=utf-8");
		}
		msg.setSubject(myEmailFormat.getTitle());
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(myEmailFormat.getTarget()));
		msg.setFrom(new InternetAddress(emailSource.getEmail()));
		return msg;
	}
	

}
