package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateSequenceModel;

@Component
public class WorkshopUsersMapDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IWorkshopUserService workshopUserService;
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		if(params.containsKey("workshopIds")){
			Object obj = params.get("workshopIds");
			TemplateSequenceModel tm = (TemplateSequenceModel) obj;
			List<String> workshopIds = Lists.newArrayList();
			for(int i=0;i<tm.size();i++){
				workshopIds.add(tm.get(i).toString());
			}
			Map<String,Object> parameter = getSelectParam(params);
			parameter.put("workshopIds", workshopIds);
			env.setVariable("workshopUserMap", new DefaultObjectWrapper().wrap(workshopUserService.getWorkshopUserMap(parameter)));
		}
		body.render(env.getOut());
	}

}
