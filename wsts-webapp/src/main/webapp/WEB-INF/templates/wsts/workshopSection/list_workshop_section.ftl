<#global wsid=workshopId>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r>
<@r.content />
<#import "model/model_edit_workshopSection.ftl" as editModel/>
<input type="hidden" id="hasView" value="${(SecurityUtils.getSubject().getSession().getAttribute(workshopId))!''}">
<@workshopSectionsDirective workshopId=workshopId getResult=true>
	<#if role='master' || role = 'member'>
		<div class="add-button add-new-stage">+添加新阶段</div>
	</#if>
	<ul class="g-train-lst">
		<#if workshopSections??>
			<#assign highLightSection=false>
			<#list workshopSections as workshopSection>
				<li class="sectionLi" sectionId="${workshopSection.id }">
					<dl class="m-guid-lst m-WSdet-tl">
						<dt
							<#if workshopSection.timePeriod??>
								<#if !highLightSection && (TimeUtils.hasBegun(workshopSection.timePeriod.startTime) && !TimeUtils.hasEnded(workshopSection.timePeriod.endTime))>
									class="z-crt"
									<#assign highLightSection=true>
								</#if>
							</#if>
						>
							<div class="dt-tl">
								<span class="inner-tl">${(workshopSection.title)!}</span>
								<span class="u-date"><span class="start-Y">${(workshopSection.timePeriod.startTime)?string('yyyy')}</span>年<span class="start-M">${(workshopSection.timePeriod.startTime)?string('MM')}</span>月～<span class="end-Y">${(workshopSection.timePeriod.endTime)?string('yyyy')}</span>年<span class="end-M">${(workshopSection.timePeriod.endTime)?string('MM')}</span>月</span>
								<#if role='master' || (((workshopSection.creator.id)!'') == ((Session.loginer.id)!''))>
									<span class="button button-del"><i class="u-delete"></i>删除</span>
									<span class="button button-edit"><i class="u-edit"></i>编辑</span>
								</#if>
							</div>
							<@editModel.content />
							<span class="u-order"><b>00</b>阶段</span>
							<i class="u-ico ico3"></i>
						</dt>
						<#list workshopSection.activities as activity>
							<dd activityId="${activity.id }" class="activityDd 
								<#if activity.timePeriod??>
									<#if !(activity.timePeriod.startTime)??>
										<#assign ahasBegin = true/>
									<#else>
										<#assign ahasBegin = TimeUtils.hasBegun(activity.timePeriod.startTime)/>
									</#if>
									<#if !(activity.timePeriod.endTime)??>
										<#assign ahasEnd = false/>
									<#else>
										<#assign ahasEnd = TimeUtils.hasEnded(activity.timePeriod.endTime)/>
									</#if>
									<#if (ahasBegin&& !ahasEnd)>
										inTime
									</#if>
									<#if !ahasBegin>
										notBegun
									</#if>
								</#if>
								<#if (activity_index == (workshopSection.activities?size - 1))>
									last
								</#if>
							">
							
							<#if TimeUtils.hasBegun(workshopSection.timePeriod.startTime) || role='master' || role = 'member' >
								<#assign sectionTimeFilter = true />
							<#else>
								<#assign sectionTimeFilter = false />
							</#if>
								<#if activity.type == 'discussion'>
									<i class="u-ico u-ico-guid-discuss"></i>
									<#if sectionTimeFilter >
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【教学研讨】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【教学研讨】</b>
									</#if>
								<#elseif activity.type == 'survey'>
									<i class="u-ico u-ico-guid-QueNaire"></i>
									<#if sectionTimeFilter>
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【问卷调查】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【问卷调查】</b>
									</#if>
								<#elseif activity.type == 'lesson_plan'>
									<i class="u-ico u-ico-guid-preLesson"></i>
									<#if sectionTimeFilter>
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【集体备课】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【集体备课】</b>
									</#if>
								<#elseif activity.type == 'test'>
									<i class="u-ico u-ico-guid-comment"></i>
									<#if sectionTimeFilter>
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【在线测试】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【在线测试】</b>
									</#if>
								<#elseif activity.type == 'video'>
									<i class="u-ico u-ico-guid-study"></i>
									<#if sectionTimeFilter>
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【教学观摩】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【教学观摩】</b>
									</#if>
								<#elseif activity.type == 'debate'>
									<i class="u-ico u-ico-guid-argue"></i>
									<#if sectionTimeFilter>
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【辩论活动】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【辩论活动】</b>
									</#if>
								<#elseif activity.type == 'lcec'>
									<i class="u-ico u-ico-guid-test"></i>
									<#if sectionTimeFilter>
										<a onclick="viewActivity('${activity.id}')" href="javascript:;"><b>【听课评课】</b>
									<#else>
										<a onclick="alert('该阶段尚未开始')" href="javascript:;"><b>【听课评课】</b>
									</#if>
								</#if>
                                	${activity.title! }</a>
                                	<#if hasMasterRole || (activity.creator.id == ((Session.loginer.id)!''))>
                                		<div onclick="editActivity('${activity.id}', '${activity.type }')" class="min-edit"><i></i>编辑</div>
                                		<div onclick="deleteActivity('${activity.id}')" class="min-edit min-del"><i></i>删除</div>                
                                	</#if>
                                	<#if 'complete' == (activityResultMap[activity.id].state)!''>
                                		<i class="u-ico-foot"></i>
                                	</#if>
                               </dd>
						</#list>
					</dl>
					<#if role='master' || role='member'>
						<div class="Add-new">
							<a href="javascript:;" class="add-new-train">+ 添加新任务</a>
						</div>
					</#if>
				</li>
			</#list>
		</#if>
	</ul>
	<#--
	<div class="u-ready-mark u-manage-ready-mark prepareAct">
		训前准备
	</div>
	-->
<#import "model/model_section_clone.ftl" as cloneModel/>
<@cloneModel.content />
</@workshopSectionsDirective>

<@workshopUsersDirective workshopId=wsid role='master' limit=1 page=1>
	<#list workshopUsers as wu>
		<#assign masterName=wu.user.realName>
		<#if '' != (wu.user.avatar)!''>
			<#assign masterAvatar=FileUtils.getFileUrl(wu.user.avatar)>
		<#else>	
			<#assign masterAvatar='${app_path}/images/defaultAvatarImg.png'>
		</#if>
	</#list>
</@workshopUsersDirective>
<div class="blackBg g-workshop-alert" style="display: none;"></div>  
<script>
    $(function(){
        if('${role}' == 'student'){
        	var hasView = $('#hasView').val();
        	if(hasView == 'first'){
        		getStudyProgess();
        	}
        }
        
    	add_new_Array();//添加新阶段
        button_edit(".button-edit");
        array_num();//编排阶段的顺序
        del_stage();//删除阶段
        starting_alert();
        u_ready_mark();
        showActiveBoxFn();
        
        var workshopType = $('#workshopType').val();
        if(workshopType !='train'){
        	$('.prepareAct').hide();
        }
    });
    
    function getStudyProgess(){
    	var $inTimeAct = $('.sectionLi .activityDd.inTime');
    	$inTimeAct.each(function(){
    		if($(this).find('.u-ico-foot').length == 0){
    			var activityId = $(this).attr('activityId');
    			$('#continueStudyBtn').click(function(){
    				viewActivity(activityId);
    			});
    			return false;
    		}
    	});    
    	var $notBegunAct = $('.sectionLi .activityDd.notBegun');
    	if($notBegunAct.length > 0){
    		$notBegunAct.eq(0).addClass('m-at-starting').append('<span class="u-start"><i class="u-ico-clock1"></i>即将开始</span>'+
                    '<div class="m-starting-alert">'+
                    '<p class="alert-head">'+
                    '<span class="head-img">'+
                    '<img src="${masterAvatar!}" alt="" />'+
                    '<span class="name">${masterName!}<ins class="status">坊主<ins></span>'+
                    '</span>'+
                    '<span class="icon-suona"></span>'+
                    '</p>'+
                    '<p class="alert-cont">'+
                    '<span>'+$notBegunAct.eq(0).find('a').text()+'</span>'+
                    '<ins class="know">我知道了</ins>'+
                    '</p>'+
                    '<i class="arrow"></i>'+
                    '</div>');
        	$('.blackBg').show();
        	screen_see();
    	}
    }
    
    /*-- add active method 显示添加活动框 --*/
	function showActiveBoxFn() {
	
		// 是否在打开状态
		var ifShow = false;
		//添加活动html
		var addActiveHtml = '<div class="m-addactive-box" id="addActiveBox">' + 
								'<input type="hidden" id="activityRelationId">'+
								'<i class="trg"></i>' + 
								'<div class="m-addActive-lst">' + 
									'<a onclick="addActivity(\'video\')" href="javascript:void(0);" class="addActiveModule-video"><span></span>教学观摩</a>' + 
									'<a onclick="addActivity(\'lcec\')" href="javascript:void(0);" class="addActiveModule-HTML"><span></span>听课评课</a>' + 
									'<a onclick="addActivity(\'discussion\')" href="javascript:void(0);" class="addActiveModule-discuss"><span></span>教学研讨</a>' + 
									'<a onclick="addActivity(\'lesson_plan\')" href="javascript:void(0);" class="addActiveModule-lessons"><span></span>集体备课</a>' + 
									'<a onclick="addActivity(\'survey\')" href="javascript:void(0);" class="addActiveModule-survey"><span></span>问卷调查</a>' + 
									'<a onclick="addActivity(\'test\')" href="javascript:void(0);" class="addActiveModule-test"><span></span>在线测试</a>' + 
									'<a onclick="addActivity(\'debate\')" href="javascript:void(0);" class="addActiveModule-argue"><span></span>辩论活动</a>' + 
								'</div>' + 
							'</div>'
	
		//点击按钮弹出
		$('.g-train-lst').on('click', '.add-new-train', function(e) {
			//获取点击的按钮
			var $this = $(this);
			//给html添加类名，用于绑定事件
			$this.parents('html').addClass('addSectionHtml');
			e.stopPropagation();
			//判断是否在打开状态
			if ($this.hasClass('z-crt')) {
				closeAddActiveBox($this);
				//修改打开状态
				ifShow = false;
			} else {
				//修改打开状态
				ifShow = true;
				//清除其他小节状态
				$('.add-new-train').removeClass('z-crt').parent().removeClass('z-crt').nextAll(".m-addactive-box").remove();
				//增加正在添加状态
				$this.addClass('z-crt').parent().addClass('z-crt').after(addActiveHtml);
				//改变添加活动框的位置
				/*$addActiveBox.show().css({
				 'top': $this.offset().top + 40 + 'px',
				 'left': $this.offset().left -564 + 'px'
				 });*/
				
				//修改活动的父ID
				$('#activityRelationId').val($this.parents('li').attr('sectionId'));
			}
			// addActiveFn();
			//获取活动弹出框
			var $addActiveBox = $('#addActiveBox');
			//判断点击其他地方关闭
			$('.addSectionHtml').off().on("click", function(event) {
				//兼容firefox和IE对象属性
				e = event || window.event;
				var target = $(e.target) || $(e.srcElement);
				event.stopPropagation();
				if (ifShow) {
					if (target.closest($addActiveBox).length == 0) {
						//执行关闭方法
						closeAddActiveBox($this);
					}
				}
			});
			//添加活动时关闭弹出框
			//$addActiveBox.on('click','a',function(){
			//执行关闭方法
			//closeAddActiveBox($this);
			//});
	
		});
		//关闭添加活动弹出框
		function closeAddActiveBox($addActiveBtn) {
			//清除正在添加的状态
			$addActiveBtn.removeClass('z-crt').parent().removeClass('z-crt').nextAll(".m-addactive-box").remove();
			//还原位置
			//$addActiveBox.hide().css({top: '50%', left: -670 + 'px'});
			//关闭以后设置状态为隐藏
			ifShow = false;
		};
	};
	
	/* add active 添加活动 */
	function addActivity(type){
		var title;
		if(type == 'discussion'){
			title = '添加教学研讨';
		}else if(type == 'lcec'){
			title = '添加听课评课';
		}else if(type == 'debate'){
			title = '添加辩论活动';
		}else if(type == 'video'){
			title = '添加教学观摩';
		}else if(type == 'survey'){
			title = '添加问卷调查';
		}else if(type == 'test'){
			title = '添加在线测试';
		}else if(type == 'lesson_plan'){
			title = '添加集体备课';
		}
		var activityRelationId = $('#activityRelationId').val();
		mylayerFn.open({
			id: 'editActivityDiv',
	        type: 2,
	        title: title,
	        fix: true,
	        area: [870, $(window).height()*99/100],
	        content: '/${role}_${workshopId}/activity/create?type='+type+"&relation.id="+activityRelationId,
	        shadeClose: false
	    });
	}
	
	function deleteActivity(id){
		confirm('确定要删除此活动?', function(){
			$.ajaxDelete('${ctx}/${role}_${workshopId}/activity/'+id, '', function(data){
				if(data.responseCode == '00'){
					listWorkshopSection();
				}	
			});
		});
	}
	
	function editActivity(id, type){
		var title = '';
		if(type == 'discussion'){
			title = '添加教学研讨';
		}else if(type == 'lcec'){
			title = '添加听课评课';
		}else if(type == 'debate'){
			title = '添加辩论活动';
		}else if(type == 'video'){
			title = '添加教学观摩';
		}else if(type == 'survey'){
			title = '添加问卷调查';
		}else if(type == 'test'){
			title = '添加在线测试';
		}else if(type == 'lesson_plan'){
			title = '添加集体备课';
		}
		mylayerFn.open({
			id:'editActivityDiv',
	        type: 2,
	        title: title,
	        fix: true,
	        area: [870, $(window).height()*99/100],
	        content: '${ctx}/${role}_${workshopId}/activity/'+id+'/edit',
	        shadeClose: false
	    });
	}
	
	function viewActivity(id){
		var role = "${role!''}";
		if(role == 'guest'){
			alert('您不在此工作坊内，无权查看活动');
		}else{
			window.location.href = '${role}_${workshopId}/activity/'+id+'/view';
		}
	}
	
	function deleteActivity(id){
		confirm('确定要删除此活动?', function(){
			$.ajaxDelete('/${role}_${workshopId}/activity/'+id, '', function(data){
				if(data.responseCode == '00'){
					alert('删除成功');
					listWorkshopSection();
				}	
			});
		});
	}
	
	 function starting_alert(){ //隐藏提示框
         $("dd .m-starting-alert .know").on("click", function(){
             $(this).parents(".m-starting-alert").css({"display":"none"});
             $(".g-workshop-alert").css({"display":"none"});
             $(this).parents("dd").removeClass("m-at-starting");
         });
     }

     function u_ready_mark(){ //课前准备收起和打开
         var up=true;
         $(".g-frame-mn").on("click",".u-ready-mark",function(){
             var studyready = $(this).parents(".g-train-ready").siblings('.g-opa-guid');
             if(up==true){
                 studyready.slideUp(200);
                 $(this).addClass('u-ready-mark2')
                 up=false;
             }else if(up==false){
                 studyready.slideDown(200)
                 $(this).removeClass('u-ready-mark2')
                 up=true;
             }
         });
     }
     
     function screen_see(){ //锁定一开始屏幕可见区域
        var txtOffset = $(".m-starting-alert").offset().top;
        $('html,body').animate({scrollTop:txtOffset-100}, 0);
        
    }
</script>