package e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import e3mall.item.pojo.Item;
import e3mall.pojo.TbItem;
import e3mall.pojo.TbItemDesc;
import e3mall.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService ItemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		TbItem tbItem = ItemService.getItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = ItemService.getItemDescByID(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	
}
