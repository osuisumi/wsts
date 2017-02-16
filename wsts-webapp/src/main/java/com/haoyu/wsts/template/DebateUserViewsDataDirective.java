package com.haoyu.wsts.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.haoyu.aip.debate.entity.DebateArgument;
import com.haoyu.aip.debate.entity.DebateRelation;
import com.haoyu.aip.debate.entity.DebateUser;
import com.haoyu.aip.debate.entity.DebateUserViews;
import com.haoyu.aip.debate.service.IDebateUserViewsService;
import com.haoyu.aip.debate.utils.RelationTypeConstants;
import com.haoyu.sip.attitude.entity.AttitudeUser;
import com.haoyu.sip.attitude.service.IAttitudeService;
import com.haoyu.sip.comment.service.impl.CommentServiceImpl;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.entity.User;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.utils.Collections3;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class DebateUserViewsDataDirective extends AbstractTemplateDirectiveModel {
	@Resource
	private IDebateUserViewsService debateUserViewsService;
	@Resource
	private IAttitudeService attitudeService;
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> parameter = getSelectParam(params);
		if (parameter.containsKey("debateRelationId")) {
			PageBounds pageBounds = getPageBounds(params);
			DebateUser dabateUser = new DebateUser();
			if (parameter.containsKey("argumentId")) {
				DebateArgument debateArgument = new DebateArgument();
				debateArgument.setId(parameter.get("argumentId").toString());
				dabateUser.setArgument(debateArgument);
			}

			if (parameter.containsKey("creatorId")) {
				dabateUser.setCreator(new User(parameter.get("creatorId").toString()));
			}
			dabateUser.setDebateRelation(new DebateRelation(parameter.get("debateRelationId").toString()));

			List<DebateUserViews> debateUserViews = debateUserViewsService.findDebateUserViewsByDebateUser(dabateUser, pageBounds);
			//获取当前登录者的点赞状态
			if(CollectionUtils.isNotEmpty(debateUserViews) && ThreadContext.getUser() != null){
				Map<String,AttitudeUser> attitudeMap = attitudeService.getAttitudesByRelationIdsAndRelationType(Collections3.extractToList(debateUserViews, "id"), RelationTypeConstants.DEBATE_USER_VIEWS, ThreadContext.getUser().getId());
				env.setVariable("attitudeMap", new DefaultObjectWrapper().wrap(attitudeMap));
			}
			env.setVariable("debateUserViews", new DefaultObjectWrapper().wrap(debateUserViews));
			if (pageBounds != null && pageBounds.isContainsTotalCount()) {
				PageList pageList = (PageList) debateUserViews;
				env.setVariable("paginator", new DefaultObjectWrapper().wrap(pageList.getPaginator()));
			}

		}
		body.render(env.getOut());

	}
	

}
