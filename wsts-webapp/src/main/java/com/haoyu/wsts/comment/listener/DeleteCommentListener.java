package com.haoyu.wsts.comment.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.aip.debate.service.IDebateUserViewsService;
import com.haoyu.aip.debate.utils.RelationTypeConstants;
import com.haoyu.sip.comment.entity.Comment;
import com.haoyu.sip.comment.entity.CommentStat;
import com.haoyu.sip.comment.event.DeleteCommentEvent;
import com.haoyu.sip.comment.service.ICommentService;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.excel.utils.StringUtils;

@Component
public class DeleteCommentListener implements ApplicationListener<DeleteCommentEvent>{
	@Resource
	private ICommentService commentService;
	@Resource
	private IDebateUserViewsService debateUserViewsService;
	
	@Override
	public void onApplicationEvent(DeleteCommentEvent event) {
		if(event.getSource() != null && event.getSource() instanceof Comment){
			Comment comment = (Comment) event.getSource();
			if(comment.getRelation()!=null &&StringUtils.isNotEmpty(comment.getRelation().getType())&&"debate_user_views".equals(comment.getRelation().getType())){
				Relation relation = comment.getRelation();
				if(relation!=null&&StringUtils.isNotEmpty(relation.getType())&&relation.getType().equals(RelationTypeConstants.DEBATE_USER_VIEWS)){
					CommentStat  cs= commentService.getCommentStatByRelation(relation);
					debateUserViewsService.updateDebateUserViewsCommentsNum(relation.getId(),cs.getCommentNum());
				}
			}else if(StringUtils.isNotEmpty(comment.getMainId()) && !"null".equals(comment.getMainId())){
				commentService.updateChildNum(comment.getMainId());
			}
		}
	}

}
