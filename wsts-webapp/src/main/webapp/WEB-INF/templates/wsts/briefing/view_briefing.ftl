<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<@layout>
	<div class="g-auto">
		<#import "/wsts/include/banner.ftl" as banner/>
		<@banner.content current="研修简报正文">
			<ins>&gt;</ins>
			<span><a href="${ctx}/briefing?relationId=${(briefing.announcementRelations[0].relation.id)!}">研修简报列表</a></span>
		</@banner.content>
		<div class="m-WS-notice">
			<div class="g-detail-cont">
				<h1 class="title">${(briefing.title)!}</h1>
				<p class="info">
					<span>发布日期：${TimeUtils.formatDate(briefing.createTime,'yyyy-MM-dd HH:mm')}</span>
				</p>
				<div class="cont">
					${(briefing.content)!}
				</div>
			</div>
		</div>
	</div>
</@layout>