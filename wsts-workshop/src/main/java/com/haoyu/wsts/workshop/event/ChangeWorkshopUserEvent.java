package com.haoyu.wsts.workshop.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

public class ChangeWorkshopUserEvent extends ApplicationEvent{
	private static final long serialVersionUID = 4170585871468918237L;
	
	public ChangeWorkshopUserEvent(Map<String,Object> source) {
		super(source);
	}

}
