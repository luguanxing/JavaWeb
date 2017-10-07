package service.impl;

import dao.RoadDao;
import domain.RoadItem;
import service.RoadService;
import utils.BeanFactory;

import java.util.*;

/**
 * Created by Administrator on 2017/10/7.
 */
public class RoadServiceImpl implements RoadService {

	@Override
	public List getAll() throws Exception {
		RoadDao dao = (RoadDao) BeanFactory.getBean("RoadDao");
		return dao.getAll();
	}

	@Override
	public Map getAllByMap() throws Exception {
		List<RoadItem> list = getAll();
		Map<String, List<RoadItem>> map = new HashMap<String, List<RoadItem>>();
		for(RoadItem item : list) {
			String year = item.getYear();
			List<RoadItem> list_year = map.get(year);
			if (list_year == null)
				list_year = new LinkedList<RoadItem>();
			list_year.add(item);
			map.put(year, list_year);
		}
		return map;
	}

}
