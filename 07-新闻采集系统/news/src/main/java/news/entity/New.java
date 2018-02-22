package news.entity;

import java.util.Date;

public class New {
    private Integer id;

    private String title;

    private String content;

    private String contentText;

    private String publishDateAndSrc;

    private Date crawlerDate;

    private String url;

    private Integer state;

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
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText == null ? null : contentText.trim();
    }

    public String getPublishDateAndSrc() {
        return publishDateAndSrc;
    }

    public void setPublishDateAndSrc(String publishDateAndSrc) {
        this.publishDateAndSrc = publishDateAndSrc == null ? null : publishDateAndSrc.trim();
    }

    public Date getCrawlerDate() {
        return crawlerDate;
    }

    public void setCrawlerDate(Date crawlerDate) {
        this.crawlerDate = crawlerDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}