package e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import e3mall.cart.service.CartService;
import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.mapper.TbItemMapper;
import e3mall.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	
	@Override
	public E3Result addCart(Long userId, Long itemId, Integer num) {
		//商品存在数量相加,否则查后添加
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId.toString());
		if (hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId.toString());
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			item.setNum(item.getNum() + num);
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, item.getId().toString(), JsonUtils.objectToJson(item));
			return E3Result.ok();
		}
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		item.setNum(num);
		String images = item.getImage();
		if (StringUtils.isNotBlank(images)) {
			item.setImage(images.split(",")[0]);
		}
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, item.getId().toString(), JsonUtils.objectToJson(item));
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(Long userId, List<TbItem> itemList) {
		//遍历商品列表有数量相加没有查后添加
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();
	}

	@Override
	public List<TbItem> getCartList(Long userId) {
		//取redis中hash数据
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> itemList = new ArrayList();
		for (String json : jsonList) {
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public E3Result updateCartNum(Long userId, Long itemId, Integer num) {
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId.toString());
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		tbItem.setNum(num);
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId.toString(), JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(Long userId, Long itemId) {
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId.toString());
		return E3Result.ok();
	}

}
