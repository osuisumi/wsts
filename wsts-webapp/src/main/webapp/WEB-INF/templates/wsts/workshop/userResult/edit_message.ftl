<form id="saveMessagesForm" action="${ctx}/message/saveMessages" method="post">
<div class="g-WSsend-news-box">
	<input type="hidden" name="userIds" value="${userIds!}">
	<input type="hidden" name="sender.id" value="${Session.loginer.id}">
	<div class="g-WS-news-cont">
		<h3>消息内容</h3>
		<div class="m-community-disc">
			<div class="m-rpl-comment">
				<div class="m-pbMod-ipt">
					<textarea name="content" id="" required class="u-textarea" placeholder="输入内容"></textarea>
				</div>
			</div>
		</div>
	</div>
	<div class="m-addElement-btn">
		<a onclick="saveMessage()" href="javascript:void(0);"  class="btn u-main-btn" id="">发布</a>
	</div>
</div>
</form>
<script>
	function saveMessage(){
		if(!$('#saveMessagesForm').validate().form()){
			return;
		}
		var response = $.ajaxSubmit('saveMessagesForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			alert('发送成功');
		}
	}
</script>