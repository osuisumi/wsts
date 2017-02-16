package com.haoyu.wsts.workshop.entity;

import com.haoyu.sip.core.entity.BaseEntity;
import com.haoyu.sip.core.entity.Relation;

public class WorkshopRelation extends BaseEntity {
	private static final long serialVersionUID = 138216662582296230L;

	private String id;

	private String workshopId;

	private Relation relation;

	private int memberNum;

	private int studentNum;

	private int activityNum;

	private int resourceNum;

	private int questionNum;

	private int answerNum;

	private int commentsNum;

	private int announcementNum;

	private int solutionNum;

	private int qualifiedStudentNum;

	private int completeActivityNum;

	private int completeVideoNum;

	private int completeDiscussionNum;

	private int completeLessonPlanNum;

	private int completeLcecNum;

	private int completeTestNum;

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

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public int getActivityNum() {
		return activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public int getResourceNum() {
		return resourceNum;
	}

	public void setResourceNum(int resourceNum) {
		this.resourceNum = resourceNum;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public int getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(int commentsNum) {
		this.commentsNum = commentsNum;
	}

	public int getAnnouncementNum() {
		return announcementNum;
	}

	public void setAnnouncementNum(int announcementNum) {
		this.announcementNum = announcementNum;
	}

	public int getSolutionNum() {
		return solutionNum;
	}

	public void setSolutionNum(int solutionNum) {
		this.solutionNum = solutionNum;
	}

	public int getQualifiedStudentNum() {
		return qualifiedStudentNum;
	}

	public void setQualifiedStudentNum(int qualifiedStudentNum) {
		this.qualifiedStudentNum = qualifiedStudentNum;
	}

	public int getCompleteVideoNum() {
		return completeVideoNum;
	}

	public void setCompleteVideoNum(int completeVideoNum) {
		this.completeVideoNum = completeVideoNum;
	}

	public int getCompleteDiscussionNum() {
		return completeDiscussionNum;
	}

	public void setCompleteDiscussionNum(int completeDiscussionNum) {
		this.completeDiscussionNum = completeDiscussionNum;
	}

	public int getCompleteLessonPlanNum() {
		return completeLessonPlanNum;
	}

	public void setCompleteLessonPlanNum(int completeLessonPlanNum) {
		this.completeLessonPlanNum = completeLessonPlanNum;
	}

	public int getCompleteLcecNum() {
		return completeLcecNum;
	}

	public void setCompleteLcecNum(int completeLcecNum) {
		this.completeLcecNum = completeLcecNum;
	}

	public int getCompleteTestNum() {
		return completeTestNum;
	}

	public void setCompleteTestNum(int completeTestNum) {
		this.completeTestNum = completeTestNum;
	}

	public int getCompleteActivityNum() {
		return completeActivityNum;
	}

	public void setCompleteActivityNum(int completeActivityNum) {
		this.completeActivityNum = completeActivityNum;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}

}
