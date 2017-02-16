package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Maps;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class InterestedWorkshopsDirective extends AbstractTemplateDirectiveModel {
	@Resource
	private IWorkshopService workshopService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		PageBounds pageBounds = getPageBounds(params);
		if(ThreadContext.getUser()!=null){
			Map<String,Object> parameter = Maps.newHashMap();
			parameter.put("userId", ThreadContext.getUser().getId());
			List<Workshop> interestedWorkshops = workshopService.selectInterestedWorkshop(parameter, pageBounds);
			env.setVariable("interestedWorkshops", new DefaultObjectWrapper().wrap(interestedWorkshops));
			if (pageBounds != null && pageBounds.isContainsTotalCount()) {
				PageList pageList = (PageList) interestedWorkshops;
				env.setVariable("paginator", new DefaultObjectWrapper().wrap(pageList.getPaginator()));
			}
		}

		body.render(env.getOut());

	}

}
