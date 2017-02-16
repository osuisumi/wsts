package com.haoyu.wsts.fileresource.dao;

import java.util.List;
import java.util.Map;

public interface IFileResourceBizDao {
	
	public int getFileResourceFileCount(Map<String,Object> parameter);
	
	List<Map<String, Object>> selectUserFileCount(Map<String, Object> map);

}
