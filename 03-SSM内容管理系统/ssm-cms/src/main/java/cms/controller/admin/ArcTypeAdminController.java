package cms.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import cms.entity.ArcType;
import cms.entity.PageBean;
import cms.service.ArcTypeService;
import cms.service.ArticleService;
import cms.service.impl.InitComponent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 管理员文章类别Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/arcType")
public class ArcTypeAdminController {

	@Autowired
	private ArcTypeService arcTypeService;
	
	@Autowired
	private InitComponent initComponent;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String list(@RequestParam(value="page", defaultValue="1")Integer page,@RequestParam(value="rows", defaultValue="10")Integer rows) throws Exception{
		PageBean pageBean=new PageBean(page, rows);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<ArcType> arcTypeList=arcTypeService.list(map);
		Long total=arcTypeService.getTotal(map);
		JSONObject json=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(arcTypeList);
		json.put("rows", jsonArray);
		json.put("total", total);
		return json.toString();
	}
	
	@RequestMapping(value="/save", produces="application/json;charset=utf-8")
	@ResponseBody
	public String save(ArcType arcType,HttpServletResponse response)throws Exception{
		int result=0;
		if(arcType.getId()==null){ // 添加
			result=arcTypeService.add(arcType);
		}else{ // 修改
			result=arcTypeService.update(arcType);
		}
		JSONObject json=new JSONObject();
		if(result > 0){
			initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
			json.put("success", true);
		}else{
			json.put("success", false);
		}
		return json.toString();
	}
	
	/**
	 * 删除文章类别
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete", produces="application/json;charset=utf-8")
	@ResponseBody
	public String delete(@RequestParam(value="ids") String ids) {
		String[] idsStr = ids.split(",");
		JSONObject json = new JSONObject();
		try {
			for (String idStr : idsStr) {
				int typeId = Integer.parseInt(idStr);
				if (!articleService.getIndex(typeId).isEmpty()) {
					json.put("exist", "编号为" + typeId + "的类别下有文章,不能删除!");
					break;
				} else {
					arcTypeService.delete(typeId);
				}
			}
			json.put("success", true);
			//刷新缓存
			initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
		} catch (Exception e) {
			json.put("success", false);
		}
		return json.toString();
	}
	
}
