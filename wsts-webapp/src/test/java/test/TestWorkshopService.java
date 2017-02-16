package test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointStrategyService;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;

public class TestWorkshopService {
	
	private static ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	
//	private static IWorkshopService WorkshopService = (IWorkshopService) ac.getBean("WorkshopService");
	
	public static void testInsert(){
		IPointStrategyService pointStrategyService = (IPointStrategyService) ac.getBean("pointStrategyService");
		List<PointStrategy> result  = pointStrategyService.findPointStrategys(Maps.newHashMap(), null);
		for(PointStrategy ps:result){
			System.out.println(DigestUtils.md5Hex(ps.getType() + ps.getRelationId()));
		}
//		Workshop Workshop = generateWorkshop();
//		Workshop.setId("test1");
//		Response response = WorkshopService.createWorkshop(Workshop);
//		if(response.isSuccess()){
//			System.out.println("testInsert success");
//		}else{
//			System.out.println("testInsert fail");
//		}
	}
	
	public static void testGet(){
//		Workshop Workshop = WorkshopService.findWorkshopById("test1");
//		if(Workshop != null){
//			System.out.println("testGet success");
//		}else{
//			System.out.println("testGet fail");
//		}
	}
	
	public static void testList(){
//		List<Workshop> Workshops = WorkshopService.findWorkshops(Maps.newHashMap(), null);
//		if(!CollectionUtils.isEmpty(Workshops)){
//			System.out.println("testList success");
//		}else{
//			System.out.println("testList fail");
//		}
	}
	
	
	
	public static void main(String[] args) {
		testInsert();
//		testGet();
//		testList();
	}
	
	public static Workshop generateWorkshop(){
		Workshop Workshop = new Workshop();
		Field [] fields  = Workshop.getClass().getDeclaredFields();
		for(Field field:fields){
			String type = field.getType().toString().toLowerCase();
			field.setAccessible(true);
			try{
				if(type.indexOf("string")>=0){
					field.set(Workshop, RandomString(3));
				}else if(type.indexOf("int")>=0){
					field.set(Workshop,RandomUtils.nextInt(0, 999) );
				}else if(type.indexOf("date")>=0){
					field.set(Workshop,new Date());
				}else if(type.indexOf("long")>=0){
					field.set(Workshop, RandomUtils.nextLong(0l, 999l));;
				}else if(type.indexOf("float")>=0){
					field.set(Workshop, RandomUtils.nextFloat(0f, 999f));
				}
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		return Workshop;
	}
	
	
	
	public static String RandomString(int bit){
		if (bit == 0)
			bit = 6; // 默认6位
		// 因为o和0,l和1很难区分,所以,去掉大小写的o和l
		String str = "";
		str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";// 初始化种子
		return RandomStringUtils.random(bit, str);// 返回6位的字符串
	}
	

}
