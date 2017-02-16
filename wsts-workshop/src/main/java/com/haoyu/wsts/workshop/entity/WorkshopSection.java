package com.haoyu.wsts.workshop.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.sip.core.entity.BaseEntity;
import com.haoyu.sip.core.entity.TimePeriod;

public class WorkshopSection extends BaseEntity implements Comparable<WorkshopSection>{
	private static final long serialVersionUID = -4056544461351970733L;

	private String id;

	private String workshopId;

	private int sortNum;

	private String title;

	private String parentId;

	private TimePeriod timePeriod = new TimePeriod();

	private List<WorkshopSection> childrens = Lists.newArrayList();
	
	private List<Activity> activities = Lists.newArrayList();

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<WorkshopSection> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<WorkshopSection> childrens) {
		this.childrens = childrens;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(String workshopId) {
		this.workshopId = workshopId;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	@Override
	public int compareTo(WorkshopSection o) {
		if(this.sortNum != o.sortNum){
			return this.sortNum - o.sortNum;
		}else{
			return new Long(this.createTime - o.createTime).intValue();
		}
	}
}
