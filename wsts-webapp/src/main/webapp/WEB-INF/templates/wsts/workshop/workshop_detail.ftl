<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#assign  jsArray=['ueditor']/>
<#global wsid=id>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<@layout jsArray=jsArray>
<@workshopDirective id=id getResourceNum="Y" getQuestionNum="Y" getStudentNum="Y" getActivityNum="Y" getMemberNum="Y" getTrainName='Y'>
<#if ((workshop.type)!'') == 'train'>
	<#if ((workshop.isTemplate)!'') == 'Y'>
		<#assign wtype = 'template'>
	<#else>
		<#assign wtype = 'train'>
	</#if>
<#else>
	<#assign wtype = 'personal'>
</#if>
<div class="g-auto">
	<div class="g-frame-sd">
		<#import "side_frame.ftl" as sideFrame/>
		<@sideFrame.sideFrameFtl workshopId=id workshop=workshop />
	</div>
	<div class="g-frame-mn">
		<#import "/wsts/include/banner.ftl" as banner/>
		<@banner.content current="工作坊详情">
			<ins>&gt;</ins>
			<span><a href="${ctx}/workshop/${id}">${workshop.title}</a></span>
		</@banner.content>
		<div class="g-WSdet-cont">
			<div class="g-WSdet-list g-WSdet-desc">
				<div class="m-WSdet-tl">
					<h2 class="who">${workshop.title}</h2>
					<#if role = 'master'>
						<span onclick="javascript:window.location.href='${ctx}/workshop/edit/${workshop.id}';" fieldName="summary" class="button m-edit"><i class="u-edit"></i>编辑</span>
					<#elseif ((workshop.type)!'') == 'personal' && role != 'guest'>
						<a onclick="apply_quit()" href="javascript:;" class="u-outback-button">退出工作坊</a>
					</#if>
				</div>
				<ul class="who-workshop-dl">
					<li>
						<i class="u-build-who"></i>
						<strong>创建人</strong>：${(workshop.creator.realName)!}
					</li>
					<li>
						<i class="u-build-time"></i>
						<strong>创建时间</strong>：${TimeUtils.formatDate(workshop.createTime,'yyyy/MM/dd')}
					</li>
					<#if wtype = 'train'>
						<li>
							<i class="u-build-project"></i>
							<strong>所属项目</strong>：${(workshop.trainName)!}
						</li>
					</#if>
					<#import  "workshop_time.ftl" as wt />
					<@wt.workshopTime workshop=workshop></@wt.workshopTime>
					<#if workshopStartTime?? && workshopEndTime??>
					<li>
						<i class="u-build-time"></i>
						<strong>开始时间</strong>：${(workshopStartTime?string('yyyy/MM/dd'))!}
					</li>
					<li>
						<i class="u-build-time"></i>
						<strong>结束时间</strong>：${(workshopEndTime?string('yyyy/MM/dd'))!}
					</li>
					</#if>
					<li class="tip">${TextBookUtils.getEntryName('STAGE',workshop.stage)}</li>
					<li class="tip">${TextBookUtils.getEntryName('SUBJECT',workshop.subject)}</li>
				</ul>
				<#if workshop.summary??>
					<div class="who-workshop-describeBox">
						${(workshop.summary)!}
						<a href="javascript:;" class="m-more"><span class="down-more">展开更多+</span><span class="up-more">收起&nbsp;-</span></a>
					</div>
				<#else>
                	<#import "/common/noContent.ftl" as  nc>
            		<@nc.noContentFtl msg='暂时没有简介' />
				</#if>
				<@filesDirective relationId=id type="workshop_solution">
					<#if fileInfos?? && fileInfos?size &gt; 0>
						<#list fileInfos as fi>
							<div class="m-WSdet-resource m-WSdet-tl">
			                    <div class="left">
			                        <div class="${FileTypeUtils.getFileTypeClass((fi.fileName)!, 'study')!''}">
			                        	<a href="javascript:;"></a>
			                        </div>
			                        <div class="txt">
			                            <p>${(fi.fileName)!}</p>
			                            <span>上传于 ${TimeUtils.formatDate(fi.createTime,'yyyy/MM/dd')}</span>
			                        </div>
			                    </div>
			                    <span onclick="previewFile('${fi.id}')" class="button m-see"><i class="u-see"></i>查看</span>
			                    <span onclick="downloadFile('${(fi.id)!}','${(fi.fileName)!}')" class="button m-load"><i class="u-load"></i>下载</span>
			                </div>
		                </#list>
	               	</#if>
	             </@filesDirective>
			</div>
			<#if wtype = 'train'>
				<@workshopUsersDirective role='student' workshopId=id workshopResult="excellent" minPoint="${(workshop.qualifiedPoint)!}">
				<div class="g-WS-reslut-list g-WSdet-list">
					<div class="g-WS-reslut-tl">
						<h2><i class="u-student-icon"></i>优秀学员</h2>
						<#if role="master" || role="member">
							<a href="${ctx}/workshop/${id}?tab=workshopUserResult" class="button"><i class="u-manage"></i>管理</a>
						</#if>
					</div>
					<#if workshopUsers?? && workshopUsers?size &gt; 0>
					<ul <#if workshopUsers?size &lt; 3>style="height:100px"</#if> class="m-WS-student-list">
						<#list workshopUsers as workshopUser>
							<li>
								<a href="javascript:;">
									<@image.imageFtl url="${(workshopUser.user.avatar)! }" default="${app_path}/images/defaultAvatarImg.png" />
								</a>
								<div class="m-WS-st-txt">
									<div class="name">
										<a href="javascript:;">${(workshopUser.user.realName)!}</a><i></i>
									</div>
									<p class="school">
										${(workshopUser.userInfo.deptName)!}
									</p>
								</div>
							</li>
						</#list>
					</ul>
					<a href="javascript:;" class="m-more"><span class="down-more">展开更多+</span><span class="up-more">收起&nbsp;-</span></a>
					<#else>
						<#import "/common/noContent.ftl" as  nc>
						<@nc.noContentFtl msg='暂时没有优秀学员' />
					</#if>
				</div>
				</@workshopUsersDirective>
			</#if>
			<div class="g-WSdet-list">
				<h2><i class="u-brisk-icon"></i>团队活跃度</h2>
				<ul class="m-WSdet-brisk">
					<#if wtype='train'>
						<li>
							<strong>${(workshop.workshopRelation.studentNum)!}</strong>
							<p>
								参研学员
							</p>
						</li>
					</#if>
					<li>
						<strong>${(workshop.workshopRelation.activityNum)!}</strong>
						<p>
							研修任务
						</p>
					</li>
					<#if wtype = 'train'>
						<li>
							<strong>${(workshop.workshopRelation.questionNum)!0}</strong>
							<p>
								学员提问
							</p>
						</li>
					</#if>
					<!--
					<li>
						<strong>22</strong>
						<p>
							回答问题
						</p>
					</li>
					-->
					<!--
					<li>
						<strong>26</strong>
						<p>
							发贴评论
						</p>
					</li>
					-->
					</li>
					<li>
						<strong>${(workshop.workshopRelation.resourceNum)!}</strong>
						<p>
							学习资源
						</p>
					</li>
				</ul>
			</div>
			<#if wtype = 'train'>
				<div class="g-WSdet-list">
					<div class="g-WS-reslut-tl m-WSdet-tl">
						<h2><i class="u-report-icon"></i>学员须知</h2>
						<#if role="master" || role="member">
						<span onclick="editAttribute('summaryNotice')" fieldName="summaryNotice" class="button m-edit"><i class="u-edit"></i>编辑</span>
						</#if>
					</div>
					<p class="m-WS-know-dl">
					<#if workshop.summaryNotice??>
						${(workshop.summaryNotice)!}
					<#else>
						工作坊研修是在坊主及管理成员引领下根据各区各校研修需求开展教学研修活动，参研学员需在限定的阶段时间内积极参与研修活动并完成研修任务获得相应积分，每期研修学员必须获取300积分以上为合格，优秀学员由坊主和管理员根据参与质量评选。
					</#if>
					</p>
					<dl class="m-WS-know-cont">
						<dt class="g-WS-reslut-tl m-WSdet-tl">
							<h2><i class="u-knowAim-icon"></i>工作坊教研目标</h2>
							<#if role="master" || role="member">
							<span onclick="editAttribute('summaryTarget')" fieldName="summaryTarget" class="button m-edit"><i class="u-edit"></i>编辑</span>
							</#if>
						</dt>
						<#if workshop.summaryTarget??>
							${(workshop.summaryTarget)!}
						<#else>
							<dd>
								1.依据学校内涵发展的需要，我们将进一步深入和完善教师工作坊建设和校本教研工作，努力打造一支理论水平高、教学业务精、科研能力强的教师工作坊优秀团队。
							</dd>
							<dd>
								2.发挥教师工作坊团队的示范引领作用，立足教研，聚焦课堂，转变教师观念，提升教师执教水平，提高教学质量，切实解决或研究教育教学改革中出现的新问题和新情况。
							</dd>
							<dd>
								3.发挥教师工作坊团队成员的传帮带作用，将培养中青年教师作为教师工作坊的主要工作之一，努力使我校形成一支师德高尚、业务精良、充满活力的反思型、科研型教师队伍，全面提高教学质量，促进学生全面、持续、和谐地发展。
							</dd>
						</#if>
					</dl>
					<dl class="m-WS-know-cont m-WS-know-cont2">
						<dt class="g-WS-reslut-tl m-WSdet-tl">
							<h2><i class="u-knowAim-icon u-knowAim-icon2"></i>学员考核说明</h2>
							<#if role="master" || role="member">
							<span onclick="editAttribute('summaryExamine')"  fieldName="summaryExamine" class="button m-edit"><i class="u-edit"></i>编辑</span>
							</#if>
						</dt>
						<#if workshop.summaryExamine??>
							${(workshop.summaryExamine)!}
						<#else>
							<dd>
								考核分为研修任务参与积分、管理员质量评分两部分；每期研修学员必须获取${(workshop.qualifiedPoint)!0}积分以上为合格，研修积分不设上线，优秀学员在本坊按积分排行前50%的学员成为备选对象，坊主和管理成员在备选对象中根据学员参与研修的质量确定优秀学员。
							</dd>
							<dd>
								研修学员考核积分来源于为研修任务、互助答疑。
							</dd>
							<dd>
								[研修任务] —— 参与并完成工作坊设置的课程学习、听课评课、教学研讨、教学观摩、辨论、微课作业、调查问卷、在线测试、线下讲座等活动1次，可获得+3以上积分，活动互动交流中发贴的质量和数量是评优的参考项；
							</dd>
							<dd>
								[互助答疑] —— 学员提问和回答可获得+1积分，由坊主和管理员推荐为最佳答案可获得额外加分。
							</dd>
						</#if>
					</dl>
				</div>
			</#if>
		</div>
	</div>
</div>

<script>
	$(function(){
        //编辑弹出框
        /*$(".m-WSdet-tl .button.m-edit").on('click',function(){
        	var fieldName = $(this).attr('fieldName');
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '编辑信息',
				content : '${ctx}/workshop/${id}/'+fieldName+'/editSummary',
				area : [700, 500],
				offset : [$(document).scrollTop()],
				fix : false,
				shadeClose : false,
			});
        });*/

        more_detail(".g-WSdet-list.g-WSdet-desc .m-more",'.who-workshop-describe');//展示更多
        more_detail(".g-WS-reslut-list.g-WSdet-list .m-more",'.m-WS-student-list');//展示更多
	});
	
	function editAttribute(fieldName){
		mylayerFn.open({
			id : '999',
			type : 2,
			title : '编辑信息',
			content : '${ctx}/workshop/${id}/'+fieldName+'/editSummary',
			area : [700, 500],
			offset : [$(document).scrollTop()],
			fix : false,
			shadeClose : false,
		});
	}

    function more_detail(labelclick,udown){ //展示更多
        var updown = true;
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
    
    function apply_quit(){
    	confirm('确定退出该工作坊吗?',function(){
	    	$.post('${ctx}/workshopUser',{
	    		'workshopId':'${(workshop.id)!}',
	    		'role':'member',
	    		'user.id':'${ThreadContext.getUser().getId()}',
	    		'state':'apply_quit'
	    	},function(response){
	    		if(response.responseCode == '00'){
	    			alert('申请成功，请等待坊主审核',function(){
	    				window.location.reload();
	    			});
	    		}else if(response.responseCode == '01'){
	    			alert('操作失败');
	    		}
	    	});
    	});
    }
</script>
</@workshopDirective>
</@layout>

