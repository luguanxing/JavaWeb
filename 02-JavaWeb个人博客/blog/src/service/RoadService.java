package service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface RoadService {

	List getAll() throws Exception;

	Map getAllByMap() throws Exception;
	
}
