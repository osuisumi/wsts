package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class WorkshopSectionsDataDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IWorkshopSectionService workshopSectionService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityResultService activityResultService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> parameter = getSelectParam(params);
		List<WorkshopSection> workshopSections = workshopSectionService.findWorkshopSections(parameter, true, getPageBounds(params));
		if (Collections3.isNotEmpty(workshopSections)) {
			List<String> relationIds = Collections3.extractToList(workshopSections, "id");
			Map<String, WorkshopSection> sectionMap = Collections3.extractToMap(workshopSections, "id", null);
			Map<String, Object> param = Maps.newHashMap();
			param.put("relationIds", relationIds);
			PageBounds pageBounds = new PageBounds();
			pageBounds.setLimit(Integer.MAX_VALUE);
			pageBounds.setOrders(Order.formString("SORT_NO,CREATE_TIME"));
			List<Activity> activities = activityService.listActivity(param, false, pageBounds);
			if (Collections3.isNotEmpty(activities)) {
				for (Activity activity : activities) {
					sectionMap.get(activity.getRelation().getId()).getActivities().add(activity);
				}
			}
			if (parameter.containsKey("getResult")) {
				boolean getResult = (boolean) parameter.get("getResult");
				if (getResult) {
					param = Maps.newHashMap();
					param.put("relationId", parameter.get("workshopId"));
					param.put("creator", ThreadContext.getUser().getId());
					Map<String, ActivityResult> activityResultMap = activityResultService.mapActivityResult(param, null);
					env.setVariable("activityResultMap", new DefaultObjectWrapper().wrap(activityResultMap));
				}
			}
		}
		env.setVariable("workshopSections", new DefaultObjectWrapper().wrap(workshopSections));
		body.render(env.getOut());
	}

}
