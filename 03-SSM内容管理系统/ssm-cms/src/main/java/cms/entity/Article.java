package cms.entity;

import java.util.Date;

/**
 * 帖子实体类
 * @author LGX
 *
 */
public class Article {

	private Integer id;
	private String title;
	private Date publishDate;
	private String content;
	private String summary;
	private String titleColor;
	private Integer click = 0;
	private Integer isRecommend = 0;
	private Integer isSlide = 0;
	private ArcType arcType;
	private String keyWords;
	private String slideImage;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTitleColor() {
		return titleColor;
	}
	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getIsSlide() {
		return isSlide;
	}
	public void setIsSlide(Integer isSlide) {
		this.isSlide = isSlide;
	}
	public ArcType getArcType() {
		return arcType;
	}
	public void setArcType(ArcType arcType) {
		this.arcType = arcType;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getSlideImage() {
		return slideImage;
	}
	public void setSlideImage(String slideImage) {
		this.slideImage = slideImage;
	}
	
}
