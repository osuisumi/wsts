package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@Component
public class WorkshopDataDirective extends AbstractTemplateDirectiveModel implements TemplateMethodModelEx{
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private IWorkshopUserService workshopUserService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> parameter = getSelectParam(params);
		String id = (String) parameter.get("id");
		if(StringUtils.isNotEmpty(id)){
			parameter.put("workshopId",id);
			Workshop workshop = workshopService.findWorkshopByIdWithStat(parameter);
			env.setVariable("workshop", new DefaultObjectWrapper().wrap(workshop));
		}
		body.render(env.getOut());
	}

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String id = arguments.get(0).toString();
		Map<String,Object> parameter=  Maps.newHashMap();
		parameter.put("workshopId", id);
		parameter.put("getMemberNum", "Y");
		return workshopService.findWorkshopByIdWithStat(parameter);
	}

}
