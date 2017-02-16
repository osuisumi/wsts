package com.haoyu.wsts.utils;

import org.apache.commons.lang3.StringUtils;

public class RemoveHtmlTagUtils {
	
	public static String removeHtmlTag(String str){
		if(StringUtils.isNotEmpty(str)){
			return str.replaceAll("<[^>]*>", "");
		}
		return "";
	}

}
