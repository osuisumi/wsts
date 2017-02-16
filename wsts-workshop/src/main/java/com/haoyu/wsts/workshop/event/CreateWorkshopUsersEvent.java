package com.haoyu.wsts.workshop.event;

import org.springframework.context.ApplicationEvent;

import com.haoyu.wsts.workshop.entity.Workshop;

public class CreateWorkshopUsersEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = -7556337904268369847L;

	public CreateWorkshopUsersEvent(Workshop workshop) {
		super(workshop);
	}

}
