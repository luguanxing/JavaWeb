package mail.entity;

/**
 * 计划表
 * @author LGX
 *
 */
public class Schedule {

	private Integer id;
	private Integer sourceid;
	private Integer targetid;
	private Integer intervaltime;
	private String lastsendtime;
	private String description;
	private String sendcontent;
	private EmailSource emailSource;
	private Target target;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSourceid() {
		return sourceid;
	}
	public void setSourceid(Integer sourceid) {
		this.sourceid = sourceid;
	}
	public Integer getTargetid() {
		return targetid;
	}
	public void setTargetid(Integer targetid) {
		this.targetid = targetid;
	}
	public Integer getIntervaltime() {
		return intervaltime;
	}
	public void setIntervaltime(Integer intervaltime) {
		this.intervaltime = intervaltime;
	}
	public String getLastsendtime() {
		return lastsendtime;
	}
	public void setLastsendtime(String lastsendtime) {
		this.lastsendtime = lastsendtime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSendcontent() {
		return sendcontent;
	}
	public void setSendcontent(String sendcontent) {
		this.sendcontent = sendcontent;
	}
	public EmailSource getEmailSource() {
		return emailSource;
	}
	public void setEmailSource(EmailSource emailSource) {
		this.emailSource = emailSource;
	}
	public Target getTarget() {
		return target;
	}
	public void setTarget(Target target) {
		this.target = target;
	}
	
}
