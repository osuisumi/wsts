package com.haoyu.wsts.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.discussion.entity.Discussion;
import com.haoyu.aip.discussion.entity.DiscussionPost;
import com.haoyu.aip.discussion.service.IDiscussionPostService;
import com.haoyu.aip.discussion.service.IDiscussionService;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.tag.entity.Tag;
import com.haoyu.sip.tag.service.ITagService;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class DiscussionUserDataDirective extends AbstractTemplateDirectiveModel {

	@Resource
	private IDiscussionService discussionService;
	@Resource
	private IDiscussionPostService discussionPostService;
	@Resource
	private ITagService tagService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private IActivityService activityService;

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> parameter = getSelectParam(params);
		if(parameter.containsKey("discussionId")){
			String discussionId = params.get("discussionId").toString();
			String relationId = params.get("relationId").toString();
			Discussion discussion = discussionService.viewDiscussion(discussionId);
			if (discussion != null) {
				env.setVariable("discussion", new DefaultObjectWrapper().wrap(discussion));
			}else{
				env.setVariable("discussion", new DefaultObjectWrapper().wrap(new Discussion()));
			}
			PageBounds pageBounds = getPageBounds(params);
			SearchParam searchParam = new SearchParam();
			searchParam.getParamMap().put("discussionRelationId", discussion.getDiscussionRelations().get(0).getId());
			List<DiscussionPost> discussionPosts = discussionPostService.list(searchParam, pageBounds);
			env.setVariable("discussionPosts", new DefaultObjectWrapper().wrap(discussionPosts));
			if (pageBounds != null && pageBounds.isContainsTotalCount()) {
				PageList pageList = (PageList)discussionPosts;
				env.setVariable("paginator" , new DefaultObjectWrapper().wrap(pageList.getPaginator()));
			}
			
			if(params.containsKey("activityId")){
				String activityId = params.get("activityId").toString();
				Subject currentUser = SecurityUtils.getSubject();
				if(currentUser.hasRole(RoleCodeConstant.STUDENT+"_" + WsIdObject.getWsIdObject().getWsid())){
					ActivityResult activityResult = activityResultService.createIfNotExists(activityId, relationId);
					env.setVariable("activityResult", new DefaultObjectWrapper().wrap(activityResult));
				}
				env.setVariable("activity", new DefaultObjectWrapper().wrap(activityService.getActivity(activityId)));
				List<Tag> tags = tagService.findTagByNameAndRelations(null, Lists.newArrayList(activityId), null);
				env.setVariable("tags", new DefaultObjectWrapper().wrap(tags));
			}
		}
		body.render(env.getOut());
	}

}
