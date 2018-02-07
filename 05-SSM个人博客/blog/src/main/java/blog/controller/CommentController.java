package blog.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import blog.entity.Blog;
import blog.entity.Blogger;
import blog.entity.Blogtype;
import blog.entity.Comment;
import blog.entity.Link;
import blog.service.BlogService;
import blog.service.BloggerService;
import blog.service.BlogtypeService;
import blog.service.CommentService;
import blog.service.LinkService;
import net.sf.json.JSONObject;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Value("${COMMENTS_NUM}")
	private Integer COMMENTS_NUM;
	

	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private LinkService linkService;

	@Autowired
	private BlogtypeService blogtypeService;

	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/comment")
	public ModelAndView showComment() {
		return showCommentPages("1");
	}
	
	@RequestMapping("/comment/{page}")
	public ModelAndView showCommentPages(@PathVariable("page") String page) {
		// 获取第page页评论
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
			pageNum = 1;
		}
		ModelAndView mav = new ModelAndView();
		Blogger blogger = bloggerService.getFirstBlogger();
		blogger.setPassword(null);	//不传输密码保护安全
		List<Link> links = linkService.getLinksBySortNo();
		List<Blogtype> blogtypes = blogtypeService.getBlogtypesBySortNo();
		List<Blog> priorityBlogs = blogService.getPriorityBlogs();
		Integer commentsCount = commentService.getCommentsCount();
		Integer totalPages = (commentsCount % COMMENTS_NUM == 0) ? (commentsCount / COMMENTS_NUM)
				: (commentsCount / COMMENTS_NUM) + 1;
		if (pageNum < 1 || pageNum > totalPages)	//页数错误设为第一页
			pageNum = 1;
		List<Comment> comments = commentService.getComments(pageNum, COMMENTS_NUM);	//先保证页数正确才能查comments
		mav.addObject("blogger", blogger);
		mav.addObject("links", links);
		mav.addObject("blogtypes", blogtypes);
		mav.addObject("priorityBlogs", priorityBlogs);
		mav.addObject("indexTitle", "留言墙");
		mav.addObject("htmlTitle", "留言墙");
		mav.addObject("commentsCount", commentsCount);
		mav.addObject("comments", comments);
		mav.addObject("pageUrl", "comment");
		mav.addObject("currentPage", pageNum);
		mav.addObject("totalPages", totalPages);
		mav.addObject("numberOfPages", COMMENTS_NUM);
		mav.setViewName("comment");
		return mav;
	}
	
	@RequestMapping(value="/say", method=RequestMethod.POST)
	@ResponseBody
	public String leaveMessage(
				@RequestParam(value="nickname", required=false) String nickname,
				@RequestParam(value="content", required=false) String content,
				@RequestParam(value="captcha", required=false) String captcha,
				HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if (nickname == null || nickname.isEmpty() || nickname.length() > 10) {
			json.put("state", 500);
			json.put("error", "昵称格式有误");
			return json.toString();
		}
		if (content == null || content.isEmpty() || content.length() > 50) {
			json.put("state", 500);
			json.put("error", "留言内容格式有误");
			return json.toString();
		}
		if (captcha == null || captcha.isEmpty() || captcha.length() != 4) {
			json.put("state", 500);
			json.put("error", "验证码格式有误");
			return json.toString();
		}
		if (!captcha.equals(request.getSession().getAttribute("captcha"))) {
			json.put("state", 500);
			json.put("error", "验证码错误");
			return json.toString();
		}
		try {
			Comment comment = new Comment();
			comment.setNickname(nickname);
			comment.setContent(content);
			comment.setUserip(getIp(request));
			//设置为UTC+8北京时间
			TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
			comment.setCommentDate(new Date());
			comment.setState(1);
			commentService.add(comment);
			json.put("state", 200);
			return json.toString();
		} catch (Exception e) {
			json.put("state", 500);
			json.put("error", "保存出现错误");
			return json.toString();
		}
	}
	
	
	private static int WIDTH = 60;
	private static int HEIGHT = 20;
	@RequestMapping("/captcha")
	@ResponseBody
	public String getCaptcha(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			//设置显示类型为图片
			response.setContentType("image/jpeg");
			ServletOutputStream sos = response.getOutputStream();
			//设置浏览器不要缓存此图片
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			//创建内容图片并获得其图形上下文
			BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			//产生随机的验证码
			char[] rands = generateCheckCode();
			//产生图像
			drawBackground(g);
			drawRands(g, rands);
			//结束图像的绘制过程,完成图像
			g.dispose();
			//将图像输出到客户端
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "JPEG", bos);
			byte[] buf = bos.toByteArray();
			response.setContentLength(buf.length);
			//下面的语句也可以写成:bos.writeTo(sos);
			sos.write(buf);
			bos.close();
			sos.close();
			//将当前验证码存入到session中
			session.setAttribute("captcha", new String(rands));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private char[] generateCheckCode() {
		//定义验证码的字符表
		String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 36);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	private void drawRands(Graphics g, char[] rands) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));
		//在不同的高度上输出验证码的每个字符
		g.drawString("" + rands[0], 1, 17);
		g.drawString("" + rands[1], 16, 15);
		g.drawString("" + rands[2], 31, 18);
		g.drawString("" + rands[3], 46, 16);
		//System.out.println(rands);
	}

	private void drawBackground(Graphics g) {
		//画背景
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//随即产生120个干扰点
		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
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
