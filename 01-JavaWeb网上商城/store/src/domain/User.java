package domain;

/**
 * Created by Administrator on 2017/9/19.
 */
public class User {
	
	/*
		CREATE TABLE `user` (
		  `uid` VARCHAR(32) NOT NULL,
		  `username` VARCHAR(20) DEFAULT NULL,
		  `password` VARCHAR(20) DEFAULT NULL,
		  `name` VARCHAR(20) DEFAULT NULL,
		  `email` VARCHAR(30) DEFAULT NULL,
		  `telephone` VARCHAR(20) DEFAULT NULL,
		  `birthday` DATE DEFAULT NULL,
		  `sex` VARCHAR(10) DEFAULT NULL,
		  `state` INT(11) DEFAULT NULL,
		  `code` VARCHAR(64) DEFAULT NULL,
		  PRIMARY KEY (`uid`)
		) ENGINE=INNODB DEFAULT CHARSET=utf8;
	 */
	
	private  String uid;
	private  String username;
	private  String password;
	private  String name;
	private  String email;
	private  String telephone;
	private  String birthday;
	private  String sex;
	private  Integer state;		//包装类，便于区分NULL和0
	private  String code;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
