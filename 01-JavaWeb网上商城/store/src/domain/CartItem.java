package domain;

/**
 * Created by Administrator on 2017/9/26.
 */
public class CartItem {
	
	private Product product;	//商品对象
	private Integer count;		//商品数量

	public CartItem(Product product, Integer count) {
		this.product = product;
		this.count = count;
	}

	public double getSubtotal() {	//商品小计
		return product.getShop_price() * count;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
