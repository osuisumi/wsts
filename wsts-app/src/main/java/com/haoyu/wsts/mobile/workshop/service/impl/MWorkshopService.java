package com.haoyu.wsts.mobile.workshop.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.file.entity.FileInfo;
import com.haoyu.sip.file.service.IFileInfoService;
import com.haoyu.sip.file.utils.FileUtils;
import com.haoyu.sip.mobile.file.entity.MFileInfo;
import com.haoyu.tip.announcement.service.IAnnouncementService;
import com.haoyu.wsts.mobile.utils.MEntityUtils;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshop;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshopSection;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshopUser;
import com.haoyu.wsts.mobile.workshop.service.IMWorkshopService;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;
import com.haoyu.wsts.workshop.utils.WorkshopState;
import com.haoyu.wsts.workshop.utils.WorkshopType;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;

@Service
public class MWorkshopService implements IMWorkshopService{	
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private IWorkshopSectionService workshopSectionService;
	@Resource
	private IWorkshopUserService workshopUserService;
	@Resource
	private IFileInfoService fileInfoService;
	@Resource
	private IAnnouncementService announcementService;
	
	@Override
	public Response view(String id) {
		Map<String,Object> result = Maps.newHashMap();
		Map<String,Object> wParam = Maps.newHashMap();
		wParam.put("getStudentNum", "Y");
		wParam.put("getMemberNum", "Y");
		wParam.put("getActivityNum", "Y");
		wParam.put("getResourceNum", "Y");
		wParam.put("workshopId",id);
		//处理工作坊
		Workshop workshop = workshopService.findWorkshopByIdWithStat(wParam);
		MWorkshop mWorkshop  = MEntityUtils.getMWorkshop(workshop);
		result.put("mWorkshop",mWorkshop);
		
		//处理阶段
		Map<String,Object> wsParameter = Maps.newHashMap();
		wsParameter.put("workshopId", id);
		List<WorkshopSection> workshopSections = workshopSectionService.findWorkshopSections(wsParameter, false, null);
		List<MWorkshopSection> mWorkshopSections = Lists.newArrayList();
		mWorkshopSections = BeanUtils.getCopyList(workshopSections, MWorkshopSection.class);
		if(CollectionUtils.isNotEmpty(workshopSections)){
			mWorkshopSections = BeanUtils.getCopyList(workshopSections, MWorkshopSection.class);
		}
		result.put("mWorkshopSections", mWorkshopSections);
		
		//处理当前用户的workshopuser
		WorkshopUser workshopUser = workshopUserService.findWorkshopUserById(WorkshopUser.getId(id, ThreadContext.getUser().getId()));

		result.put("mWorkshopUser", MEntityUtils.getMWorkshopUser(workshopUser));
		
		//处理坊主
		if(WorkshopType.PERSONAL.equals(mWorkshop.getType())){
			Map<String,Object> masterParam = Maps.newHashMap();
			masterParam.put("workshopId", id);
			masterParam.put("role", WorkshopUserRole.MASSTER);
			List<WorkshopUser> workshopUsers = workshopUserService.findWorkshopUsers(masterParam, null);
			if(CollectionUtils.isNotEmpty(workshopUsers)){
				for(WorkshopUser master:workshopUsers){
					mWorkshop.getMasters().add(MEntityUtils.getMUser(master.getUser()));
				}
			}
		}
		
		return Response.successInstance().responseData(result);
		
	}
	
	@Override
	public Response detail(String id,PageBounds wuPageBounds){
		Map<String,Object> wparam = Maps.newHashMap();
		wparam.put("getResourceNum", "Y");
		wparam.put("getQuestionNum", "Y");
		wparam.put("getStudentNum","Y");
		wparam.put("getActivityNum","Y");
		wparam.put("workshopId", id);
		wparam.put("getTrainName","Y");
		Workshop workshop = workshopService.findWorkshopByIdWithStat(wparam);//工作坊
		MWorkshop mWorkshop = MEntityUtils.getMWorkshop(workshop);
		
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("relationId", id);
		parameter.put("type", "workshop_solution");
		List<FileInfo> fileInfos = fileInfoService.listFileInfo(parameter, null);//研修方案
		MFileInfo mFileInfo = new MFileInfo();
		if(CollectionUtils.isNotEmpty(fileInfos)){
			BeanUtils.copyProperties(fileInfos.get(0), mFileInfo);
		}
		
		
		Map<String,Object> result = Maps.newHashMap();
		result.put("mWorkshop", mWorkshop);
		result.put("mFileInfo", mFileInfo);
		
		
		return Response.successInstance().responseData(result);
		
		
	}

	@Override
	public Response studyPregress(String id) {
		Workshop workshop = workshopService.findWorkshopById(id);
		WorkshopUser workshopUser = workshopUserService.findWorkshopUserWithActionInfoById(WorkshopUser.getId(id, ThreadContext.getUser().getId()));
		MWorkshopUser mWorkshopUser = MEntityUtils.getMWorkshopUser(workshopUser);
		MWorkshop mWorkshop = MEntityUtils.getMWorkshop(workshop);
		mWorkshopUser.setmWorkshop(mWorkshop);
		return Response.successInstance().responseData(mWorkshopUser);
	}

	@Override
	public Response list(String type,PageBounds pageBounds) {
		String userId = ThreadContext.getUser().getId();
		Map<String,Object> parameter = Maps.newHashMap();
		if(!"my".equals(type)){
			userId = null;
			parameter.put("isTemplate", "N");
			parameter.put("state", WorkshopState.PUBLISHED);
		}
		if(StringUtils.isNotEmpty(userId)){
			parameter.put("userId", userId);
			parameter.put("wuState","passed");
		}
		
		List<Workshop> workshops = workshopService.findWorkshops(parameter, pageBounds);
		
		List<MWorkshop> mWorkshops = Lists.newArrayList();
		
		if(CollectionUtils.isNotEmpty(workshops)){
			for(Workshop w:workshops){
				MWorkshop mw = MEntityUtils.getMWorkshop(w);
				mWorkshops.add(mw);
			}
		}
		
		Map<String,Object> countParam = Maps.newHashMap();
		countParam.put("isTemplate","N");
		countParam.put("state","published");
		int notTemplateNum = workshopService.count(countParam);
		
		countParam.clear();
		countParam.put("userId",ThreadContext.getUser().getId());
		int myRelativeNum = workshopService.count(countParam);
		
		
		Map<String,Object> result = Maps.newHashMap();
		result.put("notTemplateNum", notTemplateNum);
		result.put("myRelativeNum", myRelativeNum);
		result.put("mWorkshops", mWorkshops);
		if(workshops instanceof PageList){
			PageList pageList = (PageList) workshops;
			result.put("paginator", pageList.getPaginator());
		}
		
		return Response.successInstance().responseData(result);
	}

}
