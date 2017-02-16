<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<@fileResourcesDirective relationId=relationId parentId=parentId! page=1 limit=(pageBounds.limit)!9999 orders='IS_FOLDER.DESC,CREATE_TIME.DESC' >
<div id="moreFileResourcePage" class="ag-layer-layout">
	<input type="hidden" id="parentId" value="${parentId!''}">
	<div class="al-tp">
		<!-- <h3 class="tt">共建资源</h3> -->
		<div class="am-crm1">
			<a onclick="barJump('')" href="javascript:void(0);">共建资源</a>
		</div>
		<!--
		<div class="am-search">
			<input type="text" placeholder="搜索资源" class="au-ipt">
			<input type="submit" value="" class="submit-btn">
		</div>
		-->
	</div>
	<div class="al-bd">
		<div class="ag-lbr-tp f-cb">
			<#if role!='guest'>
				<div class="btn-line">
					<a id="innerPicker" href="javascript:void(0);" class="au-uploadFile au-default-btn"> <i class="au-uploadFile-ico"></i>上传文件 </a>
					<#if role='master' || role='member' >
						<a href="javascript:void(0);" class="au-add-folder au-default-btn"> <i class="au-addFolder-ico"></i>创建文件夹 </a>
					</#if>
				</div>
			</#if>
			<div class="am-lbr-tabli">
				<a href="javascript:void(0);" class="au-view-p z-crt" title="图标"> <i class="au-viewP-ico"></i> </a>
				<a href="javascript:void(0);" class="au-view-l" title="列表"> <i class="au-viewL-ico"></i> </a>
			</div>
			<span class="txt">已有<strong class="fileCount"></strong>个资源</span>
		</div>
		<div class="ag-lbr-bd">
			<#if fileResources??>
					<div class="ag-lbr-tabLst">
						<ul class="am-file-lst f-cb">
							<#list fileResources as fr>
								<#if fr.isFolder = 'Y'>
									<li frid="${fr.id}">
										<div class="am-file-block am-file-folder">
											<div class="file-view">
												<div onclick="reloadMoreActivityFileResource('${fr.id!}','${fr.name!}','block')" class="au-folder"></div>
											</div>
											<b class="f-name"><span>${(fr.name)!}</span><em>(${(fr.fileCount)!0})</em></b>
											<#if ((Session.loginer.id)!'')=fr.creator.id && ((fr.type)!'') !='fixed'>
												<div class="f-opa">
													<a href="javascript:void(0);" class="rename">重命名</a>
													<a href="javascript:void(0);" class="delete">删除</a>
												</div>
											</#if>
										</div>
									</li>
								<#else>
									<li frid="${fr.id}" class="file">
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
											<b class="f-name"><span>${fr.name}</span></b>
											<div class="f-info">
												<span class="u-name">${(fr.creator.realName)!}</span>
												<span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd')}</span>
											</div>
											<div class="f-opa">
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
						</ul>
					</div>
			</#if>
			
			<div class="ag-lbr-tabLst">
				<table cellpadding="0" cellspacing="0" class="ag-file-table">
					<thead>
						<tr>
							<th width="40%" style="padding-left: 20px;">文件名</th>
							<!--<th width="17%">所在文件夹</th>-->
							<th width="18%"><a href="javascript:void(0);" class="au-sort" title=""> 文件大小<!--<i class="au-sort-ico"></i>--> </a></th>
							<th width="25%"><a href="javascript:void(0);" class="au-sort z-crt" title=""> 上传时间<!--<i class="au-sort-ico"></i>--> </a></th>
						</tr>
					</thead>
					<tbody class="list-type">
						<#if fileResources??>
							<#list fileResources as fr>
								<#if fr.isFolder='Y'>
									<tr frId="${fr.id}">
										<td><a onclick="reloadMoreActivityFileResource('${fr.id}','${fr.name}')" href="javascript:void(0);" onclick="" class="name"> <i class="au-sFile-folder au-sFile-ico"></i>${(fr.name)!} </a></td>
										<!--<td>---</td>-->
										<td>--</td>
										<td><span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd HH:mm')}</span>
										<div class="opa">
											<!--
											<a href="javascript:void(0);" class="au-opens z-crt"> <i class="au-opens-ico"></i>打开 </a>
											<a href="javascript:void(0);" class="au-download z-crt"> <i class="au-download-ico"></i>下载 </a>
											-->
											<#if ((Session.loginer.id)!'')=fr.creator.id && ((fr.type)!'') !='fixed'>
												<a href="javascript:void(0);" class="au-dlt z-crt delete"> <i class="au-dlt-ico"></i>删除 </a>
											</#if>
										</div></td>
									</tr>
								<#else>
									<tr frId="${fr.id}">
										<td><a href="javascript:void(0);" class="name"> <i class="au-sFile-${FileTypeUtils.getFileTypeClass(fr.name,'suffix')} au-sFile-ico"></i>${(fr.name)!}</a></td>
										<!--<td>教案设计</td>-->
										<td>${FileSizeUtils.getFileSize(fr.newestFile.fileSize)}</td>
										<td><span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd HH:mm')}</span>
										<div class="opa">
											<a onclick="downloadFile('${(fr.newestFile.id)!}','${(fr.newestFile.fileName)!}')" href="javascript:void(0);" class="au-download z-crt"> <i class="au-download-ico"></i>下载 </a>
											<!--<a href="javascript:void(0);" class="au-classify z-crt"> <i class="au-classify-ico"></i>分类 </a>-->
											<#if role='master' || role='member' || ((Session.loginer.id)!'')=fr.creator.id>
												<a href="javascript:void(0);" class="au-dlt z-crt delete"> <i class="au-dlt-ico"></i>删除 </a>
											</#if>
										</div></td>
									</tr>
								</#if>
							</#list>
						</#if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</@fileResourcesDirective>

<script>
	$(function(){
		activityFile.fn.initWithParent('#moreFileResourcePage');
		var showType = '${showType!"list"}';
		var p = 1;
		if(showType == 'block'){
			p = 0;
		}
		
	    $(".ag-layer-layout").myTab({
	        pars    : '.ag-layer-layout',   //最外层父级
	        tabNav  : '.am-lbr-tabli',  //标签导航
	        li      : 'a',       //标签
	        tabCon  : '.ag-lbr-bd',  //区域父级
	        tabList : '.ag-lbr-tabLst', //t区域模块
	        cur     : 'z-crt',//选中类
	        page    : p //默认显示第几个模块
	    });
	    
		$('.delete').on('click',function(){
			var id = $(this).closest('li').attr('frId');
			if(!id){
				id = $(this).closest('tr').attr('frId');
			}
			confirm('确定要删除?', function(){
				$.ajaxDelete('${ctx!}/${role}_${wsid}/fileResource?id='+id,'' , function(data){
					if(data.responseCode == '00'){
						alert('删除成功',function(){
							reloadMoreActivityFileResource('${parentId!}');
						});
					}	
				});
			});
		});
		
		setFrbar();//设置导航条
	    
	   $('.fileCount').text($('.file').size());//设置文件总数
	   
	   	var inneruploader = WebUploader.create({
        		swf : $('#ctx').val() + '/js/webuploader/Uploader.swf',
        		server : '${ctx}/file/uploadRemote?relationId=${relationId}&parentId=${parentId!""}&type=${type!}',
        		pick : '#innerPicker',
        		resize : true,
        		duplicate : true,
        		accept : {
        		    extensions: ''
        		},
        		fileSizeLimit: ''
        	});
        	// 当有文件被添加进队列的时候
        	inneruploader.on('fileQueued', function(file) {
        		inneruploader.upload();
        		ajaxLoading();
        	});
			inneruploader.on('uploadError', function(file, reason) {
				ajaxEnd();
				alert('上传失败,请重新上传');
			});
        	inneruploader.on('uploadSuccess', function(file, response) {
        		ajaxEnd();
        		if (response != null && response.responseCode == '00') {
        			reloadMoreActivityFileResource('${parentId!""}');
					listFileResource('');
		   		}else{
	    			if(response.responseMsg){
		    				alert(response.responseMsg);
		    			}else{
		    				alert('上传失败');
		    			}
		    		}
        	});
	});
	
	function setFrbar(){
		$.each(frbar.bars,function(i,n){
			var btn = $('<span class="line">&gt;</span><a>'+n.frName+'</a>');
			btn.on('click',function(){
				frbar.jump(n.frId);
				barJump(frbar.getLast().frId);
			});
			$('.am-crm1').append(btn);
		});
	}
	
	function barJump(parentId){
		var title = $('.am-lbr-tabli .z-crt').attr('title');
		var showType = 'list';
		if(title =='图标'){
			showType = 'block';
		}
		reloadMoreActivityFileResource(parentId,'',showType);
	}
	
	function reloadMoreActivityFileResource(parentId,name,type){
		var showType = 'list';
		if(type){
			showType = type;
		}else{
			var title = $('.am-lbr-tabli .z-crt').attr('title');
			if(title =='图标'){
				showType = 'block';
			}
		}
		var url = '';
		if(parentId){
			if(name){
				frbar.push(parentId,name);
			}
			url = '${ctx}/${role}_${wsid}/activity/file_resource/more?type=${type!}&relationId=${relationId}&parentId='+parentId+'&showType='+showType;
		};
		if(parentId == ''){
			frbar.flush();
			url = '${ctx}/${role}_${wsid}/activity/file_resource/more?type=${type!}&relationId=${relationId}&showType='+showType;
		};
		mylayerFn.refresh({
            id: 'listMoreResourceDiv',
            content: url
        });
	};
	
	/*
	function reloadPage(parentId){
		//刷新本页面并刷新父页面
		reloadMoreActivityFileResource(parentId);
		listFileResource('');
	}*/

</script>
