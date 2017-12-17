package e3mall.search.service;

import e3mall.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String keyword, Integer page, Integer rows) throws Exception;
	
}
