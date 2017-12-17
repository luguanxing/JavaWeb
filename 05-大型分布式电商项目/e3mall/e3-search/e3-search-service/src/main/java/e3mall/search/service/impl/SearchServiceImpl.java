package e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3mall.common.pojo.SearchResult;
import e3mall.search.dao.SearchDao;
import e3mall.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyword, Integer page, Integer rows) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery(keyword);
		if (page == null || page <= 0)
			page = 1;
		if (rows == null || rows <= 0)
			rows = 10;
		query.setStart((page-1)*rows);
		query.setRows(rows);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		SearchResult searchResult = searchDao.search(query);
		Integer totalPages = searchResult.getRecordCount().intValue() / rows;
		if (searchResult.getRecordCount().intValue() % rows > 0)
			totalPages++;
		searchResult.setTotalPages(totalPages);
		return searchResult;
	}

}
