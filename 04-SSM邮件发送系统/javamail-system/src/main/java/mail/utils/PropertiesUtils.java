package mail.utils;

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
	 * 根据PropertyName获取Properties
	 * @param key
	 * @return
	 */
	public static Properties getProperties(String PropertyName) {
		InputStream in = new PropertiesUtils().getClass().getResourceAsStream("/mailproperties/" + PropertyName + ".properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
}
