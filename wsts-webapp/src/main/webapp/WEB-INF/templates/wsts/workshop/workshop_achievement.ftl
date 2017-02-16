<#import "/common/image.ftl" as image/>
<#global wsid=workshopId>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<@workshopDirective id=workshopId getStudentNum="Y" getQuestionNum="Y" getActivityNum="Y" getResourceNum="Y">
<div class="g-WS-reslut-cont">
	<div class="g-WS-reslut-list">
		<h2><i class="u-count-icon"></i>本期学习统计</h2>
		<ul class="m-WS-reslut-count">
			<li>
				<i class="u-time-icon"></i>
				已开展天数(天)<span id="begunedDay" class="u-num"></span>
			</li>
			<#if ((workshop.type)!'') == 'train'>
				<li>
					<i class="u-who-icon"></i>
					参研学员<span class="u-num">${(workshop.workshopRelation.studentNum)!0}</span>
				</li>
			</#if>
			<li>
				<i class="u-task-icon"></i>
				研修任务<span class="u-num">${(workshop.workshopRelation.activityNum)!0}</span>
			</li>
			<#if ((workshop.type)!'') == 'train'>
				<li>
					<i class="u-ask-icon"></i>
					学员提问量<span class="u-num">${(workshop.workshopRelation.questionNum)!0}</span>
				</li>
			</#if>
			<li>
				<i class="u-disc-icon"></i>
				学习资源<span class="u-num">${(workshop.workshopRelation.resourceNum)!0}</span>
			</li>
			<!--
			<li>
				<i class="u-disc-icon"></i>
				评论发贴<span class="u-num">236</span>
			</li>-->
		</ul>
	</div>
	<@briefingDirective relationId=workshopId!'' type='workshop_briefing' page=page!1  limit=3 orders="IS_TOP.DESC,CREATE_TIME.DESC">
	<div class="g-WS-reslut-list">
		<h2><i class="u-report-icon"></i>研修简报</h2>
		<#if briefings?? && briefings?size &gt; 0>
		<ul class="m-WS-report-count">
				<#list briefings as briefing>
					<li>
						<a href="${ctx}/briefing/${briefing.id}"> <span class="txt">${(briefing.title)!} </span> <span class="time">${TimeUtils.formatDate(briefing.createTime,'yyyy/MM/dd')}</span> </a>
					</li>
				</#list>
		</ul>
		<a href="${ctx}/briefing?relationId=${workshopId}" class="m-more">更多+</a>
		<#else>
			<#import "/common/noContent.ftl" as  nc>
			<@nc.noContentFtl msg='暂时没有研修简报' minHeight="190" />
		</#if>
	</div>
	</@briefingDirective>
	<#if ((workshop.type)!'') == 'train'>
		<div class="g-WS-reslut-list g-WSdet-list">
			<div class="g-WS-reslut-tl">
				<h2><i class="u-student-icon"></i>优秀学员</h2>
				<#if role="master" || role="member">
					<a href="${ctx}/workshop/${workshopId}?tab=workshopUserResult" class="button"><i class="u-manage"></i>管理</a>
				</#if>
			</div>
			<@workshopUsersDirective role='student' workshopId=workshopId workshopResult="excellent" minPoint="${(workshop.qualifiedPoint)!}">
			<#if workshopUsers?? && workshopUsers?size &gt; 0>
			<ul <#if workshopUsers?size &lt; 3>style="height:100px"</#if> class="m-WS-student-list">
				<#list workshopUsers as workshopUser>
					<li>
						<a href="javascript:;">
							<@image.imageFtl url="${(workshopUser.user.avatar)! }" default="${app_path}/images/defaultAvatarImg.png" />
						</a>
						<div class="m-WS-st-txt">
							<div class="name">
								<a href="javascript:;">${(workshopUser.user.realName)!}</a><i></i>
							</div>
							<p class="school">
								${(workshopUser.userInfo.deptName)!}
							</p>
						</div>
					</li>
				</#list>
			</ul>
			<a href="javascript:;" class="m-more"><span class="down-more">展开更多+</span><span class="up-more">收起&nbsp;-</span></a>
			<#else>
				<#import "/common/noContent.ftl" as  nc>
				<@nc.noContentFtl msg='暂时没有优秀学员' minHeight="190" />
			</#if>
			</@workshopUsersDirective>
		</div>
	</#if>
	<div class="g-WS-reslut-list">
		<h2 class="m-fill-tl"><i class="u-rescurce-icon"></i>精品资源</h2>
		<ul class="am-file-lst f-cb am-Mana-file-lst">
			<@fileResourcesDirective relationId=workshopId type="excellent" isFolder='N'>
				<#if fileResources?? && fileResources?size &gt; 0>
					<#list fileResources as fr>
						<li>
							<div class="am-file-block am-file-word">
								<div class="file-view">
									<#if (FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'study')! != 'img')>
										<div class="${FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'study')!''}">
											<a href="javascript:;"></a>
										</div>
								    <#else>
									    <img src=${FileUtils.getFileUrl((fr.newestFile.url)!'')} width="100" height="75" class="u-ico-file">
								    </#if>
								</div>
								<b class="f-name"><span>${(fr.name)!}</span></b>
								<div class="f-info">
									<span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd')}</span>
								</div>
								<div class="opt">
									<a onclick="downloadFile('${(fr.newestFile.id)!}','${(fr.newestFile.fileName)!}')" href="javascript:void(0);" class="u-opt u-edit-ico"><i class="u-WS-load-ico"></i><span class="tip">下载</span></a>
								</div>
							</div>
						</li>
					</#list>
				<#else>
					<#import "/common/noContent.ftl" as  nc>
					<@nc.noContentFtl msg='暂时没有精品资源' minHeight="190" />
				</#if>
			</@fileResourcesDirective>
		</ul>
	</div>
</div>

<script>
	$(function(){
		//设置已开展天数
		var hasBegin = $('#hasBegin').val();
		if(hasBegin == 'true'){
			$('#begunedDay').text(parseInt($('#betweenDaysFromNow').val()) + 1);
		}else{
			$('#begunedDay').text(0);
		}
		
		//展示更多
		more_detail(".g-WS-reslut-list.g-WSdet-list .m-more",'.m-WS-student-list');
		
	});
	
    function more_detail(labelclick,udown){ //展示更多
        var updown = true;
        $(labelclick).on("click",function(){
            if(updown==true){
                $(this).siblings(udown).addClass('Heightauto');
                $(this).find(".up-more").addClass('canfind').siblings('.down-more').addClass('notfind');
                updown=false;
            }else if(updown==false){
                $(this).siblings(udown).removeClass('Heightauto');
                $(this).find(".up-more").removeClass('canfind').siblings('.down-more').removeClass('notfind');
                updown=true;
                
            }
        });
    }
</script>
</@workshopDirective>