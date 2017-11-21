package cms.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * ajax返回输出流工具类
 * @author Administrator
 *
 */
public class ResponseUtil {

	public static void write(HttpServletResponse response, Object object) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(object);
		out.flush();
		out.close();
	}
	
}
