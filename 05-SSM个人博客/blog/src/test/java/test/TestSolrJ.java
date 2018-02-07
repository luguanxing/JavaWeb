package test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blog.service.BlogService;

//junit启动spring容器注解，不用手动创建容器了
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   ({"/spring/spring-*.xml"}) //加载配置文件  
public class TestSolrJ {

	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private BlogService blogService;
	
	@Test
	public void addDocument() throws Exception {
		//创建文档
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域(包含id且在配置文件schema.xml中)
		document.addField("id", "doc01");
		document.addField("blog_title", "测试博客01");
		document.addField("blog_summary", "测试测试测试");
		document.addField("blog_content", "测试博客博客博客博客博客");
		//写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception {
		//设置条件并删除
		solrServer.deleteByQuery("id:doc01");
		//提交
		solrServer.commit();
	}
	
	@Test
	public void queryIndex() throws Exception {
		//设置查询条件
		SolrQuery query = new SolrQuery();
		query.set("q", "*:*");
		//执行查询,获取QueryResponse对象
		QueryResponse queryResponse = solrServer.query(query);
		//取文档列表,取总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("总记录数="+solrDocumentList.getNumFound());
		//遍历文档列表,取出域内容
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println();
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("blog_title"));
			System.out.println(solrDocument.get("blog_summary"));
			System.out.println(solrDocument.get("blog_content"));
		}
	}
	
	@Test
	public void queryIndexFuza() throws Exception {
		//设置查询条件
		SolrQuery query = new SolrQuery();
		query.set("q", "测试");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "blog_keyword");
		query.setHighlight(true);
		query.addHighlightField("blog_title");
		query.addHighlightField("blog_summary");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//执行查询,获取QueryResponse对象
		QueryResponse queryResponse = solrServer.query(query);
		//取文档列表,取总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("总记录数="+solrDocumentList.getNumFound());
		//遍历文档列表,取出域内容
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			//取高亮
			List<String> list1 = highlighting.get(solrDocument.get("id")).get("blog_title");
			if (list1 != null && !list1.isEmpty()) {
				String hightLightTitle = list1.get(0);
				System.out.println(hightLightTitle);
			} else {
				String defaultTitle = solrDocument.get("blog_title").toString();
				System.out.println(defaultTitle);
			}
			List<String> list2 = highlighting.get(solrDocument.get("id")).get("blog_summary");
			if (list2 != null && !list2.isEmpty()) {
				String hightLightTitle = list2.get(0);
				System.out.println(hightLightTitle);
			} else {
				String defaultTitle = solrDocument.get("blog_summary").toString();
				System.out.println(defaultTitle);
			}
			System.out.println(solrDocument.get("blog_content"));
		}
	}
	
	@Test
	public void importData() {
		System.out.println(blogService.importAllBlogs());;
	}
	
}
