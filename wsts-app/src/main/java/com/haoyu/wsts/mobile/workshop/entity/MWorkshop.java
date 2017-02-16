package com.haoyu.wsts.mobile.workshop.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.file.utils.FileUtils;
import com.haoyu.sip.user.mobile.entity.MUser;

public class MWorkshop implements Serializable {

	private static final long serialVersionUID = -1131707906803008314L;

	private String id;

	private String title;

	private String summary;

	private String imageUrl;

	private String type;

	private double qualifiedPoint = 0l;

	private int studentNum;

	private int activityNum;

	private int faqQuestionNum;

	private int resourceNum;

	private int memberNum;

	private String summaryExamine;

	private double studyHours = 0l;

	private List<MWorkshopSection> mWorkshopSections = Lists.newArrayList();

	private TimePeriod timePeriod;

	private String trainName;

	private long createTime;

	private MUser creator;

	private List<MUser> masters = Lists.newArrayList();

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageUrl() {
		String prefix = FileUtils.getHttpHost();
		if (imageUrl != null && !imageUrl.contains(prefix)) {
			imageUrl = prefix + imageUrl;
		}
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public List<MWorkshopSection> getmWorkshopSections() {
		return mWorkshopSections;
	}

	public void setmWorkshopSections(List<MWorkshopSection> mWorkshopSections) {
		this.mWorkshopSections = mWorkshopSections;
	}


	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public int getActivityNum() {
		return activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public int getFaqQuestionNum() {
		return faqQuestionNum;
	}

	public void setFaqQuestionNum(int faqQuestionNum) {
		this.faqQuestionNum = faqQuestionNum;
	}

	public int getResourceNum() {
		return resourceNum;
	}

	public void setResourceNum(int resourceNum) {
		this.resourceNum = resourceNum;
	}

	public String getSummaryExamine() {
		return summaryExamine;
	}

	public void setSummaryExamine(String summaryExamine) {
		this.summaryExamine = summaryExamine;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public MUser getCreator() {
		return creator;
	}

	public void setCreator(MUser creator) {
		this.creator = creator;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public List<MUser> getMasters() {
		return masters;
	}

	public void setMasters(List<MUser> masters) {
		this.masters = masters;
	}

	public double getQualifiedPoint() {
		return qualifiedPoint;
	}

	public void setQualifiedPoint(double qualifiedPoint) {
		this.qualifiedPoint = qualifiedPoint;
	}

	public double getStudyHours() {
		return studyHours;
	}

	public void setStudyHours(double studyHours) {
		this.studyHours = studyHours;
	}
	

}
