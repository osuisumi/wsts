package com.haoyu.wsts.comment.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.comment.dao.ICommentBizDao;
import com.haoyu.wsts.comment.entity.Comment;

@Repository
public class CommentBizDao extends MybatisDao implements ICommentBizDao{

	@Override
	public List<Comment> select(Map<String, Object> param, PageBounds pageBounds) {
		return selectList("select",param, pageBounds);
	}

	@Override
	public int selectCount(Map<String, Object> paramMap) {
		return super.selectOne("selectCount", paramMap);
	}

}
