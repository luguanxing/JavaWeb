package mail.entity;

import java.io.File;

/**
 * 自定义的Email格式
 * @author LGX
 *
 */
public class MyEmailFormat {

	String target;
	Integer emailSourceid;
	String title;
	String content;
	File fujian;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Integer getEmailSourceid() {
		return emailSourceid;
	}
	public void setEmailSourceid(Integer emailSourceid) {
		this.emailSourceid = emailSourceid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public File getFujian() {
		return fujian;
	}
	public void setFujian(File fujian) {
		this.fujian = fujian;
	}
	
}
