package e3mall.order.service;

import e3mall.common.utils.E3Result;
import e3mall.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
	
}
