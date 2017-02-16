package com.haoyu.wsts.utils;

public class RoleCodeConstant {
	
	public static final String MASTER = "master";
	
	public static final String MEMBER = "member";
	
	public static final String STUDENT = "student";
	
	public static final String GUEST = "guest";
	
	public static String[] getRoleCodeConstants(){
		return new String[]{RoleCodeConstant.MASTER, RoleCodeConstant.MEMBER, RoleCodeConstant.STUDENT, RoleCodeConstant.GUEST};
	}

}
