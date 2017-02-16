package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class WorkshopMapDirective extends AbstractTemplateDirectiveModel {
	@Resource
	private IWorkshopService workshopService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

		Map<String, Object> parameter = getSelectParam(params);

		Map<String, Workshop> workshopMap = workshopService.workshopIdKeyMap(parameter);

		env.setVariable("workshopMap", new DefaultObjectWrapper().wrap(workshopMap));

		body.render(env.getOut());
	}

}
