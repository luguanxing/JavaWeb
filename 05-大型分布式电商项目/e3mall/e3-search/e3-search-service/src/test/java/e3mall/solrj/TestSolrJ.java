package e3mall.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		solrServer.deleteByQuery("id:doc01");
		solrServer.commit();
	}
	
}
