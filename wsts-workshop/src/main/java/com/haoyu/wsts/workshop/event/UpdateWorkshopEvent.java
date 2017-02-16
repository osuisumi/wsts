package com.haoyu.wsts.workshop.event;

import org.springframework.context.ApplicationEvent;

import com.haoyu.wsts.workshop.entity.Workshop;

public class UpdateWorkshopEvent extends ApplicationEvent{

	private static final long serialVersionUID = 4110983454747635893L;

	public UpdateWorkshopEvent(Workshop workshop) {
		super(workshop);
	}

}
