package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.service.impl.WorkshopUserService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component
public class WorkshopUserCountDataDirective extends AbstractTemplateDirectiveModel{
	
	@Resource
	private WorkshopUserService workshopUserService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		env.setVariable("count", new DefaultObjectWrapper().wrap(workshopUserService.count(getSelectParam(params))));;
		body.render(env.getOut());
	}

}
