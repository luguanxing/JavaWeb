package constant;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface Constant {
	
	//用户未激活
	int USER_IS_NOT_ACTIVE = 0;

	//用户已激活
	int USER_IS_ACTIVE = 1;
	
	//记住用户名
	String IS_SAVE_NAME = "ok";
	
	//商品热门
	int PRODUCT_IS_HOT = 1;

	//商品未下架
	int PRODUCT_IS_UP = 0;
	
	//商品下架
	int PRODUCT_IS_DOWN = 1;

	//显示热门最新商品数
	int PRODUCT_COUNT = 9;

	//显示每页商品数
	int PRODUCT_PAGE_COUNT = 12;

	//显示每页订单数
	int ORDER_PAGE_COUNT = 3;
	
	//订单未付款
	int ORDER_UNPAID = 0;
	
	//订单已付款，待审核
	int ORDER_PAID = 1;
	
	//订单已发货
	int ORDER_DELIVERED = 2;
	
	//订单已完成
	int ORDER_FINISHED = 3;

	//订单审核不过
	int ORDER_FAILED = -1;
}
