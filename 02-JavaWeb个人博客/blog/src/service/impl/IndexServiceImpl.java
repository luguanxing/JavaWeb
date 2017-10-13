package service.impl;

import dao.IndexDao;
import domain.Index;
import service.IndexService;
import utils.BeanFactory;

/**
 * Created by Administrator on 2017/10/14.
 */
public class IndexServiceImpl implements IndexService {

	@Override
	public Index getIndexContent() throws Exception {
		IndexDao dao = (IndexDao) BeanFactory.getBean("IndexDao");
		return dao.IndexContent();
	}
	
}
