package cms.utils;

import cms.entity.Article;

/**
 * 页面工具类
 * @author LGX
 *
 */
public class HtmlUtils {
	
	/**
	 * 生成帖子导航
	 * @param typeName
	 * @param typeId
	 * @param articleTitle
	 * @return
	 */
	public static String getArticleNavigation(String typeName, Integer typeId, String articleTitle, String contextPath) {
		StringBuffer sb = new StringBuffer();
		sb.append("当前位置：&nbsp;&nbsp;");
		//有域名可使用properties代替
		sb.append("<a href='" +contextPath + "'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		sb.append("<a href='" +contextPath + "/arcType/"+typeId+".html'>"+typeName+"</a>&nbsp;&nbsp;>&nbsp;&nbsp;"+articleTitle);
		return sb.toString();
	}
	
	/**
	 * 生成帖子上一页下一页
	 * @param lastArticle
	 * @param nextArticleDao
	 * @param projectContext
	 * @return
	 */
	public static String getArticleUpAndDownCode(Article lastArticle, Article nextArticle, String contextPath) {
		StringBuffer sb = new StringBuffer();
		if (lastArticle == null || lastArticle.getId() == null) {
			sb.append("<p>").append("上一篇：没有了").append("</p>");
		} else {
			sb.append("<p>")
			.append("上一篇：<a href='"+contextPath+"/article/"+lastArticle.getId()+".html'>"+lastArticle.getTitle()+"</a>")
			.append("</p>");
		}
		if (nextArticle == null || nextArticle.getId() == null) {
			sb.append("<p>").append("下一篇：没有了").append("</p>");
		} else {
			sb.append("<p>")
			.append("下一篇：<a href='"+contextPath+"/article/"+nextArticle.getId()+".html'>"+nextArticle.getTitle()+"</a>")
			.append("</p>");
		}
		return sb.toString();
	}
	
	/**
	 * 生成帖子类别导航
	 * @param typeName
	 * @return
	 */
	public static String getArticleListNavigation(String typeName, String contextPath) {
		StringBuffer sb = new StringBuffer();
		sb.append("当前位置：&nbsp;&nbsp;");
		sb.append("<a href='" +contextPath + "'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		sb.append(typeName);
		return sb.toString();
	}
	
	/**
	 * 生成帖子类别上一页下一页
	 * @param totalNum
	 * @param currentPage
	 * @param pageSize
	 * @param typeId
	 * @return
	 */
	public static String getArctypeUpAndDownCode(int totalNum, int currentPage, int pageSize, int typeId, String contextPath) {
		int totalPage = (totalNum%pageSize==0) ? totalNum/pageSize :  totalNum/pageSize+1;
		StringBuffer sb = new StringBuffer();
		if (currentPage == 1) {
			sb.append("<a>上一页</a>");
		} else {
			sb.append("<a href='"+contextPath+"/arcType/"+typeId+".html?page="+(currentPage-1)+"'>上一页</a>");
		}
		sb.append("&nbsp;&nbsp;");
		if(currentPage==totalPage){
			sb.append("<a>下一页</a>");
		}else{
			sb.append("<a href='"+contextPath+"/arcType/"+typeId+".html?page="+(currentPage+1)+"'>下一页</a>");
		}
		return sb.toString();
	}
	
}
