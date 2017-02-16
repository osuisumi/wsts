package com.haoyu.wsts.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component
public class ActivityResultDataDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IActivityResultService activityResultService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String,Object> parameter = getSelectParam(params);
		if(parameter.containsKey("activityId")&&parameter.containsKey("relationId")){
			env.setVariable("activityResult", new DefaultObjectWrapper().wrap(activityResultService.createIfNotExists(parameter.get("activityId").toString(), parameter.get("relationId").toString())));
		}
		body.render(env.getOut());
	}

}
