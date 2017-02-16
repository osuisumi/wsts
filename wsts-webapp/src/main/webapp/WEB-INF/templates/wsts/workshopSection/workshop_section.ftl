<#global wsid=workshopId!''>
<#import "/wsts/common/role.ftl" as r />
<@r.content />
<div class="g-workshop-con">
	<input type="hidden" id="currentWorkshopId" value="${workshopId!''}">
	<#--<div class="g-opa-guid prepareAct">
		<div class="g-opa-guid-in">
			<dl class="m-guid-lst">
				<dt>
					熟悉平台这些常用的线上研修活动操作
				</dt>
				<@activitiesDirective relationId='SYSTEM' state="static">
					<#if activities??>
						<#list activities as activity>
							<dd>
								<#if activity.type == 'discussion'>
									<i class="u-ico u-ico-guid-discuss"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【教学研讨】</b>
								<#elseif activity.type == 'survey'>
									<i class="u-ico u-ico-guid-QueNaire"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【问卷调查】</b>
								<#elseif activity.type == 'lesson_plan'>
									<i class="u-ico u-ico-guid-preLesson"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【集体备课】</b>
								<#elseif activity.type == 'test'>
									<i class="u-ico u-ico-guid-comment"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【在线测试】</b>
								<#elseif activity.type == 'video'>
									<i class="u-ico u-ico-guid-study"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【教学观摩】</b>
								<#elseif activity.type == 'debate'>
									<i class="u-ico u-ico-guid-argue"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【辩论活动】</b>
								<#elseif activity.type == 'lcec'>
									<i class="u-ico u-ico-guid-test"></i>
									<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【听课评课】</b>
								</#if>
                                	${activity.title }</a>
							</dd>
						</#list>
					</#if>
				</@activitiesDirective>
			</dl>
		</div>
	</div>
	-->
	<div class="g-train-ready g-manage-train-ready">
		<div id="timeProgress" class="m-progress">
			<span class="u-bar">
				 <span id="progress" class="val" style="width:20%;"> 
				 	<i></i> 
				 	<span class="tips">已开展到第<em id="now"></em>天</span> 
				 </span> 
			</span>
			<span class="u-txt">共&nbsp;<span id="total"></span>&nbsp;天</span>
		</div>
		<div id="listWorkshopSectionDiv">
		
		</div>
	</div>
</div>
<script>
    $(function(){
        listWorkshopSection();
        setTimeProgress();
    });
    //设置时间进度
    function setTimeProgress(){
    	var workshopType = $('#workshopType').val();
    	if(workshopType != 'train'){
    		$('.prepareAct').hide();
    	}
    	if($('#workshopStartTime').size()>0 && $('#workshopEndTime').size()>0){
    		var hasBegin = $('#hasBegin').val();
	    	if(hasBegin == 'true'){
		    	var betweenDays = (parseInt($('#betweenDays').val()))||0;
		    	var betweenDaysFromNow = parseInt($('#betweenDaysFromNow').val()) + 1;
		    	$('#now').text(betweenDaysFromNow);
		    	$('#total').text(betweenDays);
		    	var percent = 0;
		    	if(betweenDaysFromNow>=betweenDays){
		    		percent = 1;
		    	}else{
		    		percent = betweenDaysFromNow/betweenDays;
		    	}
		    	$('#progress').attr('style','width:'+percent*100+'%');
	    	}else{
	    		var betweenDays = (parseInt($('#betweenDays').val()))||0;
	    		$('#total').text(betweenDays);
	    		$('.tips').text('尚未开始');
	    		$('#progress').attr('style','width:'+0+'%');
	    	}
    	}else{
    		$('#timeProgress').remove();
    	}

    };
    
    function listWorkshopSection(){
    	$('#listWorkshopSectionDiv').load('${ctx}/workshopSection/list','workshopId=${workshopId!""}&t' + new Date().getTime());
    }
</script>