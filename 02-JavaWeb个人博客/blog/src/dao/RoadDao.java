package dao;

import domain.RoadItem;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface RoadDao {
	
	List getAll() throws Exception;

	RoadItem getById(String rid) throws Exception;

	void save(RoadItem roadItem) throws Exception;

	void update(RoadItem roadItem) throws Exception;
	
	void deleteById(String rid) throws Exception;
	
}
