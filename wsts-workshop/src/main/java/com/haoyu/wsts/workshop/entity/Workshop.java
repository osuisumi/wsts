package com.haoyu.wsts.workshop.entity;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.haoyu.sip.core.entity.BaseEntity;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.file.entity.FileInfo;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;

public class Workshop extends BaseEntity {
	private static final long serialVersionUID = -7016269006820979706L;

	private String id;

	private String title;

	private String summary;

	private String termNo;

	private String isTemplate;

	private String sourceId;

	private int maxStudentNum;

	private String imageUrl;

	private String qrcodeUrl;

	private String type;

	private String state;

	private Float qualifiedPoint;

	private String summaryNotice;

	private String summaryTarget;

	private String summaryExamine;

	private TimePeriod timePeriod;

	private Float hourPerweek;

	private String subject;

	private String stage;

	private BigDecimal studyHours;

	private WorkshopRelation workshopRelation;

	private List<WorkshopSection> workshopSections;

	private List<WorkshopUser> workshopUsers = Lists.newArrayList();

	private List<WorkshopUser> masters = null;

	private List<WorkshopUser> members = null;

	private List<WorkshopUser> students = null;

	private FileInfo image;

	private List<FileInfo> solutions;

	private String trainName;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

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

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public int getMaxStudentNum() {
		return maxStudentNum;
	}

	public void setMaxStudentNum(int maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSummaryNotice() {
		return summaryNotice;
	}

	public void setSummaryNotice(String summaryNotice) {
		this.summaryNotice = summaryNotice;
	}

	public String getSummaryTarget() {
		return summaryTarget;
	}

	public void setSummaryTarget(String summaryTarget) {
		this.summaryTarget = summaryTarget;
	}

	public String getSummaryExamine() {
		return summaryExamine;
	}

	public void setSummaryExamine(String summaryExamine) {
		this.summaryExamine = summaryExamine;
	}

	public WorkshopRelation getWorkshopRelation() {
		return workshopRelation;
	}

	public void setWorkshopRelation(WorkshopRelation workshopRelation) {
		this.workshopRelation = workshopRelation;
	}

	public List<WorkshopSection> getWorkshopSections() {
		return workshopSections;
	}

	public void setWorkshopSections(List<WorkshopSection> workshopSections) {
		this.workshopSections = workshopSections;
	}

	public List<WorkshopUser> getWorkshopUsers() {
		return workshopUsers;
	}

	public void setWorkshopUsers(List<WorkshopUser> workshopUsers) {
		this.workshopUsers = workshopUsers;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Float getHourPerweek() {
		return hourPerweek;
	}

	public void setHourPerweek(Float hourPerweek) {
		this.hourPerweek = hourPerweek;
	}

	private void initUserRoleList() {
		if (this.masters != null || this.members != null || this.students != null) {
			return;
		}
		this.masters = Lists.newArrayList();
		this.members = Lists.newArrayList();
		this.students = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(this.workshopUsers)) {
			for (WorkshopUser wu : this.workshopUsers) {
				if (WorkshopUserRole.MASSTER.equals(wu.getRole())) {
					this.masters.add(wu);
				} else if (WorkshopUserRole.MEMBER.equals(wu.getRole())) {
					this.members.add(wu);
				} else if (WorkshopUserRole.STUDENT.equals(wu.getRole())) {
					this.students.add(wu);
				}
			}
		}
	}

	public FileInfo getImage() {
		return image;
	}

	public void setImage(FileInfo image) {
		this.image = image;
	}

	public List<WorkshopUser> getMasters() {
		initUserRoleList();
		return masters;
	}

	public void setMasters(List<WorkshopUser> masters) {
		this.masters = masters;
	}

	public List<WorkshopUser> getMembers() {
		initUserRoleList();
		return members;
	}

	public void setMembers(List<WorkshopUser> members) {
		this.members = members;
	}

	public List<WorkshopUser> getStudents() {
		initUserRoleList();
		return students;
	}

	public void setStudents(List<WorkshopUser> students) {
		this.students = students;
	}

	public Float getQualifiedPoint() {
		return qualifiedPoint;
	}

	public void setQualifiedPoint(Float qualifiedPoint) {
		this.qualifiedPoint = qualifiedPoint;
	}

	public String getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public BigDecimal getStudyHours() {
		return studyHours;
	}

	public void setStudyHours(BigDecimal studyHours) {
		this.studyHours = studyHours;
	}

	public List<FileInfo> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<FileInfo> solutions) {
		this.solutions = solutions;
	}

}
