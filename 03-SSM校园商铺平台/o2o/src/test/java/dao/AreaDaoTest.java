package dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.Area;

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
