package com.haoyu.wsts.workshop.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date getDayBegin(Date date){
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND,0);
		return cd.getTime();
	}
	
	public static Date getDayEnd(Date date){
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.set(Calendar.HOUR_OF_DAY, 23);
		cd.set(Calendar.MINUTE, 59);
		cd.set(Calendar.SECOND,59);
		return cd.getTime();
	}
	

}
