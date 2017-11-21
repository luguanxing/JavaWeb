package cms.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * @author LGX
 *
 */
public class StringUtils {

	/**
	 * 判断非空字符串
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && !str.isEmpty());
	}
	
	/**
	 * 过滤空元素
	 * @param strs
	 * @return
	 */
	public static List<String> filter(List<String> strs) {
		List<String> list = new ArrayList();
		for (String str : strs) {
			if (isNotEmpty(str)) {
				list.add(str);
			}
		}
		return list;
	}
	
}
