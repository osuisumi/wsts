<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<form id="saveLessonPlanRecordForm" action="${ctx}/${role}_${wsid}/lessonPlan/saveRecord" method="post">
	<input type="hidden" name="id" value="">
	<input type="hidden" name="lessonPlanId" value="${lessonPlanId}">
	<input type="hidden" name="relationId" value="${wsid}">
    <div class="ag-layer-layout">
        <div class="ag-publish-page">
			<!--<h3 class="title">活动记录标题</h3>-->
			<div>
			<input required type="text" style="" value="${(lessonPlanRecord.title)! }" name="title" placeholder="输入活动记录标题" class="title u-pbIpt">
			</div>
			<div>
            <textarea required name="content" id=""  placeholder="请填写活动记录内容"></textarea>
            </div>
          	<div class="btn-block f-cb">
                <a onclick="saveRecord()" href="javascript:void(0);" class="au-confirm-btn">发布</a>
                <a href="javascript:void(0);" class="au-cancel-btn btn u-inverse-btn u-cancelLayer-btn mylayer-cancel">取消</a>
            </div>
        </div>
    </div><!--end .ag-layer-layout -->  
</form>
<script type="text/javascript">
	function saveRecord(){
		if(!$('#saveLessonPlanRecordForm').validate().form()){
			return;
		}
		
		var response = $.ajaxSubmit('saveLessonPlanRecordForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			alert('保存成功',function(){
				window.location.reload();
			});
		}
		
		
	
	}

</script>
