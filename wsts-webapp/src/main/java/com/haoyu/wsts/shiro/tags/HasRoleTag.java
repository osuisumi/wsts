package com.haoyu.wsts.shiro.tags;

import java.util.List;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.HasRoleTag}</p>
 */
public class HasRoleTag extends RoleTag implements TemplateMethodModelEx {
    protected boolean showTagBody(String roleName) {
        return getSubject() != null && getSubject().hasRole(roleName);
    }

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		SimpleScalar ss = (SimpleScalar) arguments.get(0);
		return showTagBody(ss.getAsString());
	}
}
