package dao;

import domain.Comment;
import domain.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
public interface CommentDao {
	
	List<Comment> findByPage(PageBean<Comment> pageBean) throws Exception;

	int getTotalRecord() throws Exception;

	void addComment(Comment newcomment) throws Exception;
}
