package cms.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties文件工具类
 * @author LGX
 *
 */
public class PropertiesUtils {

	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		InputStream in = new PropertiesUtils().getClass().getResourceAsStream("/cms.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
}
