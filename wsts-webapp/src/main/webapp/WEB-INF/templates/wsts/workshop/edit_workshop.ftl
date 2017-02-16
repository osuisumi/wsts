<#include "/wsts/include/layout.ftl"/>
<#assign  jsArray=['${ctx}/wsts/js/uploadImage.js','webuploader','ueditor']/>
<@layout jsArray=jsArray>
<@workshopDirective id=id!''>
<div class="g-auto">
	<#import "/wsts/include/banner.ftl" as banner/>
	<#if (workshop.id)??>
		<@banner.content current="修改工作坊信息">
			<ins>&gt;</ins>
			<span><a id="back" href="${ctx}/workshop/${workshop.id}">${(workshop.title)!}</a></span>
		</@banner.content>
	<#else>
		<@banner.content current="创建工作坊">
			<ins>&gt;</ins>
			<span><a id="back" href="${PropertiesLoader.get('wsts.list.workshop') }">工作坊列表</a></span>
		</@banner.content>
	</#if>
	<div class="m-WS-notice m-WS-list">
		<div class="m-WS-notice-tl m-WS-list-tl">
			<#if (workshop.id)??>
				<h2>修改工作坊信息</h2>
			<#else>
				<h2>创建工作坊</h2>
				<span class="add-notice"><a href="${PropertiesLoader.get('wsts.list.workshop') }"><i class="back"></i>返回工作坊列表</a></span>
			</#if>
		</div>
		<form id="saveWorkshopForm" action="${ctx}/workshop/save" method="post">
			<#if id??>
				<script>
					$(function(){
						$('#saveWorkshopForm').attr('action','${ctx}/workshop/update/${id}').attr('method','put');
					});					
				</script>	
			<#else>
				<input type="hidden" name="type" value="personal">
				<input type="hidden" name="isTemplate" value="N">
				<input type="hidden" name="workshopRelation.relation.id" value="personal">
				<input type="hidden" name="masters[0].user.id" value="${Session.loginer.id}">
				<input type="hidden" name="masters[0].state" value="passed">
				<input type="hidden" name="state" value="editing">
			</#if>
			<div class="m-build-workshop">
				<div id="fileDiv" class="m-build-head">
					<#import "/common/image.ftl" as image/>
					<@image.imageFtl url="${(workshop.imageUrl)! }" default="${app_path }/images/defaultWorkshop.png" />
					<div class="m-file-upload">
						<a href="javascript:;" class="u-file-show-btn picker"><i></i>上传封面图片</a>
						<span id="imageParam">
						</span>
						<!--<input type="file" class="u-file-hide-btn"/>-->
						<p class="upload-txt">
							仅支持JPG、JPEG、PNG格式（2M以下）
						</p>
					</div>
				</div>
				<ul class="m-workshop-cont g-WS-lyBox">
					<li class="m-addElement-item">
						<div class="ltxt">
							<em>*</em>工作坊名称：
						</div>
						<div class="center">
							<div class="m-pbMod-ipt">
								<input type="text" value="${(workshop.title)!}" required name="title"  placeholder="请输入工作坊名称" class="u-pbIpt">
							</div>
						</div>
					</li>
					<li class="m-addElement-item">
						<div class="ltxt">
							工作坊简介:
						</div>
						<div class="center">
							<div class="m-pbMod-ipt">
								<!--<textarea name="summary" class="u-textarea" placeholder="">${(workshop.summary)!}</textarea>-->
								<script id="editor" type="text/plain" style="height: 200px;width:600px"></script>
								<input id="workshopSummary" name="summary" type="hidden">
							</div>
						</div>
					</li>
					<li class="m-addElement-item">
		                <div class="ltxt">学段/学科：</div>
		                <div class="center">
							<div class="m-slt-row m-active-grade">
                                <div class="block">
                                    <div class="m-selectbox style1">
                                        <strong><span class="simulateSelect-text">请选择学科</span><i class="trg"></i></strong>
		                                <select name="stage">
		                                	${TextBookUtils.getEntryOptionsSelected('STAGE', (workshop.stage)!'') }
 		                                </select>
                                    </div>
                                </div>                                            
                                <div class="block">
                                    <div class="m-selectbox style1">
                                        <strong><span class="simulateSelect-text">请选择学段</span><i class="trg"></i></strong>
		                                <select name="subject">
		                                	 ${TextBookUtils.getEntryOptionsSelected('SUBJECT', (workshop.subject)!'') }
		                                </select>
                                    </div>
                                </div>
                            </div>
		                </div>
		            </li>
					<li class="m-addElement-item">
						<div class="ltxt">
							研修方案:
						</div>
						<div class="center">
							<div class="m-pbMod-ipt" id="solution">
								<#import "/wsts/common/upload_file_list.ftl" as uploadFileList /> 
								<@uploadFileList.uploadFileListFtl relationId="${(workshop.id)!}" relationType="workshop_solution" paramName="solutions" divId="solution" btnTxt="上传" fileNumLimit=1 fileTypeLimit="doc,docx,wps,pdf" downloadTemplate="workshop_template.docx"/>
							</div>
						</div>
					</li>
					<li class="m-addElement-btn">
						<a href="javascript:location.href=document.referrer;"  class="btn u-inverse-btn">取消</a>
						<a href="javascript:void(0);" class="btn u-main-btn" id="confirmLayer">保存</a>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>

<script>
	var ue = initProduceEditor('editor', '${(workshop.summary)!}', '${(Session.loginer.id)!}');

	$(function(){
		$('#fileDiv img').attr('id','image');
		
		$('.m-pbMod-udload').append('<p class="m-addElement-ex">注：研修方案是工作坊是否通过审核的主要参考资料。</p>');
		
		//上传图像
		$('#fileDiv .picker').uploadImage({
			url:'${ctx}/file/uploadTemp',
			image:$('#image'),
			afterSuccess:function(serverFile){
				var paramDiv = $('#imageParam');
				paramDiv.empty();
				paramDiv.append('<input type="hidden" name="image.id" value="'+serverFile.id+'" />');
				paramDiv.append('<input type="hidden"  name="image.url" value="'+serverFile.url+'" />');
				paramDiv.append('<input type="hidden"  name="image.fileName" value="'+serverFile.fileName+'" />');
			},
		});
		
		//保存按钮
		$('#confirmLayer').on('click',function(){
			if(!$('#saveWorkshopForm').validate().form()){
				return;
			}
			var content = ue.getContent();
			$('#workshopSummary').val(content);
			var response = $.ajaxSubmit('saveWorkshopForm');
			response = $.parseJSON(response);
			if(response.responseCode == '00'){
				alert('保存成功',function(){
					location.href=document.referrer;
				});
			}else{
				alert(response.responseMsg);
			}
		});
	});
	
</script>
</@workshopDirective>
</@layout>