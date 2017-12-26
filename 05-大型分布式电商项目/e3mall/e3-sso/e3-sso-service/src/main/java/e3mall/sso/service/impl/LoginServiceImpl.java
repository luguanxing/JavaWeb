package e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.mapper.TbUserMapper;
import e3mall.pojo.TbUser;
import e3mall.pojo.TbUserExample;
import e3mall.pojo.TbUserExample.Criteria;
import e3mall.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result userLogin(String username, String password) {
		//判断用户名密码是否正确,不正确返回失败
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return E3Result.build(500, "用户不存在");
		}
		TbUser user = list.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return E3Result.build(500, "密码错误");
		}
		//key由uuid生成token作为sessionid, value对应用户信息(不写入密码),设置过期时间,存入redis
		user.setPassword(null);
		String token = UUID.randomUUID().toString();
		jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
		jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
		//token写入cookie(表现层),返回登录成功
		return E3Result.ok(token);
	}
	
}
