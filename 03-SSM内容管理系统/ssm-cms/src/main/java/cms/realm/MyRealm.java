package cms.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cms.entity.Manager;
import cms.service.ManagerService;

/**
 * 自定义realm
 * @author LGX
 *
 */
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private ManagerService managerService;
	
	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		Object principal = token.getPrincipal();
		Manager manager = managerService.getByUsername(principal.toString());
		if (manager != null) {	//从数据库中取出正确的manager进行比较
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(manager.getUsername(), manager.getPassword(), "cms");
			return authenticationInfo;
		} else {
			return null;
		}
	}

	/**
	 * 用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
