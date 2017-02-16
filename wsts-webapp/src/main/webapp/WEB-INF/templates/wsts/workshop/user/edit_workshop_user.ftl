<div class="g-addElement-lyBox g-WS-lyBox">
	<form id="saveMemberForm" action="${ctx}/workshopUser/batchSave" method="post">
	<input type="hidden" name="id" value="${workshopId}">
	<span id="param" stype="display:none">
		
	</span>
	<ul class="g-addElement-lst g-addCourse-lst">
		<li class="m-addElement-item g-WS-addmember">
			<div class="ltxt">
				添加成员：
			</div>
			<div class="center ">
				<div class="m-add-tag m-WSadd-tag">
					<div class="m-pbMod-ipt m-tagipt">
						<input id="userSelect" type="text" value="" placeholder="请输入关键字" class="u-pbIpt u-ipt">
					</div>
					<ul id="result" class="m-tag-lst">
					</ul>
				</div>

			</div>
		</li>
		<li class="m-addElement-btn">
			<a href="javascript:void(0);" onclick="mylayerFn.closelayer($('#saveMemberForm'))" class="btn u-inverse-btn ">取消</a>
			<a onclick="saveMember()" href="javascript:void(0);"  class="btn u-main-btn" id="confirmLayer">添加</a>
		</li>
	</ul>
	</form>
</div>

<script>
	$(function(){
		$('#userSelect').userSelect({
			url : '${ctx}/userInfo/entities',
			userList : $('#result'),
			paramName : 'paramMap[realName]',
			afterInit:function(userSelectDiv){
				userSelectDiv.find('.u-add-tag').css('background-color','#1e8be8').css('height','24px');
			},
			onBeforeSelect:function(value){
				var isAllow = true;
				$.ajax({
					type:'GET',
					url:'${ctx}/workshopUser/isAllow?userId='+value+'&workshopId=${workshopId}',
					async:false,
					success:function(response){
						if(response.responseCode == '00'){
							isAllow = true;
						}else{
							alert(response.responseMsg);
							isAllow = false;
						}
					},					
				});
				return isAllow;
			},
		});
	});
	
	function saveMember(){
		var paramDiv = $('#param');
		paramDiv.empty();
		$.each($('#result li'),function(i,n){
			paramDiv.append('<input type="hidden" name="workshopUsers['+i+'].user.id" value="'+$(n).find("a").attr('uid')+'" />');
			paramDiv.append('<input type="hidden" name="workshopUsers['+i+'].role" value="member" />');
			paramDiv.append('<input type="hidden" name="workshopUsers['+i+'].state" value="passed" />');
		});
		var response = $.ajaxSubmit('saveMemberForm');
		response = $.parseJSON(response);
		if(response.responseCode == '00'){
			window.location.href = '${ctx}/workshopUser?workshopId=${workshopId}';
		}
	}
</script>