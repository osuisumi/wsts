package com.haoyu.wsts.utils;

import com.haoyu.sip.core.utils.PropertiesLoader;

public class TemplateUtils {
	
	public static String getTemplatePath(String path){
		return new StringBuilder((String)PropertiesLoader.get("wsts.template.path")).append(path).toString(); 
	}

}
