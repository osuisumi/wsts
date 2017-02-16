<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<@fileResourcesDirective relationId=relationId  parentId=parentId!'' page=1 limit=8 orders='IS_FOLDER.DESC,CREATE_TIME.DESC'>
<div id="listFileResourcePage">
	<div class="am-section-tit">
		<span class="au-stit au-stit1">共建资源</span>
		<div class="ag-opa">
			<a href="javascript:void(0);" class="au-chart"> <i class="au-chart-ico"></i>资源统计 </a>
			<span class="line">|</span>
			<a onclick="viewMoreFileResource()" href="javascript:void(0);" class="all-resource-btn">查看全部&gt;</a>
		</div>
	</div>
	
	<div class="am-line-block f-cb">
		<div class="l">
			<#if role !='guest'>
				<a id="picker" href="javascript:void(0);" class="au-uploadFile au-default-btn"> <i class="au-uploadFile-ico"></i>上传文件 </a>
				<#if role='master' || role='member'>
					<a href="javascript:void(0);" class="au-add-folder au-default-btn"> <i class="au-addFolder-ico"></i>创建文件夹 </a>
				</#if>
			</#if>
		</div>
		<div class="r">
			<span>当前共有&nbsp;<b id="fileCount">${(fileCount)!0}</b>&nbsp;个资源</span>
		</div>
	</div>
	
	<ul class="am-file-lst f-cb">
		<input type="hidden" id="parentId" value="${parentId!''}">
		<#if fileResources??>
			<#list fileResources as fr>
				<#if fr.isFolder == 'Y'>
					<li frId="${fr.id}" frName="${(fr.name)!}">
						<div class="am-file-block am-file-folder">
							<div class="file-view folder">
								<div onclick="viewMoreFileResource('${fr.id}','${fr.name }')" class="au-folder au-folder1">
									<#if fr.name='共案'>
										<i class="au-ico"></i>
									<#elseif fr.name='初备'>
										<i class="au-ico"></i>
									</#if>
								</div>
							</div>
							<b class="f-name"><span>${(fr.name)!}</span><em>(${(fr.fileCount)!0})</em></b>
							<#if ((Session.loginer.id)!'')=fr.creator.id && ((fr.type)!'') !='fixed'>
								<div class="f-opa">
									<a href="javascript:void(0);" class="rename">重命名</a>
									<a href="javascript:void(0);" class="download delete">删除</a>
								</div>
							</#if>
						</div>
					</li>
				<#else>
					<li frId="${fr.id}">
						<div class="am-file-block am-file-word">
							<div class="file-view">
								<#if (FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'study')! != 'img')>
							   		<div class="${FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'study') }" >
							   			<a href="javascript:;"></a>
							   		</div>
							    <#else>
							   	 	<div class="au-file-pic" >
								    	<img src=${FileUtils.getFileUrl((fr.newestFile.url)!'')} >
								    </div>
							    </#if>
						    </div>
							<b class="f-name"><span>${(fr.name)!}</span></b>
							<div class="f-info" style="display: block;">
								<span class="u-name">${(fr.creator.realName)!}</span>
								<span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd')}</span>
							</div>
							<div class="f-opa" style="display: none;">
								<a onclick="downloadFile('${(fr.newestFile.id)!}','${(fr.newestFile.fileName)!}')" href="javascript:void(0);" class="download">下载</a>
								<!--<a href="javascript:void(0);" class="move">移动</a>-->
								<#if ((Session.loginer.id)!'')=fr.creator.id>
									<a href="javascript:void(0);" class="rename">重命名</a>
								</#if>
								<#if role='master' || role='member' || ((Session.loginer.id)!'')=fr.creator.id>
									<a href="javascript:void(0);" class="delete">删除</a>
								</#if>
							</div>
						</div>
					</li>
				</#if>
			</#list>
		</#if>
	</ul>
</div>
<script>
	$(function(){
		//初始化各种按钮的点击事件
		activityFile.fn.initWithParent('#listFileResourcePage');
		
		//初始化上传文件点击事件
		var uploader = WebUploader.create({
    		swf : $('#ctx').val() + '/js/webuploader/Uploader.swf',
    		server : '${ctx}/${role}_${wsid}/file/uploadRemote?relationId=${relationId}&type=${type!}',
    		pick : '#picker',
    		resize : true,
    		duplicate : true,
    		accept : {
    		    extensions: ''
    		},
    		fileSizeLimit: ''
    	});
    	// 当有文件被添加进队列的时候
    	uploader.on('fileQueued', function(file) {
    		ajaxLoading();
    		uploader.upload();
    	});
		uploader.on('uploadError', function(file, reason) {
			ajaxEnd();
			alert('上传失败,请重新上传');
		});
    	uploader.on('uploadSuccess', function(file, response) {
    		ajaxEnd();
    		if (response != null && response.responseCode == '00') {
    			listFileResource('');
    		}else{
    			if(response.responseMsg){
    				alert(response.responseMsg);
    			}else{
    				alert('上传失败');
    			}
    		}
    	});
    	
    	
  		//资源统计展示
        $(".ag-file-bd .au-chart").on("click",function(){
            mylayerFn.open({
				id : 'listUserCountDiv',
				type : 2,
				title : '资源统计',
				content : '${ctx}/${role}_${wsid}/activity/file_resource/listUserFileCount?relationId=${relationId}',
				area : [880, 600],
				offset : ['auto', 'auto'],
				fix : true,
				shadeClose : false,
			});
        });
	});

	//删除文件、文件夹
	$(function(){
		$('.delete').on('click',function(){
			var id = $(this).closest('li').attr('frId');
			confirm('确定要删除?', function(){
				$.ajaxDelete('${ctx!}/${role}_${wsid}/fileResource?id='+id,'' , function(data){
					if(data.responseCode == '00'){
						alert('删除成功');
						listFileResource("${(parentId)!''}");
					}	
				});
			});
		});
		
	});

	//点击文件夹，弹出详情页面
	function viewMoreFileResource(parentId, parentName){
		var url = '';
		if(parentId){
			url = '${ctx}/${role}_${wsid}/activity/file_resource/more?type=${type!}&relationId=${relationId}&parentId='+parentId;
			if(parentName){
				frbar.flush();
				frbar.push(parentId,parentName);
			}
		}else{
			url = '${ctx}/${role}_${wsid}/activity/file_resource/more?type=${type!}&relationId=${relationId}';
		}
		mylayerFn.open({
			id : 'listMoreResourceDiv',
			type : 2,
			title : '查看资源',
			content : url,
			area : [880, 700],
			offset : ['auto', 'auto'],
			fix : false,
			shadeClose : false,
		});
	}
	
	//重新加载本页面
	/*
	function reloadPage(parentId){
		listFileResource(parentId);
	}*/
	
</script>
</@fileResourcesDirective>