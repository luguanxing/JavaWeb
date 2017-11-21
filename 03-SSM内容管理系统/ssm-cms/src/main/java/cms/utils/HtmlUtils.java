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
	public static String getArticleNavigation(String typeName, Integer typeId, String articleTitle, String projectContext) {
		StringBuffer sb = new StringBuffer();
		sb.append("当前位置：&nbsp;&nbsp;");
		//有域名可使用properties代替
		sb.append("<a href='" +projectContext + "'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		sb.append("<a href='" +projectContext + "/arcType/"+typeId+".html'>"+typeName+"</a>&nbsp;&nbsp;>&nbsp;&nbsp;"+articleTitle);
		return sb.toString();
	}
	
	/**
	 * 生成上一页下一页
	 * @param lastArticle
	 * @param nextArticleDao
	 * @param projectContext
	 * @return
	 */
	public static String getUpAndDownCode(Article lastArticle, Article nextArticle, String projectContext) {
		StringBuffer sb = new StringBuffer();
		if (lastArticle == null || lastArticle.getId() == null) {
			sb.append("<p>").append("上一篇：没有了").append("</p>");
		} else {
			sb.append("<p>")
			.append("上一篇：<a href='"+projectContext+"/article/"+lastArticle.getId()+".html'>"+lastArticle.getTitle()+"</a>")
			.append("</p>");
		}
		if (nextArticle == null || nextArticle.getId() == null) {
			sb.append("<p>").append("下一篇：没有了").append("</p>");
		} else {
			sb.append("<p>")
			.append("下一篇：<a href='"+projectContext+"/article/"+nextArticle.getId()+".html'>"+nextArticle.getTitle()+"</a>")
			.append("</p>");
		}
		return sb.toString();
	}
	
}
