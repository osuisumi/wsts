<div id="selectUser" class="g-addElement-lyBox">
	<ul class="g-addElement-lst g-addChapter-WS">
		<li class="m-addElement-item">
			<input id="test" type="text">
		</li>
		<li class="m-addElement-btn">
			<a onclick="choose()" href="javascript:void(0);" class="btn u-main-btn" id="confirmAddChapter">确定</a>
		</li>
	</ul>
</div>
<script>
	$(function(){
		$('#test').searchableSelect({
			'url':'${ctx}/workshopUser/entities',
			'paramName':'paramMap[realName]',
			'param':'',
			'single':true,
		});
	});
	
	function choose(){
		var user = $('.searchable-select-holder .uname');
		if(user.size()<=0){
			alert('请选择用户');
			return;
		}
		setUser(user);
		mylayerFn.closelayer($('#selectUser'));
	}
</script>