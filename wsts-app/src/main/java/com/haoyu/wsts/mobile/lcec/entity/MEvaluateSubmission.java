package com.haoyu.wsts.mobile.lcec.entity;

import java.io.Serializable;

import com.haoyu.sip.core.entity.User;

public class MEvaluateSubmission implements Serializable{

	private static final long serialVersionUID = 7107603181636428582L;
	
	private String id;
	
	private String comment;
	
	private User creator;
	
	private long createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	

	

	
	
	

}
