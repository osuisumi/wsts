package com.haoyu.wsts.mobile.workshop.entity;

import java.io.Serializable;

import com.haoyu.sip.core.entity.TimePeriod;

public class MWorkshopSection implements Serializable{

	private static final long serialVersionUID = -4348309371906457154L;
	
	private String id;

	private String title;

	private TimePeriod timePeriod = new TimePeriod();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	

}
