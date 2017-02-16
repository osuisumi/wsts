package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class WorkshopUserDataDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IWorkshopUserService workshopUserService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String id = getId(params);
		Map<String,Object> parameter = getSelectParam(params);
		if(StringUtils.isEmpty(id)){
			if(parameter.containsKey("workshopId")&&parameter.containsKey("userId")){
				id = WorkshopUser.getId(parameter.get("workshopId").toString(),parameter.get("userId").toString());
			}
		}
		if(StringUtils.isNotEmpty(id)){
			if(parameter.containsKey("withActionInfo")){
				WorkshopUser workshopUser = workshopUserService.findWorkshopUserWithActionInfoById(id);
				env.setVariable("workshopUser", new DefaultObjectWrapper().wrap(workshopUser));
			}else{
				WorkshopUser workshopUser = workshopUserService.findWorkshopUserById(id);
				env.setVariable("workshopUser", new DefaultObjectWrapper().wrap(workshopUser));
			}
		}
		body.render(env.getOut());
		
	}

}
