package com.haoyu.wsts.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.haoyu.aip.courseware.service.ICoursewareService;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.evaluate.entity.EvaluateSubmission;
import com.haoyu.sip.evaluate.service.IEvaluateSubmissionService;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.sip.file.service.IFileService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component
public class CoursewareDataDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private ICoursewareService coursewareService;
	@Resource
	private IFileService fileService;
	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private IEvaluateSubmissionService evaluateSubmissionService;
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String id = getId(params);
		if(StringUtils.isNotEmpty(id)){
			env.setVariable("courseware", new DefaultObjectWrapper().wrap(coursewareService.get(id)));
			SearchParam searchParam = new SearchParam();
			searchParam.getParamMap().put("relationId", id);
			env.setVariable("fileResources", new DefaultObjectWrapper().wrap(fileResourceService.list(searchParam, null)));
		}
		body.render(env.getOut());
	}

}
