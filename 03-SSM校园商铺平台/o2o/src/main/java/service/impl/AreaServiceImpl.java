package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AreaDao;
import entity.Area;
import service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Area> getAreaList() {
		return areaDao.getAreaList();
	}

}
