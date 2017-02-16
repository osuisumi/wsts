package com.haoyu.wsts.fileresource.listener;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.file.entity.FileRelation;
import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.event.DeleteFileResourceEvent;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;
/*
 * 删除fileresource事件
 */
@Component
@Async
public class DeleteFileResourceListener implements ApplicationListener<DeleteFileResourceEvent>{
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IFileResourceBizService fileResourceBizService;
	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;
	@Override
	public void onApplicationEvent(DeleteFileResourceEvent event) {
		FileResource fileResource = (FileResource) event.getSource();
		if("Y".equals(fileResource.getIsFolder())){
			//删除文件夹
				//lessonplan 删除所有的子文件和子文件夹 删除子文件时 更新对应用户的上传文件数
			SearchParam searchParam = new SearchParam();
			searchParam.getParamMap().put("parentIds", fileResource.getId());
			searchParam.getParamMap().put("relationId", fileResource.getFileRelations().get(0).getRelation().getId());
			searchParam.getParamMap().put("parentId",fileResource.getId());
			List<FileResource> sonFileResources = fileResourceService.list(searchParam, null);
			for(FileResource sf:sonFileResources){
				fileResourceService.delete(sf.getId());
			}
		}
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(StringUtils.isNotEmpty(wsid)){
			FileRelation fileRelation = fileResource.getFileRelations().get(0);
			if(fileRelation != null && "workshop_resource".equals(fileRelation.getType())){
				if(workshopTimeUtils.isWorkshopOnGoing(wsid)){
					PointRecord pointRecord = new PointRecord();
					pointRecord.setEntityId(fileResource.getId());
					pointRecord.setUserId(fileResource.getCreator().getId());
					pointRecord.setRelationId(wsid);
					pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.DELETE_WORKSHOP_FILE_RESOURCE_FILE, "wsts"));
					pointRecordService.createPointRecord(pointRecord);
				}
			}
		}
	}

}
