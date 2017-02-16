<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#include "/wsts/include/layout.ftl"/>
<@layout>
<@coursewareDirective id=lcecId>
<@lcecEvaluateDirective lcecId=lcecId getEvaluate="Y" getEvaluateItemAvgScoreMap="Y">
<div class="g-auto">
	<div class="g-frame-sd">
		<#import "/wsts/workshop/side_frame.ftl" as sideFrame/>
		<@sideFrame.sideFrameFtl workshopId=wsid />
	</div>
	<div class="g-frame-mn">
		<#import "/wsts/include/banner.ftl" as banner/>
		<@banner.content current="查看评价结果">
			<ins>&gt;</ins>
			<span><a href="${ctx}/workshop/${role}_${wsid}/activity/${aid!}/view">${(courseware.title)!}</a></span>
		</@banner.content>
		<div class="WS-active-cont g-ListenComm-write">
			<div class="ag-activity-bd">
				<div class="m-crm">
					<a class="goback" href="${ctx}/workshop/${role}_${wsid}/activity/${aid!}/view">返回课程页></a>
				</div>
				<div class="g-pk-main">
					<table class="m-studyGrade-table" id="taskGradeTable" cellpadding="0" cellspacing="0" border="0" width="100%">
						<thead>
							<tr>
								<th colspan="3" class="am-title"><h2 class="txt">${(courseware.title)!}课程评价表</h2>
								<p>
									授课人：${(courseware.teacher.realName)!}<span style="margin-left:50px"></span>年级/学科：${TextBookUtils.getEntryName('STAGE',(courseware.stage)!)}/${TextBookUtils.getEntryName('GRADE',(courseware.grade)!)}${DictionaryUtils.getEntryName('SUBJECT',(courseware.subject)!)}
									<!--<span>|</span>授课时间：2015-3-26<span>|</span>评课时间：2015-4-26 至 2015-5-26-->
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
								<td class="tl" colspan="2"><strong>本课程的平均得分：</strong></td>
								<td><span class="score"><em id="avgScore"></em>分</span></td>
							</tr>
						</tfoot>
						<tbody>
							<#if (lcecEvaluate.evaluateItems)??>
								<#list lcecEvaluate.evaluateItems as item>
									<tr>
										<td>${item_index + 1}</td>
										<td class="tl">${(item.content)!}</td>
										<td>
										<div class="m-ev-star">
											<span class="u-df">${(avgScoreMap[item.id])!0}</span>
											<a onclick="viewEvaluateResultDetail('${lcecId}','${item.id}')" href="javascript:;" class="score-more">查看得分明细</a>
										</div></td>
									</tr>
								</#list>
							</#if>
						</tbody>
					</table>
					<div id="evaluateSubmissionsContent" class="m-zj-jy">
						<script>
							$('#evaluateSubmissionsContent').load('${ctx}/${role}_${wsid}/lcec/listLcecEvaluateSubmissions?lcecId=${lcecId}');
						</script>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$(function(){
		//计算平均分
		var sumScore = 0;
		$.each($('.m-ev-star .u-df'),function(i,n){
			sumScore = sumScore + parseInt($(n).text());
		});
		$('#avgScore').text(Math.floor((sumScore/$('.m-ev-star .u-df').size())*100)/100);
	});

	function viewEvaluateResultDetail(lcecId,itemId){
		mylayerFn.open({
			id : '999',
			type : 2,
			title : '创建文件夹',
			content : '${ctx}/lcec/'+lcecId+'/'+itemId+'/evaluateResultDetail',
			area : [500, 300],
			offset : ['auto', 'auto'],
			fix : false,
			shadeClose : false,
		});
	};
</script>
</@lcecEvaluateDirective>
</@coursewareDirective>
</@layout>