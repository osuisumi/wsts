<#macro viewLcecFtl lcecId aid relationId>
<#include '/wsts/common/webuploader_js.ftl'/>
<#include '/wsts/common/flowplayer_js.ftl'/>
<@activityDirective id=aid>
<@coursewareDirective id=lcecId >
<#global inCurrentDate=TimeUtils.inCurrentDate((activity.timePeriod)!'', (workshop.timePeriod)!'')>
<div class="ag-activity-bd">
	<@lcecEvaluateDirective lcecId=lcecId getEvaluateSubmission='Y'>
		<#assign evaluateSubmission = evaluateSubmission! />
	 </@lcecEvaluateDirective>
	<#if hasStudentRole>
		<div class="g-study-prompt">
			<p>
				填写评课表完成活动
				<i>/</i>
				<#if ('submited' == (evaluateSubmission.state)!'')>
					您已填写
				<#else>
					您还未填写
				</#if>
			</p>
        	<i class="close">X</i>
        </div>
    
    </#if>
	<div class="ag-cMain">
		<div class="ag-main-hd g-xl-main">
			<div class="am-title">
				<h2><span class="aa-type-txt">【听课评课】</span><span class="txt">${(courseware.title)!}</span><!--<span class="u-state-type not">未开始</span>--></h2>
			</div>
			<div class="am-title-info f-cb ">
				<div class="c-infor">
					<#assign type = (courseware.type)!'' />
					<span class="txt">活动类型：
						<em>
							<#if type = 'onLine'>
								实录评课
							<#elseif type = 'offLine'>
								现场评课
							</#if>
						</em>
					</span>
					<span class="line">|</span>
					<span class="txt">年级学科：${TextBookUtils.getEntryName('STAGE',(courseware.stage)!)}${TextBookUtils.getEntryName('GRADE',(courseware.grade)!)}${TextBookUtils.getEntryName('SUBJECT',(courseware.subject)!)}</span>
					<#if courseware.textbook?? && courseware.textbook != ''>
						<span class="line">|</span>
						<span class="txt">选用教材：${(courseware.textbook)!}</span>
					</#if>
					<br/>
					<span class="txt">授课人：${(courseware.teacher.realName)!}</span>
					<!--
					<span class="line">|</span>
					<span class="txt">授课时间：2015-03-26</span>
					<span class="line">|</span>
					<span class="txt">评课时间：2015-04-07至2015-06-30</span>-->
				</div>
				<div class="am-mnTag-lst">
					<span class="au-tag-type type1"> <i class="au-bulb-ico"></i>活动 </span>
					<@tagsDirective relationId=aid>
						<#if tags??>
							<#list tags as tag>
								<span class="au-tt-type">${(tag.name)!}</span>
							</#list>
						</#if>
					</@tagsDirective>
				</div>
			</div>
			<div class="am-main-r ">
				<#assign timePeriods=[]>
				<#assign timePeriods = timePeriods + [(activity.timePeriod)!]>
				<#assign timePeriods = timePeriods + [(workshop.timePeriod)!]>
				<#import "/wsts/common/show_time.ftl" as st /> 
				<@st.showTimeFtl timePeriods=timePeriods label="活动" />
				<!--
				<div class="m-small-ewm">
					<img src="../images/small-erweima.png" alt="" />
					<span class="u-smk">扫码评课</span>
					<a class="u-btn-tb" href="${ctx}/${role!}_${wsid}/lcec/${lcecId}/evaluate">填写评课表</a>
					<a class="u-btn-tb" href="${ctx}/${role!}_${wsid}/lcec/${lcecId}/evaluateResult">查看评课结果</a>
					<div class="m-bid-ewm">
						<img src="../images/big-erweima.png" alt="" />
					</div>
				</div>
				-->
				<div class="am-opa1">
					<#if role != 'guest'>
						<#if ('submited' == (evaluateSubmission.state)!'')>
							<a href="${ctx}/${role!}_${wsid}/lcec/${lcecId}/evaluateResult?aid=${aid}" class="au-delete"> <i class="au-txt-ico"></i>查看评课结果 </a>
						<#else>
							<a href="${ctx}/${role!}_${wsid}/lcec/${lcecId}/evaluate?aid=${aid}" class="au-edit"> <i class="au-edit-ico"></i>填写评课表 </a>
						</#if>
					</#if>
				</div>
			</div>
			<#if type = 'onLine'>
				<div class="m-xl-video">
					<div id="player" style="height: 500px;"></div>
				</div>
			</#if>
			<div style="margin-top:150px" class="g-pk-con">
				<div class="m-pk-dir">
					<h3>评课方向</h3>
					<p>
						${(courseware.content)!}
					</p>
				</div>
				<div id="fileDiv" class="m-lesson-data">
					<h3>课程资料</h3>
					<ul class="fileList">
						<#if fileResources??>
							<#list fileResources as fr>
								<li frId=${(fr.id)!}>
									<i class="u-${FileTypeUtils.getFileTypeClass(fr.name,'suffix')}"></i>
									<div class="u-lesson-per">
										<a hef="#">${(fr.newestFile.fileName)!}</a>
										<span class="date">${TimeUtils.formatDate(fr.createTime,'yyyy/MM/dd HH:mm')}</span>
									</div>
									<div class="u-lesson-dl">
										<a onclick="downloadFile('${(fr.newestFile.id)!}','${(fr.newestFile.fileName)!}')" href="javascript:;">下载</a>
										<#if role='master' || role='member'>
											<span>|</span>
											<a href="javascript:;" class="delete">删除</a>
										</#if>
									</div>
								</li>
							</#list>
						</#if>
					</ul>
					<#if role='master' || role='member'>
						<div class="u-upload">
							<a id="picker" href="javascript:void(0);" class="au-uploadFile au-default-btn"> <i class="au-uploadFile-ico"></i>上传文件 </a>
						</div>
					</#if>
				</div>
			</div>

		</div>

		<div class="am-comment-box am-ipt-mod">
			<label> <span class="comment-placeholder"></span> 				<textarea id="commentContent" class="au-textarea"></textarea> </label>
			<div class="am-cmtBtn-block f-cb">
				<!--<a href="javascript:void(0);" class="au-face"></a>-->
				<a href="javascript:void(0);" onclick="saveComment($('#commentContent'),'${lcecId}')" class="au-cmtPublish-btn au-confirm-btn1">发表</a>
			</div>
		</div><!--end .am-comment-box 评论框-->
	</div><!--end .ag-cMain -->
	<div class="ag-comment-layout">
		<div id="commentsDiv">
			<script>
				$(function(){
					loadComments('${lcecId}');
				});
			</script>			
		</div>
	</div>
</div>

<li id="fileLiTemplet" class="fileLi" style="display: none;">
    <i class="ico"></i>
    <a class="fileName name">二元二次方程教学方案.doc</a>
    <div class="fileBar m-pbar">
        <div class="bar"><div class="barLength yet" style="width: 50%;"><span class="barNum">50%</span></div></div>
        <span class="barTxt bar-txt">上传中....</span>
    </div>
</li>

<script>
	$(function(){
		var uploader = WebUploader.create({
        		swf : $('#ctx').val() + '/js/webuploader/Uploader.swf',
        		server : '${ctx}/file/uploadRemote?relationId=${lcecId}',
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
        		var fileNumLimit = 5;
        		if(fileNumLimit != 0){
        			var fileNum = $('.fileList').find(".fileLi").length;
            		if(fileNum > fileNumLimit){
            			alert('只允许上传'+fileNumLimit+'个附件');
            			uploader.removeFile(file.id);
            			return false;
            		}
        		}
        		var $li = $('#fileLiTemplet').clone();
        		$li.attr('id', file.id).addClass('fileItem').show();
        		$li.find('.fileName').text(file.name);
        		$(".fileList").append($li);
        		uploader.upload();
        	});
        	// 文件上传过程中创建进度条实时显示。
        	uploader.on('uploadProgress', function(file, percentage) {
        		var $li = $('#' + file.id), $bar = $li.find('.fileBar');
        		// 避免重复创建
        		/* if (!$percent.length) {
        			$li.find('.state').html('<div class="sche">' + '<div class="bl">' + '<div class="bs" role="progressbar" style="width: 0%"></div>' + '</div>' + '<span class="num">' + '0%' + '</span>' + '<span class="status"></span>' + '</div>');
        			$percent = $li.find('.sche');
        		} */
        		var progress = Math.round(percentage * 100);
        		$bar.find('.barLength').css('width', progress + '%');
        		$bar.find('.barNum').text(progress + '%');
        		$bar.find('.barTxt').text('上传中');
        	});
        	uploader.on('uploadSuccess', function(file, response) {
        		if (response != null && response.responseCode == '00') {
        			$('#' + file.id).find('.fileBar').addClass('finish');
        			$('#' + file.id).find('.barTxt').text('已上传');
        			var fileInfo = response.responseData;
        			$('#' + file.id).attr('fileId', fileInfo.id);
        			$('#' + file.id).attr('url', fileInfo.url);
        			$('#' + file.id).attr('fileName', fileInfo.fileName);
        			$('#' + file.id).addClass('success');
        			
        		}
        	});
        	uploader.on('uploadError', function(file) {
        		$('#' + file.id).find('.fileBar').addClass('error');
        		$('#' + file.id).find('.barTxt').text('上传出错');
        	});
        	uploader.on('uploadComplete', function(file) {
        		$('#' + file.id).find('.progress').fadeOut();
        	});
//        	$('#uploadBtn').click(function() {
//        		uploader.upload();
//        	});
        	$(".fileList").on('click', '.dlt', function() {
        		var _this = $(this);
        		confirm('是否确定删除该附件?',function(){
        			if ($(this).parents('.fileLi').hasClass('fileItem')) {
        				uploader.removeFile($(this).parents('.fileLi').attr('id'));
        			}
        			_this.parents('.fileLi').remove();
        			
        		});
        	});
        	uploader.on('error', function(type) {
        		if (type == 'Q_TYPE_DENIED') {
        			alert('请检查上传的文件类型');
        		}else if(type == 'Q_EXCEED_SIZE_LIMIT'){
        			alert('文件大小超出限制');
        		}
        	}); 
        	
        	//删除文件
			$('.delete').on('click',function(){
				var _this = this;
				var id = $(this).closest('li').attr('frId');
				confirm('确定要删除?', function(){
					$.ajaxDelete('${ctx!}/${role}_${wsid}/fileResource?id='+id,'' , function(data){
						if(data.responseCode == '00'){
							alert('删除成功');
							$(_this).closest('li').remove();
						}	
					});
				});
			});
		
	});

	$(function(){
		var type = '${type}';
		if(type == 'onLine'){
				var container = document.getElementById("player");
				var url = "${FileUtils.getFileUrl("")}${(courseware.video.url)!}";
				var qualities = null;
					api =  flowplayer(container, {
						qualities: qualities,
					    defaultQuality: "标清",
						autoplay: true,		
					    clip: {
					        sources: [
								{
									type: "video/flash",
									src:  url
								},
					        	{ 
					            	type: "video/webm",
					                src:  url 
					            },
					            {
					            	type: "video/mp4",
					                src:  url 
					            }
					        ]
					    }
					});
			
		};
	});
	
</script>

</@coursewareDirective>
</@activityDirective>
</#macro>