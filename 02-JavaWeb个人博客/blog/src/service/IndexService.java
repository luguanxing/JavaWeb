package service;

import domain.Index;

/**
 * Created by Administrator on 2017/10/14.
 */
public interface IndexService {

	Index getIndexContent() throws Exception;

	void update(Index index) throws Exception;
	
}
