package news.utils;

import org.apache.shiro.crypto.hash.Md5Hash;


/**
 * MD5加密工具类
 * @author LGX
 *
 */
public class Md5Utils {

	public static final String SALT = "LGX";
	
	public static String md5(String source, String salt) {
		return new Md5Hash(source, salt).toString();
	}
	
	public static void main(String[] args) {
		String testPassword = "123";
		System.out.println(md5(testPassword, Md5Utils.SALT));
	}
	
}
