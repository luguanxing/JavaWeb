package e3mall.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.mapper.TbOrderItemMapper;
import e3mall.mapper.TbOrderMapper;
import e3mall.mapper.TbOrderShippingMapper;
import e3mall.order.pojo.OrderInfo;
import e3mall.order.service.OrderService;
import e3mall.pojo.TbOrderItem;
import e3mall.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;
	
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		//生成订单及其相关
		if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setStatus(1); 	//1-未付款
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		orderMapper.insert(orderInfo);
		//订单明细
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String itemId = jedisClient.incr(ORDER_DETAIL_GEN_KEY).toString();
			tbOrderItem.setId(itemId);
			tbOrderItem.setOrderId(orderId);
			orderItemMapper.insert(tbOrderItem);
		}
		//订单物流
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		return E3Result.ok(orderId);
	}

}
