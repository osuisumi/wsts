<#macro viewAssignmentFtl assignmentId aid index relationId>
	<#assign cid=CSAIdObject.getCSAIdObject().cid>
	<#assign hasTeachRole=SecurityUtils.getSubject().hasRole('course_teacher_'+cid)>
	<#if hasTeachRole>
		<#assign role='teach'>
	<#else>
		<#assign role='study'>
	</#if>
	<#if index??>
		<#assign step=(index[0]?number)!-1>
	<#else>
		<#assign step=-1>
	</#if>
	<@assignmentUserDirective assignmentId=assignmentId activityId=aid relationId=relationId>
		<#if !hasTeachRole>
			<#assign assignmentRelation=assignmentUser.assignmentRelation> 
		</#if>
		<#assign assignment=assignmentRelation.assignment> 
		<#if assignmentRelation.responseTime??>
			<#assign inCurrentDate=(TimeUtils.hasBegun(assignmentRelation.responseTime.startTime) && !TimeUtils.hasEnded(assignmentRelation.responseTime.endTime))>	
		<#else>
			<#assign inCurrentDate=true>
		</#if> 
		<div class="g-study-dt">
			<#if !hasTeachRole>
				<div class="g-study-prompt">
					<p>
						<#if assignment.markType! == 'each_other'>
							要求提交 <span>1</span>份作业 （<span>${100-(assignment.eachOtherMarkConfig.markScorePct)!0 }</span>分），
							 批改 <span>${assignment.eachOtherMarkConfig.markNum }</span>份作业（<span>${(assignment.eachOtherMarkConfig.markScorePct)!0 }</span>分）
							<br>
							<#if assignmentUser.state != 'not_attempt'>
								<#if assignmentUser.responseTime?? >
									您已提交<span> 1</span>份作业
									（<#if assignmentUser.responseScore??>
										得分：<span>${assignmentUser.responseScore }</span>分
									 <#else>
									 	还未批改
									</#if>）
								</#if>
								,
								批改 <span>${assignmentUser.markNum!0 }</span>份作业（得分：<span>${assignmentUser.markScore!0 }</span>分）
							</#if>
						<#else>	
							要求提交 <span>1</span>份作业 （<span>${100-(assignment.eachOtherMarkConfig.markScorePct)!0 }</span>分）
							<br>
							<#if assignmentUser.state != 'not_attempt'>
								<#if assignmentUser.responseTime?? >
									您已提交<span> 1</span>份作业
									（<#if assignmentUser.state == 'return' >
										退回重做
									 <#elseif assignmentUser.responseScore??>	
										得分：<span>${assignmentUser.responseScore }</span>分
									 <#else>
									 	还未批改
									</#if>）
								</#if>
							</#if>
						</#if>
					</p>                                    
	            	<i class="close">X</i>
	            </div>
	        </#if>
	        <div class="g-studyAct-box1">
	        	<#if !hasTeachRole>
		            <div class="g-add-step style1">
		                <ol id="assignmentStepOl"  class="m-add-step num3">
		                    <li class="step 
		                    	<#if (step == 0 || (step < 0 && (assignmentUser.state == 'not_attempt' || assignmentUser.state == 'return') ))>
		                    		in
		                    	<#else>
		                    		yet
		                    	</#if>
		                    	">
		                        <span class="line"></span>
		                        <a href="javascript:void(0);">
		                            <span class="ico"><i class="u-rhombus-ico">1</i></span>
		                            <br>
		                            <span class="txt">提交作业</span>
		                        </a>
		                    </li>
		                    <li class="step 
		                    	<#if step == 1 || (step < 0 && assignmentUser.state == 'commit' )>
		                    		in
		                    	<#elseif assignmentUser.state != 'not_attempt' && assignmentUser.state != 'return'>
		                    		yet
		                    	</#if>
		                    	">
		                        <span class="line"></span>
		                        <a href="javascript:void(0);">
		                            <span class="ico"><i class="u-rhombus-ico">2</i></span>
		                            <br>
		                            <span class="txt">
			                            <#if assignment.markType! == 'each_other'>
			                            	学员互评
			                            <#else>
			                            	等待批阅
			                            </#if>
		                           	</span>
		                        </a>
		                    </li>
		                    <li class="step last
		                    	<#if step == 2 || (step < 0 && assignmentUser.state == 'complete' )>
		                    		in
		                    	<#elseif assignmentUser.state == 'complete'>
		                    		yet
		                    	</#if>
		                    	">
		                        <span class="line"></span>
		                        <a href="javascript:void(0);">
		                            <span class="ico"><i class="u-rhombus-ico">3</i></span>
		                            <br>
		                            <span class="txt">完成</span>
		                        </a>
		                    </li>
		                </ol>
		            </div> 
		        </#if>
	            <#if (hasTeachRole || assignmentUser.state == 'not_attempt' || assignmentUser.state == 'return' || step == 0)>
	            	<#if !hasTeachRole>
	            		<div class="g-studyAct-time">
			                <div class="am-main-r">
			                	<#import "/ncts/study/common/show_time.ftl" as st /> 
								<@st.showTimeFtl timePeriod=(assignmentRelation.responseTime)! label="作业提交" /> 
			                </div>    
			            </div>
	            	</#if>
	            	<div id="assignmentContentDiv" class="g-studyTask-dt">
	            		<div class="g-studyAct-info">
		                    <p class="text">${assignment.content }</p>
		                    <#if (assignment.fileInfos?size>0)>
								<div class="ag-adjunct-cont">
									<div class="am-mod-tt">
										<h3 class="t1">附件</h3>
									</div>
									<div class="ag-adjunct-dt">
										<ul class="am-file-lst f-cb" id="activityFileList">
											<#list assignment.fileInfos as file>
												<li>
													<div class="am-file-block am-file-word">
														<div class="file-view">
															<div class="${FileTypeUtils.getFileTypeClass(file.fileName, 'study') }">
																<#if FileTypeUtils.getFileTypeClass(file.fileName, 'study') == 'img'>
																	<img src="${FileUtils.getFileUrl(file.url) }" >
																</#if>
															</div>
														</div>
														<b class="f-name"><span>${file.fileName }</span></b>
														<div class="f-info">
															<!-- <span class="u-name">${file.creator.realName! }</span>  -->
															<span class="time">${TimeUtils.formatDate(file.createTime, 'yyyy/MM/dd') }</span>
														</div>
														<div class="f-opa">
															<a onclick="previewFile('${file.id}')" href="javascript:void(0);" >预览</a>
															<a onclick="downloadFile('${file.id}', '${file.fileName }')" href="javascript:void(0);" class="download">下载</a> 
															<!-- <a href="javascript:void(0);" class="move">移动</a> 
															<a href="javascript:void(0);" class="rename">重命名</a> 
															<a href="javascript:void(0);" class="delete">删除</a> -->
														</div>
													</div>
												</li>
											</#list>
										</ul>
									</div>
								</div>
							</#if>
		                    <br>
		                    <form id="updateAssignmentUserForm" action="${ctx }/${aid}/${role }/unique_uid_${Session.loginer.id }/assignment/user/${(assignmentUser.id)!}" method="put">
		                    	<input type="hidden" name="state" value="commit">
		                    	<ul class="addEle-lst">
			                    	<#if assignment.responseType! == 'editor'>
			                    		<li>
					                    	<div class="m-pbMod-upload">
					                    		<p style="margin: 0">作业满分: ${assignment.score }</p>
					                        </div>
					                    </li>
			                    		<li>
				                            <div class="m-pbMod-ipt">
				                                <textarea name="" class="u-textarea" placeholder="请输入答案内容" style="height: 170px;"></textarea>
				                            </div>
				                        </li>
			                    	<#else>
			                    		<li>
					                    	<div class="m-pbMod-upload">
					                    		<p style="margin: 0">作业满分:  <em style="color:red;">${assignment.score!100 }</em>分</p>
					                    		<br>
					                            <p style="margin: 0">文件格式: ${(assignment.uploadResponseConfig.fileTypes)!'无要求' }</p>
					                            <br>
					                            <#if assignment.uploadResponseConfig.fileSize?? && (assignment.uploadResponseConfig.fileSize > 0)>
					                            	<p style="margin: 0">文件大小限制:  <em style="color:red;">${(assignment.uploadResponseConfig.fileSize)!}</em>MB</p>
					                            </#if>
					                        </div>
					                    </li>
			                    		<li>
			                    			<div id="fileDiv">
			                    				<#import "/wsts/common/upload_file_list.ftl" as uploadFileList /> 
												<@uploadFileList.uploadFileListFtl relationId="${(assignmentUser.id)!}" paramName="fileInfos" fileNumLimit=1 fileTypeLimit=(assignment.uploadResponseConfig.fileTypes)! relationType="assignment_user" fileSizeLimit=(assignment.uploadResponseConfig.fileSize*1024*1024)!0 />
			                    			</div>
				                        </li>
			                    	</#if>
			                    	<#if (inCurrentDate) && (('not_attempt' == (assignmentUser.state)!'') || ('return' == (assignmentUser.state)!'')) && (!hasTeachRole)>
			                    		<li class="m-addElement-btn hasborder">
				                            <a onclick="updateAssignmentUser()" class="btn u-main-btn">提交</a>
				                        </li>
			                    	</#if>
			                    </ul>
		                    </form>
		                </div>    
	            	</div>
	            <#elseif assignmentUser.state == 'commit' || step == 1>
	            	<#if assignment.markType! == 'each_other'>
	            		<#import "mark_each_other.ftl" as meo /> 
						<@meo.markEachOtherFtl assignmentUser=assignmentUser aid=aid/>
	            	<#else>
	            		<#import "/ncts/study/activity/assignment/mark_by_teacher.ftl" as mbt /> 
						<@mbt.markByTeacherFtl/>
	            	</#if>
            	<#elseif assignmentUser.state == 'complete' || step == 2>
            		<#import "view_assignment_result.ftl" as var /> 
					<@var.viewAssignmentResultFtl assignmentUser=assignmentUser/>
            	</#if>
	        </div>
	    </div>
	    <div id="test"></div>
		<script>
			$(function(){
				$('#assignmentStepOl li').click(function(){
					if($(this).hasClass('yet')){
						var index = $('#assignmentStepOl li').index($(this));
						$('#studyCourseForm').attr('action', '${ctx}/${aid}/study/course/${cid}');
						$('#studyCourseForm').append('<input type="hidden" name="index" value="'+index+'">');
						$('#studyCourseForm').submit();
					}
				});
			});
		
			function updateAssignmentUser(){
				var data = $.ajaxSubmit('updateAssignmentUserForm');
				if (!isMatchJson(data)){
					$('body').html($(data));
				}else{
					var json = $.parseJSON(data);
					if(json.responseCode == '00'){
						alert('提交成功', function(){
							studyCourse('${cid}','','${aid}')
						});	
					}
				}
			}
			
			function goStep(index){
				$('#assignmentStepOl li').eq(index).addClass('yet');
				$('#assignmentStepOl li').eq(index).trigger('click');
			}
		</script>
	</@assignmentUserDirective>
</#macro>