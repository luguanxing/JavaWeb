package service.impl;

import constant.Constant;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import service.UserService;
import utils.MailUtils;

import javax.mail.MessagingException;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/19.
 */
public class UserServiceImpl implements UserService {
	
	@Override
	public void regist(User user) throws Exception {
		//调用DAO往数据库中添加用户
		UserDao dao = new UserDaoImpl();
		dao.save(user);
		
		/*
		发送邮件(该部分暂时省略，默认激活)
		String emailMsg = user.getName() + "欢迎您注册商城，<a href='http://localhost/user?method=active&code=" + user.getCode() + "'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
		*/
	}

	@Override
	public String isRegistable(User user) {
		//检测用户名格式和是否重复
		if (user.getUsername() == null || !Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{2,19}").matcher(user.getUsername()).matches()) {
			return "用户名格式错误(3-20位)";
		}
		if (user.getPassword() == null || !Pattern.compile("[a-zA-Z0-9]{6,20}").matcher(user.getPassword()).matches()) {
			return "密码格式错误(6-20位)";
		}
		if (user.getTelephone() == null || !Pattern.compile("[0-9]*").matcher(user.getTelephone()).matches()) {
			return "电话格式错误(数字串)";
		}
		if (user.getEmail() == null || !Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$").matcher(user.getEmail()).matches()) {
			return "邮箱格式错误(*@*.*)";
		}
		if (user.getName() == null || user.getName().trim().isEmpty()) {
			return "姓名为空";
		}
		//性别可不填，或者填了错误格式设置为"1"
		if (user.getSex() == null ||  !(user.getSex().equals("0") || user.getSex().equals("1"))) {
			user.setSex(null);
		}
		//生日不填，或者填了错误格式设置为"1000-00-00"
		if (user.getBirthday() == null ||  !Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"
		).matcher(user.getBirthday()).matches()) {
			user.setBirthday("1000-00-00");
		}
		try {
			UserDao dao = new UserDaoImpl();
			if (dao.getUserByUsername(user.getUsername()) != null) {
				return "用户名已经存在";
			}
		} catch (Exception e) {
			return "校验发生错误";
		}
		return "success";
	}

	@Override
	public User active(String code) throws Exception {
		UserDao dao = new UserDaoImpl();
		User user = dao.getByCode(code);
		
		//通过code获取用户
		if (user == null)
			return null;
		user.setState(Constant.USER_IS_ACTIVE);
		user.setCode(null);
		
		dao.update(user);
		return user;
	}

	@Override
	public User login(String username, String password) throws Exception {
		//调用DAO从数据库中获取用户
		UserDao dao = new UserDaoImpl();
		return dao.getUserByUsernameAndPassword(username, password);
	}

	public static void main(String[] args) throws MessagingException {
		UserServiceImpl test = new UserServiceImpl();
		User user = new User();
		user.setUsername("bababac");
		user.setPassword("31dvcx");
		user.setName("haha");
		user.setTelephone("12345");
		user.setBirthday("0000-01-01");
		user.setSex("3");
		user.setEmail("12@s.c");
		System.out.println(test.isRegistable(user));
	}
	
}
