package service.impl;

import dao.ProjectDao;
import domain.PageBean;
import domain.Project;
import service.ProjectService;
import utils.BeanFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
public class ProjectServiceImpl implements ProjectService {

	@Override
	public PageBean<Project> findByPage(int pageNumber, int pageSize) throws Exception {
		//新建对象
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		PageBean<Project> pageBean = new PageBean<>(pageNumber, pageSize);
		//设置当前页数据
		List<Project> list = dao.findByPage(pageBean);
		pageBean.setData(list);
		//设置总记录数
		int totalRecord = dao.getTotalRecord();
		pageBean.setTotalRecord(totalRecord);
		return pageBean;
	}

	@Override
	public PageBean<Project> findByPage(int pageNumber, int pageSize, String type) throws Exception {
		//新建对象
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		PageBean<Project> pageBean = new PageBean<>(pageNumber, pageSize);
		//设置当前页数据
		List<Project> list = dao.findByPage(pageBean, type);
		pageBean.setData(list);
		//设置总记录数
		int totalRecord = dao.getTotalRecord();
		pageBean.setTotalRecord(totalRecord);
		return pageBean;
	}

	@Override
	public Project getById(String pid) throws Exception {
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		return dao.getById(pid);
	}

	@Override
	public void update(Project project) throws Exception {
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		dao.update(project);
	}

	@Override
	public List<Project> findTheLastest(int projectIndexCount) throws Exception {
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		return dao.findTheLastest(projectIndexCount);
	}

	@Override
	public void save(Project project) throws Exception {
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		dao.save(project);
	}

	@Override
	public void delete(String pid) throws Exception {
		ProjectDao dao = (ProjectDao) BeanFactory.getBean("ProjectDao");
		dao.delete(pid);
	}

}
