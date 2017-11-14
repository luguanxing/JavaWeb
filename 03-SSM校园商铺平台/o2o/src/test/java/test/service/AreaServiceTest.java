package test.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.o2o.entity.Area;
import com.o2o.service.AreaService;

import test.BaseTest;

/**
 * AreaService测试类，简单验证Service配置是否成功
 * @author LGX
 *
 */
public class AreaServiceTest extends BaseTest {

	@Autowired
	private AreaService areaService;
	
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getAreaList();
		System.out.println(areaList);
		assertEquals(areaList.size(), 2);
	}
	
}
