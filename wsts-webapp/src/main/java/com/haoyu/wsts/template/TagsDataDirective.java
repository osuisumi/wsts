package com.haoyu.wsts.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.tag.service.ITagService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class TagsDataDirective extends AbstractTemplateDirectiveModel {
	@Resource
	private ITagService tagService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String,Object> parameter = getSelectParam(params);
		if(parameter.containsKey("relationId")){
			env.setVariable("tags", new DefaultObjectWrapper().wrap(tagService.findTagByNameAndRelations(null, Lists.newArrayList(parameter.get("relationId").toString()), null)));;
		}
		body.render(env.getOut());
	}

}
