package e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.pojo.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.content.service.ContentService;
import e3mall.pojo.TbContent;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/query/list")
	@ResponseBody
	public EasyUIDataGridResult listContent(Long categoryId, Integer page, Integer rows) {
		EasyUIDataGridResult contentList = contentService.getContentList(categoryId, page, rows);
		return contentList;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content) {
		E3Result result = contentService.addContent(content);
		return result;
	}
	
}
