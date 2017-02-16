package com.haoyu.wsts.fileresource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.file.entity.FileRelation;
import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.event.CreateFileResourceEvent;
import com.haoyu.sip.file.event.UploadFileEvent;
import com.haoyu.sip.file.service.IFileRelationService;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.sip.file.service.IFileService;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.sip.utils.Identities;
import com.haoyu.wsts.fileresource.dao.IFileResourceBizDao;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.workshop.utils.FileResourceType;

@Service
public class FileResourceBizService implements IFileResourceBizService{

	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private IFileRelationService fileRelationService;
	@Resource
	private IFileService fileService;
	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private IFileResourceBizDao fileResourceBizDao;
	
	@Override
	public Response createFileResource(FileResource fileResource) {
		Response response;
		if(StringUtils.isNotEmpty(fileResource.getParentId())){
			FileResource parentFile = fileResourceService.get(fileResource.getParentId());
			fileResource.setParentIds(parentFile.getParentIds()+","+fileResource.getParentId());
		}
		if(StringUtils.isEmpty(fileResource.getId())){
			fileResource.setId(Identities.uuid2());
		}
		if(!fileResource.getIsFolder().equals("Y")){
			response = fileService.createFile(fileResource.getNewestFile(), fileResource.getId(), "workshop_resource");
			if(!response.isSuccess()){
				return response;
			}
		}
		response = fileResourceService.create(fileResource);
		if (response.isSuccess()) {
			if (Collections3.isNotEmpty(fileResource.getFileRelations())) {
				for (FileRelation fileRelation : fileResource.getFileRelations()) {
					if (StringUtils.isEmpty(fileRelation.getFileId())) {
						fileRelation.setFileId(fileResource.getId());
					}
					fileRelation.setFileId(fileResource.getId());
					String id = FileRelation.getId(fileRelation.getFileId(), fileRelation.getRelation().getId());
					fileRelation.setId(id);
					fileRelationService.create(fileRelation);
				}
			}
		}
		if (response.isSuccess()) {
			response.setResponseData(fileResource);
			applicationContext.publishEvent(new CreateFileResourceEvent(fileResource));
		}
		return response;
	}

	@Override
	public int getFileResourceFileCount(Map<String, Object> parameter) {
		return this.fileResourceBizDao.getFileResourceFileCount(parameter);
	}

	@Override
	public Response initFileResourceCreate(String relationId, String type) {
		FileRelation fileRelation = new FileRelation();
		fileRelation.setRelation(new Relation(relationId));
		fileRelation.setType(type);
		FileResource fileResource = new FileResource();
		fileResource.setName("共案");
		fileResource.setIsFolder("Y");
		fileResource.setType(FileResourceType.FIXED);
		fileResource.setFileRelations(Lists.newArrayList(fileRelation));
		Response response = fileResourceService.createFileResource(fileResource);
		if (response.isSuccess()) {
			fileResource.setId(Identities.uuid2());
			fileResource.setName("初备");
			fileRelation.setFileId(fileResource.getId());
			response = fileResourceService.createFileResource(fileResource);
//			if (response.isSuccess()) {
//				fileResource.setId(Identities.uuid2());
//				fileResource.setName("授课资源");
//				fileRelation.setFileId(fileResource.getId());
//				response = fileResourceService.createFileResource(fileResource);
//			}
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> listUserFileCount(Map<String,Object> parameter) {
		return fileResourceBizDao.selectUserFileCount(parameter);
	}

}
