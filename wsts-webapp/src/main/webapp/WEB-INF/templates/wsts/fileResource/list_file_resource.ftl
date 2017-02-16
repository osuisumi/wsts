<#global wsid=relationId!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#if role != 'guest'>
<div class="g-WSstd-res-cont">
	<input type="hidden" id="currentFolderId" value="${parentId!''}">
	<#assign loginerId = (Session.loginer.id)!'' />
	<#if (isStatePage!'N') != 'Y'>
	    <div class="g-crm">
	  
	    </div>
    </#if>
	<div class="am-line-block">
		<#if (isStatePage!'N') != 'Y'>
			<div class="l">
				<a id="picker" class="au-uploadFile au-default-btn"> <i class="au-uploadFile-ico"></i>上传文件 </a>
				<#if role="master" || role="member">
					<a class="au-add-folder au-default-btn"> <i class="au-addFolder-ico"></i>创建文件夹 </a>
				</#if>
			</div>
		</#if>
		<div class="r">
			<a onclick="changeDisplay('block')" class="across crt" id="blockDisplay"><i></i></a>
			<a onclick="changeDisplay('list')" class="list" id="listDisplay"><i></i></a>
		</div>
	</div>
	<#if (isStatePage!'N') != 'Y'>
		<@fileResourcesDirective relationId=relationId parentId=parentId! page=(pageBounds.page)!1 orders='IS_FOLDER.DESC,CREATE_TIME.DESC' limit=12>
			<#assign fileResources=fileResources>
		</@fileResourcesDirective>
	<#else>
		<@fileResourcesDirective relationId=relationId parentId=parentId! page=(pageBounds.page)!1 orders='IS_FOLDER.DESC,CREATE_TIME.DESC' limit=12 isFolder='N' creator=Session.loginer.id>
			<#assign fileResources=fileResources>
		</@fileResourcesDirective>
	</#if>
	<#if fileResources?? && (fileResources?size > 0)>
	<ul id="fileResourcesUl" class="am-file-lst f-cb am-Mana-file-lst ">
		<#if fileResources??>
			<#list fileResources as fr>
				<#if fr.isFolder == 'Y'>
					<#-- 显示方式为块状的文件夹 -->
					<li class="displayBlock folder " folderId="${fr.id}" folderName = "${fr.name!}" id="${(fr.id)!}">
						<div class="am-file-block am-file-folder">
							<div class="file-view">
								<div class="au-folder">
									<a href="javascript:;"></a>
								</div>
							</div>
							<b class="f-name"><span>${fr.name!}</span><em>(${(fr.fileCount)!0})</em></b>
							<div class="f-info">
								<span class="u-name">${(fr.creator.realName)!}</span>
								<span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd')}</span>
							</div>
							<#if role='member' || role='master'>
								<div class="opt">
									<a class="u-opt u-del-ico"><i class="u-WS-del-ico"></i><span class="tip">删除</span></a>
									<!--<span class="line">|</span>
									<a class="u-opt u-edit-ico"><i class="u-WS-edit-ico"></i><span class="tip">编辑</span></a>
									-->
								</div>
							</#if>
						</div>
					</li>
					<#-- 显示方式为列表的文件夹 -->
					<li class="displayList" style="display:none;" id="${(fr.id)!}" folderId="${fr.id}" folderName = "${fr.name!}" >
                        <div class="m-fill-cont">
                            <a class="u-fill-folder u-fill" href="javascript:;"></a>
                            <p>${fr.name!}(${(fr.fileCount)!0})</p>                                   
                        </div>
                        <div class="m-fill-load">
                            <p>
                            	<span><a>${(fr.creator.realName)!}</a></span>
                            	创建于<ins class="time">${TimeUtils.formatDate(fr.createTime,'yyyy-MM-dd')}</ins>
                            	<ins class="time">${TimeUtils.formatDate(fr.createTime,'HH:MM:ss')}</ins>
                            	<!--
                            	<i class="line">|</i>类型：课件
                            	<i class="line">|</i>下载量：
                            	-->
                            	<#-- <i class="line">|</i>浏览量：8 -->
                            </p>
                        </div>
                        <!--<a class="u-load-resc"><i></i>下载资源</a>-->                                    
                    </li>
				<#else>
					<#-- 显示方式为块状的文件 -->
					<li class="displayBlock" id="${(fr.id)!}">
						<div class="am-file-block am-file-word">
							<div class="file-view" >
								<#if (FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'study')! != 'img')>
							   		<div class="${FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'study') }" >
							   			<a href="javascript:;"></a>
							   		</div>
							    <#else>
								    <img src=${FileUtils.getFileUrl((fr.newestFile.url)!'')} width="100" height="75" class="u-ico-file">
							    </#if>
							</div>
							<#if fr.type ?? && fr.type='excellent'>
								<div class="u-best">
									精品
								</div>
							</#if>
							<b class="f-name"><span>${fr.name}</span></b>
							<div class="f-info">
								<span class="u-name">${(fr.creator.realName)!}</span>
								<span class="time">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd')}</span>
							</div>
							<div class="opt"><!--成员随时可以删除，学员是创建者，并且资源没评优时可以删除-->
								<#if role="master" || role="member">
									<a class="u-opt u-del-ico"><i class="u-WS-del-ico"></i><span class="tip">删除</span></a>
								<#elseif ((Session.loginer.id)!'') = fr.creator.id && (fr.type!'') != 'excellent'>
									<a class="u-opt u-del-ico"><i class="u-WS-del-ico"></i><span class="tip">删除</span></a>
								</#if>
								<span class="line">|</span>
								<!--<a class="u-opt u-edit-ico"><i class="u-WS-edit-ico"></i><span class="tip">编辑</span></a>
								<span class="line">|</span>-->
								<i class="u-WS-more-ico">
								
								<div class="m-more-list">
								<#if (role="master" || role="member")>
									<#if ((fr.type)!'') != 'excellent'>
										<span class="excellent"><a>设为精品资源</a></span>
									<#else>
										<span class="unExcellent"><a>取消精品资源</a></span>
									</#if>
								</#if>
									<!--<span class="moveFile"><a>移动资源</a></span>-->
									<span><a onclick="downloadFile('${(fr.newestFile.id)!}','${(fr.newestFile.fileName)!}')">下载资源</a></span>
									<!--<span><a>权限设置</a></span>-->
									<ins class="arrow"></ins>
								</div> </i>
							</div>
						</div>
					</li>
					<#-- 显示方式为列表的文件 -->
		            <li class="displayList" style="display:none;" id="${(fr.id)!}">
                        <div class="m-fill-cont">
                            <a class=" u-fill u-fill-${FileTypeUtils.getFileTypeClass((fr.newestFile.fileName)!, 'suffix')!''} "></a>
                            <p>${fr.name}</p>
                            <#if ((fr.type)!'') = 'excellent'>
								<div class="u-best">精品</div>
                            </#if>
                        </div>
                        <div class="m-fill-load">
                            <p>
                            	<span><a>管理员</a></span>
                    			创建于<ins class="time">${TimeUtils.formatDate(fr.createTime,'yyyy-MM-dd')}</ins>
                    			<ins class="time">${TimeUtils.formatDate(fr.createTime,'HH:MM:ss')}</ins>
                    			<ins class="time">${TimeUtils.formatDate(fr.createTime,'HH:MM:ss')}</ins>
                    			<!--<i class="line">|</i>类型：课件--> 
                    			<i class="line">|</i>下载量：${(fr.fileRelations[0].downloadNum)!0}
                    			<#-- <i class="line">|</i>浏览量：8 -->
                            </p>
                        </div>
                        <span class="u-load-resc">
                    	<#if (role="master" || role="member")>
							<#if ((fr.type)!'') != 'excellent'>
								<a class="excellent" >设为精品资源</a> 
							<#else>
								<a class="unExcellent">取消精品资源</a>
							</#if>
						</#if>
                        <a style="margin-left:20px" onclick="downloadFile('${(fr.newestFile.id)!}','${(fr.newestFile.fileName)!}')"><i></i>下载资源</a>  
                        </span>
                    </li>
				</#if>
			</#list>
		</#if>
	</ul>
	<#else>
		<div class="g-no-notice-Con">
            <p class="txt">暂时没数据！</p>
        </div>
	</#if>
	<form id="listFileResourcesForm"  method="post" action="${ctx!}/fileResource" >	
		<input type="hidden" name="_method" value="get" >
		<input type="hidden" name="orders" value="IS_FOLDER.DESC,CREATE_TIME.DESC">
		<input class="limit" type="hidden" name="limit" value="12" >
		<input id="showType" type="hidden" name="showType" value="${(showType)!'block'}">
		<input type="hidden" name="relationId" value="${(relationId)!}" >
		<input type="hidden" name="parentId" value="${(parentId)!}" >
	    <div id="myCoursePage" class="m-laypage"></div>
	    <#if paginator??>
	 		<#import "/common/pagination_ajax.ftl" as p/>
			<@p.paginationAjaxFtl formId="listFileResourcesForm" divId="myCoursePage" paginator=paginator! contentId="tabContent"/>
		</#if>
	</form>

</div>
<#else>
		<div class="g-no-notice-Con">
            <p class="txt">您不在此工作坊内无权查看该页面！</p>
        </div>
</#if>
<script>
	$(function() {
		changeDisplay('${(showType)!"block"}');
		
		$("#fileResourcesUl .displayList:even").css("background","#FFFFFF");
	
	   	var inneruploader = WebUploader.create({
        		swf : $('#ctx').val() + '/js/webuploader/Uploader.swf',
        		server : '${ctx}/${role}_${wsid}/file/uploadRemote?relationId=${relationId}&parentId=${parentId!""}&type=workshop_resource',
        		pick : '#picker',
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
        	});
        	inneruploader.on('uploadSuccess', function(file, response) {
        		if (response != null && response.responseCode == '00') {
        			listFileResource('${parentId!""}');
        		}
        	});
		
		$('.au-add-folder').on('click',function(){
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '创建文件夹',
				content : '${ctx}/${role}_${wsid}/fileResource/create?relationId=${relationId}&parentId='+$('#currentFolderId').val()+'&isFolder=Y',
				area : [500, 300],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});
		
		$('.au-folder').on('click',function(){
			var frId = $(this).parents('li').attr('folderId');
			var frName = $(this).parents('li').attr('folderName');
			frbar.push(frId,frName);
			listFileResource(frId);
		});
		
		$('.m-fill-cont').on('click',function(){
			if(!$(this).find('a').hasClass('u-fill-folder')){
				return;
			}
			var frId = $(this).parents('li').attr('folderId');
			var frName = $(this).parents('li').attr('folderName');
			frbar.push(frId,frName);
			listFileResource(frId);
		});
		<#if (isStatePage!'N') != 'Y'>
		setFileResourceBar();
		</#if>
		
		//删除
		$('.u-del-ico').on('click',function(){
			var id = $(this).parents('li').attr('id');
			confirm('确定要删除?', function(){
				$.ajaxDelete('${ctx!}/${role}_${wsid}/fileResource?id='+id,'' , function(data){
					if(data.responseCode == '00'){
						alert('删除成功');
						listFileResource("${(parentId)!''}");
					}	
				});
			});
		});
		
		//编辑
		$('.u-WS-edit-ico').on('click',function(){
			var id = $(this).parents('li').attr('id');
			var url = '${ctx}/${role}_${wsid}/fileResource/edit?relationId=${wsid}id='+id;
		    var folderId = $(this).parents('li').attr('folderId');
		    if(folderId != '' && folderId != undefined){
		    	url +='&isFolder=Y'
		    }
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '编辑',
				content : url,
				area : [500, '300'],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});
		
		//设为优秀资源
		$('.excellent').on('click',function(){
			var _this = this;
			confirm('确定将该资源设置为优秀资源吗',function(){
				$.post('${ctx}/${role}_${wsid}/fileResource',{
					'_method':'PUT',
					'id':$(_this).closest('li').attr('id'),
					'type':'excellent'
				},function(response){
					if(response.responseCode == '00'){
						alert('操作成功',function(){
							listFileResource("${(parentId)!''}");
						});
					}
				});
			});
		});
		
		//取消优秀资源
		$('.unExcellent').on('click',function(){
			var _this = this;
			confirm('确定取消该优秀资源？',function(){
				$.post('${ctx}/${role}_${wsid}/fileResource',{
					'_method':'PUT',
					'id':$(_this).closest('li').attr('id'),
					'type':'normal'
				},function(response){
					if(response.responseCode == '00'){
						alert('操作成功',function(){
							listFileResource("${(parentId)!''}");
						});
					}
				});
			});
		});
		
		//移动资源
		$('.moveFile').on('click',function(){
			var id = $(this).closest('li').attr('id');
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '编辑',
				content : '${ctx}/${role}_${wsid}/fileResource/moveFile?id='+id+'&relationId=${relationId}',
				area : [500, '300'],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});		

	});
	
	function listFileResource(parentId){
		var showType = $('#listFileResourcesForm input[name=showType]').val();
		$('#tabContent').load('${ctx}/${role}_${wsid}/fileResource','parentId='+parentId+'&relationId='+$('#currentWokshopId').val()+'&showType='+showType);
	}
	
	function setFileResourceBar(){
		var root =  $('<a>工作坊资源 </a>');
		root.on('click',function(){
			frbar.flush();
			listFileResource('');
		});
		$('.g-crm').append(root);
		$.each(frbar.bars,function(i,n){
			var btn = $('<span class="u-line">&gt;</span><a>'+n.frName+'</a>');
			btn.on('click',function(){
				frbar.jump(n.frId);
				listFileResource(frbar.getLast().frId);
			});
			$('.g-crm').append(btn);
		});
	}
	
	function back(){
		frbar.pop();
		var last = frbar.getLast();
		if(last){
			listFileResource(last.frId);
		}else{
			listFileResource('');
		}
	};
	
	//显示模式 
	function changeDisplay(type){
		$('.r').find('a').removeClass('crt');
		if(type == 'list'){
			$('#fileResourcesUl').attr('class','am-file-list');
			$('.displayList').show();
			$('.displayBlock').hide();
			$('#listDisplay').addClass('crt');
		}
		if(type == 'block'){
			$('#fileResourcesUl').attr('class','am-file-lst f-cb am-Mana-file-lst');
			$('.displayList').hide();
			$('.displayBlock').show();
			$('#blockDisplay').addClass('crt');
		}
		$('#listFileResourcesForm input[name=showType]').val(type);
	};

</script>