package blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import blog.dao.CommentDao;
import blog.entity.Comment;
import blog.entity.CommentExample;
import blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<Comment> getComments(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		CommentExample example = new CommentExample();
		example.setOrderByClause("commentDate desc");
		List<Comment> comments = commentDao.selectByExample(example);
		return comments;
	}

	@Override
	public Integer getCommentsCount() {
		CommentExample example = new CommentExample();
		example.createCriteria();
		int count = commentDao.countByExample(example);
		return count;
	}

	@Override
	public void add(Comment comment) {
		commentDao.insert(comment);
	}

	@Override
	public Boolean delete(Integer id) {
		int count = commentDao.deleteByPrimaryKey(id);
		return (count > 0);
	}
	
}
