<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#assign jsArray=['/wsts/js/workshop_section.js','webuploader']/>
<@layout jsArray=jsArray>
<@workshopDirective id=id getMemberNum="Y">
<input type="hidden" id="ctx" value="">
<input type="hidden" id="currentWokshopId" value="${workshop.id}">
<#if workshop.type = 'train'>
	<#import  "workshop_time.ftl" as wt />
	<@wt.workshopTime workshop=workshop></@wt.workshopTime>
	<#if workshopStartTime?? && workshopEndTime??>
		<input type="hidden" id="workshopStartTime" value="${(workshopStartTime)?string('yyyy-MM-dd')}">
		<input type="hidden" id="workshopEndTime" value="${(workshopEndTime)?string('yyyy-MM-dd')}">
		<input type="hidden" id="betweenDays" value="${TimeUtils.getBetweenDays(workshopStartTime,workshopEndTime) + 2}">
		<input type="hidden" id="betweenDaysFromNow" value="${TimeUtils.getBetweenDaysFromNow(workshopStartTime)}">
	</#if>
</#if>
<input type="hidden" id="qualifiedPoint" value="${(workshop.qualifiedPoint)!300}">
<div class="g-auto">
	<#import "/wsts/include/banner.ftl" as banner/>
	<@banner.content current="我的研修情况">
	</@banner.content>
	<div class="g-content">
		<div class="g-training-wrap">
			<div class="g-tit-tab">
			<@workshopUserDirective workshopId=id userId=Session.loginer.id withActionInfo='Y'>
				<input type="hidden" id="workshopResult" value="${(workshopUser.workshopUserResult.workshopResult)!''}">
				<input type="hidden" id="myPoint" value="${(workshopUser.workshopUserResult.point)!0}">
				<a tab="pointRecord"  onclick="loadTab('pointRecord',this)" href="javascript:;"><i class="u-ico-crad"></i>积分(${(workshopUser.workshopUserResult.point)!0})</a>
				<a tab="workshopSection" onclick="loadTab('workshopSection',this)" href="javascript:;"><i class="u-ico-text"></i>完成任务(${(workshopUser.actionInfo.activityCompleteNum)!})</a>
				<a tab="faqQuestion" onclick="loadTab('faqQuestion',this)" href="javascript:;"><i class="u-ico-question"></i>提问(${(workshopUser.actionInfo.faqQuestionNum)!})</a>
				<!--<a href="javascript:;"><i class="u-ico-hmwork"></i>完成作业(10)</a>-->
				<a tab="fileResource" onclick="loadTab('fileResource',this)" href="javascript:;"><i class="u-ico-upload"></i>上传资源(${(workshopUser.actionInfo.uploadResourceNum)!})</a>
			</@workshopUserDirective>
			</div>
			<div id="studyContent" class="g-con-tab">
				
			</div>
		</div>
	</div>
</div>
</@workshopDirective>
<script>
	$(function(){
		var tab = getQueryString('tab')||'pointRecord';
		loadTab(tab,$('.g-tit-tab a[tab='+tab+']'));
	});

	function loadTab(type,a){
		if(type == 'pointRecord'){
			$('#studyContent').load('${ctx}/pointRecord','workshopId=${id}');
		}else if(type == 'workshopSection'){
			$('#studyContent').load('${ctx}/workshopSection','workshopId=${id}');
		}else if(type == 'faqQuestion'){
			$('#studyContent').load('${ctx}/faq_question','relationId=${id}&type=my&isStatePage=Y');
		}else if(type == 'fileResource'){
			$('#studyContent').load('${ctx}/fileResource','relationId=${id}&isStatePage=Y');
		}
		$('.g-tit-tab a').removeClass('z-crt');
		$(a).addClass('z-crt');
	};
	
	function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	}
</script>
</@layout>