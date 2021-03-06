package e3mall.service;

import e3mall.common.pojo.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.pojo.TbItem;
import e3mall.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItemById(long itemId);
	
	EasyUIDataGridResult getItemList(int page, int rows);
	
	E3Result addItem(TbItem item, String desc);
	
	TbItemDesc getItemDescByID(long itemId);
	
}
