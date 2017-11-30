package e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3mall.mapper.TbItemMapper;
import e3mall.pojo.TbItem;
import e3mall.pojo.TbItemExample;
import e3mall.pojo.TbItemExample.Criteria;
import e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getByItemId(long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

}
