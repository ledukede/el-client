package de.hska.eb.domain;

import java.util.Date;

public class Comment {
	
	private Integer commentId;
	private Commentable commentable;
	private User user; //poster
	private Date date;
	private String message;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Commentable getCommentable() {
		return commentable;
	}
	public void setCommentable(Commentable commentable) {
		this.commentable = commentable;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
