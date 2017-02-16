package com.haoyu.wsts.mobile.utils;

import java.util.Date;

import com.haoyu.sip.core.entity.TimePeriod;

public class TimeUtils extends com.haoyu.sip.utils.TimeUtils{
	
	//判断是否在当前时间之内, 时间参数不定个数, 全部验证通过才返回true
	public static boolean inCurrentDate(Object... timePeriods){
		for (Object timePeriod : timePeriods) {
			if (timePeriod instanceof TimePeriod) {
				TimePeriod tp = (TimePeriod) timePeriod;
				if (tp != null) {
					if (!hasBegun(tp.getStartTime()) || hasEnded(tp.getEndTime())) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static TimePeriod getMinTimePeriod(Object... timePeriods){
		TimePeriod timePeriod = new TimePeriod();
		for (Object object : timePeriods) {
			if (object instanceof TimePeriod) {
				TimePeriod tp = (TimePeriod) object;
				if (tp != null) {
					if (timePeriod.getStartTime() == null) {
						timePeriod.setStartTime(tp.getStartTime());
					}else{
						if (tp.getStartTime() != null && tp.getStartTime().getTime() > timePeriod.getStartTime().getTime()) {
							timePeriod.setStartTime(tp.getStartTime());
						}
					}
					if (timePeriod.getEndTime() == null) {
						timePeriod.setEndTime(tp.getEndTime());
					}else{
						if (tp.getEndTime() != null && tp.getEndTime().getTime() < timePeriod.getEndTime().getTime()) {
							timePeriod.setEndTime(tp.getEndTime());
						}
					}
				}
			}
		}
		return timePeriod;
	}
	
	public static boolean hasBegun(Object... startTimes){
		for (Object startTime : startTimes) {
			if (startTime instanceof Date) {
				Date date = (Date) startTime;
				if(startTime != null){
					if (date.compareTo(new Date()) > 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static boolean hasBegun(Date startTime){
		if (startTime instanceof Date) {
			Date date = (Date) startTime;
			if(startTime != null){
				if (date.compareTo(new Date()) > 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean hasEnded(Object... endTimes){
		for (Object endTime : endTimes) {
			if (endTime instanceof Date) {
				Date date = (Date) endTime;
				if(endTime != null){
					if (date.compareTo(new Date()) < 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean hasEnded(Date endTime){
			if (endTime instanceof Date) {
				Date date = (Date) endTime;
				if(endTime != null){
					if (date.compareTo(new Date()) < 0) {
						return true;
					}
				}
			}
		return false;
	}

}
