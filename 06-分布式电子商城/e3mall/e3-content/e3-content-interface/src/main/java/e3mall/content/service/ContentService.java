package e3mall.content.service;

import java.util.List;

import e3mall.common.pojo.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.pojo.TbContent;

public interface ContentService {

	EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);
	
	E3Result addContent(TbContent content);
	
	List<TbContent> getContentListByCid(Long cid);
	
}
