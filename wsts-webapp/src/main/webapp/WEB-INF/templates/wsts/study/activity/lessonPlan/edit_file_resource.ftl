<div class="g-addElement-lyBox">
	<#assign isFolder = isFolder!'N' />
	<#assign parentId = parentId!''>
	<form id="saveFileResourceForm" action="${ctx}/fileResource" method="post">
		<ul class="g-addElement-lst g-addChapter-WS">
			<input type="hidden" name="parentId" value="${parentId!}">
			<input type="hidden" name="fileRelations[0].relation.id" value="${relationId!}">
			<li class="m-addElement-item">
	            <div class="center">
	                <div class="m-pbMod-ipt">
						<input type="hidden" name="isFolder" value="N">
						<input type="text" name="name" value="${(fileResource.name)!}" placeholder="名称" class="u-pbIpt">
	        		</div>
	                <p class="m-addElement-ex" id="CourseTypeExplain">文件的名称，如：尽量用描述性名称</p>
	            </div>
	        </li>
	        <li class="m-addElement-item">
	            <div class="center">
			        <div id="fileDiv" class="center">
			            <#import "/common/upload_file_list.ftl" as uploadFileList />
						<@uploadFileList.uploadFileListFtl relationId="${(fileResource.id)!}" relationType="workshop_resource" paramName="newestFile" btnTxt="上传文件" fileNumLimit=1 />
			        	<input type="hidden" name="newestFile.id" value="">
			        	<input type="hidden" name="newestFile.fileName" value="">
			        	<input type="hidden" name="newestFile.url" value="">
			        </div>  
	            </div>
	        </li>	        
			<li class="m-addElement-btn">
	    		<a class="btn u-main-btn" onclick="saveFileResource()">创建</a>
	            <a class="btn u-inverse-btn u-cancelLayer-btn">取消</a>
	        </li>
		</ul>
	</form>
	
	<script>
		function saveFileResource(){
			if(!$('#saveFileResourceForm').validate().form()){
				return;
			}
			if($('input[name=name]').val().trim().length <= 0){
				return;
			}
			if($('input[name=isFolder]').val() == 'N'){
				$('#saveFileResourceForm input[name="newestFile.id"]').val($('#saveFileResourceForm input[name="newestFile[0].id"]').val());
				$('#saveFileResourceForm input[name="newestFile.fileName"]').val($('#saveFileResourceForm input[name="newestFile[0].fileName"]').val());
				$('#saveFileResourceForm input[name="newestFile.url"]').val($('#saveFileResourceForm input[name="newestFile[0].url"]').val());
				if($('#saveFileResourceForm input[name="newestFile[0].id"]').val() == null || $('#saveFileResourceForm input[name="newestFile[0].id"]').val() == ''){
					return;
				}
			}
			$('.fileParam').remove();
			var response = $.ajaxSubmit('saveFileResourceForm');					
			response = $.parseJSON(response);
			if(response.responseCode == '00'){
				alert('创建成功',function(){
					mylayerFn.closelayer($('#saveFileResourceForm'),function(){
						listFileResource("${parentId!''}");
					});
				});
			}
		}
	</script>

</div>