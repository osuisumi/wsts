<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<div class="g-addElement-lyBox">
	<div class="g-addElement-tab">
		<div class="g-add-step">
			<ol class="m-add-step num3">
				<li class="step in">
					<span class="line"></span>
					<a href="javascript:void(0);"> <span class="ico"><i class="u-rhombus-ico">1</i></span>
					<br>
					<span class="txt">听课评课</span> </a>
				</li>
				<li class="step <#if activity.entityId??>yet</#if>">
					<span class="line"></span>
					<a href="javascript:void(0);"> <span class="ico"><i class="u-rhombus-ico">2</i></span>
					<br>
					<span class="txt">评分项设置</span> </a>
				</li>
				<li class="step last <#if activity.entityId??>yet</#if>">
					<span class="line"></span>
					<a href="javascript:void(0);"> <span class="ico"><i class="u-rhombus-ico">3</i></span>
					<br>
					<span class="txt">活动设置</span> </a>
				</li>
			</ol>
		</div>
		<br>
		<div id="tabListDiv" class="g-addElement-tabCont">
			<div class="g-addElement-tabList" style="display: block">
				<@coursewareDirective id="${activity.entityId! }">
				<#if (activity.id)??>
				<form id="saveActivityForm" action="${ctx }/${role }_${wsid }/activity/${activity.id!}" method="put">
					<input type="hidden" name="lcec.id" value="${courseware.id! }">
					<#else>
					<form id="saveActivityForm" action="${ctx }/${role }_${wsid }/activity" method="post">
						<input type="hidden" name="activity.relation.id" value="${activity.relation.id }">
						<input type="hidden" name="lcec.coursewareRelations[0].relation.id" value="${wsid }">
						</#if>
						<input type="hidden" name="activity.type" value="${activity.type! }">
						<ul class="g-addElement-lst g-addCourse-lst">
							<li class="m-addElement-item">
								<div class="ltxt">
									<em>*</em>课程主题：
								</div>
								<div class="center">
									<div class="m-pbMod-ipt">
										<input type="text" required value="${(courseware.title)!}" name="lcec.title" placeholder="输入课程主题" class="u-pbIpt">
									</div>
								</div>
							</li>
							<li class="m-addElement-item">
								<div class="ltxt">
									评课方式：
								</div>
								<div class="center">
									<div class="m-check-mod">
										<#assign type=(courseware.type)!'onLine' />
										<label class="m-radio-tick"> <strong> <i class="ico"></i>
											<input class="lcecType" type="radio" name="lcec.type" <#if type='offLine'>checked="checked"</#if>  value="offLine">
											</strong> <span>现场评课</span> 
										</label>
										<label class="m-radio-tick"> <strong> <i class="ico"></i>
											<input class="lcecType" type="radio" name="lcec.type" <#if type='onLine'>checked="checked"</#if> value="onLine">
											</strong> <span>实录评课</span> 
										</label>
									</div>
								</div>
							</li>
							<li class="m-addElement-item chooseTickItem uploadVideo">
								<div class="ltxt">
									<label class="m-radio-tick"> <strong> </strong> 
										<span>上传视频：</span>
									</label>
								</div>
								<div id="videoDiv" class="center byChooseTick">
									<#import "/wsts/common/upload_file_list.ftl" as uploadFileList /> 
									<@uploadFileList.uploadFileListFtl relationId="${(courseware.id)!}" relationType="coursewareVideo" paramName="lcec.video" divId="videoDiv" btnTxt="上传视频" fileNumLimit=1 fileTypeLimit="mp4,flv,wav"/>
									<span id="videoParam"></span>
								</div>
							</li>
							<li class="m-addElement-item">
								<div class="ltxt">
									授课人：
								</div>
								<div class="center">
									<div class="m-check-mod">
										<label class="m-radio-tick"> <strong> <i class="ico"></i>
											<#assign teacherId = (courseware.teacher.id)!''>
											<input  type="radio" name="lcec.teacher.id" <#if teacherId = '' || teacherId = ((Session.loginer.id)!'') >checked="checked"</#if> value="${(Session.loginer.id)!}">
											</strong> <span>我是授课人</span> </label>
										<#if teacherId!='' && teacherId != ((Session.loginer.id)!'')>
											<script>
												$(function(){
													$('#other-skr').show();
												});
											</script>
										</#if>
										<label class="m-radio-tick"> <strong> <i class="ico"></i>
											<input type="radio" <#if teacherId!='' && teacherId != ((Session.loginer.id)!'')>checked="checked"</#if>  name="lcec.teacher.id" value="other">
											</strong> <span>其他授课人</span> </label>
									</div>
								</div>
							</li>
							<li class="m-addElement-item" id="other-skr" style="display:none;">
								<div class="center">
									<div class="m-pbMod-ipt">
										<input type="text" style="width:400px" id="teacher" class="u-pbIpt">
										<ul id="result">
											<#if (courseware.teacher)??>
												<li class="labelLi"><span class="txt">${(courseware.teacher.realName)!}<input class="userIdClass" type="hidden" value="${(courseware.teacher.id)!}"></span><a id="userLabel${(courseware.teacher.id)!}" uid="${(courseware.teacher.id)!}" href="javascript:void(0);" class="dlt" title="删除该用户">×</a></li>
											</#if>
										</ul>
									</div>
								</div>
							</li>
							<li class="m-addElement-item">
								<div class="ltxt">
									学段/学科：
								</div>
								<div class="center">
									<div class="m-slt-row m-active-grade">
										<div class="block">
											<div class="m-selectbox style1">
												<strong><span class="simulateSelect-text">请选择学段</span><i class="trg"></i></strong>
												<select name="lcec.stage">
													<option value="" <#if ((courseware.stage)!'') = ''>selected="selected"</#if> >请选择学段</option>
													 ${TextBookUtils.getEntryOptionsSelected('STAGE', (courseware.stage)!'') }
												</select>
											</div>
										</div>
										<div class="block">
											<div class="m-selectbox style1">
												<strong><span class="simulateSelect-text">请选择学科</span><i class="trg"></i></strong>
												<select name="lcec.subject">
													<option value=""  <#if ((courseware.stage)!'') = ''>selected="selected"</#if> >请选择学科</option>
													${TextBookUtils.getEntryOptionsSelected('SUBJECT', (courseware.subject)!'') }
												</select>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li class="m-addElement-item">
								<div class="ltxt">
									选用教材：
								</div>
								<div class="center">
									<div class="m-pbMod-ipt">
										<input type="text" value="${(courseware.textbook)!}" name="lcec.textbook" placeholder="" class="u-pbIpt">
									</div>
								</div>
							</li>
							<li class="m-addElement-item">
								<div class="ltxt">
									评课方向：
								</div>
								<div class="center">
									<div class="m-pbMod-ipt">
										<textarea class="u-textarea" name="lcec.content" placeholder="输入活动描述">${(courseware.content)!}</textarea>
									</div>
								</div>
							</li>
							<li class="m-addElement-btn">
								<a onclick="saveLcec()" href="javascript:void(0);" data-href="index1.html" class="btn u-main-btn">保存并下一步</a>
								<a href="javascript:void(0);" class="btn u-inverse-btn u-cancelLayer-btn mylayer-cancel">取消</a>
							</li>
						</ul>
					</form>
					</@coursewareDirective>
			</div>
			<div class="g-addElement-tabList">
				<#import "edit_evaluate.ftl" as ee /> 
				<@ee.editEvaluateFtl relationId=(activity.entityId)!'' type="lcec" />
			</div>
			<div class="g-addElement-tabList">
				<#import "../edit_activity.ftl" as ea />
				<@ea.editActivityFtl activity=activity />
			</div>
		</div>
	</div>
</div>

<script>
	$(function(){
		$(".m-selectbox select").simulateSelectBox();
		
		$('.lcecType').on('click',function(){
			if($(this).val() == 'offLine'){
				$('.uploadVideo').hide();
			}else{
				$('.uploadVideo').show();
			}
		});
		
        $(":radio[name='lcec.teacher.id']").click(function(){ 
            var index=$(":radio[name='lcec.teacher.id']").index($(this));
            if(index==0){
                $('#other-skr').hide(500)
            }else{
                $('#other-skr').show(500);
            }
        });
        
        $('#teacher').userSelect({
			url : '${ctx}/userInfo/entities',
			userList : $('#result'),
			paramName : 'paramMap[realName]',
			single:true,
			afterInit:function(userSelectDiv){
				userSelectDiv.find('.u-add-tag').css('background-color','#1e8be8').css('height','33px');
			},
        });
	});
	
	
	function initFileParam(){
		var files = $('.fileList .fileLi.success');
		$('#videoParam').empty();
		if(files.size()>0){
			var file = files.eq(0);
			var fileId = file.attr('fileid');
			var fileName = file.attr('filename');
			var url = file.attr('url');
			if(fileId && fileName && url){
				$('#videoParam').append('<input class="fileParam" type="hidden" name="lcec.video.id" value="'+fileId+'">');
				$('#videoParam').append('<input class="fileParam" type="hidden" name="lcec.video.fileName"  value="'+fileName+'" >');
				$('#videoParam').append('<input class="fileParam" type="hidden" name="lcec.video.url" value="'+url+'" >');
			}
		}
		initFileType($('.fileList'));
	}

	function saveLcec(){
		if(!$('#saveActivityForm').validate().form()){
			return false;
		}
		//是否实录评课
		var coursewareType = $('input[name="lcec.type"]:checked').val();
		if(coursewareType=="onLine"){
			var videoCount = $('.fileList .fileLi.success').size();
			if(videoCount<=0){
				alert('实录评课需要上传视频');
				return;
			}
		}else{
			$('.fileList').empty();
		}
		
		//是否本人授课
		var teacher = $('input[name="lcec.teacher.id"]:checked').val();
		if(teacher =='other'){
			var teacherCount = $('#result li').size();
			if(teacherCount<=0){
				alert('请选择授课教师');
				return;
			}
			var teacherId = $('#result li a').eq(0).attr('uid');
			$('input[name="lcec.teacher.id"]').val(teacherId);
		};
		
		var data = $.ajaxSubmit('saveActivityForm');
		var json = $.parseJSON(data);
		if(json.responseCode == '00'){
			listWorkshopSection();
			var activity = json.responseData;
			if (activity != null) {
				refreshAndNextForm(activity.id);
			}else{
				$('.m-add-step li').eq(1).trigger('click');
			}
		}
	}
</script>