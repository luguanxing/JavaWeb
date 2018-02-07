package blog.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import blog.entity.Comment;
import blog.entity.PageBean;
import blog.service.CommentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {

	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize) {
		PageBean pageBean = new PageBean(page, pageSize);
		List<Comment> comments = commentService.getComments(page, pageSize);
		Integer total = commentService.getCommentsCount();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(comments);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		String[] idsStr = ids.split(",");
		Boolean result = true;
		for (int i = 0; i < idsStr.length; i++) {
			result = result & commentService.delete(Integer.parseInt(idsStr[i]));
		}
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
