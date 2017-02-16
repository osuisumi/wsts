package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component
public class WorkshopUsersDataDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IWorkshopUserService workshopUserService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		PageBounds pageBounds = getPageBounds(params);
		Map<String,Object> parameter = getSelectParam(params);
		if(parameter.containsKey("timeParam")){
			String timeParam = params.get("timeParam").toString();
			if (StringUtils.isNotEmpty(timeParam)) {
				if ("notBegun".equals(timeParam)) {
					parameter.put("startTimeGreaterThan", new Date());
				}else if("beginning".equals(timeParam)){
					parameter.put("startTimeLessThanOrEquals", new Date());
					parameter.put("endTimeGreaterThanOrEquals", new Date());
				}else if("end".equals(timeParam)){
					parameter.put("endTimeLessThan", new Date());
				}
			}
		}
		List<WorkshopUser> workshopUsers = Lists.newArrayList();
		if(params.containsKey("withActionInfo")){
			workshopUsers = workshopUserService.findWorkshopUsersWithActionInfo(parameter, pageBounds);
		}else{
			workshopUsers = workshopUserService.findWorkshopUsers(parameter, pageBounds);
		}
		env.setVariable("workshopUsers", new DefaultObjectWrapper().wrap(workshopUsers));
		
		if (pageBounds != null && pageBounds.isContainsTotalCount()) {
			PageList pageList = (PageList) workshopUsers;
			env.setVariable("paginator", new DefaultObjectWrapper().wrap(pageList.getPaginator()));
		}

		body.render(env.getOut());
		
	}

}
