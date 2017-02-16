<#include "/wsts/include/layout.ftl"/>
<@layout>
<@workshopDirective id=workshopId >
<div class="g-auto">
	<div class="g-frame-sd">
		<#import "side_frame.ftl" as sideFrame/>
		<@sideFrame.sideFrameFtl workshopId=workshopId workshop=workshop />
	</div>
	<div class="g-frame-mn">
		<!-- begin manager entrance -->
		<div class="g-manager-entrance">
			<div class="m-mn-tit">
				<h3 class="u-tt">管理员入口</h3>
			</div>
			<div class="g-entrance-con">
				<ul class="m-entrance-lst">
					<li>
						<a href="${ctx}/workshop/${workshopId}?tab=section" class="u-yxrw">研修任务</a>
					</li>
					<li>
						<a href="${ctx}/workshop/${workshopId}?tab=faq" class="u-hzwd">互助问答</a>
					</li>
					<li>
						<a href="${ctx}/workshop/${workshopId}?tab=resource" class="u-yxzy">研修资源</a>
					</li>
					<li>
						<a href="${ctx}/announcement?relationId=${workshopId}" class="u-tzgg">通知公告</a>
					</li>
					<li>
						<a href="${ctx}/briefing?relationId=${workshopId}" class="u-yxjb">研修简报</a>
					</li>
					<#if ((workshop.isTemplate)!'')=='N' && ((workshop.type)!'') == 'train'>
						<li class="studentReview">
							<a href="${ctx}/workshop/${workshopId}?tab=workshopUserResult" class="u-xykh">学员考核</a>
						</li>
					</#if>
					<li>
						<a href="${ctx}/workshop/${workshopId}/detail" class="u-gzfjj">工作坊简介</a>
					</li>
					<li>
						<a href="${ctx}/workshopUser?workshopId=${workshopId}" class="u-glcy">管理成员</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
</@workshopDirective>

</@layout>