package dao;

import domain.Index;

/**
 * Created by Administrator on 2017/10/14.
 */
public interface IndexDao {
	
	Index IndexContent() throws Exception;

	void update(Index index) throws Exception;
	
}
