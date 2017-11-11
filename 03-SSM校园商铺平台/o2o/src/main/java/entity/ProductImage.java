package entity;

import java.util.Date;

public class ProductImage {

	private Long productImageId;
	private String imageAddress;
	private String imageDescription;
	private Integer priority;
	private Date createTime;
	private Long productId;
	
	public Long getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(Long productImageId) {
		this.productImageId = productImageId;
	}
	public String getImageAddress() {
		return imageAddress;
	}
	public void setImageAddress(String imageAddress) {
		this.imageAddress = imageAddress;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}
