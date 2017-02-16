<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>

<div class="g-addElement-lyBox" id="questionnaireWrap">
	<div class="g-addElement-tab">
		<div class="g-add-step">
			<ol class="m-add-step num3">
				<li class="step in">
					<span class="line"></span>
					<a href="javascript:void(0);"> <span class="ico"><i class="u-rhombus-ico">1</i></span>
					<br/>
					<span class="txt">辩论</span> </a>
				</li>
				<li id="editQuestion" class="step <#if activity.entityId??>yet</#if>">
					<span class="line"></span>
					<a  href="javascript:void(0);"> <span class="ico"><i class="u-rhombus-ico">2</i></span>
					<br/>
					<span class="txt">完成指标</span> </a>
				</li>
				<li class="step last <#if activity.entityId??>yet</#if>">
					<span class="line"></span>
					<a  href="javascript:void(0);"> <span class="ico"><i class="u-rhombus-ico">3</i></span>
					<br/>
					<span class="txt">活动设置</span> </a>
				</li>
			</ol>
		</div>
		<br/>
		<div id="tabListDiv" class="g-addElement-tabCont">
			<@debateDirective id=(activity.entityId)!''>
				<div class="g-addElement-tabList" style="display:block">
					<#if (activity.id)??>
					<form id="saveDebateForm" action="${ctx }/${role }_${wsid }/activity/${activity.id!}" method="put">
						<input type="hidden" name="debate.id" value="${debate.id! }">
						<input type="hidden" name="debate.debateRelations[0].id" value="${(debate.debateRelations[0].id)!}">
						<#else>
						<form id="saveDebateForm" action="${ctx }/${role }_${wsid }/activity" method="post">
							<input type="hidden" name="activity.relation.id" value="${activity.relation.id }">
							<input type="hidden" name="debate.debateRelations[0].relation.id" value="${wsid }">
							</#if>
							<input type="hidden" name="activity.type" value="${activity.type! }">
							<ul class="g-addElement-lst g-addCourse-lst ag-pb-lst">
								<li class="m-addElement-item">
									<div class="ltxt">
										<em>*</em>论题：
									</div>
									<div class="center">
										<div class="m-pbMod-ipt">
											<input type="text" required name="debate.title" value="${(debate.title)!}" placeholder="输入你的辩论主题" class="u-pbIpt">
										</div>
									</div>
								</li>
								<li class="m-addElement-item">
									<div class="ltxt">
										补充说明：
									</div>
									<div class="center">
										<div class="m-pbMod-ipt">
											<textarea name="debate.supplementExplanation" class="u-textarea" placeholder="补充说明">${(debate.supplementExplanation)!}</textarea>
										</div>
									</div>
								</li>
								<#if (debate.id)??>
									<#list debate.arguments as argument>
										<li class="m-addElement-item am-pb-mod am-pb-mod-vp">
											<div class="ltxt c-txt">
												<#if debate.arguments?size &gt; 2>
													<#if argument_index =0>
														<em>*</em><span>甲方论点：</span>
													<#elseif argument_index =1>
														<em>*</em><span>乙方论点：</span>
													<#elseif argument_index =2>
														<em>*</em><span>丙方论点：</span>
													<#else>
														<em>*</em><span>丁方论点：</span>
													</#if>
												<#else>
													<#if argument_index =0>
														<em>*</em><span>正方论点：</span>
													<#else>
														<em>*</em><span>反方论点：</span>
													</#if>
												</#if>
											</div>
											<div class="c-center">
												<div class="m-pbMod-ipt">
													<input type="hidden" name="debate.arguments[${argument_index}].id" value="${(argument.id)}">
													<input type="hidden" name="debate.arguments[${argument_index}].orderNo" value="${argument_index}">
													<input type="text" value="${(argument.description)!}" required name="debate.arguments[${argument_index}].description" class="u-pbIpt" placeholder="一句话描述论点">
												</div>
											</div>
											<div class="c-side">
												<a href="javascript:void(0);" class="au-nbtn au-add-ep" content="${(argument.supplementExplanation)!}">+补充说明</a>
												<a href="javascript:void(0);" class="au-nbtn au-delete-vp">×删除</a>
											</div>
										</li>
									</#list>
								<#else>
									<li class="m-addElement-item am-pb-mod am-pb-mod-vp">
										<div class="ltxt c-txt">
											<em>*</em><span>正方论点：</span>
										</div>
										<div class="c-center">
											<div class="m-pbMod-ipt">
												<input type="hidden" name="debate.arguments[0].orderNo" value="0">
												<input type="text" required name="debate.arguments[0].description" class="u-pbIpt" placeholder="一句话描述论点">
											</div>
										</div>
										<div class="c-side">
											<a href="javascript:void(0);" class="au-nbtn au-add-ep">+补充说明</a>
											<a href="javascript:void(0);" class="au-nbtn au-delete-vp">×删除</a>
										</div>
									</li>
									<li class="m-addElement-item am-pb-mod am-pb-mod-vp">
		
										<div class="ltxt c-txt">
											<em>*</em><span>反方论点：</span>
										</div>
										<div class="c-center">
											<div class="m-pbMod-ipt">
												<input type="hidden" name="debate.arguments[1].orderNo" value="1">
												<input type="text" required name="debate.arguments[1].description" class="u-pbIpt" placeholder="一句话描述论点">
											</div>
										</div>
										<div class="c-side">
											<a href="javascript:void(0);" class="au-nbtn au-add-ep">+补充说明</a>
											<a href="javascript:void(0);" class="au-nbtn au-delete-vp">×删除</a>
										</div>
		
									</li>
								</#if>
								<li class="m-addElement-item am-pb-mod s-height">
	
									<div class="c-center">
										<div class="m-btnBlock1">
											<a href="javascript:void(0);" class="au-nbtn au-add-vp">+新论点</a>
										</div>
									</div>
	
								</li>
								<li class="m-addElement-btn">
									<a onclick="saveDebate()" href="javascript:void(0);" data-href="index1.html" class="btn u-main-btn">保存并下一步</a>
									<a href="javascript:void(0);" class="btn u-inverse-btn u-cancelLayer-btn mylayer-cancel">取消</a> 
								</li>
							</ul>
						</form>
				</div>
			</@debateDirective>
			<div class="g-addElement-tabList">
				<#import "edit_debate_activity_attribute.ftl" as eva />
				<@eva.editDebateActivityAttributeFtl activity=activity />
			</div>
			<!--活动设置-->
			<div class="g-addElement-tabList">
				<#import "../edit_activity.ftl" as ea />
				<@ea.editActivityFtl activity=activity />
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	$(function(){
		activityArgue.fn.init();
	});

	function saveDebate() {
		if (!$('#saveDebateForm').validate().form()) {
			return false;
		}
		var data = $.ajaxSubmit('saveDebateForm');
		var json = $.parseJSON(data);
		if (json.responseCode == '00') {
			listWorkshopSection();
			var activity = json.responseData;
			if (activity != null) {
				refreshAndNextForm(activity.id, '${(courseType[0])!""}');
			} else {
				$('.m-add-step li').eq(1).trigger('click');
			}
		}
	}
</script>