package com.haoyu.wsts.workshop.listener;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.message.entity.Message;
import com.haoyu.sip.message.service.IMessageService;
import com.haoyu.sip.message.utils.MessageType;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.event.CreateWorkshopUsersEvent;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.utils.WorkshopType;

@Component
public class CreateWorkshopUsersListener implements ApplicationListener<CreateWorkshopUsersEvent>{
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private IMessageService messageService;

	@Override
	public void onApplicationEvent(CreateWorkshopUsersEvent event) {
		Workshop workshop = (Workshop) event.getSource();
		if(workshop!=null){
			if(CollectionUtils.isNotEmpty(workshop.getWorkshopUsers())&&StringUtils.isNotEmpty(workshop.getId())){
				Workshop w = workshopService.findWorkshopById(workshop.getId());
				if(w!=null&&WorkshopType.PERSONAL.equals(w.getType())){
					Message message = new Message();
					message.setType(MessageType.SYSTEM_MESSAGE);
					message.setContent(ThreadContext.getUser().getRealName() + "邀请您加入'"+w.getTitle()+"'工作坊成员。");
					List<String> userIds =  Collections3.extractToList(workshop.getWorkshopUsers(), "user.id");
					if(CollectionUtils.isNotEmpty(userIds)){
						messageService.sendMessageToUsers(message, userIds);
					}
				}
			}
		}
	}
}
