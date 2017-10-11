package service;

import domain.PageBean;
import domain.Project;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface ProjectService {


	PageBean<Project> findByPage(int pageNumber, int projectPageCount, String type) throws Exception;

	Project getById(String pid) throws Exception;

	void update(Project project) throws Exception;
}
