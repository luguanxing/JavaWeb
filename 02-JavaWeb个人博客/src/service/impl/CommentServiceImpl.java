package service.impl;

import dao.CommentDao;
import domain.Comment;
import domain.PageBean;
import service.CommentService;
import utils.BeanFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
public class CommentServiceImpl implements CommentService {
	
	@Override
	public PageBean<Comment> findByPage(int pageNumber, int pageSize) throws Exception {
		//新建对象
		CommentDao dao = (CommentDao) BeanFactory.getBean("CommentDao");
		PageBean<Comment> pageBean = new PageBean<>(pageNumber, pageSize);
		//设置当前页数据
		List<Comment> list = dao.findByPage(pageBean);
		pageBean.setData(list);
		//设置总记录数
		int totalRecord = dao.getTotalRecord();
		pageBean.setTotalRecord(totalRecord);
		return pageBean;
	}

	@Override
	public String checkformat(String comment, String nickname, String inputCaptcha) throws Exception {
		if (comment == null || nickname == null || inputCaptcha == null)
			return "信息不全";
		if (comment.trim().isEmpty() || nickname.trim().isEmpty() || inputCaptcha.trim().isEmpty())
			return "信息为空";
		if (nickname.length() > 10)
			return "昵称过长(<=10字)";
		if (comment.length() > 500)
			return "留言过长(<=500字)";
		return "success";
	}

	@Override
	public void addComment(Comment newcomment) throws Exception {
		CommentDao dao = (CommentDao) BeanFactory.getBean("CommentDao");
		dao.addComment(newcomment);
	}

}
