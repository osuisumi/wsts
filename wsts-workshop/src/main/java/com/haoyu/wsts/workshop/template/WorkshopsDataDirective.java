package com.haoyu.wsts.workshop.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class WorkshopsDataDirective extends AbstractTemplateDirectiveModel {
	@Resource
	private IWorkshopService workshopService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> param = getSelectParam(params);
		PageBounds pageBounds  = getPageBounds(params);
		
		List<Workshop> workshops = Lists.newArrayList();
		if(param.containsKey("withStat")){
			//查询出统计信息
			workshops = workshopService.findWorkshopsWithStat(param, pageBounds);
			env.setVariable("workshops", new DefaultObjectWrapper().wrap(workshops));
		}else{
			workshops = workshopService.findWorkshops(param, pageBounds);
			env.setVariable("workshops", new DefaultObjectWrapper().wrap(workshops));
		}
		if (pageBounds != null && pageBounds.isContainsTotalCount()) {
			PageList pageList = (PageList) workshops;
			env.setVariable("paginator", new DefaultObjectWrapper().wrap(pageList.getPaginator()));
		}

		body.render(env.getOut());
	}

}
