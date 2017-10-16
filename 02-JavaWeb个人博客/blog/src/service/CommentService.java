package service;

import domain.Comment;
import domain.PageBean;

/**
 * Created by Administrator on 2017/10/9.
 */
public interface CommentService {
	
	PageBean<Comment> findByPage(int pageNumber, int pageSize) throws Exception;

	String checkformat(String comment, String nickname, String inputCaptcha) throws Exception;

	void addComment(Comment newcomment) throws Exception;

	void deleteById(String cid) throws Exception;
	
}
