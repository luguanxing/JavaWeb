package e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import e3mall.common.utils.E3Result;
import e3mall.mapper.TbUserMapper;
import e3mall.pojo.TbUser;
import e3mall.pojo.TbUserExample;
import e3mall.pojo.TbUserExample.Criteria;
import e3mall.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public E3Result checkData(String param, Integer type) {
		//参数：从url中取参数1、String param（要校验的数据）2、Integer type（校验的数据类型）
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return E3Result.build(500, "数据类型错误");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(TbUser user) {
		//后端还需要校验一次
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())) {
			return E3Result.build(500, "数据不完整");
		}
		if (!(boolean) checkData(user.getUsername(), 1).getData()) {
			return E3Result.build(500, "用户名被占用");
		}
		if (!(boolean) checkData(user.getPhone(), 2).getData()) {
			return E3Result.build(500, "手机号被占用");
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Password);
		userMapper.insert(user);
		return E3Result.ok();
	}

}
