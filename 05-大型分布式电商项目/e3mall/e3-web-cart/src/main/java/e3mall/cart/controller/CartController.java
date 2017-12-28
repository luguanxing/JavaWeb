package e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.CookieUtils;

import e3mall.cart.service.CartService;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.pojo.TbItem;
import e3mall.pojo.TbUser;
import e3mall.service.ItemService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isBlank(json)) {
			return new ArrayList();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	@RequestMapping("/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//判断是否登录,登录的话购物车保存到redis
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}
		//1、从cookie中查询商品列表。
		//2、判断商品在商品列表中是否存在。
		//3、如果存在，商品数量相加。
		//4、不存在，根据商品id查询商品信息，得TbItem对象(内部属性定义有改动)。
		//5、把商品添加到购车列表。
		//6、把购车商品列表写入cookie。
		List<TbItem> cartList = getCartListFromCookie(request);
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			//注意两个Long对象不能直接比较值,应使用longValue
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(tbItem.getNum() + num);
				flag = true;
				break;
			}
		}
		if (!flag) {
			TbItem tbItem = itemService.getItemById(itemId);
			tbItem.setNum(num);
			String images = tbItem.getImage();
			if (StringUtils.isNoneBlank(images)) {
				tbItem.setImage(images.split(",")[0]);
			}
			cartList.add(tbItem);
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String showCartUI(HttpServletRequest request, HttpServletResponse response) {
		//判断是否登录,登录了则从redis取出购物车合并
		List<TbItem> cartList = getCartListFromCookie(request);
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.mergeCart(user.getId(), cartList);
			CookieUtils.deleteCookie(request, response, "cart");
			cartList = cartService.getCartList(user.getId());
		}
		request.setAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//判断是否登录,登录了则对redis更新
		List<TbItem> cartList = getCartListFromCookie(request);
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				tbItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return E3Result.ok();
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		//判断是否登录,登录了则对redis删除
		List<TbItem> cartList = getCartListFromCookie(request);
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				cartList.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return "redirect:/cart/cart.html";
	}
	
}
