package com.o2o.entity;

import java.util.Date;

public class ShopCategory {

	private Long shopCategoryId;
	private String shopCategoryName;
	private String shopCategoryDescription;
	private String shopCategoryImage;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private ShopCategory parent;
	
	public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getShopCategoryDescription() {
		return shopCategoryDescription;
	}
	public void setShopCategoryDescription(String shopCategoryDescription) {
		this.shopCategoryDescription = shopCategoryDescription;
	}
	public String getShopCategoryImage() {
		return shopCategoryImage;
	}
	public void setShopCategoryImage(String shopCategoryImage) {
		this.shopCategoryImage = shopCategoryImage;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}
	
}
