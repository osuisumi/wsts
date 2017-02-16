<#include "/wsts/include/layout.ftl"/>
<@layout>
<div class="g-auto">
	<#import "/wsts/include/banner.ftl" as banner/>
	<@banner.content current="工作坊列表"/>
	<div class="m-WS-notice m-WS-list">
		<div class="m-WS-notice-tl m-WS-list-tl">
			<h2>工作坊列表</h2>
			<span id="createWorkshop" class="add-notice"><a href="javascript:;"><i></i>创建工作坊</a></span>
		</div>
		<@workshopsDirective page=(pageBounds.page)!1 limit=10 withStat="Y" getMemberNum="Y" getActivityNum="Y" getResourceNum="Y"  getTrainName='Y'>
			<ul class="m-workshop-list">
				<#if workshops?? && workshops?size &gt; 0>
					<#list workshops as workshop>
						<li>
							<a href="javascript:;" class="head-pic">
								<#import "../../common/image.ftl" as image/>
								<@image.imageFtl url="${(workshop.imageUrl)! }" default="${app_path }/images/defaultWorkshop.png" />
							</a>
							<div class="m-who-workshop">
								<h3 class="who"><a href="javascript:;">${(workshop.title)!}</a>
									<#if shiro.hasRole('master_${workshop.id}')>
										<span class="host">坊主</span>
									<#elseif shiro.hasRole('member_${workshop.id}')>
										<span class="member">成员</span>
									<#elseif shiro.hasRole('student_${workshop.id}')>
										<span class="student">学员</span>
									<#else>
									</#if>	
								</h3>
								<p>
									坊主：<a href="javascript:;">
											<@workshopUsersDirective workshopId=workshop.id role='master'>
												<#if workshopUsers??>
													<#list workshopUsers as wu>
														${(wu.user.realName)!}&nbsp;
													</#list>
												</#if>
											</@workshopUsersDirective>
										</a>
								</p>
								<p>
									成员：<strong>${(workshop.workshopRelation.memberNum)!}</strong>人&nbsp;&nbsp;/&nbsp;&nbsp;研修活动：<strong>${(workshop.workshopRelation.activityNum)!}</strong>个&nbsp;&nbsp;/&nbsp;&nbsp;资源：<strong>${(workshop.workshopRelation.resourceNum)!}</strong>个
								</p>
								<p>
									所属培训：${(workshop.trainName)!}
								</p>
								<p class="txt">
									简介：${RemoveHtmlTagUtils.removeHtmlTag((workshop.summary)!'')}
								</p>
							</div>
							<div class="go-workshop">
								<a href="${ctx}/workshop/${workshop.id!}">进入工作坊</a>
							</div>
						</li>
					</#list>
				<#else>
					<#import "/common/noContent.ftl" as  nc>
                	<@nc.noContentFtl msg='暂时没有工作坊' />
				</#if>
			</ul>
			<form id="listWorkshopForm" action="${ctx}/workshop">
				<input type="hidden" value="${limit!}" name="limit">
				<div id="workshopPage" class="m-laypage"></div>
				<#import "/common/pagination.ftl" as p/>
				<@p.paginationFtl formId="listWorkshopForm" divId="workshopPage" paginator=paginator />
			</form>
		</@workshopsDirective>
	</div>
</div>

<script>
	$(function(){
		$('#createWorkshop').on('click',function(){
			window.location.href = "${ctx}/workshop/create";
		});
	});
</script>
</@layout>