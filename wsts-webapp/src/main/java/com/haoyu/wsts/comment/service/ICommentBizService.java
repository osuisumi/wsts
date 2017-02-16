package com.haoyu.wsts.comment.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.comment.entity.Comment;

public interface ICommentBizService {

	List<Comment> list(Map<String, Object> param, PageBounds pageBounds);
	
	List<Comment> list(Map<String, Object> param, PageBounds pageBounds,boolean isIncludeChildren);

	int getCount(Map<String, Object> paramMap);

}
