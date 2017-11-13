package test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dao.AreaDao;
import entity.Area;
import test.BaseTest;

/**
 * AreaDao测试类，简单验证Dao配置是否成功
 * @author LGX
 *
 */
public class AreaDaoTest extends BaseTest {

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaDao.getAreaList();
		System.out.println(areaList);
		assertEquals(areaList.size(), 2);
	}
	
}
