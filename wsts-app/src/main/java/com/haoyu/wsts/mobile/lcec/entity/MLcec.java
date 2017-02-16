package com.haoyu.wsts.mobile.lcec.entity;

import java.io.Serializable;
import java.util.List;
import com.haoyu.sip.core.entity.User;
import com.haoyu.sip.mobile.file.entity.MFileInfo;

public class MLcec implements Serializable {

	private static final long serialVersionUID = 7905144686959516313L;
	
	
	private String id;
	private String title;
	private String content;
	private String textbook;
	private String stage;
	private String subject;
	private User teacher;
	private MFileInfo mVideo;
	private List<MFileInfo> mFileInfos;
	private String type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTextbook() {
		return textbook;
	}

	public void setTextbook(String textbook) {
		this.textbook = textbook;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public List<MFileInfo> getmFileInfos() {
		return mFileInfos;
	}

	public void setmFileInfos(List<MFileInfo> mFileInfos) {
		this.mFileInfos = mFileInfos;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MFileInfo getmVideo() {
		return mVideo;
	}

	public void setmVideo(MFileInfo mVideo) {
		this.mVideo = mVideo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
