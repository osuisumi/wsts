package com.haoyu.wsts.fileresource.listener;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.event.UpdateFileResourceEvent;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.utils.FileResourceType;
/*
 * 工作坊资源加精事件
 */
@Component
public class UpdateFileResourceListener implements ApplicationListener<UpdateFileResourceEvent>{
	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;

	@Override
	public void onApplicationEvent(UpdateFileResourceEvent event) {
		FileResource fileResource = (FileResource) event.getSource();
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(StringUtils.isNotEmpty(wsid) && workshopTimeUtils.isWorkshopOnGoing(wsid)){
			if(FileResourceType.EXCELLENT.equals(fileResource.getType())){
				FileResource fr = fileResourceService.get(fileResource.getId());
				PointRecord pointRecord = new PointRecord();
				pointRecord.setEntityId(fr.getId());
				pointRecord.setUserId(fr.getCreator().getId());
				pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.BECOME_EXCELLENT_FILE_RESOURCE, "wsts"));
				pointRecord.setRelationId(wsid);
				pointRecordService.createPointRecord(pointRecord);
			}else if(FileResourceType.NORMAL.equals(fileResource.getType())){
				FileResource fr = fileResourceService.get(fileResource.getId());
				PointRecord pointRecord = new PointRecord();
				pointRecord.setEntityId(fr.getId());
				pointRecord.setUserId(fr.getCreator().getId());
				pointRecord.setRelationId(wsid);
				pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.CANCEL_EXCELLENT_FILE_RESOURCE, "wsts"));
				pointRecordService.createPointRecord(pointRecord);
				
			}
		}
	}

}
