<#include "/wsts/include/layout.ftl"/>
<#assign path="/wsts"> 
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/common/image.ftl" as image/>
<#global wsid=id>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#if role='master' || role='member'>
	<#assign jsArray=['${path }/js/workshop_section.js','${ctx }/common/js/jquery-ui.js','webuploader','activity','ueditor','selectUser','datePicker']/>
<#else>
	<#assign jsArray=['${path }/js/workshop_section.js','${ctx }/common/js/jquery-ui.js','webuploader']/>
</#if>
<@layout jsArray=jsArray>
<@workshopDirective id=id getMemberNum="Y">
<div class="g-auto">
	<input type="hidden" id="ctx" value="">
	<input type="hidden" id="currentWokshopId" value="${workshop.id}">
	<input type="hidden" id="role" value="${role}">
	<#if workshop.type = 'train'>
		<#import  "workshop_time.ftl" as wt />
		<@wt.workshopTime workshop=workshop></@wt.workshopTime>
		<#if workshopStartTime?? && workshopEndTime??>
			<input type="hidden" id="workshopStartTime" value="${((workshopStartTime)!)?string('yyyy-MM-dd')}">
			<input type="hidden" id="workshopEndTime" value="${((workshopEndTime)!)?string('yyyy-MM-dd')}">
			<input type="hidden" id="betweenDays" value="${TimeUtils.getBetweenDays(workshopStartTime,workshopEndTime) + 1}">
			<input type="hidden" id="betweenDaysFromNow" value="${TimeUtils.getBetweenDaysFromNow(workshopStartTime)}">
			<input type="hidden" id="hasBegin" value="${(TimeUtils.hasBegun(workshopStartTime))?c}">
		</#if>
	</#if>
	<input type="hidden" id="qualifiedPoint" value="${(workshop.qualifiedPoint)!0}">
	<#if ((workshop.type)!'') = 'train'>
		<#if ((workshop.isTemplate)!'') = 'Y'>
			<input type="hidden" id="workshopType" value="example">
		<#else>
			<input type="hidden" id="workshopType" value="train">
		</#if>
	<#else>
		<input type="hidden" id="workshopType" value="personal">
	</#if>
	<div class="g-frame-sd">
		<#import "side_frame.ftl" as sideFrame/>
		<@sideFrame.sideFrameFtl workshopId=id workshop=workshop/>
	</div>
	<div class="g-frame-mn">
		<#if role="student">
			<div class="g-mWorShop-frame">
				<div class="g-mWorShop-info">
					<div class="m-lt">
						<div class="u-myInfo">
							<i class="u-ico-wsp"></i><span>我的
								<br />
								研修情况</span>
						</div>
					</div>
					<@workshopUserDirective workshopId=id userId=Session.loginer.id withActionInfo='Y'>
						<div class="info-lst">
							<a href="${ctx}/workshop/${id}/studyState?tab=pointRecord" class="block"><b>${(workshopUser.workshopUserResult.point)!0}</b><span>积分</span></a>
							<a href="${ctx}/workshop/${id}/studyState?tab=workshopSection" class="block"><b>${(workshopUser.actionInfo.activityCompleteNum)!0}</b><span>完成任务</span></a>
							<a href="${ctx}/workshop/${id}/studyState?tab=faqQuestion" class="block"><b>${(workshopUser.actionInfo.faqQuestionNum)!0}</b><span>提问</span></a>
							<!--<a href="javascript:;" class="block"><b>0</b><span>完成作业</span></a>-->
							<a href="${ctx}/workshop/${id}/studyState?tab=fileResource" class="block"><b>${(workshopUser.actionInfo.uploadResourceNum)!0}</b><span>上传资源</span></a>
						</div>
					</@workshopUserDirective>
				</div>
			</div>
		</#if>
		<div class="g-workshop-mn">
			<div class="g-workshop-nav">
				<ul id="tabList" class="m-workshop-nav">
					<li class="z-crt">
						<a href="javascript:;" class="section">研修任务</a>
					</li>
					<li>
						<a href="javascript:;" class="faq">互助问答</a>
					</li>
					<li>
						<a href="javascript:;" class="comment"  >自由交流</a>
					</li>
					<li>
						<a href="javascript:;" class="resource"  >学习资源</a>
					</li>
					<li>
						<a href="javascript:;" class="achievement">成果展示</a>
					</li>
					<#if ((workshop.isTemplate)!'')=='N' && ((workshop.type)!'') == 'train'>
						<#if role="master" || role="member">
							<li class="studentReview">
								<a href="javascript:;" class="workshopUserResult">学员考核</a>
							</li>
						</#if>
					</#if>
				</ul>
			</div>
			
			<div id="tabContent" class="" style="min-height:500px; background-color: white;">
			
			
			</div>
			
		</div>
	</div>
</div>

<script>

	$(function(){
		var tab = getQueryString('tab')||'section';
		$('#tabList li a').on('click',function(){
			var $this = $(this);
			if($this.hasClass('section')){
				$('#tabContent').load('${ctx}/${role}_${wsid}/workshopSection','workshopId='+$('#currentWokshopId').val());
			}else if($this.hasClass('faq')){
				$('#tabContent').load('${ctx}/${role}_${wsid}/faq_question','relationId='+$('#currentWokshopId').val());
			}else if($this.hasClass('resource')){
				//清空资源导航
				frbar.flush();
				$('#tabContent').load('${ctx}/${role}_${wsid}/fileResource','relationId='+$('#currentWokshopId').val());
			}else if($this.hasClass('comment')){
				$('#tabContent').load('${ctx}/${role}_${wsid}/comment?creatorOrTarget=${ThreadContext.getUser().getId()}','relation.id='+$('#currentWokshopId').val());
			}else if($this.hasClass('workshopUserResult')){
				$('#tabContent').load('${ctx}/${role}_${wsid}/workshopUserResult','workshopId='+$('#currentWokshopId').val()+'&orders=CREATE_TIME.DESC');
			}else if($this.hasClass('achievement')){
				$('#tabContent').load('${ctx}/workshop/achievement','workshopId='+$('#currentWokshopId').val());
			}
			
			$('#tabList li').removeClass('z-crt');
			$this.closest('li').addClass('z-crt');
		});
		$('#tabList li .'+tab).eq(0).click();
	});

	function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	}
	
	var frbar = new FileResourceNAV();
	
</script>
</@workshopDirective>

</@layout>