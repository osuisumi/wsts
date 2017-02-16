package com.haoyu.wsts.mobile.lcec.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class MEvaluateItem implements Serializable {

	private static final long serialVersionUID = -3292515655279697217L;

	private String id;

	private String content;
	
	private double avgScore = 0l;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}


	
	

}
