package e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.pojo.TbUser;
import e3mall.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient  jedisClient;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result getUserByToken(String token) {
		//1、从url中取参数。
		//2、根据token查询redis。
		//3、如果查询不到数据。返回用户已经过期。
		//4、如果查询到数据，说明用户已经登录。
		//5、需要重置key的过期时间。
		//6、把json数据转换成TbUser对象，然后使用e3Result包装并返回。
		String json = jedisClient.get("SESSION:" + token);
		if (StringUtils.isBlank(json)) {
			return E3Result.build(300, "用户登录未登录或已经过期");
		}
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
		return E3Result.ok(user);
	}

}
