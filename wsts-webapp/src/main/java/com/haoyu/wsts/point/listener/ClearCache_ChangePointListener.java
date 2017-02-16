package com.haoyu.wsts.point.listener;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.haoyu.sip.core.utils.PropertiesLoader;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.event.ChangePointRecordEvent;

@Component
public class ClearCache_ChangePointListener implements ApplicationListener<ChangePointRecordEvent>{
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Override
	public void onApplicationEvent(ChangePointRecordEvent event) {
		String redis_key = PropertiesLoader.get("redis.app.key");
		PointRecord pointRecord = (PointRecord) event.getSource();
		if (StringUtils.isNotEmpty(pointRecord.getUserId())) {
			Set<String> keys = redisTemplate.keys(redis_key + ":userCenterProgess:" + pointRecord.getUserId() + ":*");
			redisTemplate.delete(keys);
			keys = redisTemplate.keys(redis_key + ":userCenterIndexPage:workshops:" + pointRecord.getUserId() + ":*");
			redisTemplate.delete(keys);
		}
	}
}
