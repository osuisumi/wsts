package com.haoyu.wsts.workshop.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

public class ChangeWorkshopEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 3035906217886762861L;

	public ChangeWorkshopEvent(Map<String,Object> source) {
		super(source);
	}

}
