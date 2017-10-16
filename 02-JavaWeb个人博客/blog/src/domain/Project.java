package domain;

/**
 * Created by Administrator on 2017/10/11.
 */
public class Project {
	
	String pid;
	String title;
	String description;
	String imagepath;
	String date;
	String type;
	Integer click;
	String link;

	public Project() {};
	
	public Project(String pid, String title, String description, String imagepath, String date, String type, Integer click, String link) {
		this.pid = pid;
		this.title = title;
		this.description = description;
		this.imagepath = imagepath;
		this.date = date;
		this.type = type;
		this.click = click;
		this.link = link;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
