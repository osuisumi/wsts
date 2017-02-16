<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#assign jsArray=['/wsts/js/activity-file.js']/>
<@workshopDirective id=wsid>
	<#global workshop=workshop />
</@workshopDirective>
<@layout jsArray=jsArray>
<div class="g-auto">
	<div class="g-frame-sd">
		<#import "/wsts/workshop/side_frame.ftl" as sideFrame/>
		<@sideFrame.sideFrameFtl workshopId=wsid />
	</div>
	<div class="g-frame-mn">
		<#if role != 'guest'>
		<div class="m-WSdet-where">                    
            <a href="${PropertiesLoader.get('wsts.list.workshop') }"><i></i>首页</a>
            <ins>&gt;</ins>
            <span><a href="/workshop/${wsid}">${workshop.title! }</a></span>                        
            <ins>&gt;</ins>
            <span>${activity.title! }</span>
        </div>
        <div class="WS-active-cont">
			<@activityDirective id=activity.id>
				<#if ((activity.state)!'') = 'static'>
					<#assign relationId='SYSTEM' />
				<#else>
					<#assign relationId=wsid />
				</#if>
				<#if (activity.type)! == 'discussion'> 
					<#import "discussion/view_discussion.ftl" as vd /> 
					<@vd.viewDiscussionFtl discussionId=activity.entityId! aid=activity.id! relationId=relationId /> 
				<#elseif (activity.type)! == 'video'> 
					<#import "video/view_video.ftl" as vv /> 
					<@vv.viewVideoFtl videoId=activity.entityId! aid=activity.id! relationId=relationId/> 
				<#elseif (activity.type)! == 'html'> 
					<#import "text/view_html.ftl" as vh /> 
					<@vh.viewHtmlFtl textInfoId=activity.entityId! aid=activity.id! relationId=relationId/> 
				<#elseif (activity.type)! == 'survey'>
					<#if (surveyStep[0])! == 'viewResult'>
						<#import "survey/view_survey_result.ftl" as viewSurveyResult/>
						<@viewSurveyResult.viewSurveyResultFtl surveyId=activity.entityId! aid=activity.id relationId=relationId />
					<#elseif (surveyStep[0])! == 'questionResultDetail'>
						<#import "survey/list_survey_submission.ftl" as listSubmissions/>
						<@listSubmissions.listSurveySubmissionsFtl surveyId=activity.entityId! aid=activity.id questionId=(questionId[0])! relationId=relationId />
					<#else>
						<#import "survey/view_survey.ftl" as viewSurvey />
						<@viewSurvey.viewSurveyFtl surveyId=activity.entityId! aid=activity.id relationId=relationId /> 
					</#if>
				<#elseif (activity.type)! == 'assignment'>
					<#import "assignment/view_assignment.ftl" as va />
					<@va.viewAssignmentFtl assignmentId=activity.entityId! aid=activity.id! index=index! relationId=relationId/> 
				<#elseif (activity.type)! == 'test'>
					<#import "test/view_test.ftl" as vt />
					<@vt.viewTestFtl testId=activity.entityId! aid=activity.id! relationId=relationId/>
				<#elseif (activity.type)! == 'lesson_plan'>
					<#import "lessonPlan/view_lesson_plan.ftl" as lp />
					<@lp.viewLessonPlanFtl lessonPlanId=activity.entityId! aid=activity.id! relationId=relationId/>
				<#elseif (activity.type)! == 'debate' >
					<#import "debate/view_debate.ftl" as lp />
					<@lp.viewDebateFtl debateId=activity.entityId! aid=activity.id! relationId=relationId/>
				<#elseif (activity.type)! == 'lcec'>
					<#import "lcec/view_lcec.ftl" as lcec />
					<@lcec.viewLcecFtl lcecId=activity.entityId! aid=activity.id! relationId=relationId/>
				</#if> 
			</@activityDirective> 
		</div>
		<#else>
			<#import "/common/noContent.ftl" as  nc>
			<@nc.noContentFtl msg='您不在此工作坊内无权查看该页面' />
		</#if>
	</div>
</div>
<script>
	$(function(){
		study_prompt();
	});

	//关闭提示
	function study_prompt(){ 
	    $(".g-study-prompt .close").on("click",function(){
	        var $this_p = $(this).parent(".g-study-prompt");
	            $this_p.css({"display":"none"});
	    });
	    $(".g-study-prompt").delay(10000).fadeOut(500,0);
	}
</script>
</@layout>