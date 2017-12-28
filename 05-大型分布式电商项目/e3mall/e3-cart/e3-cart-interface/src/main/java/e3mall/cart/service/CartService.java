package e3mall.cart.service;

import java.util.List;

import e3mall.common.utils.E3Result;
import e3mall.pojo.TbItem;

public interface CartService {

	E3Result addCart(Long userId, Long itemId, Integer num);
	
	E3Result mergeCart(Long userId, List<TbItem> itemList);
	
	List<TbItem> getCartList(Long userId);
	
	E3Result updateCartNum(Long userId, Long itemId, Integer num);
	
	E3Result deleteCartItem(Long userId, Long itemId);
	
}
