package de.hska.eb.domain;

import java.util.List;

public class Commentable {
	
	private Integer commentableId;
	private List<Comment> comments;
	public Integer getCommentableId() {
		return commentableId;
	}
	public void setCommentableId(Integer commentableId) {
		this.commentableId = commentableId;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
