package com.haoyu.wsts.mobile.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.haoyu.sip.core.entity.User;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.sip.user.mobile.entity.MUser;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshop;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshopUser;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.utils.WorkshopResultType;
import com.haoyu.wsts.workshop.utils.WorkshopType;

public class MEntityUtils {
	
	public static MUser getMUser(User user){
		MUser mUser = new MUser();
		if(user != null){
			BeanUtils.copyProperties(user, mUser);
		}
		return mUser;
	}
	
	public static MWorkshop getMWorkshop(Workshop workshop){
		MWorkshop mWorkshop = new MWorkshop();
		if(workshop!=null){
			BeanUtils.copyProperties(workshop, mWorkshop);
			if(workshop.getWorkshopRelation()!=null){
				mWorkshop.setStudentNum(workshop.getWorkshopRelation().getStudentNum());//设置学员数
				mWorkshop.setActivityNum(workshop.getWorkshopRelation().getActivityNum());//设置活动数
				mWorkshop.setResourceNum(workshop.getWorkshopRelation().getResourceNum());//设置资源数
				mWorkshop.setMemberNum(workshop.getWorkshopRelation().getMemberNum());//设置成员数
			}
			if(workshop.getStudyHours()!=null){
				mWorkshop.setStudyHours(workshop.getStudyHours().doubleValue());
			}
			if(workshop.getQualifiedPoint()!=null){
				mWorkshop.setQualifiedPoint(workshop.getQualifiedPoint().doubleValue());
			}
			if("Y".equals(workshop.getIsTemplate()) && WorkshopType.TRAIN.equals(workshop.getType())){
				mWorkshop.setType("template");//设置工作坊类型
			}
			mWorkshop.setCreator(getMUser(workshop.getCreator()));
		}
		return mWorkshop;
	}
	
	public static MWorkshopUser getMWorkshopUser(WorkshopUser workshopUser){
		MWorkshopUser mWorkshopUser = new MWorkshopUser();
		if(workshopUser!=null){
			BeanUtils.copyProperties(workshopUser, mWorkshopUser);
			mWorkshopUser.setmUser(MEntityUtils.getMUser(workshopUser.getUser()));
			if(workshopUser.getWorkshopUserResult()!=null){
				mWorkshopUser.setEvaluate(workshopUser.getWorkshopUserResult().getWorkshopResult());
				mWorkshopUser.setPoint(workshopUser.getWorkshopUserResult().getPoint() == null?0l:workshopUser.getWorkshopUserResult().getPoint().doubleValue());
				mWorkshopUser.setEvaluateCreator(getMUser(workshopUser.getWorkshopUserResult().getWorkshopResultCreator()));
				if(workshopUser.getWorkshop()!=null){
					mWorkshopUser.setFinallyResult(gte(workshopUser.getWorkshopUserResult().getPoint(),workshopUser.getWorkshop().getQualifiedPoint())?workshopUser.getWorkshopUserResult().getWorkshopResult():WorkshopResultType.FAIL);
				}
			}
			if(workshopUser.getActionInfo()!=null){
				mWorkshopUser.setCompleteActivityNum(workshopUser.getActionInfo().getActivityCompleteNum());
				mWorkshopUser.setFaqQuestionNum(workshopUser.getActionInfo().getFaqQuestionNum());
				mWorkshopUser.setUploadResourceNum(workshopUser.getActionInfo().getUploadResourceNum());
			}
		}
		return mWorkshopUser;
	}
	
	public static List<MWorkshopUser> getMWorkshopUserArray(List<WorkshopUser> workshopUsers){
		List<MWorkshopUser> mWorkshopUsers = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(workshopUsers)){
			for(WorkshopUser wu:workshopUsers){
				mWorkshopUsers.add(getMWorkshopUser(wu));
			}
		}
		return mWorkshopUsers;
	}
	
	private static boolean gte(Float my,Float other){
		if(my == null && other == null){
			return true;
		}else if(my == null){
			return false;
		}else if(other == null){
			return true;
		}else{
			return my.compareTo(other)>=0;
		}
	}

}
