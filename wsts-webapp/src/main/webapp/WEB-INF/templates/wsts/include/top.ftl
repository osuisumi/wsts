<#macro topFtl>
<#import "/common/image.ftl" as image/>
<div class="g-header">
	<div class="g-auto">
		<h1 class="m-logo">
			<a href="/">
				<#global app_path=PropertiesLoader.get('app.wsts.path') >
				<img alt="${PropertiesLoader.get('app.name') }" src="${app_path}/images/logo.png">
			</a>
			<span class="u-itips-ws">工作坊</span>
		</h1>
		<div class="g-hd-rt">
			<!-- <a href="javascript:;" class="m-skin"><i class="u-ico-skin"></i>换肤</a> -->
			<span class="g-WS-user"> 
				<a href="javascript:;" class="m-user"> 
					<@image.imageFtl url="${(Session.loginer.avatar)! }" default="${app_path}/images/defaultAvatarImg.png" class="img" /> <span class="name">${(Session.loginer.realName)!}<i></i></span>
				</a>
				<div class="lst">
					<i class="trg"><i></i></i>
                    <a onclick="goEditUser('${(ThreadContext.getUser().id)!}')" href="javascript:void(0);" class="u-user2"><i class="u-user1-ico"></i>&nbsp;用户资料</a>
					<a onclick="goChangePassword()" href="javascript:void(0);"><i class="u-user2-ico"></i>修改密码</a>
					<a href="${ctx}/logout"><i class="u-exit2-ico"></i>退出登录</a>
				</div>
			</span>
			<span class="g-WS-notice"> 
				<a id="messageBtn" href="javascript:void(0);" class="m-notice">
					<i class="u-bell-ico"></i>消息
					<!-- <i class="ico"></i> -->
				</a>
				<div class="m-mouse-news">
					<i class="arrow-icon"></i>
					<ul id="messageList" class="m-mouse-txt">
						
					</ul>
					<div class="m-h-more">
						<a onclick="loadMore()" href="javascript:void(0);" class="more"><i class="u-h-see"></i>查看更多</a>
					</div>
				</div> </span>
		</div>
	</div>
</div>
<input type="hidden" id="ctx" value="${ctx}">
<script>
function loadMore(){
	$('#content').load('${ctx}/message/list/more?orders=CREATE_TIME.DESC');
}

$(function(){
	$('#messageBtn').hover(function(){
		var li = $('#messageList li');
		if(li.size() <= 0){
			$('#messageList').load("${ctx}/message/list?orders=CREATE_TIME.DESC");
		}
	},function(){});
});
</script>
</#macro>
