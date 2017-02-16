<form id="saveBriefingForm" action="${ctx!}/briefing" method="post">
	<#if (briefing.id)??>
		<input id="id" type="hidden" name="id" value="${(briefing.id)!}">
		<script>
			$('#saveBriefingForm').attr('method', 'put');
		</script>
	<#else>
		<input type="hidden" name="type" value="workshop_briefing">
		<input type="hidden" name="announcementRelations[0].relation.id" value="${relationId!}">
		<input type="hidden" name="announcementRelations[0].relation.type" value="workshop">
	</#if>
	<div class="g-addElement-lyBox g-WS-lyBox">
		<ul class="g-addElement-lst g-addCourse-lst">
		    <li class="m-addElement-item">
		        <div class="ltxt"><em>*</em>发布标题：</div>
		        <div class="center">
		            <div class="m-pbMod-ipt">
		                <input name="title" type="text" value="${(briefing.title)!}"  placeholder="输入名称" class="u-pbIpt">
		            </div>
		        </div>
		    </li>
		    <li class="m-addElement-item">
		        <div class="ltxt"><em>*</em>发布内容：</div>
		        <div class="center">
			        <script id="editor"  type="text/plain" style="min-height:260px;">
		            </script>                        
					<input id="briefingContent" name="content" type="hidden" value="${(briefing.content)!}">
		        </div>
		    </li>
		    <li class="m-addElement-btn">                
		        <a onclick="cancle()" class="btn u-inverse-btn ">取消</a>
		        <a onclick="saveBriefing()" class="btn u-main-btn" id="confirmLayer">发布</a>
		    </li>
		
		</ul>
	</div>
</form>
<script>

	var ue = initProduceEditor('editor', '${(briefing.content)!}', '${(Session.loginer.id)!}');
	
	function saveBriefing(){
		if(!$('#saveBriefingForm').validate().form()){
			return;
		}
		var content = ue.getContent();
		if(content.length == 0){
			alert("内容不能为空");
			return false;
		}
		$('#briefingContent').val(content);
		var response = $.ajaxSubmit('saveBriefingForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			alert('发布成功！');
			location.reload();
			cancle();
		}
	};
	
	function cancle(){
		$('.mylayer-wrap').remove();
	};
</script>