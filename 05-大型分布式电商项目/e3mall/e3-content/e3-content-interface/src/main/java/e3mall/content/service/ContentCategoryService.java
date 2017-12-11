package e3mall.content.service;

import java.util.List;

import e3mall.common.pojo.EasyUIDataGridResult;
import e3mall.common.pojo.EasyUITreeNode;
import e3mall.common.utils.E3Result;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCatList(Long parentId);
	
	E3Result addContentCategory(Long parentId, String name);
	
}
