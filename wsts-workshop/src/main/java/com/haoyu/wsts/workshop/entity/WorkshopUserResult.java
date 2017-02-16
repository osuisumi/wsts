package com.haoyu.wsts.workshop.entity;


import com.haoyu.sip.core.entity.BaseEntity;
import com.haoyu.sip.core.entity.User;

public class WorkshopUserResult extends BaseEntity {

	private static final long serialVersionUID = -8832337857891769659L;

	private String id;

	private WorkshopUser workshopUser;

	private Float learnMinuteLength;

	private Float point;

	private String workshopResult;

	private User workshopResultCreator;

	private String finallyResult;
	
	public WorkshopUserResult(){}
	
	public WorkshopUserResult(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WorkshopUser getWorkshopUser() {
		return workshopUser;
	}

	public void setWorkshopUser(WorkshopUser workshopUser) {
		this.workshopUser = workshopUser;
	}

	public Float getLearnMinuteLength() {
		return learnMinuteLength;
	}

	public void setLearnMinuteLength(Float learnMinuteLength) {
		this.learnMinuteLength = learnMinuteLength;
	}

	public Float getPoint() {
		return point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	public String getWorkshopResult() {
		return workshopResult;
	}

	public void setWorkshopResult(String workshopResult) {
		this.workshopResult = workshopResult;
	}

	public User getWorkshopResultCreator() {
		return workshopResultCreator;
	}

	public void setWorkshopResultCreator(User workshopResultCreator) {
		this.workshopResultCreator = workshopResultCreator;
	}

	public String getFinallyResult() {
		return finallyResult;
	}

	public void setFinallyResult(String finallyResult) {
		this.finallyResult = finallyResult;
	}
	
}
