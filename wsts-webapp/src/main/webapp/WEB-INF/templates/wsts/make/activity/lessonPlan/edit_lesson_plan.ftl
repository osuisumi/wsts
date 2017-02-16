<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>

<div class="g-addElement-lyBox" id="questionnaireWrap">
	<div class="g-addElement-tab">
		<div class="g-add-step">
			<ol class="m-add-step num3">
				<li class="step in">
					<span class="line"></span>
					<a href="javascript:void(0);">
					<span class="ico"><i class="u-rhombus-ico">1</i></span><br/> <span class="txt">集体备课</span> </a>
				</li>
				<li id="editQuestion" class="step <#if activity.entityId??>yet</#if>">
					<span class="line"></span>
					<a  href="javascript:void(0);"> 
					<span class="ico"><i class="u-rhombus-ico">2</i></span><br/> <span class="txt">完成指标</span> </a>
				</li>
				<li class="step last <#if activity.entityId??>yet</#if>">
					<span class="line"></span>
					<a  href="javascript:void(0);"> 
					<span class="ico"><i class="u-rhombus-ico">3</i></span><br/> <span class="txt">活动设置</span> </a>
				</li>
			</ol>
		</div>
		<br/>
		<div id="tabListDiv" class="g-addElement-tabCont">
			<@lessonPlanDirective id=(activity.entityId)!''>
			<div class="g-addElement-tabList" style="display:block">
				<#if (activity.id)??>
					<form id="saveLessonPlanForm" action="${ctx }/${role }_${wsid }/activity/${activity.id!}" method="put">
					<input type="hidden" name="lessonPlan.id" value="${lessonPlan.id! }"> 
				<#else>
					<form id="saveLessonPlanForm" action="${ctx }/${role }_${wsid }/activity" method="post">
					<input type="hidden" name="activity.relation.id" value="${activity.relation.id }"> 
					<input type="hidden" name="lessonPlan.lessonPlanRelations[0].relation.id" value="${wsid }"> 
				</#if>
				<input type="hidden" name="activity.type" value="${activity.type! }">
				<ul class="g-addElement-lst g-addCourse-lst">
					<li class="m-addElement-item">
						<div class="ltxt">
							<em>*</em>备课名称：
						</div>
						<div class="center">
							<div class="m-pbMod-ipt">
								<input type="text" name="lessonPlan.title" placeholder="输入备课名称" class="u-pbIpt required" value="${(lessonPlan.title)!}">
							</div>
						</div>
					</li>
					<li class="m-addElement-item">
						<div class="ltxt">
							<em>*</em>备课描述：
						</div>
						<div class="center">
							<div class="m-pbMod-ipt">
								<script id="editor" type="text/plain" style="height: 200px; width: 100%"></script>
								<input id="lessonPlanContent" name="lessonPlan.content" type="hidden">
							</div>
						</div>
					</li>
					<li class="m-addElement-btn">
						<a onclick="saveLessonPlan()" href="javascript:void(0);" data-href="index1.html" class="btn u-main-btn">保存并下一步</a>
						<a href="javascript:void(0);" class="btn u-inverse-btn u-cancelLayer-btn mylayer-cancel">取消</a> 
					</li>
				</ul>
				</form>	
			</div>
			</@lessonPlanDirective>
			<div class="g-addElement-tabList">
				<#import "edit_lessonPlan_activity_attribute.ftl" as eva /> 
				<@eva.editLessonPlanActivityAttributeFtl activity=activity />
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
	var ue = initProduceEditor('editor', '${(lessonPlan.content)!}', '${(Session.loginer.id)!}');
	
	function saveLessonPlan(){
		if(!$('#saveLessonPlanForm').validate().form()){
			return false;
		}
		var content = ue.getContent();
		if(content.length == 0){
			alert("描述不能为空");
			return false;
		}
		$('#lessonPlanContent').val(content);
		var data = $.ajaxSubmit('saveLessonPlanForm');
		var json = $.parseJSON(data);
		if(json.responseCode == '00'){
			listWorkshopSection();
			var activity = json.responseData;
			if(activity != null){
				refreshAndNextForm(activity.id, '${(courseType[0])!""}');
			}else{
				$('.m-add-step li').eq(1).trigger('click');
			}
		}
	}
</script>