package e3mall.service;

import java.util.List;

import e3mall.common.pojo.EasyUITreeNode;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatList(Long parentId);
	
}
