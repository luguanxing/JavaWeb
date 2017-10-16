package service;

import domain.RoadItem;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface RoadService {

	List getAll() throws Exception;

	Map getAllByMap() throws Exception;

	RoadItem getById(String rid) throws Exception;

	void save(RoadItem roadItem) throws Exception;

	void update(RoadItem roadItem) throws Exception;

	void deleteById(String rid) throws Exception;
	
}
