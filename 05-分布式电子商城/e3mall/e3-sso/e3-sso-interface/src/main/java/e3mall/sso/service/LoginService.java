package e3mall.sso.service;

import e3mall.common.utils.E3Result;

public interface LoginService {

	E3Result userLogin(String username, String password);
	
}
