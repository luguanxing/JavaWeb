package news.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import news.entity.New;
import news.entity.PageBean;
import news.service.NewService;

@Controller
@RequestMapping("/admin/new")
public class AdminNewController {

	@Autowired
	private NewService newService;
	
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="rows", defaultValue="10") int pageSize) {
		PageBean pageBean = new PageBean(page, pageSize);
		List<New> news = newService.getNewsByPublishDateAndSrc(page, pageSize);
		Integer total = newService.getNewsCount();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(news);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(New newObj, String crawlerDateInput) {
		//需要做一些属性的转换
		Date crawlerDate = new Date();
		try {
			//新增时获取的日期格式带秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			crawlerDate = sdf.parse(crawlerDateInput);	
		} catch (Exception e) {
			try {
				//修改时easyui控件获取的日期格式不带秒
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				crawlerDate = sdf.parse(crawlerDateInput);	
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		newObj.setCrawlerDate(crawlerDate);
		Boolean result = newService.updateNew(newObj);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		String[] idsStr = ids.split(",");
		Boolean result = true;
		for (int i = 0; i < idsStr.length; i++) {
			result = result & newService.deleteNewById(Integer.parseInt(idsStr[i]));
		}
		if (result) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
