package com.haoyu.wsts.comment.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.comment.dao.ICommentBizDao;
import com.haoyu.wsts.comment.entity.Comment;
import com.haoyu.wsts.comment.service.ICommentBizService;

@Service
public class CommentBizService implements ICommentBizService{

	@Resource
	private ICommentBizDao commentBizDao;
	
	@Override
	public List<Comment> list(Map<String, Object> param, PageBounds pageBounds) {		
		return commentBizDao.select(param, pageBounds);
	}
	
	@Override
	public List<Comment> list(Map<String, Object> param, PageBounds pageBounds,boolean isIncludeChildren) {		
		if(isIncludeChildren){
			param.put("isIncludeChildren", true);
		}
		return this.list(param, pageBounds);
	}

	@Override
	public int getCount(Map<String, Object> paramMap) {
		return commentBizDao.selectCount(paramMap);
	}

}
