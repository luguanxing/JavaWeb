package e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.pojo.EasyUIDataGridResult;
import e3mall.common.pojo.EasyUITreeNode;
import e3mall.common.utils.E3Result;
import e3mall.content.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(parentId);
		return contentCatList;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCategory(Long parentId, String name) {
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}
	
}
