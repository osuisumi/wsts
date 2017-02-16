package com.haoyu.wsts.comment.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.comment.entity.Comment;

public interface ICommentBizDao {
	
	List<Comment> select(Map<String, Object> param, PageBounds pageBounds);

	int selectCount(Map<String, Object> paramMap);
	
}
