package com.haoyu.wsts.fileresource.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.fileresource.dao.IFileResourceBizDao;

@Component
public class FileResourceBizDao extends MybatisDao implements IFileResourceBizDao{

	@Override
	public int getFileResourceFileCount(Map<String,Object> parameter) {
		return super.selectOne("getFileCount",parameter);
	}

	@Override
	public List<Map<String, Object>> selectUserFileCount(Map<String, Object> map) {
		return selectList("selectUserFileCount", map);
	}

}
