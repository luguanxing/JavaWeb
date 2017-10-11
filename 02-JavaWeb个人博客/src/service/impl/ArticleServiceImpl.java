package service.impl;

import dao.ArticleDao;
import domain.Article;
import domain.PageBean;
import service.ArticleService;
import utils.BeanFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public class ArticleServiceImpl implements ArticleService {

	@Override
	public PageBean<Article> findByPage(int pageNumber, int pageSize) throws Exception {
		//新建对象
		ArticleDao dao = (ArticleDao) BeanFactory.getBean("ArticleDao");
		PageBean<Article> pageBean = new PageBean<>(pageNumber, pageSize);
		//设置当前页数据
		List<Article> list = dao.findByPage(pageBean);
		pageBean.setData(list);
		//设置总记录数
		int totalRecord = dao.getTotalRecord();
		pageBean.setTotalRecord(totalRecord);
		return pageBean;
	}

	@Override
	public Article getById(String aid) throws Exception {
		ArticleDao dao = (ArticleDao) BeanFactory.getBean("ArticleDao");
		return dao.getById(aid);
	}

	@Override
	public void update(Article article) throws Exception {
		ArticleDao dao = (ArticleDao) BeanFactory.getBean("ArticleDao");
		dao.update(article);
	}

}
