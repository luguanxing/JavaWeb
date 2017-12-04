package mail.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间日期工具类
 * @author LGX
 *
 */
public class TimeUtils {

	public static String getCurrentTime() {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		return dateFormater.format(date);
	}
	
	public static long getSecondsFromTimeString(String timeString) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long millionSeconds = sdf.parse(timeString).getTime();
		return millionSeconds/1000;
	}
	
	public static void main(String[] args) throws Exception {
		String currentTime = getCurrentTime();
		System.out.println(currentTime);
		System.out.println(getSecondsFromTimeString(currentTime));
	}
	
}
