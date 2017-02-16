package com.haoyu.wsts.workshop.entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.haoyu.sip.core.entity.BaseEntity;
import com.haoyu.sip.core.entity.User;
import com.haoyu.sip.user.entity.UserInfo;
import com.haoyu.wsts.workshop.utils.WorkshopResultType;
import com.haoyu.wsts.workshop.utils.WorkshopUserActionInfo;

public class WorkshopUser extends BaseEntity {

	private static final long serialVersionUID = 2986182242804772939L;

	private String id;

	private String workshopId;
	private User user;
	private String role;
	private String state;
	private WorkshopUserResult workshopUserResult;

	private WorkshopUserActionInfo actionInfo;

	private UserInfo userInfo;

	private Workshop workshop;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public static String getId(String workshopId, String userId, String role) {
		if (StringUtils.isAnyEmpty(workshopId, userId)) {
			return null;
		}
		if (StringUtils.isEmpty(role)) {
			return getId(workshopId, userId);
		}
		return DigestUtils.md5Hex(workshopId + userId + role);
	}

	public static String getId(String workshopId, String userId) {
		if (StringUtils.isAnyEmpty(workshopId, userId)) {
			return null;
		}
		return DigestUtils.md5Hex(workshopId + userId);
	}

	public WorkshopUserActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(WorkshopUserActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}

	public WorkshopUserResult getWorkshopUserResult() {
		return workshopUserResult;
	}

	public void setWorkshopUserResult(WorkshopUserResult workshopUserResult) {
		this.workshopUserResult = workshopUserResult;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}
	
	public String getFinallyResult(){
		String result = "";
		if(workshop != null && workshopUserResult != null && workshop.getQualifiedPoint() != null){
			Float userPoint = this.workshopUserResult.getPoint()==null?0:this.workshopUserResult.getPoint();
			Float qualifiedPoint = this.workshop.getQualifiedPoint();
			if(userPoint<qualifiedPoint){
				result = WorkshopResultType.FAIL;
			}else{
				if(workshopUserResult.getWorkshopResult() != null){
					result = workshopUserResult.getWorkshopResult();
				}
			}
		}
		return result;
	}

}
