package e3mall.solrj;

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

public class TestSolrJ {

	@Test
	public void addDocument() throws Exception {
		//创建SolrServer创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建文档
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域(包含id且在配置文件schema.xml中)
		document.addField("id", "doc01");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 10000);
		//写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception {
		//创建SolrServer创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//设置条件并删除
		solrServer.deleteByQuery("id:doc01");
		//提交
		solrServer.commit();
	}
	
	@Test
	public void queryIndex() throws Exception {
		//创建SolrServer创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
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
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
	
	@Test
	public void queryIndexFuza() throws Exception {
		//创建SolrServer创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//设置查询条件
		SolrQuery query = new SolrQuery();
		query.set("q", "手机");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
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
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if (list != null && !list.isEmpty()) {
				String hightLightTitle = list.get(0);
				System.out.println(hightLightTitle);
			} else {
				String defaultTitle = solrDocument.get("item_title").toString();
				System.out.println(defaultTitle);
			}
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
}
