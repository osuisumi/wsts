<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#import "/common/image.ftl" as image/>
<body>
	<div class="ag-layer-layout">
		<div class="al-tp">
			<!-- <h3 class="tt">资源统计</h3> -->
			<span class="txt">已有<strong class="fileCount"></strong>个资源</span>
			<div class="am-search">
				<input name="realName" type="text" placeholder="搜索人名" class="au-ipt" value="${realName!}">
				<input onclick="searchUserFileCount(this)" type="submit" value="" class="submit-btn">
			</div>
		</div>
		<div class="al-bd">
			<ul class="am-user-lst">
				<#if userFileCounts??>
					<#list userFileCounts as userFileCount>
						<li>
							<a href="javascript:void(0);"> 
								<@image.imageFtl url="${(userFileCount['avatar'])! }" default="${app_path}/images/defaultAvatarImg.png" />
								<strong>${(userFileCount['realName'])!}</strong> <span>文件<em>(${(userFileCount['FILE_NUM'])!})</em></span> 
							</a>
						</li>
					</#list>
				</#if>
			</ul>
		</div>
	</div>

<script type="text/javascript">
	$(function(){
		$('.fileCount').text($('#fileCount').text());
	});

	function searchUserFileCount(btn){
		var realName = $(btn).closest('div').find('input[name=realName]').val();
		realName = $.trim(realName);
		//if(realName != ''){
			mylayerFn.refresh({
	            id: 'listUserCountDiv',
	            content: '${ctx}/${role}_${wsid}/activity/file_resource/listUserFileCount?relationId=${relationId}&realName='+realName
	        });
		//}
	}
</script>
</body>