<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<@layout>
	<div class="g-auto">
	<#import "/wsts/include/banner.ftl" as banner/>
	<@banner.content current="通知公告正文">
		<ins>&gt;</ins>
		<span><a href="${ctx}/announcement?relationId=${(announcement.announcementRelations[0].relation.id)!}">通知公告</a></span>
	</@banner.content>
		<div class="m-WS-notice">
			<div class="g-detail-cont">
				<h1 class="title">${(announcement.title)!}</h1>
				<p class="info">
					<span>发布日期：${TimeUtils.formatDate(announcement.createTime,'yyyy-MM-dd HH:mm')}</span>
				</p>
				<div class="cont">
					${(announcement.content)!}
				</div>
			</div>
		</div>
	</div>
</@layout>