<#macro sideFrameFtl workshopId workshop=workshopDirective(workshopId)>
<script type="text/javascript" src="${ctx }/common/js/jquery.dsTab.js"></script>
<script type="text/javascript" src="${ctx }/common/js/jquery.timers.js"></script>
<#import "/common/image.ftl" as image/>
<#global wsid=workshopId>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#import '/wsts/workshop/user/workshop_user_state.ftl' as s>
<@s.wuState workshopId=workshopId userId=(ThreadContext.getUser().getId())!/>
<#assign state = state!''>
	<div id="side_workshop_info_div" class="m-user-workshop">
		<div id="wpicker" class="img">
			<@image.imageFtl url="${(workshop.imageUrl)! }" default="${app_path }/images/defaultWorkshop.png" />
			<#if role='master' || role='member'>
				<a href="${ctx}/workshop/edit/${workshop.id}" class="u-edit"><i class="u-ico-edit1"></i></a>
				<!--<a href="javascript:;" class="u-file">修改封面</a>-->
			</#if>
		</div>
		<a href="${ctx}/workshop/${wsid}" class="tit">${(workshop.title)!}</a>
    	<@workshopUsersDirective workshopId=workshopId role='master' limit=1>
    		<#if workshopUsers??>
    			<#assign masterName=(workshopUsers?first.user.realName)!/>
    		</#if>
        </@workshopUsersDirective>
        <span class="txt">类型：
        	<#if ((workshop.type)!'') == 'personal'>
        		<i class="cor1">个人工作坊</i>
        	<#elseif ((workshop.type)!'') == 'train' && ((workshop.isTemplate)!'') == 'Y'>
        		<i class="cor2">示范性工作坊</i>
        	<#elseif ((workshop.type)!'') == 'train' && ((workshop.isTemplate)!) == 'N'>
        		<i class="cor3">培训工作坊</i>
        	</#if>
        </span>
        <span class="txt"><#if masterName??><ins>坊主：${(masterName)!}</ins></#if><span>创建于${TimeUtils.formatDate(workshop.createTime,'yyyy/MM/dd')}</span></span>
	    <div class="m-attend manage">
	    	<@workshopUsersDirective workshopId=workshopId role='member' limit=3>
	    		<#if workshopUsers??>
	    			<#list workshopUsers as wu>
	    				<@image.imageFtl url="${(wu.user.avatar)! }" default="${app_path}/images/defaultAvatarImg.png"  />
	    			</#list>
	    		</#if>
	        </@workshopUsersDirective>
	        <a style="color:#666666;cursor:default;text-decoration:none;" href="javascript:;" class="attend"><b>
				${(workshop.workshopRelation.memberNum)!}
	        </b>位成员</a>
	        <a href="${ctx}/workshopUser?workshopId=${workshopId}" class="mag">查看成员&gt;</a>
	    </div>
	    <a href="${ctx}/workshop/${workshopId}/detail" class="u-btn-theme">查看详情</a>
	    <#if role="master">
	    	<a href="${ctx}/workshop/manage/index?workshopId=${workshopId}" class="u-btn-com">管理员入口</a>
	    </#if>
	    <#if ((workshop.type)!'') == 'personal' && role="guest">
	    	<a onclick="applyJoin()" class="u-btn-com" href="javascript:;">加入工作坊</a>
	    </#if>
	</div>
	<div class="m-news" id="mNews">
		<div class="m-tit">
			<h3 class="tt"><i class="u-ico-news"></i>通知公告</h3>
			<a href="${ctx}/announcement?relationId=${workshopId}" class="more">更多&gt;</a>
		</div>
		<@announcementsDirective relationId=workshopId type="workshop_announcement" orders='CREATE_TIME.DESC' limit=5>
		<div class="m-con">
			<ul class="news-lst">
				<#if announcements??>
					<#list announcements as announcement>
						<li>
							<span class="u-date"><i class="u-ico-clock"></i>${TimeUtils.formatDate(announcement.createTime,'yyyy/MM/dd')}</span>
							<a href="${ctx}/announcement/${announcement.id}" class="txt">${RemoveHtmlTagUtils.removeHtmlTag((announcement.content)!'')}</a>
						</li>
					</#list>
				</#if>
			</ul>
		</div>
		<#if announcements?? && announcements?size &gt; 1>
			<div class="m-opa">
				<a href="javascript:;" class="next"></a>
				<a href="javascript:;" class="prev"></a>
			</div>
		</#if>
		</@announcementsDirective>
	</div>

<script>
    $(function(){
        $("#mNews").dsTab({
            itemEl        : '.news-lst li',
            prev          : '.prev',
            next          : '.next',
            maxSize       : 5,
            overStop      : true,
            changeType    : 'fade',
            changeTime    : 3000
        }); 
        
        //在线计时
        $('#side_workshop_info_div').everyTime('60s', function(){
        	$.put('/${role}_${wsid}/online/incOnlineTime');
        });
        
        //隐藏学员考核
        //$('.studentReview').hide();
    });
    
    function applyJoin(){
    	var state = '${state!""}';
    	if(state == 'apply'){
    		alert('您已申请加入,请等待坊主审核!');
    		return;
    	}else if(state == 'apply_quit'){
    		confirm('您正在申请退出,确定重新申请加入?',function(){
    			//发申请
    			sendRequest();
    		});
    	}else if(state == 'quited'){
    		confirm('您已退出该工作坊，确定重新申请加入?',function(){
    			//发申请
    			sendRequest();
    		});
    	}else if(state == 'nopass'){
    		confirm('坊主拒绝了您的申请，确定重新申请加入?',function(){
    			//发
    			sendRequest();
    		});
    	}else if(state == ''){
    		sendRequest();
    	}
    }
    
    function sendRequest(){
    	$.post('${ctx}/workshopUser',{
    		'workshopId':'${(workshop.id)!}',
    		'role':'member',
    		'user.id':'${ThreadContext.getUser().getId()}',
    		'state':'apply'
    	},function(response){
    		if(response.responseCode == '00'){
    			alert('申请成功，请等待坊主审核',function(){
    				window.location.reload();
    			});
    		}else if(response.responseCode == '01'){
    			alert('操作失败');
    		}
    	});
    }
    
</script>
</#macro>