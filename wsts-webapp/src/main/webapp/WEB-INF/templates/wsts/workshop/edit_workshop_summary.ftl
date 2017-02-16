<body>
	<@workshopDirective id=id>
		<form id="saveWorkshopSummaryForm" action="${ctx}/workshop/${id}/update" method="put">
		    <div class="g-addElement-lyBox">
		        <ul class="g-addElement-lst g-addChapter-WS">
		            <li class="m-addElement-item">
		                <div class="center">
		                    <div class="m-pbMod-ipt">
		                    	<#if fieldName="summary">
		                    		<#assign content=(workshop.summary)!/>
		                    	<#elseif fieldName="summaryNotice">
		                    		<#assign content=(workshop.summaryNotice)!/>
		                    	<#elseif fieldName="summaryTarget">
		                    		<#assign content=(workshop.summaryTarget)!/>
		                    	<#elseif fieldName="summaryExamine">
		                    		<#assign content=(workshop.summaryExamine)!/>
		                    	<#else>
		                    	</#if>
		                    	<script id="editor" type="text/plain" style="height: 200px; width: 100%"></script>
								<input id="summary" name="${fieldName!}" type="hidden" value='${content!}'>
		                    </div>
		                    <p class="m-addElement-ex" id="CourseTypeExplain">工作坊的介绍，如：描述工作坊的来历，尽量用描述性名称，简洁明了</p>
		                </div>
		            </li>
		            <li class="m-addElement-btn">
		                <a onclick="saveSummary()" href="javascript:void(0);" class="btn u-main-btn" id="confirmAddChapter">创建</a>
		                <a href="javascript:void(0);" class="btn u-inverse-btn u-cancelLayer-btn mylayer-cancel">取消</a>
		            </li>
		        </ul>
		    </div>
		 </form>
	 </@workshopDirective>
<script type="text/javascript">

var ue = initProduceEditor('editor', '${content!}', '${(Session.loginer.id)!}');

function saveSummary(){
	var content = ue.getContent();
	if(content.length == 0){
		alert("描述不能为空");
		return false;
	}
	$('#summary').val(content);
	var response  = $.ajaxSubmit('saveWorkshopSummaryForm');
	response = $.parseJSON(response);
	if(response.responseCode == '00'){
		alert('操作成功',function(){
			window.location.reload();
		});
	}
}

</script>
</body>