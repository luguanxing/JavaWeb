package service;

import domain.Article;
import domain.PageBean;
import domain.Project;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface ProjectService {

	PageBean<Project> findByPage(int pageNumber, int projectAdminpageCount) throws Exception;
	
	PageBean<Project> findByPage(int pageNumber, int projectPageCount, String type) throws Exception;

	Project getById(String pid) throws Exception;

	void update(Project project) throws Exception;

	List<Project> findTheLastest(int projectIndexCount) throws Exception;

	void save(Project project) throws Exception;

	void delete(String pid) throws Exception;
}
