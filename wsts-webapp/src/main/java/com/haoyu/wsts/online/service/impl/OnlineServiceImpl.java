package com.haoyu.wsts.online.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.PropertiesLoader;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.wsts.online.service.IOnlineService;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.entity.WorkshopUserResult;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;

@Service
public class OnlineServiceImpl implements IOnlineService{

	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private IWorkshopUserResultService workshopUserResultService;
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;
	
	@Override
	public Response incOnlineTime() {
		String wsid = WsIdObject.getWsIdObject().getWsid();
		String uid = ThreadContext.getUser().getId();
		if (StringUtils.isNotEmpty(wsid) && StringUtils.isNotEmpty(uid)) {
			//更新缓存的间隔分钟数
			int cache_inc_minute = Integer.parseInt(PropertiesLoader.get("point.online.cache.inc.minute"));
			//更新数据库之前所需更新缓存次数
			int db_inc_times = Integer.parseInt(PropertiesLoader.get("point.online.db.inc.times"));
			//在线时长能获取的最大积分
			int max_point = Integer.parseInt(PropertiesLoader.get("point.online.max.point"));
			//获取积分的间隔分钟数
			int point_inc_minute = Integer.parseInt(PropertiesLoader.get("point.online.point.inc.minute"));
			
			String key = "online_time_" + uid + "_" + wsid;
			redisTemplate.setValueSerializer(redisTemplate.getDefaultSerializer());
			ValueOperations<String,Map<String, Object>> valueOper = redisTemplate.opsForValue();
			String id = WorkshopUser.getId(wsid, uid);
			if (redisTemplate.hasKey(key)) {
				Map<String, Object> map = valueOper.get(key);
				long lastTime = (long) map.get("lastTime");
				long now = new Date().getTime();
				int cache_inc_times = (int) map.get("cache_inc_times");
				Float online_minute = (Float) map.get("online_minute"); 
				if (online_minute == 0) {
					WorkshopUserResult workshopUserResult = workshopUserResultService.findWorkshopUserResultById(id);
					//缓存有数据，工作坊没有，直接返回
					if(workshopUserResult == null ){
						return Response.successInstance();
					}
					online_minute = workshopUserResult.getLearnMinuteLength();
				}
				online_minute ++;
				//与上次间隔1分钟, 更新缓存
				if (now - lastTime >= cache_inc_minute * 60 * 1000) {
					map.put("lastTime", now);
					map.put("online_minute", online_minute);
					if (cache_inc_times + 1 < db_inc_times) {
						map.put("cache_inc_times", cache_inc_times + 1);
					}else{
						map.put("cache_inc_times", 0);
						//每更新缓存5次, 更新1次数据库
						WorkshopUserResult workshopUserResult =  new WorkshopUserResult();
						workshopUserResult.setId(id);
						workshopUserResult.setLearnMinuteLength(online_minute);
						Response response = workshopUserResultService.updateWorkshopUserResult(workshopUserResult);
						if (response.isSuccess()) {
							//每30分钟+1积分, 20分封顶
							if(workshopTimeUtils.isWorkshopOnGoing(wsid)){
								if (online_minute / point_inc_minute < max_point) {
									int after = (int) ((online_minute) / point_inc_minute);
									int before = (int) ((online_minute - db_inc_times * cache_inc_minute) / point_inc_minute);
									if (after == before + 1) {
										PointRecord pointRecord = new PointRecord();
										pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.ONLINE, "wsts"));
										pointRecord.setUserId(ThreadContext.getUser().getId());
										pointRecord.setRelationId(wsid);
										pointRecordService.createPointRecord(pointRecord);
									}
								}
							}
						}
					}
					valueOper.set(key, map);
				}
			}else{
				//缓存不存在, 查询数据库的在线时长, 并保存到缓存
				long now = new Date().getTime();
				WorkshopUserResult workshopUserResult = workshopUserResultService.findWorkshopUserResultById(id);
				//不在工作坊内
				if(workshopUserResult !=null){
					Float online_minute = workshopUserResult.getLearnMinuteLength();
					
					Map<String, Object> map = Maps.newHashMap();
					map.put("cache_inc_times", 1);
					map.put("lastTime", now);
					map.put("online_minute", online_minute==null?1:online_minute + 1);
					valueOper.set(key, map);
				}

			}
			return Response.successInstance();
		}
		return Response.failInstance();
	}
}
