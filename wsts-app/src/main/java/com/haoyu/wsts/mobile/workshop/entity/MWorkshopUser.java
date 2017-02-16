package com.haoyu.wsts.mobile.workshop.entity;

import java.io.Serializable;

import com.haoyu.sip.user.mobile.entity.MUser;

public class MWorkshopUser implements Serializable {

	private static final long serialVersionUID = -7329558515488112571L;
	
	private String id;
	private MWorkshop mWorkshop;
	private MUser mUser;
	private String role;
	private String evaluate;
	private MUser evaluateCreator;
	private String finallyResult;
	private double point = 0l;
	private int completeActivityNum;
	private int faqQuestionNum;
	private int uploadResourceNum;
	
	private String state;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MWorkshop getmWorkshop() {
		return mWorkshop;
	}

	public void setmWorkshop(MWorkshop mWorkshop) {
		this.mWorkshop = mWorkshop;
	}

	public MUser getmUser() {
		return mUser;
	}

	public void setmUser(MUser mUser) {
		this.mUser = mUser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public int getCompleteActivityNum() {
		return completeActivityNum;
	}

	public void setCompleteActivityNum(int completeActivityNum) {
		this.completeActivityNum = completeActivityNum;
	}

	public int getFaqQuestionNum() {
		return faqQuestionNum;
	}

	public void setFaqQuestionNum(int faqQuestionNum) {
		this.faqQuestionNum = faqQuestionNum;
	}

	public int getUploadResourceNum() {
		return uploadResourceNum;
	}

	public void setUploadResourceNum(int uploadResourceNum) {
		this.uploadResourceNum = uploadResourceNum;
	}

	public String getFinallyResult() {
		return finallyResult;
	}

	public void setFinallyResult(String finallyResult) {
		this.finallyResult = finallyResult;
	}

	public MUser getEvaluateCreator() {
		return evaluateCreator;
	}

	public void setEvaluateCreator(MUser evaluateCreator) {
		this.evaluateCreator = evaluateCreator;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
	

}
