package com.haoyu.wsts.fileresource.service;

import java.util.List;
import java.util.Map;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.file.entity.FileResource;

public interface IFileResourceBizService {

	Response createFileResource(FileResource fileResource);
	
	int getFileResourceFileCount(Map<String,Object> parameter);
	
	public Response initFileResourceCreate(String relationId, String type);
	
	List<Map<String, Object>> listUserFileCount(Map<String,Object> parameter);
}
