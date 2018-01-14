package e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import e3mall.common.pojo.SearchItem;
import e3mall.common.pojo.SearchResult;

@Component
public class SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception {
		SearchResult result = new SearchResult();
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		result.setRecordCount(solrDocumentList.getNumFound());
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((Long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if (list != null && !list.isEmpty()) {
				item.setTitle(list.get(0));
			} else {
				item.setTitle((String) solrDocument.get("item_title"));
			}
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
	}
	
}
