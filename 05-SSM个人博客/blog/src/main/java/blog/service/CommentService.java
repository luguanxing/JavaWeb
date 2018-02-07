package blog.service;

import java.util.List;

import blog.entity.Comment;

public interface CommentService {

	public List<Comment> getComments(Integer pageNum, Integer pageSize);
	
	public Integer getCommentsCount();

	public void add(Comment comment);

	public Boolean delete(Integer id);
	
}
