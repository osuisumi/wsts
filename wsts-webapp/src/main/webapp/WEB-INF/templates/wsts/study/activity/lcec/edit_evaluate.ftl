<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#include "/wsts/include/layout.ftl"/>
<#assign jsArray=['/wsts/js/evaluateStar.js','validate']/>
<@layout jsArray=jsArray>
<@coursewareDirective id=lcecId >
<@lcecEvaluateDirective lcecId=lcecId getEvaluate="Y" getEvaluateSubmission="Y">
<div class="g-auto">
	<div class="g-frame-sd">
		<#import "/wsts/workshop/side_frame.ftl" as sideFrame/>
		<@sideFrame.sideFrameFtl workshopId=wsid />
	</div>
	<div class="g-frame-mn">
		<#import "/wsts/include/banner.ftl" as banner/>
		<@banner.content current="填写评价表">
		</@banner.content>
		<div class="WS-active-cont g-ListenComm-write">
			<div class="ag-activity-bd">
				<div class="m-crm">
					<a class="goback" href="${role}_${wsid}/activity/${(aid)!}/view">返回课程页></a>
				</div>
				<div class="g-pk-main">
					<table class="m-studyGrade-table" id="taskGradeTable" cellpadding="0" cellspacing="0" border="0" width="100%">
						<thead>
							<tr>
								<th colspan="3" class="am-title"><h2 class="txt">${(courseware.title)!}课程评价表</h2>
								<p>
									授课人：${(courseware.teacher.realName)!}<span style="margin-left:50px"></span>学段/学科：${TextBookUtils.getEntryName('STAGE',(courseware.stage)!)}/${TextBookUtils.getEntryName('SUBJECT',(courseware.subject)!)}
									<!--<span>|</span>授课时间：2015-3-26
									<span>|</span>评课时间：2015-4-26 至 2015-5-26-->
								</p></th>
							</tr>
							<tr>
								<th width="12%">序号</th>
								<th width="54%">评价内容</th>
								<th width="34%">得分</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td class="tl" colspan="2"><strong>您为本课的评分：</strong></td>
								<td><span class="score"><em id="evlScore"></em>分</span></td>
							</tr>
						</tfoot>
						<tbody>
							<#if (lcecEvaluate.evaluateItems)??>
								<#list lcecEvaluate.evaluateItems as item>
									<tr class="evaluateItem" itemId = "${(item.id)!}">
										<td>${item_index+1}</td>
										<td class="tl">${(item.content)!}</td>
										<td>
										<div class="m-ev-star">
											<span class="star <#if ((evaluateSubmission.evaluateItemSubmissionMap[item.id].score)!0) &gt; 19>  z-crt</#if>"><em>20</em></span>
											<span class="star <#if ((evaluateSubmission.evaluateItemSubmissionMap[item.id].score)!0) &gt; 39>  z-crt</#if>"><em>40</em></span>
											<span class="star <#if ((evaluateSubmission.evaluateItemSubmissionMap[item.id].score)!0) &gt; 59>  z-crt</#if>"><em>60</em></span>
											<span class="star <#if ((evaluateSubmission.evaluateItemSubmissionMap[item.id].score)!0) &gt; 79>  z-crt</#if>"><em>80</em></span>
											<span class="star <#if ((evaluateSubmission.evaluateItemSubmissionMap[item.id].score)!0) &gt; 99>  z-crt</#if>"><em>100</em></span>
										</div></td>
									</tr>
								</#list>
							</#if>
						</tbody>
					</table>
						<form id="saveEvaluateSubmissionForm" action="${ctx}/${role}_${wsid}/evaluate/submission/${evaluateSubmission.id}" method="put">
							<input type="hidden" name="evaluateRelation.id" value="${(evaluateSubmission.evaluateRelation.id)!}">
							<input type="hidden" name="evaluateRelation.relation.id" value="${lcecId}">
							<span id="evaluateItemParam"></span>
							<div class="m-pj-jy">
								<h3>评价总结及建议</h3>
								<div class="txt-jy">
									<textarea required class="txt-in" name="comment" placeholder="请从本节课的不足之处、成功之处与需改进意见出发简要点评本课。">${(evaluateSubmission.comment)!}</textarea>
								</div>
							</div>
						</form>
					<ul class="g-addElement-lst g-addCourse-lst">
						<li class="m-addElement-btn">
							<a onclick="saveEvaluateSubmission()" href="javascript:void(0);" data-href="index1.html" class="btn u-main-btn" id="confirmLayer">确定</a>
							<a href="javascript:void(0);" onclick="window.history.back()" class="btn u-inverse-btn u-cancelLayer-btn">取消</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</@lcecEvaluateDirective>
</@coursewareDirective>
<script>
	$(function(){
		$('#taskGradeTable .m-ev-star').evaluateStar();
		
		//计算平均分
		var starNum = $('.star.z-crt').size();
		var itemNum = $('.evaluateItem').size();
		$('#evlScore').text(Math.floor((starNum*20/itemNum)*100)/100);
		
	});
	
	function saveEvaluateSubmission(){
		if(!$('#saveEvaluateSubmissionForm').validate().form()){
			return;
		}
		//为各项评分生成参数
		var paramDiv = $('#evaluateItemParam');
		paramDiv.empty();
		$.each($('.evaluateItem'),function(i,n){
			//获取该项打分
			var itemId = $(n).attr('itemId');
			var wi = 20;
			var score = $(n).find('.star.z-crt').size() * 20;
			paramDiv.append('<input type="hidden" name="evaluateItemSubmissionMap['+itemId+'].score" value="'+score+'">');
			paramDiv.append('<input type="hidden" name="evaluateItemSubmissionMap['+itemId+'].creator.id" value="${(Session.loginer.id)!}">');
		});
		var response = $.ajaxSubmit('saveEvaluateSubmissionForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			alert('操作成功');
			window.location.reload();
		}
	};
	
</script>
</@layout>