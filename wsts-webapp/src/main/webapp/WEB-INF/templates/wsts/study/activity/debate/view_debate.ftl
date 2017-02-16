<#macro viewDebateFtl debateId aid relationId>
<#include '/wsts/common/validate_js.ftl'/>
<@activityDirective id=aid>
<#global inCurrentDate=TimeUtils.inCurrentDate((activity.timePeriod)!'', (workshop.timePeriod)!'')>	
<@debateDirective id=debateId>
<@debateUserDirective debateRelationId=debate.debateRelations[0].id userId=Session.loginer.id>
<input type="hidden" id="relationId" value="${relationId}">
<div class="ag-activity-bd">
	<#if hasStudentRole>
		<@activityResultDirective activityId=aid relationId=debate.debateRelations[0].relation.id>
		<div class="g-study-prompt">
			<p>
				<#assign debate_view_num=(activity.attributeMap['debate_view_num'].attrValue)!'0'>
					要求发表 <span>${(debate_view_num)!0}</span>个观点
					您已发表 <span>${(activityResult.detailMap['debate_view_num'])!0 }</span>个观点
			</p>
        	<i class="close">X</i>
        </div>
        </@activityResultDirective>
	</#if>
	<div class="ag-cMain">
		<div class="ag-main-hd">
			<div class="am-title">
				<h2>
					<span class="txt">${debate.title}</span>
					<!--<span class="au-it-type hot">热</span><span class="au-it-type top">顶</span><span class="au-it-type ese">精</span>-->
				</h2>
			</div>
			<div class="am-title-info f-cb">
				<div class="c-infor">
					<span class="txt">发起人：${(debate.creator.realName)!}</span>
					<span class="line">|</span>
					<span class="txt">参与人数：${(debate.debateRelations[0].participateNum)!}</span>
					<span class="line">|</span>
					<span class="txt">被阅读数：${(debate.debateRelations[0].browseNum)!}</span>
				</div>
				<div class="am-mnTag-lst">
					<span class="au-tag-type type1"> <i class="au-bulb-ico"></i>活动 </span>
					<@tagsDirective relationId=aid>
						<#if tags??>
							<#list tags as tag>
								<span class="au-tt-type">${(tag.name)!}</span>
							</#list>
						</#if>
					</@tagsDirective>
				</div>
			</div>
			<div class="am-main-r">
				<#assign timePeriods=[]>
				<#assign timePeriods = timePeriods + [(activity.timePeriod)!]>
				<#assign timePeriods = timePeriods + [(workshop.timePeriod)!]>
				<#import "/wsts/common/show_time.ftl" as st /> 
				<@st.showTimeFtl timePeriods=timePeriods label="活动" /> 
			</div>
			<div class="ag-detail-txt ag-detail-txt1">
				<p class="cont-txt">
					<h3>${(debate.description)!}</h3>
					<p>${(debate.supplementExplanation)!}</p>
				</p>
			</div>
		</div><!--end .ag-main-hd -->
		<@debateArgumentStatsDirective debateRelationId=debate.debateRelations[0].id>
		<div class="g-argue-bd">
			<div class="ag-argue-box">
					<#if debateArgumentStats??>
						<#list debateArgumentStats as debateArgumentStat>
							<div class="am-viewpoint am-viewpoint${debateArgumentStat_index+1} first">
								<div class="ts-con">
									<h3>${(debateArgumentStat.argument.description)!}</h3>
									<p>
										${(debateArgumentStat.argument.supplementExplanation)!}
									</p>
									<span argumentId="${debateArgumentStat.argument.id}" class="au-vp-type type${debateArgumentStat_index+1}">${DebateArgumentLabelUtil.getArgumentLabel(debateArgumentStats?size,debateArgumentStat_index)}</span>
									<#if inCurrentDate>
										<div class="support" argumentId="${debateArgumentStat.argument.id}">
											<div class="t-tp">
												<#if ((debateUser.argument.id)!'') = debateArgumentStat.argument.id>
													<a href="javascript:void(0);" class="au-support"> <i class="au-bSupport-ico"></i><em>已支持</em> </a>
												<#else>
													<a href="javascript:void(0);" class="au-support doSupport"> <i class="au-bSupport-ico"></i><em>支持</em> </a>
												</#if>
											</div>
											<span class="s-txt"><em>${(debateArgumentStat.participateNum)!}</em></span>
										</div>
									</#if>
								</div>
								<div class="au-vp-border"></div>
								<div class="au-vp-shade"></div>
							</div><!--end .am-viewpoint 观点模块-->
						</#list>
					</#if>
				<div class="am-vp-border"></div>
				<div class="am-vs-icon">
					选择论点
				</div>
			</div>
			<div class="am-ratio">
				<span class="txt">当前统计结果：</span>
				<div class="ratio-p f-cb" style="width: 80%;">
					<#if debateArgumentStats??>
						<#list debateArgumentStats as debateArgumentStat>
						<#assign pn = (debate.debateRelations[0].participateNum)!0>
						<#if pn = 0>
							<#assign der=1/>
						<#else>
							<#assign der=pn/>
						</#if>
							<span class="ratio ratio${debateArgumentStat_index+1}" style="width: ${((debateArgumentStat.participateNum)!0)/(der)*80}%">${((debateArgumentStat.participateNum)!0)/(der)*100}%<span class="b"></span></span>
						</#list>
					</#if>
				</div>
			</div>
		</div>
		</@debateArgumentStatsDirective>
		<form id="saveDebateUserViewForm" action="${ctx}/${role}_${wsid}/debateUserViews" method="post">
			<input type="hidden" name="creator.id"  value="${(Session.loginer.id)!}">
				<input type="hidden" id="debateUserId" name="debateUser.id" value="${(debateUser.id)!}">
				<div class="am-comment-box am-ipt-mod">
					<label> 
						<span class="comment-placeholder"></span>
						<textarea id="" required name="viewsContent" class="au-textarea"></textarea> 
					</label>
					<#if inCurrentDate>
						<div class="am-cmtBtn-block f-cb">
							<!--<a href="javascript:void(0);" class="au-face"></a>-->
							<a href="javascript:void(0);" onclick="saveDebateUserView()" class="au-cmtPublish-btn au-confirm-btn1">发表</a>
						</div>
					</#if>
				</div>
		</form>
	</div>
	<div id="debateUserViewsDiv">
		<script>
			$(function(){
				listDebateUserViews();
			});
		</script>
	</div>
</div>
<script>
	var argumentChineseNoMap = {};
	$.each($('.au-vp-type'),function(i,n){
		var element = new Object();
		element.index = i;
		element.content = $(n).text();
		argumentChineseNoMap[$(n).attr('argumentId')] = element;
	});
	
	function listDebateUserViews(argumentId,orders){
		var _argumentId = argumentId||'';
		var _orders = orders||'CREATE_TIME.DESC';
		$('#debateUserViewsDiv').load('${ctx}/${role}_${wsid}/debateUserViews','debateRelation.id=${debate.debateRelations[0].id}&argument.id='+_argumentId+'&orders='+_orders);
	}

	$(function(){
		//支持按钮
		$('.doSupport').on('click',function(){
			var _this = this;
			var debateUserId = $('#debateUserId');
			if(debateUserId.val()){
				alert('您已支持过观点');
				return;
			}
			confirm('确定支持该观点？',function(){
				var supportDiv = $(_this).closest('.support');
				var argumentId = supportDiv.attr('argumentId');
				$.post('${ctx}/debateUser',{
					"debateRelation.id":"${debate.debateRelations[0].id}",
					"argument.id":argumentId,
					"creator":"${(Session.loginer.id)!}"
				},function(response){
					if(response.responseCode == '00'){
						alert('操作成功',function(){
							$(_this).replaceWith('<a href="javascript:void(0);" class="au-support"> <i class="au-bSupport-ico"></i><em>已支持</em> </a>');
						});
						if(response.responseData){
							debateUserId.val(response.responseData.id);
						}
					}
					
				});
			});
		});
		
	});
	
	function saveDebateUserView(){
		var debateUserId = $('#debateUserId');
		if(!debateUserId.val()){
			alert('请先支持一个观点');
			return;
		}
		if(!$('#saveDebateUserViewForm').validate().form()){
			return;
		}
		var response = $.ajaxSubmit('saveDebateUserViewForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			alert('发表成功');
			$('#saveDebateUserViewForm').find('textarea').val('');
			listDebateUserViews();
		}
		
	}
	
</script>
</@debateUserDirective>
</@debateDirective>
</@activityDirective>
</#macro>
