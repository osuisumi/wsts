package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.workshop.service.IWorkshopService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class WorkshopNumDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IWorkshopService workshopService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String,Object> parameter = getSelectParam(params);
		if(parameter.containsKey("getNotTemplateNum")){
			Map<String,Object> param = Maps.newHashMap();
			param.put("isTemplate","N");
			param.put("state","published");
			env.setVariable("notTemplateNum", new DefaultObjectWrapper().wrap(workshopService.count(param)));
		}
		if(parameter.containsKey("getMyRelativeNum")){
			Map<String,Object> param = Maps.newHashMap();
			param.put("userId",ThreadContext.getUser().getId());
			env.setVariable("myRelativeNum", new DefaultObjectWrapper().wrap(workshopService.count(param)));
		}
		body.render(env.getOut());
		
	}

}
