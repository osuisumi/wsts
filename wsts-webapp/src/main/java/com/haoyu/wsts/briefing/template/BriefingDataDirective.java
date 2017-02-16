package com.haoyu.wsts.briefing.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.tip.announcement.entity.Announcement;
import com.haoyu.tip.announcement.service.IAnnouncementService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class BriefingDataDirective extends AbstractTemplateDirectiveModel{

	@Resource
	private IAnnouncementService announcementService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		PageBounds pageBounds = getPageBounds(params);
		Map<String,Object> paramerts = getSelectParam(params);
		
		SearchParam searchParam = new SearchParam();
		searchParam.setParamMap(paramerts);
		List<Announcement> briefings = announcementService.list(searchParam, pageBounds);
		env.setVariable("briefings", new DefaultObjectWrapper().wrap(briefings));

		if (pageBounds != null && pageBounds.isContainsTotalCount()) {
			PageList pageList = (PageList)briefings;
			env.setVariable("paginator" , new DefaultObjectWrapper().wrap(pageList.getPaginator()));
		}
		body.render(env.getOut());
	}

}
