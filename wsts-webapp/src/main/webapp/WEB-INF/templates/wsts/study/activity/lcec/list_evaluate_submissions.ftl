<#import "/common/image.ftl" as image/>
<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<@lcecEvaluateDirective lcecId=lcecId getEvaluateSubmissions="Y" limit=10 page=(pageBounds.page)!1 orders="CREATE_TIME.DESC">
<#if evaluateSubmissions?? && evaluateSubmissions?size &gt; 0>
<div class="g-ListenComm-result">
	<div class="ag-comment-main">
		<h3 class="spe-col">评价总结与建议<span>（共有<em>${(paginator.totalCount)!0}</em>条信息<!--，已采纳2条-->）</span></h3>
		<ul class="ag-cmt-lst ag-cmt-lst-p">
			<#if evaluateSubmissions??>
				<#list evaluateSubmissions as es>
					<li class="am-cmt-block">
						<!--
						<div class="img-acc">
							<img src="images/accept.png" alt="" />
						</div>
						-->
						<div class="c-info">
							<a href="#" class="au-cmt-headimg">
								<@image.imageFtl url="${(es.creator.avatar)! }" default="${app_path}/images/defaultAvatarImg.png" />
							</a>
							<p class="tp">
								<a href="#" class="name">${(es.creator.realName)!}</a>
								<span class="time">${(TimeUtils.formatDate(es.createTime,'yyyy-MM-dd'))}</span>
							</p>
							<p class="cmt-dt">
								${(es.comment)!}
							</p>
							<!--
							<div class="ag-opa">
								<a href="javascript:void(0);" class="au-praise"> <i class="au-praise-ico"></i>赞同<b>（189）</b> </a>
								<i class="au-opa-dot"></i>
								<a href="javascript:void(0);" class="au-comment"> <i class="au-accept-uc"></i>采纳 </a>
							</div>
							-->
						</div>
					</li>
				</#list>
			</#if>
		</ul>
	</div>
	<form id="listLcecEvaluateSubmissionForm"  method="get" action="${ctx}/${role}_${wsid}/lcec/listLcecEvaluateSubmissions" >
		<input type="hidden" name="lcecId" value="${lcecId!}">	
		<div id="submissionsPage" class="m-laypage"></div>
		<#if paginator??>
			<#import "/common/pagination_ajax.ftl" as p/>
			<@p.paginationAjaxFtl formId="listLcecEvaluateSubmissionForm" divId="submissionsPage" paginator=paginator contentId="evaluateSubmissionsContent"/>
		</#if>
	</form>
</div>
<#else>
<#import "/common/noContent.ftl" as  nc>
<@nc.noContentFtl msg='暂无总结建议' />
</#if>
</@lcecEvaluateDirective>