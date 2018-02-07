package blog.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(MyExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		String ip = getIp(request);
		logger.error("=================");
		logger.error(ip + " : 访问时系统发生异常");
		logger.error("错误地址 : " + request.getRequestURL());
		logger.error("错误信息 : " + ex.getMessage());
		logger.error("错误原因 : " + ex.getCause());
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		String errorDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		logger.error("错误时间 : " + errorDate);
		logger.error("异常详情 : ", ex);
		logger.error("=================");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error");
		return mav;
	}

	private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
}
