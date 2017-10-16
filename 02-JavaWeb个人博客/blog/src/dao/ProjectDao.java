package dao;

import domain.PageBean;
import domain.Project;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface ProjectDao {
	
	List<Project> findByPage(PageBean<Project> pageBean) throws Exception;
	
	List<Project> findByPage(PageBean<Project> pageBean, String type) throws Exception;

	int getTotalRecord() throws Exception;

	Project getById(String pid) throws Exception;

	void update(Project project) throws Exception;

	List<Project> findTheLastest(int projectIndexCount) throws Exception;

	void save(Project project) throws Exception;

	void delete(String pid) throws Exception;
}
