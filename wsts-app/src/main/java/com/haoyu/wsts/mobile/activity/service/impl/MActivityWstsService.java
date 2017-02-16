package com.haoyu.wsts.mobile.activity.service.impl;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.act.mobile.entity.MActivity;
import com.haoyu.act.mobile.entity.MActivityResult;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.activity.utils.ActivityAttributeName;
import com.haoyu.aip.activity.utils.ActivityType;
import com.haoyu.aip.courseware.entity.Courseware;
import com.haoyu.aip.courseware.service.ICoursewareService;
import com.haoyu.aip.discussion.entity.Discussion;
import com.haoyu.aip.discussion.mobile.entity.MDiscussion;
import com.haoyu.aip.discussion.mobile.entity.MDiscussionRelation;
import com.haoyu.aip.discussion.mobile.entity.MDiscussionUser;
import com.haoyu.aip.discussion.service.IDiscussionService;
import com.haoyu.aip.mobile.video.entity.MVideo;
import com.haoyu.aip.mobile.video.entity.MVideoUser;
import com.haoyu.aip.qti.dao.ITestDeliveryDao;
import com.haoyu.aip.qti.entity.MultipleChoiceQuestion;
import com.haoyu.aip.qti.entity.Question;
import com.haoyu.aip.qti.entity.QuestionType;
import com.haoyu.aip.qti.entity.SingleChoiceQuestion;
import com.haoyu.aip.qti.entity.TestDelivery;
import com.haoyu.aip.qti.entity.TestDeliveryUser;
import com.haoyu.aip.qti.entity.TestPackage;
import com.haoyu.aip.qti.entity.TestSubmission;
import com.haoyu.aip.qti.entity.TrueFalseQuestion;
import com.haoyu.aip.qti.mobile.entity.MQuestion;
import com.haoyu.aip.qti.mobile.entity.MTest;
import com.haoyu.aip.qti.mobile.entity.MTestSubmission;
import com.haoyu.aip.qti.mobile.entity.MTestUser;
import com.haoyu.aip.qti.service.ITestDeliveryUserService;
import com.haoyu.aip.survey.entity.Survey;
import com.haoyu.aip.survey.entity.SurveyUser;
import com.haoyu.aip.survey.mobile.entity.MSurvey;
import com.haoyu.aip.survey.mobile.entity.MSurveyUser;
import com.haoyu.aip.survey.service.ISurveyService;
import com.haoyu.aip.survey.service.ISurveyUserService;
import com.haoyu.aip.video.entity.Video;
import com.haoyu.aip.video.entity.VideoRelation;
import com.haoyu.aip.video.entity.VideoUser;
import com.haoyu.aip.video.service.IVideoService;
import com.haoyu.aip.video.service.IVideoUserService;
import com.haoyu.dict.utils.DictionaryUtils;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.core.entity.User;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.sip.core.utils.PropertiesLoader;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.evaluate.entity.Evaluate;
import com.haoyu.sip.evaluate.entity.EvaluateItem;
import com.haoyu.sip.evaluate.entity.EvaluateRelation;
import com.haoyu.sip.evaluate.service.IEvaluateRelationService;
import com.haoyu.sip.evaluate.service.IEvaluateService;
import com.haoyu.sip.mobile.file.entity.MFileInfo;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.wsts.activity.service.IWstsActivityBizService;
import com.haoyu.wsts.activity.web.ActivityParam;
import com.haoyu.wsts.mobile.activity.service.IMActivityWstsService;
import com.haoyu.wsts.mobile.lcec.entity.MEvaluateItem;
import com.haoyu.wsts.mobile.lcec.entity.MLcec;
import com.haoyu.wsts.mobile.utils.TimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;

import uk.ac.ed.ph.jqtiplus.SimpleJqtiFacade;
import uk.ac.ed.ph.jqtiplus.node.item.AssessmentItem;
import uk.ac.ed.ph.jqtiplus.node.result.AssessmentResult;
import uk.ac.ed.ph.jqtiplus.node.result.ItemResult;
import uk.ac.ed.ph.jqtiplus.resolution.ResolvedAssessmentItem;
import uk.ac.ed.ph.jqtiplus.state.TestPlan;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode;
import uk.ac.ed.ph.jqtiplus.state.TestSessionState;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode.TestNodeType;
import uk.ac.ed.ph.jqtiplus.utils.contentpackaging.QtiContentPackageExtractor;
import uk.ac.ed.ph.jqtiplus.xmlutils.CustomUriScheme;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.FileSandboxResourceLocator;

@Service
public class MActivityWstsService implements IMActivityWstsService{
	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private IVideoUserService videoUserService;
	@Resource
	private IVideoService videoService;
	@Resource
	private ISurveyService surveyService;
	@Resource
	private ISurveyUserService surveyUserService;
	@Resource
	private ICoursewareService coursewareService;
	@Resource
	private IDiscussionService discussionService;
	@Resource
	private ITestDeliveryDao testDeliveryDao;
	@Resource
	private ITestDeliveryUserService testDeliveryUserService;
	@Resource
	private IWstsActivityBizService wstsActivityBizService;
	@Resource
	private IEvaluateRelationService evaluateRelationService;
	@Resource
	private IEvaluateService evaluateService;
	
	@Override
	public Response list(String sectionId) {
		List<MActivity> mActivities = Lists.newArrayList();
		Map<String, Object> param = Maps.newHashMap();
		param.put("relationId",sectionId);
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(Integer.MAX_VALUE);
		pageBounds.setOrders(Order.formString("SORT_NO,CREATE_TIME"));
		List<Activity> activities = activityService.listActivity(param, true, pageBounds);
		if (Collections3.isNotEmpty(activities)) {
			List<String> activityIds = Collections3.extractToList(activities, "id");
			param = Maps.newHashMap();
			param.put("activityIds", activityIds);
			param.put("creator", ThreadContext.getUser().getId());
			Map<String, ActivityResult> activityResultMap = activityResultService.mapActivityResult(param, null);
			for (Activity activity : activities) {
				MActivity mActivity = new MActivity();
				BeanUtils.copyProperties(activity, mActivity);
				if (activityResultMap.containsKey(activity.getId())) {
					String state = activityResultMap.get(activity.getId()).getState();
					mActivity.setCompleteState(PropertiesLoader.get("property.activity.completeState."+state));
				}else{
					mActivity.setCompleteState(PropertiesLoader.get("property.activity.completeState.notAttempt"));
				}
				mActivities.add(mActivity);
			}
		}
		return Response.successInstance().responseData(mActivities);
	}

	@Override
	public Response view(String actId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		String wsid = WsIdObject.getWsIdObject().getWsid();
		Workshop workshop = workshopService.findWorkshopById(wsid);
		Activity activity = activityService.getActivity(actId);
		
		boolean hasStudentRole = SecurityUtils.getSubject().hasRole(WorkshopUserRole.STUDENT + "_" + wsid);
		boolean inCurrentDate = TimeUtils.inCurrentDate(workshop.getTimePeriod(), activity.getTimePeriod());
		
		TimePeriod timePeriod = TimeUtils.getMinTimePeriod(activity.getTimePeriod(), workshop.getTimePeriod());
		
		String relationId = workshop.getId();
		ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), relationId);
		
		MActivity mActivity = new MActivity();
		MActivityResult mActivityResult = new MActivityResult();
		BeanUtils.copyProperties(activity, mActivity);
		BeanUtils.copyProperties(activityResult, mActivityResult);
		
		mActivity.setTimePeriod(timePeriod);
		mActivity.setInCurrentDate(inCurrentDate);
		mActivityResult.setmActivity(mActivity);
		
	
		if (ActivityType.VIDEO.equals(activity.getType())) {
			String videoId = activity.getEntityId();
			String videoRelationId = VideoRelation.getId(videoId, relationId);
			VideoUser videoUser = videoUserService.createVideoUserIfNotExists(videoRelationId);
			Video video = videoService.viewVideo(videoId, relationId);
			
			MVideoUser mVideoUser = new MVideoUser();
			MVideo mVideo = new MVideo();
			BeanUtils.copyProperties(video, mVideo);
			BeanUtils.copyProperties(videoUser, mVideoUser);
			mVideoUser.setmVideo(mVideo);
			
			mVideo.setInterval(Integer.parseInt(PropertiesLoader.get("video.lastTime.update.interval")));
			if (activity.getAttributeMap() != null && activity.getAttributeMap().containsKey(ActivityAttributeName.VIDEO_VIEW_TIME)) {
				String attrValue = activity.getAttributeMap().get(ActivityAttributeName.VIDEO_VIEW_TIME).getAttrValue();
				if (StringUtils.isNotEmpty(attrValue)) {
					mVideo.setViewTime(Double.parseDouble(attrValue));
				}
			}
			boolean isTiming = hasStudentRole && inCurrentDate;
			mVideoUser.setTiming(isTiming);
			
			mVideo.setVideoFiles(BeanUtils.getCopyList(video.getVideoFiles(), MFileInfo.class));
			mVideo.setAttchFiles(BeanUtils.getCopyList(video.getFileInfos(), MFileInfo.class));

			resultMap.put("mVideoUser", mVideoUser);
		}else if(ActivityType.SURVEY.equals(activity.getType())){
			String surveyId = activity.getEntityId();
			Survey survey = surveyService.findSurveyById(surveyId);
			SurveyUser surveyUser = surveyUserService.createFirstTimeGetSurveyUser(surveyId, relationId);
			
			MSurvey mSurvey = new MSurvey();
			MSurveyUser mSurveyUser = new MSurveyUser();
			BeanUtils.copyProperties(survey, mSurvey);
			BeanUtils.copyProperties(surveyUser, mSurveyUser);
			mSurveyUser.setmSurvey(mSurvey);
			
			resultMap.put("mSurveyUser", mSurveyUser);
		}else if(ActivityType.LCEC.equals(activity.getType())){
			String lcecId = activity.getEntityId();
			Courseware lcec = coursewareService.get(lcecId);
			
			MLcec mLcec = new MLcec();
			
			BeanUtils.copyProperties(lcec, mLcec);
			
			if(lcec.getVideo()!=null){
				MFileInfo mVideo = new MFileInfo();
				BeanUtils.copyProperties(lcec.getVideo(), mVideo);
				mLcec.setmVideo(mVideo);
			}
			
			if(CollectionUtils.isNotEmpty(lcec.getFileInfos())){
				mLcec.setmFileInfos(BeanUtils.getCopyList(lcec.getFileInfos(), MFileInfo.class));
			}
			
			if(StringUtils.isNotEmpty(lcec.getStage())){
				mLcec.setStage(DictionaryUtils.getEntryName("STAGE", lcec.getStage()));
			}
			if(StringUtils.isNotEmpty(lcec.getSubject())){
				mLcec.setSubject(DictionaryUtils.getEntryName("SUBJECT", lcec.getSubject()));
			}
			
			resultMap.put("mLcec", mLcec);
			
			
		}else if (ActivityType.DISCUSSION.equals(activity.getType())) {
			String discussionId = activity.getEntityId();
			Discussion discussion = discussionService.viewDiscussion(discussionId);
			
			MDiscussion mDiscussion = new MDiscussion();
			MDiscussionUser mDiscussionUser = new MDiscussionUser();
			BeanUtils.copyProperties(discussion, mDiscussion);
			mDiscussionUser.setmDiscussion(mDiscussion);
			
			mDiscussion.setmDiscussionRelations(BeanUtils.getCopyList(discussion.getDiscussionRelations(), MDiscussionRelation.class));
			mDiscussion.setmFileInfos(BeanUtils.getCopyList(discussion.getFileInfos(), MFileInfo.class));
			if (activity.getAttributeMap() != null && activity.getAttributeMap().containsKey(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM)) {
				String attrValue = activity.getAttributeMap().get(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM).getAttrValue();
				if (StringUtils.isNotEmpty(attrValue)) {
					mDiscussion.setMainPostNum(Integer.parseInt(attrValue));
				}
			}
			if (activity.getAttributeMap() != null && activity.getAttributeMap().containsKey(ActivityAttributeName.DISCUSSION_SUB_POST_NUM)) {
				String attrValue = activity.getAttributeMap().get(ActivityAttributeName.DISCUSSION_SUB_POST_NUM).getAttrValue();
				if (StringUtils.isNotEmpty(attrValue)) {
					mDiscussion.setSubPostNum(Integer.parseInt(attrValue));
				}
			}
			
			if (activityResult.getDetailMap() != null && activityResult.getDetailMap().containsKey(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM)) {
				String attrValue = String.valueOf(activityResult.getDetailMap().get(ActivityAttributeName.DISCUSSION_MAIN_POST_NUM));
				if (StringUtils.isNotEmpty(attrValue)) {
					mDiscussionUser.setMainPostNum(Integer.parseInt(attrValue));
				}
			}
			if (activityResult.getDetailMap() != null && activityResult.getDetailMap().containsKey(ActivityAttributeName.DISCUSSION_SUB_POST_NUM)) {
				String attrValue = String.valueOf(activityResult.getDetailMap().get(ActivityAttributeName.DISCUSSION_SUB_POST_NUM));
				if (StringUtils.isNotEmpty(attrValue)) {
					mDiscussionUser.setSubPostNum(Integer.parseInt(attrValue));
				}
			}

			resultMap.put("mDiscussionUser", mDiscussionUser);
		}else if (ActivityType.TEST.equals(activity.getType())) {
			String testId = activity.getEntityId();
			TestDelivery testDelivery = testDeliveryDao.selectTestDelivery(testId,relationId);
			User deliveryUser = ThreadContext.getUser();
			TestDeliveryUser testDeliveryUser = new TestDeliveryUser();
			testDeliveryUser.setTestDelivery(testDelivery);
			testDeliveryUser.setDeliveryUser(deliveryUser);
			AssessmentResult assessmentResult = testDeliveryUserService.enterOrReenterTestDeliveryUser(testDeliveryUser);
			
			TestPackage testPackage = testDeliveryUser.getTestDelivery().getTest().getTestPackage();
			List<Question> questions = getQuestions(testDeliveryUser,testPackage.getSandboxPath());
			
			MTestUser mTestUser = new MTestUser();
			MTest mTest = new MTest();
			BeanUtils.copyProperties(testDelivery.getTest(), mTest);
			BeanUtils.copyProperties(testDeliveryUser, mTestUser);
			mTestUser.setmTest(mTest);
			
			mTest.setmQuestions(BeanUtils.getCopyList(questions, MQuestion.class));
			
			if(assessmentResult!=null){
				List<ItemResult> itemResults = assessmentResult.getItemResults();
				for(ItemResult itemResult:itemResults){
					MTestSubmission mTestSubmission = new MTestSubmission();
					TestSubmission testSubmission = new TestSubmission(itemResult);
					BeanUtils.copyProperties(testSubmission, mTestSubmission);
					mTestUser.getmTestSubmissionMap().put(itemResult.getIdentifier(), mTestSubmission);
				}
			}
			resultMap.put("mTestUser", mTestUser);
		}
		resultMap.put("mActivityResult", mActivityResult);
		return Response.successInstance().responseData(resultMap);
	}
	
	/**
	 * @param testDeliveryUser
	 * @return
	 */
	private List<Question> getQuestions(TestDeliveryUser testDeliveryUser,String sandboxPath) {
		TestSessionState testSessionState = testDeliveryUserService.loadTestSessionState(testDeliveryUser);
		TestPlan testPlan = testSessionState.getTestPlan();
		List<TestPlanNode> testPlanNodeList = testPlan.getTestPlanNodeList();
		SimpleJqtiFacade simpleJqtiFacade = new SimpleJqtiFacade();
		List<Question> questions = Lists.newArrayList();
		for(TestPlanNode testPlanNode:testPlanNodeList){
			if(testPlanNode.getTestNodeType().equals(TestNodeType.ASSESSMENT_ITEM_REF)){
				URI uri = testPlanNode.getItemSystemId();
				final File sandboxDirectory = new File(sandboxPath);
	            final CustomUriScheme packageUriScheme = QtiContentPackageExtractor.PACKAGE_URI_SCHEME;
	            final FileSandboxResourceLocator fileSandboxResourceLocator = new FileSandboxResourceLocator(packageUriScheme, sandboxDirectory);
				ResolvedAssessmentItem ra =simpleJqtiFacade.loadAndResolveAssessmentItem(fileSandboxResourceLocator, uri);
				AssessmentItem assessmentItem = ra.getRootNodeLookup().extractIfSuccessful();
				QuestionType questionType  =  Question.getQuesType(assessmentItem);
				switch(questionType){
						case SINGLE_CHOICE:
							Question question =new SingleChoiceQuestion(assessmentItem); 
							question.setId(testPlanNode.getIdentifier().toString());
							question.setItemKey(testPlanNode.getKey().toString());
							questions.add(question);
							break;
						case TRUE_FALSE:
							question =new TrueFalseQuestion(assessmentItem); 
							question.setId(testPlanNode.getIdentifier().toString());
							question.setItemKey(testPlanNode.getKey().toString());
							questions.add(question);
							break;
						case MULTIPLE_CHOICE:
							question =new MultipleChoiceQuestion(assessmentItem); 
							question.setId(testPlanNode.getIdentifier().toString());
							question.setItemKey(testPlanNode.getKey().toString());
							questions.add(question);
							break;
						default:
							break;
				}
			}
		}
		return questions;
	}

	@Override
	public Response createActivity(ActivityParam activityParam) {
		Response response  = wstsActivityBizService.createActivity(activityParam);
		if(response.isSuccess()){
			if(response.getResponseData()!=null && response.getResponseData() instanceof Activity){
				Activity act = (Activity) response.getResponseData();
				MActivity mActivity = new MActivity();
				BeanUtils.copyProperties(act, mActivity);
				response.setResponseData(mActivity);
			}
		}
		return response;
	}

	@Override
	public Response viewForUpdate(String actId) {
		Activity activity = activityService.getActivity(actId);
		Map<String,Object> result = Maps.newHashMap();
		if(activity!=null){
			MActivity mActivity = new MActivity();
			BeanUtils.copyProperties(activity, mActivity);
			result.put("mActivity", mActivity);
			switch(activity.getType()){
				case ActivityType.DISCUSSION:
					Discussion discussion = discussionService.get(activity.getEntityId());
					String main_post_num = activity.getAttributeMap().get("main_post_num").getAttrValue();
					String sub_post_num = activity.getAttributeMap().get("sub_post_num").getAttrValue();
					MDiscussion mDiscussion = new MDiscussion();
					BeanUtils.copyProperties(discussion, mDiscussion);
					result.put("mDiscussion", mDiscussion);
					result.put("main_post_num", main_post_num);
					result.put("sub_post_num", sub_post_num);
					break;
				case ActivityType.VIDEO:
					Video video = videoService.get(activity.getEntityId());
					String view_time = activity.getAttributeMap().get("view_time").getAttrValue();
					
					MVideo mVideo = new MVideo();
					BeanUtils.copyProperties(video, mVideo);
					
					result.put("mVideo",mVideo);
					result.put("view_time", view_time);
					break;
				case ActivityType.LCEC:
					Courseware lcec = coursewareService.get(activity.getEntityId());
					
					EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcec.getId());
					
					List<MEvaluateItem> mEvaluateItems = Lists.newArrayList();
					if(evaluateRelation!=null && evaluateRelation.getEvaluate()!=null){
						Evaluate evaluate = evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
						List<EvaluateItem> evaluateItems = evaluate.getEvaluateItems();
						for(EvaluateItem ei:evaluateItems){
							MEvaluateItem mei = new MEvaluateItem();
							BeanUtils.copyProperties(ei, mei);
							mEvaluateItems.add(mei);
						}
					}
					
					MLcec mLcec = new MLcec();
					BeanUtils.copyProperties(lcec, mLcec);
					
					result.put("mEvaluateItems", mEvaluateItems);
					result.put("mLcec", mLcec);
					
			}
		}

		return Response.successInstance().responseData(result);
	}

}
