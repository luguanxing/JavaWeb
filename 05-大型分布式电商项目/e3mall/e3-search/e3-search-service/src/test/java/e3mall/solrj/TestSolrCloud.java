package e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

	@Test
	public void testAddDocument() throws Exception {
		//创建一个集群的连接,应该使用CloudSolrServer,但需要初始化参数zookeeper地址列表
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
		//设置一个defaultCollection属性
		solrServer.setDefaultCollection("collection2");
		//之后操作和solrj一致
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", "SolrCloud01");
		document.setField("item_title", "测试商品01");
		document.setField("item_price", 123);
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void testQueryDocument() throws Exception {
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
		solrServer.setDefaultCollection("collection2");
		SolrQuery query = new SolrQuery();
		query.setQuery("id:SolrCloud01");
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("找到"+solrDocumentList.getNumFound()+"条记录");
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	
}
