<#macro viewLessonPlanFtl lessonPlanId aid relationId>
<@activityDirective id=aid>
<#include '/wsts/common/webuploader_js.ftl'/>
<#include '/wsts/common/validate_js.ftl'/>
<#global inCurrentDate=TimeUtils.inCurrentDate((activity.timePeriod)!'', (workshop.timePeriod)!'')>
<@lessonPlanDirective id=lessonPlanId>
<div class="ag-activity-bd">
	<input type="hidden" id="lessonPlanId" value="${lessonPlanId}">
	<input type="hidden" id="fileRelationRelationId" value="${lessonPlan.lessonPlanRelations[0].id}">
	<input type="hidden" id="fileRelationType" value="lesson_plan_relation">
	<div class="ag-cMain">
		<#if hasStudentRole>
			<@activityResultDirective activityId=aid relationId=(lessonPlan.lessonPlanRelations[0].relation.id)!>
			<div class="g-study-prompt">
				<p>
					<#assign upload_num=(activity.attributeMap['upload_num'].attrValue)!'0'>
						要求上传 <span>${(upload_num)!1}</span>个文件
						您已上传 <span>${(activityResult.detailMap['upload_num'])!0 }</span>个文件
				</p>
	        	<i class="close">X</i>
	        </div>
	        </@activityResultDirective>
		</#if>
		<div class="ag-main-hd">
			<div class="am-title">
				<h2><span class="aa-type-txt">【备课】</span><span class="txt">${(lessonPlan.title)!}</span><!--<span class="au-it-type must">必修</span>--></h2>
			</div>
			<div class="am-title-info f-cb">
				<div class="c-infor">
					<span class="txt">发起人：${(lessonPlan.creator.realName)!}</span>
					<!--
					<span class="line">|</span>
					<span class="txt">参与人数：${(lessonPlan.lessonPlanRelations[0].participateNum)!}</span>
					<span class="line">|</span>
					<span class="txt">被阅读数：${(lessonPlan.lessonPlanRelations[0].browseNum)!}</span>
					-->
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
			<div class="am-main-r">
				<#assign timePeriods=[]>
				<#assign timePeriods = timePeriods + [(activity.timePeriod)!]>
				<#assign timePeriods = timePeriods + [(workshop.timePeriod)!]>
				<#import "/wsts/common/show_time.ftl" as st /> 
				<@st.showTimeFtl timePeriods=timePeriods label="活动" /> 
				<div class="am-opa1">
					<a href="javascript:void(0);" class="au-top editRecord"> <i class="au-record-ico"></i>活动记录 </a>
				</div>
			</div>
			<div class="ag-detail-txt ag-detail-txt1">
				${(lessonPlan.content)!}
				<@lessonPlanRecordsDirective lessonPlanId=lessonPlanId relationId=relationId>
					<#if lessonPlanRecords?? && lessonPlanRecords?size &gt; 0>
						<div class="am-record-log">
							<ul class="am-txt-lst">
								<#if lessonPlanRecords??>
									<#list lessonPlanRecords as lp>
										<li>
											<i class="au-book-ico"></i>
											<a href="javascript:void(0);" class="tt recordTitle" title="${(lp.title)!}">${(lp.title)!}<span class="recordContent" style="display:none">${(lp.content)!}</span></a>
											<span class="time">发表于<em>${TimeUtils.formatDate(lp.createTime,'yyyy-MM-dd')}</em></span>
											<span class="user">记录人：<em>${(lp.creator.realName)!}</em></span>
											<#if lp.creator.id = Session.loginer.id>
												<a style="float:right" onclick="deleteLessonPlanRecord('${lp.id}',this)" class="au-delete" href="javascript:void(0);"> <i class="au-delete-ico"></i>删除 </a>
											</#if>
										</li>
									</#list>
								</#if>
							</ul>
							<a href="javascript:;" class="m-more">
                                <span class="down-more">展开更多+</span>
                                <span class="up-more">收起&nbsp;-</span>
                            </a>
						</div>
					</#if>
				</@lessonPlanRecordsDirective>
			</div>
		</div><!--end .ag-main-hd -->
		<div id="lessonPlanFileResourceDiv" class="ag-file-bd" id="activityLessonFile">
			<script>
				$(function(){
					listFileResource('');
				});
			</script>
		</div>
		<div class="am-comment-box am-ipt-mod">
			<label> <span class="comment-placeholder"></span> 	
				<textarea id="commentContent" class="au-textarea"></textarea>
			</label>
			<div class="am-cmtBtn-block f-cb">
				<!--<a href="javascript:void(0);" class="au-face"></a>-->
				<a href="javascript:void(0);" class="au-cmtPublish-btn au-confirm-btn1" onclick="saveComment($('#commentContent'),'${lessonPlanId}')">发表</a>
			</div>
		</div><!--end .am-comment-box 评论框-->
	</div><!--end .ag-cMain -->
	<div id="commentsDiv">
		<script>
			$(function(){
				loadComments('${lessonPlanId}');
			});
		</script>			
	</div>
</div>

<div style="display:none">	 
<li id="model">
	<i class="au-book-ico"></i>
	<a href="javascript:void(0);" class="recordTitle"></a>
	<span class="time">发表于<em>片刻之前</em></span>
	<span class="user">记录人：<em>${(Session.loginer.realName)!}</em></span>
</li>
<div id="recordLayer" class="ag-layer-layout">
    <div class="ag-publish-page">
		<!--<h3 class="title">活动记录标题</h3>-->
		<div>
			<h3 class="title" id="recordLayerTitle"></h3>
		</div>
		<div id="recordLayerContent">
        	
        </div>
    </div>
</div><!--end .ag-layer-layout -->  
</div>

<script>
	//为子页面创建文件导航条
	var frbar = new FileResourceNAV();
	$(function(){
		$('.editRecord').on('click',function(){
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '发布活动记录',
				content : '${ctx}/${role}_${wsid}/lessonPlan/editRecord?lessonPlanId=${lessonPlanId}',
				area : [880, 580],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});
		
		$('.recordTitle').on('click',function(){
			var content = $(this).find('.recordContent').text();
			var title = $(this).attr('title');
			$('#recordLayerTitle').text(title);
			$('#recordLayerContent').text(content);
			mylayerFn.open({
				id : '999',
				type : 1,
				title : '记录详情',
				content : $('#recordLayer').html(),
				area : [500, 500],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});
		more_discrible(".am-record-log .m-more",'.am-record-log .am-txt-lst');//展示更多
	});
	
	//局部刷新资源列表
	function listFileResource(parentId){
		$('#lessonPlanFileResourceDiv').load('${ctx}/${role}_${wsid}/activity/file_resource','type=lesson_plan_relation&relationId=${lessonPlan.lessonPlanRelations[0].id}&parentId='+parentId);
	}
	
	function deleteLessonPlanRecord(lrId,a){
		$.post('${ctx}/${role}_${wsid}/lessonPlan/deleteLessonPlanRecord/'+lrId,{
			'_method':'DELETE'
		},function(response){
			if(response.responseCode=='00'){
				alert('操作成功',function(){
					$(a).closest('li').remove();
				});
			}else{
				alert('操作失败');
			}
		});
	}
	
	function more_discrible(labelclick,udown){
        var updown = true;
        var height_txt = $(udown).height();
        if(height_txt<=90){
            $(udown).siblings('.m-more').css({"display":"none"});
        }
        $(udown).addClass('height-self');

        $(labelclick).on("click",function(){
            if(updown==true){
                $(this).siblings(udown).addClass('Heightauto');
                $(this).find(".up-more").addClass('canfind').siblings('.down-more').addClass('notfind');
                updown=false;
            }else if(updown==false){
                $(this).siblings(udown).removeClass('Heightauto');
                $(this).find(".up-more").removeClass('canfind').siblings('.down-more').removeClass('notfind');
                updown=true;
                
            }

        });      
    }
</script>
</@lessonPlanDirective>
</@activityDirective>
</#macro>
