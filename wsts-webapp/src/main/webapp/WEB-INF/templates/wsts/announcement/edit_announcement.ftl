<form id="saveAnnouncementForm" action="${ctx}/announcement" method="post">
	<#if announcement??>
		<input type="hidden" name="id" value="${announcement.id}">
		<script>
			$(function(){
				$('#saveAnnouncementForm').attr('action','${ctx}/announcement/update').attr('method','put');
			});
		</script>
	<#else>
		<input type="hidden" name="announcementRelations[0].relation.id" value="${relationId}">
		<input type="hidden" name="announcementRelations[0].relation.type" value="workshop">
		<input type="hidden" name="type" value="workshop_announcement" />
	</#if>
    <div class="g-addElement-lyBox g-WS-lyBox">
        <ul class="g-addElement-lst g-addCourse-lst">
            <li class="m-addElement-item">
                <div class="ltxt"><em>*</em>发布标题：</div>
                <div class="center">
                    <div class="m-pbMod-ipt">
                        <input name="title" required type="text" value="${(announcement.title)!}" placeholder="输入名称" class="u-pbIpt">
                    </div>
                </div>
            </li>
            <li class="m-addElement-item">
                <div class="ltxt"><em>*</em>发布内容：</div>
                <div class="center">
                   <script id="editor" type="text/plain" style="height: 200px; width: 100%"></script>
                   <input type="hidden" id="announcementContent" name="content">
                </div>
            </li>
            <li class="m-addElement-btn">                
                <a href="javascript:void(0);" onclick="mylayerFn.closelayer($('#saveAnnouncementForm'))" class="btn u-inverse-btn ">取消</a>
                <a onclick="saveAnnouncement()" href="javascript:void(0);"  class="btn u-main-btn" id="confirmLayer">发布</a>
            </li>

        </ul><!--end add element list -->
    </div>
</form>

<script>
	var ue = initProduceEditor('editor', '${(announcement.content)!}', '${(Session.loginer.id)!}');


	function saveAnnouncement(){
		if(!$('#saveAnnouncementForm').validate().form()){
			return;
		}
		
		var content = ue.getContent();
		if(content.length == 0){
			alert("内容不能为空");
			return false;
		}
		$('#announcementContent').val(content);
		var response = $.ajaxSubmit('saveAnnouncementForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			alert('操作成功',function(){
				<#if announcement??>
					window.location.href='${ctx}/announcement?relationId=${announcement.announcementRelations[0].relation.id}';
				<#else>
					window.location.href='${ctx}/announcement?relationId=${relationId}';
				</#if>
				
			});
		}
	}

</script>