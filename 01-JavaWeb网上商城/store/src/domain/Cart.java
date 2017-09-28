package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/26.
 */
public class Cart {
	
	private Map<String, CartItem> itemMap = new HashMap<String, CartItem>();
	private double total = 0.0;
	
	public Collection<CartItem> getCartItems() {
		return itemMap.values();
	}
	
	public void addToCart(CartItem item) {
		String pid = item.getProduct().getPid();
		if (itemMap.containsKey(pid)) {
			//已在购物车
			CartItem item_old = itemMap.get(pid);
			item_old.setCount(item_old.getCount() + item.getCount());
		} else {
			//还未在购物车
			itemMap.put(pid, item);
		}
		total += item.getSubtotal();
	}
	
	public void removeFromCart(String pid) {
		CartItem item = itemMap.remove(pid);
		total -= item.getSubtotal();
	}
	
	public void clearCart() {
		itemMap.clear();
		total = 0;
	}
	
	public Map<String, CartItem> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, CartItem> itemMap) {
		this.itemMap = itemMap;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
