package com.haoyu.wsts.activity.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.entity.ActivityAttribute;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityAttributeService;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.activity.utils.ActivityAttributeName;
import com.haoyu.aip.activity.utils.ActivityResultState;
import com.haoyu.aip.activity.utils.ActivityType;
import com.haoyu.aip.courseware.entity.Courseware;
import com.haoyu.aip.courseware.entity.CoursewareRelation;
import com.haoyu.aip.courseware.service.ICoursewareService;
import com.haoyu.aip.debate.entity.Debate;
import com.haoyu.aip.debate.entity.DebateArgument;
import com.haoyu.aip.debate.entity.DebateRelation;
import com.haoyu.aip.debate.service.IDebateService;
import com.haoyu.aip.discussion.entity.Discussion;
import com.haoyu.aip.discussion.entity.DiscussionPost;
import com.haoyu.aip.discussion.entity.DiscussionRelation;
import com.haoyu.aip.discussion.entity.DiscussionUser;
import com.haoyu.aip.discussion.service.IDiscussionPostService;
import com.haoyu.aip.discussion.service.IDiscussionRelationService;
import com.haoyu.aip.discussion.service.IDiscussionService;
import com.haoyu.aip.discussion.service.IDiscussionUserService;
import com.haoyu.aip.lessonplan.entity.LessonPlan;
import com.haoyu.aip.lessonplan.entity.LessonPlanRelation;
import com.haoyu.aip.lessonplan.service.ILessonPlanService;
import com.haoyu.aip.qti.entity.Question;
import com.haoyu.aip.qti.entity.QuestionFormKey;
import com.haoyu.aip.qti.entity.Test;
import com.haoyu.aip.qti.entity.TestDelivery;
import com.haoyu.aip.qti.service.ITestService;
import com.haoyu.aip.survey.entity.Survey;
import com.haoyu.aip.survey.entity.SurveyQuestion;
import com.haoyu.aip.survey.entity.SurveyRelation;
import com.haoyu.aip.survey.service.ISurveyQuestionService;
import com.haoyu.aip.survey.service.ISurveyService;
import com.haoyu.aip.video.entity.Video;
import com.haoyu.aip.video.entity.VideoRelation;
import com.haoyu.aip.video.service.IVideoService;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.evaluate.entity.Evaluate;
import com.haoyu.sip.evaluate.entity.EvaluateItem;
import com.haoyu.sip.evaluate.entity.EvaluateRelation;
import com.haoyu.sip.evaluate.service.IEvaluateItemService;
import com.haoyu.sip.evaluate.service.IEvaluateRelationService;
import com.haoyu.sip.evaluate.service.IEvaluateService;
import com.haoyu.sip.file.entity.FileInfo;
import com.haoyu.sip.file.entity.FileRelation;
import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.service.IFileRelationService;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.sip.file.service.IFileService;
import com.haoyu.sip.tag.entity.Tag;
import com.haoyu.sip.tag.entity.TagRelation;
import com.haoyu.sip.tag.service.ITagRelationService;
import com.haoyu.sip.tag.service.ITagService;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.sip.utils.Identities;
import com.haoyu.wsts.activity.service.IWstsActivityBizService;
import com.haoyu.wsts.activity.web.ActivityParam;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.utils.FileResourceType;

@Service
public class WstsActivityBizServiceImpl implements IWstsActivityBizService{
	
	@Resource
	private IDiscussionService discussionService;
	@Resource
	private IVideoService videoService;
	@Resource
	private IActivityService activityService;
	@Resource
	private ISurveyService surveyService;
	@Resource
	private ITestService testService;
	@Resource
	private IDiscussionPostService discussionPostService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private ILessonPlanService lessonPlanService;
	@Resource
	private IDebateService debateService;
	@Resource
	private ICoursewareService coursewareService;
	@Resource
	private IFileService fileService;
	@Resource
	private IFileRelationService fileRelationService;
	@Resource
	private ISurveyQuestionService surveyQuestionService;
	@Resource
	private IActivityAttributeService activityAttributeService;
	@Resource
	private IEvaluateRelationService evaluateRelationService;
	@Resource
	private IEvaluateService evaluateService;
	@Resource
	private IEvaluateItemService evaluateItemService;
	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private ITagService tagService;
	@Resource
	private ITagRelationService tagRelationService;
	@Resource
	private IWorkshopSectionService workshopSectionService;
	@Resource
	private IDiscussionUserService discussionUserService;
	@Resource
	private IDiscussionRelationService discussionRelationService;

	@Override
	public Response createActivity(ActivityParam activityParam) {
		Activity activity = activityParam.getActivity();
		String type = activity.getType();
		Response response = null;
		if (ActivityType.DISCUSSION.equals(type)) {
			Discussion discussion = activityParam.getDiscussion();
			activity.setTitle(discussion.getTitle());
			response = discussionService.createDiscussion(discussion);
			activity.setEntityId(discussion.getId());
		}else if(ActivityType.VIDEO.equals(type)){
			Video video = activityParam.getVideo();
			activity.setTitle(video.getTitle());
			response = videoService.createVideo(video);
			activity.setEntityId(video.getId());
		}else if(ActivityType.SURVEY.equals(type)){
			Survey survey = activityParam.getSurvey();
			activity.setTitle(survey.getTitle());
			response = surveyService.createSurvey(survey);
			activity.setEntityId(survey.getId());
		}else if(ActivityType.TEST.equals(type)){
			Test test = activityParam.getTest();
			activity.setTitle(test.getTitle());
			response = testService.createTest(test);
			activity.setEntityId(test.getId());
		}else if(ActivityType.LESSON_PLAN.equals(type)){
			LessonPlan lessonPlan = activityParam.getLessonPlan();
			activity.setTitle(lessonPlan.getTitle());
			response = lessonPlanService.createLessonPlan(lessonPlan);
			activity.setEntityId(lessonPlan.getId());
		}else if(ActivityType.DEBATE.equals(type)){
			Debate debate = activityParam.getDebate();
			activity.setTitle(debate.getTitle());
			response = debateService.createDebate(debate);
			activity.setEntityId(debate.getId());
		}else if(ActivityType.LCEC.equals(type)){
			Courseware courseware = activityParam.getLcec();
			activity.setTitle(courseware.getTitle());
			response = coursewareService.create(courseware);
			activity.setEntityId(courseware.getId());
		}
		if (response.isSuccess()) {
			return activityService.createActivity(activity);
		}
		return Response.failInstance();
	}

	@Override
	public Response updateActivity(ActivityParam activityParam) {
		Activity activity = activityParam.getActivity();
		String type = activity.getType();
		if (ActivityType.DISCUSSION.equals(type)) {
			Discussion discussion = activityParam.getDiscussion();
			if (discussion != null) {
				activity.setTitle(discussion.getTitle());
				discussionService.updateDiscussion(discussion);
			}
		}else if(ActivityType.VIDEO.equals(type)){
			Video video = activityParam.getVideo();
			if (video != null) {
				activity.setTitle(video.getTitle());
				videoService.updateVideo(video);
			}
		}else if(ActivityType.SURVEY.equals(type)){
			Survey survey = activityParam.getSurvey();
			if(survey!=null){
				activity.setTitle(survey.getTitle());
				surveyService.updateSurvey(survey);
			}
		}else if(ActivityType.TEST.equals(type)){
			Test test = activityParam.getTest();
			activity.setTitle(test.getTitle());
			testService.updateTest(test);
		}else if(ActivityType.LESSON_PLAN.equals(type)){
			LessonPlan lessonPlan = activityParam.getLessonPlan();
			activity.setTitle(lessonPlan.getTitle());
			lessonPlanService.updateLessonPlan(lessonPlan);
		}else if(ActivityType.DEBATE.equals(type)){
			Debate debate = activityParam.getDebate();
			activity.setTitle(debate.getTitle());
			debateService.updateDebate(debate);
		}else if(ActivityType.LCEC.equals(type)){
			Courseware courseware = activityParam.getLcec();
			activity.setTitle(courseware.getTitle());
			coursewareService.update(courseware);
		}
		return activityService.updateActivity(activity);
	}

	@Override
	public void doDiscussionActivity(DiscussionPost discussionPost) {
		discussionPost = discussionPostService.get(discussionPost.getId());
		DiscussionUser discussionUser = discussionUserService.get(discussionPost.getDiscussionUser().getId());
		DiscussionRelation discussionRelation = discussionRelationService.get(discussionUser.getDiscussionRelation().getId());
		String relationId = discussionRelation.getRelation().getId();
		String discussionId = discussionRelation.getDiscussion().getId();
		Activity activity = activityService.getActivityByEntityId(discussionId);
		if (activity != null) {
			ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), relationId);
			Map<String, ActivityAttribute> attributeMap = activity.getAttributeMap();
			Map<String, Object> param = Maps.newHashMap();
			param.put("discussionUserId", discussionPost.getDiscussionUser().getId());
			param.put("creator", ThreadContext.getUser().getId());
			param.put("mainOrSub", "main");
			int myMainPostNum = discussionPostService.getCount(param);
			param.put("mainOrSub", "sub");
			int mySubPostNum = discussionPostService.getCount(param);
			
			int mainPostNum = 0;
			int subPostNum = 0;
			float pct = 0;
			if (attributeMap.containsKey(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM)) {
				String num = attributeMap.get(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM).getAttrValue();
				if (StringUtils.isNotEmpty(num)) {
					mainPostNum = Integer.parseInt(num);
				}
			}
			if (attributeMap.containsKey(ActivityAttributeName.DISCUSSION_SUB_POST_NUM)) {
				String num = attributeMap.get(ActivityAttributeName.DISCUSSION_SUB_POST_NUM).getAttrValue();
				if (StringUtils.isNotEmpty(num)) {
					subPostNum = Integer.parseInt(num);
				}
			}
			if (myMainPostNum == 0 && mySubPostNum == 0){
				activityResult.setState(ActivityResultState.NOT_ATTEMPT);
			}else if(myMainPostNum < mainPostNum || mySubPostNum < subPostNum) {
				activityResult.setState(ActivityResultState.IN_PROGRESS);
			}else{
				activityResult.setState(ActivityResultState.COMPLETE);
				pct = 100;
			}
			
			Map<String, Object> result = Maps.newHashMap();
			result.put(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM, myMainPostNum);
			result.put(ActivityAttributeName.DISCUSSION_SUB_POST_NUM, mySubPostNum);
			result.put(ActivityAttributeName.COMPLETE_PCT, pct);
			activityResult.setDetail(new JsonMapper().toJson(result));
			activityResult.setScore(BigDecimal.valueOf(pct));
			activityResult.setActivity(activity);
			activityResultService.updateActivityResult(activityResult);
		}
	}

	@Override
	public Response createExtendActivity(Activity activity, String sectionId, String workshopId, String origWorkshopId) {
		ActivityParam activityParam = new ActivityParam();
		String origActivityId = activity.getId();
		activity.setId(null);
		activity.setRelation(new Relation(sectionId));
		activityParam.setActivity(activity);
		String testId = null;
		Test test = null;
		
		String type = activity.getType();
		if (ActivityType.DISCUSSION.equals(type)) {
			String discussionId = activity.getEntityId();
			Discussion discussion = discussionService.get(discussionId);
			if (discussion != null) {
				discussion.setId(Identities.uuid2());
				DiscussionRelation discussionRelation = new DiscussionRelation();
				discussionRelation.setRelation(new Relation(workshopId));
				discussion.setDiscussionRelations(Lists.newArrayList(discussionRelation));
				List<FileInfo> fileInfos = fileService.listFileInfoByRelationId(discussionId);
				if (Collections3.isNotEmpty(fileInfos)) {
					for (FileInfo fileInfo : fileInfos) {
						FileRelation fileRelation = new FileRelation();
						fileRelation.setFileId(fileInfo.getId());
						fileRelation.setRelation(new Relation(discussion.getId()));
						fileRelation.setType("discussion");
						fileRelationService.create(fileRelation);
					}
				}
				activityParam.setDiscussion(discussion);
			}
		}else if(ActivityType.VIDEO.equals(type)){
			String videoId = activity.getEntityId();
			Video video = videoService.getVideo(videoId);
			if (video != null) {
				video.setId(Identities.uuid2());
				VideoRelation videoRelation = new VideoRelation();
				videoRelation.setRelation(new Relation(workshopId));
				video.setVideoRelations(Lists.newArrayList(videoRelation));
				List<FileInfo> videoFiles = video.getVideoFiles();
				if (Collections3.isNotEmpty(videoFiles)) {
					for (FileInfo fileInfo : videoFiles) {
						FileRelation fileRelation = new FileRelation();
						fileRelation.setFileId(fileInfo.getId());
						fileRelation.setRelation(new Relation(video.getId()));
						fileRelation.setType("video");
						fileRelationService.create(fileRelation);
					}
				}
				List<FileInfo> fileInfos = video.getFileInfos();
				if (Collections3.isNotEmpty(fileInfos)) {
					for (FileInfo fileInfo : fileInfos) {
						FileRelation fileRelation = new FileRelation();
						fileRelation.setFileId(fileInfo.getId());
						fileRelation.setRelation(new Relation(video.getId()));
						fileRelation.setType("video_file");
						fileRelationService.create(fileRelation);
					}
				}
				video.setVideoFiles(null);
				video.setFileInfos(null);
				activityParam.setVideo(video);
			}
		}else if(ActivityType.SURVEY.equals(type)){
			String surveyId = activity.getEntityId();
			Survey survey = surveyService.findSurveyById(surveyId);
			if (survey != null) {
				survey.setId(Identities.uuid2());
				SurveyRelation surveyRelation = new SurveyRelation();
				surveyRelation.setRelation(new Relation(workshopId));
				survey.setSurveyRelations(Lists.newArrayList(surveyRelation));
				List<SurveyQuestion> surveyQuestions = survey.getQuestions();
				if (Collections3.isNotEmpty(surveyQuestions)) {
					for (SurveyQuestion surveyQuestion : surveyQuestions) {
						surveyQuestion.setId(null);
					}
					surveyQuestionService.createSurveyQuestions(survey);
				}
				activityParam.setSurvey(survey);
			}
		}else if(ActivityType.TEST.equals(type)){
			testId = activity.getEntityId();
			test = testService.findTestById(testId);
			if (test != null) {
				test.setId(Identities.uuid2());
				test.setTestPackage(null);
				TestDelivery testDelivery = new TestDelivery();
				testDelivery.setRelationId(workshopId);
				test.setTestDeliveries(Lists.newArrayList(testDelivery));
				activityParam.setTest(test);
			}
		}else if(ActivityType.DEBATE.equals(type)){
			//复制辩论
			String debateId = activity.getEntityId();
			Debate debate = debateService.findDebateById(debateId);
			if(debate != null){
				debate.setId(Identities.uuid2());
				DebateRelation debateRelation = debate.getDebateRelations().get(0);
				debateRelation.setDebate(debate);
				debateRelation.setRelation(new Relation(workshopId));
				debate.getDebateRelations().set(0, debateRelation);
				
				List<DebateArgument> arguments = debate.getArguments();
				if(CollectionUtils.isNotEmpty(arguments)){
					for(DebateArgument da:arguments){
						da.setId(Identities.uuid2());
					}
				}
				activityParam.setDebate(debate);
			}
		}else if(ActivityType.LCEC.equals(type)){
			//复制听课评课
			String lcecId = activity.getEntityId();
			Courseware courseware = coursewareService.get(lcecId);
			if(courseware != null){
				courseware.setId(Identities.uuid2());
				CoursewareRelation coursewareRelation = courseware.getCoursewareRelations().get(0);
				coursewareRelation.getRelation().setId(workshopId);
				coursewareRelation.setCoursewareId(courseware.getId());
				courseware.getCoursewareRelations().set(0,coursewareRelation);
				
				//复制附件
				List<FileInfo> fileInfos = courseware.getFileInfos();
				if (Collections3.isNotEmpty(fileInfos)) {
					for (FileInfo fileInfo : fileInfos) {
						FileRelation fileRelation = new FileRelation();
						fileRelation.setFileId(fileInfo.getId());
						fileRelation.setRelation(new Relation(courseware.getId()));
						fileRelation.setType("video_file");
						fileRelationService.create(fileRelation);
					}
				}
				//复制视频
				FileInfo video = courseware.getVideo();
				if(video != null){
					FileRelation fileRelation = new FileRelation();
					fileRelation.setFileId(video.getId());
					fileRelation.setRelation(new Relation(courseware.getId()));
					fileRelation.setType("video_file");
					fileRelationService.create(fileRelation);
				}
				courseware.setFileInfos(null);
				courseware.setVideo(null);
				
				//复制评价项
				EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcecId);
				if (evaluateRelation != null && evaluateRelation.getEvaluate() != null) {
					Evaluate evaluate = evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
					evaluate.setId(null);
					Response response = evaluateService.createEvaluate(evaluate);
					if (response.isSuccess()) {
						evaluateRelation.setId(null);
						evaluateRelation.setEvaluate(evaluate);
						evaluateRelation.setRelation(new Relation(courseware.getId()));
						response = evaluateRelationService.createEvaluateRelation(evaluateRelation);
						if (response.isSuccess()) {
							List<EvaluateItem> evaluateItems = evaluate.getEvaluateItems();
							if (Collections3.isNotEmpty(evaluateItems)) {
								for (EvaluateItem evaluateItem : evaluateItems) {
									evaluateItem.setId(null);
									evaluateItem.setEvaluate(evaluate);
									evaluateItemService.createEvaluateItem(evaluateItem);
								}
							}
						}
					}
				}
				activityParam.setLcec(courseware);
			}
		}else if(ActivityType.LESSON_PLAN.equals(type)){
			//复制备课
			String lessonPlanId = activity.getEntityId();
			LessonPlan lessonPlan = lessonPlanService.get(lessonPlanId);
			if(lessonPlan != null){
				lessonPlan.setId(Identities.uuid2());
				LessonPlanRelation lessonPlanRealtion = lessonPlan.getLessonPlanRelations().get(0);
				lessonPlanRealtion.getRelation().setId(workshopId);
				lessonPlanRealtion.setLessonPlan(lessonPlan);
				lessonPlan.getLessonPlanRelations().set(0, lessonPlanRealtion);
				
				
				FileRelation fileRelation = new FileRelation();
				fileRelation.setRelation(new Relation(LessonPlanRelation.getId(lessonPlan.getId(), workshopId)));
				fileRelation.setType("lesson_plan_relation");
				FileResource fileResource = new FileResource();
				fileResource.setName("共案");
				fileResource.setIsFolder("Y");
				fileResource.setType(FileResourceType.FIXED);
				fileResource.setFileRelations(Lists.newArrayList(fileRelation));
				Response response = fileResourceService.createFileResource(fileResource);
				if (response.isSuccess()) {
					fileResource.setId(Identities.uuid2());
					fileResource.setName("初备");
					fileRelation.setFileId(fileResource.getId());
					response = fileResourceService.createFileResource(fileResource);
				}
				
				activityParam.setLessonPlan(lessonPlan);
			}
		}
		Response response = this.createActivity(activityParam);
		if (response.isSuccess()) {
			List<Tag> tags = tagService.findTagByNameAndRelations(null, Lists.newArrayList(origActivityId), null);
			if (Collections3.isNotEmpty(tags)) {
				for (Tag tag : tags) {
					TagRelation tagRelation = new TagRelation();
					tagRelation.setTag(tag);
					tagRelation.setRelation(new Relation(activity.getId(), "activity"));
					tagRelationService.createTagRelation(tagRelation);
				}
			}
			if (ActivityType.TEST.equals(type)) {
				List<Question> questions = testService.findQuestionsByTestId(testId);
				if(Collections3.isNotEmpty(questions)){
					int index = 0;
					for (Question question : questions) {
						question.setId(null);
						testService.addTestQuestion(test, question, new QuestionFormKey("P1:S1:Q"+index));
						index++;
					}
				}
			}
		}
		return response;
	}

	@Override
	public Response createSystemActivity(String workshopId) {
		Map<String,Object> old = Maps.newHashMap();
		old.put("state", "static");
		List<Activity> oldSystemActivity = activityService.listActivity(old, false, null);
		String ids = "";
		for(Activity activity:oldSystemActivity){
			if("".equals(ids)){
				ids = activity.getId();
			}else{
				ids = ids + "," + activity.getId();
			}
		}
		Activity delete = new Activity();
		delete.setId(ids);
		activityService.deleteActivityByLogic(delete);
		Map<String, Object> param = Maps.newHashMap();
		param.put("workshopId", workshopId);
		List<WorkshopSection> workshopSections = workshopSectionService.findWorkshopSections(param, true, null);
		if (Collections3.isNotEmpty(workshopSections)) {
			for (WorkshopSection section : workshopSections) {
				param = Maps.newHashMap();
				param.put("relationId", section.getId());
				PageBounds pageBounds = new PageBounds();
				pageBounds.setOrders(Order.formString("SORT_NO,CREATE_TIME"));
				List<Activity> activities = activityService.listActivity(param, true, pageBounds);
				if (Collections3.isNotEmpty(activities)) {
					for (Activity activity : activities) {
						activity.setState("static");
						createExtendActivity(activity, "SYSTEM", "SYSTEM", "");
					}
				}
			}
		}
		return Response.successInstance();
	}
	
}
