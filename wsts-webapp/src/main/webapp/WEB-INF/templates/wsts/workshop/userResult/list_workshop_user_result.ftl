<@workshopUsersDirective  workshopId=workshopId realName=realName!'' orders=orders!'CREATE_TIME.DESC' role='student' deptName=deptName!'' limit=10 page=(pageBounds.page)!1>
<div class="g-WSstd-res-cont g-WS-stCheck-cont">
	<form id="listWorkshopUserResultForm" action="${ctx}/workshopUserResult" method="get">
		<input type="hidden" name="workshopId" value="${(workshopId)!}">
		<input type="hidden" name="orders" value="${(orders)!'CREATE_TIME.DESC'}">
		<div class="m-WSMe-search">
			<label> <span>姓名：</span>
				<input name="realName" value="${(realName)!}" type="text" />
			</label>
			<label> <span>学校：</span>
				<input name="deptName" value="${(deptName)!}" type="text" class="school"/>
			</label>
			<div onclick="reset()" class="button reset">
				<i></i>重置
			</div>
			<div id="search" class="button search">
				<i></i>查询
			</div>
		</div>
		<#if paginator??>
			<#import "/common/pagination_ajax.ftl" as p/>
			<@p.paginationAjaxFtl formId="listWorkshopUserResultForm" divId="workshopUserResultPage" paginator=paginator contentId="tabContent"/>
		</#if>
	</form>
	<div class="m-WSMe-cont">
		<div class="m-WSMe-cont-tl">
			<p>
				<a onclick="reloadListWorkshopUserResult('CREATE_TIME.DESC')">按时间<i <#if orders='CREATE_TIME.DESC'>class="time-up-down"<#else>class="score-up-down"</#if> ></i></a>
				<a onclick="reloadListWorkshopUserResult('POINT.DESC')"><span>按积分<i <#if orders='POINT.DESC'>class="time-up-down"<#else>class="score-up-down"</#if> ></i></span></a>
				共<@workshopUserCountDirective workshopId=workshopId role='student'>${count}</@workshopUserCountDirective>个学员，研修积分未达<span class="qualifiedPoint"></span>分默认为未达标学员。
			</p>
			<div class="add-button sent-news">
				发送消息
			</div>
			<div class="add-button button-comment">
				批量评价
			</div>
		</div>
		<table class="m-WS-train-table m-WSMe-table">
			<tr>
				<th class="checkboxs"><label class="m-checkbox-tick"> <strong> <i class="ico"></i>
					<input type="checkbox" name="">
					</strong> </label></th>
				<!--<th class="num">期次</th>-->
				<th style="width:80px" class="name">姓名</th>
				<th style="width:230px" class="unit">所在单位</th>
				<!--<th class="status">研修状态</th>-->
				<th class="score">研修积分</th>
				<th class="ws-comment" >工作坊评价</th>
				<th class="comment" >总评</th>
			</tr>
			<#if workshopUsers??>
				<#list workshopUsers as wu>
					<tr>
						<td><label class="m-checkbox-tick"> <strong> <i class="ico"></i>
							<input uid="${(wu.userInfo.id)!}" type="checkbox" name="subjectName" value="${(wu.id)!}">
							</strong> </label></td>
						<!--<td>2期</td>-->
						<td>${(wu.userInfo.realName)!}</td>
						<td>${(wu.userInfo.department.deptName)!}</td>
						<!--<td class="starting">正在开展</td>-->
						<td >${(wu.workshopUserResult.point)!0}</td>
						<td class="gone-comment">
							<#if ((wu.workshopUserResult.workshopResult)!'') = 'qualified'>
								合格
								<p>评估人：${(wu.workshopUserResult.workshopResultCreator.realName)!}</p>
							<#elseif ((wu.workshopUserResult.workshopResult)!'') = 'excellent'>
								优秀
								<p>评估人：${(wu.workshopUserResult.workshopResultCreator.realName)!}</p>
							<#elseif ((wu.workshopUserResult.workshopResult)!'') = 'fail'>
								不合格
								<p>评估人：${(wu.workshopUserResult.workshopResultCreator.realName)!}</p>
							<#else>
								待评价
							</#if>
						</td>
						<td class="final" point="${(wu.workshopUserResult.point)!0}">
							<#if wu.finallyResult?? &&wu.finallyResult != ''>
								<#if wu.finallyResult == 'fail'>
									未达标
								<#elseif wu.finallyResult == 'qualified'>
									合格
								<#elseif wu.finallyResult == 'excellent'>
									优秀
								</#if>
							</#if>
						</td>
					</tr>
				</#list>
			</#if>
		</table>
		<div id="workshopUserResultPage" class="m-laypage"></div>
	</div>

</div>

<script>
	$(function(){
		$('.m-checkbox-tick input').bindCheckboxRadioSimulate();
		$('.qualifiedPoint').text($('#qualifiedPoint').val());
		//全选
		addall_checkbox();
		//发送消息
        $(".add-button.sent-news").on("click",function(){
        	var ids = "";
        	$.each($('input[name="subjectName"]'),function(i,n){
        		if($(this).closest('strong').hasClass('on')){
        			if(ids == ""){
        				ids = $(this).attr('uid');
        			}else{
        				ids = ids + "," + $(this).attr('uid');
        			}
        		}	
        	});
        	
        	if(ids == ""){
        		alert("请至少选择一个学员");
        		return;
        	}
        	
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '发送消息',
				content : '${ctx}/workshopUserResult/editMessage?userIds='+ids,
				area : [720, 570],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
        });
        //批量评价
        $(".add-button.button-comment").on("click",function(){
        	//获取选中用户id
        	var ids = "";
        	$.each($('input[name="subjectName"]'),function(i,n){
        		if($(this).closest('strong').hasClass('on')){
        			if(ids == ""){
        				ids = $(this).val();
        			}else{
        				ids = ids + "," + $(this).val();
        			}
        		}	
        	});
        	
        	if(ids == ""){
        		alert("请至少选择一个学员");
        		return;
        	}
        	
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '学员表现',
				content : '${ctx}/workshopUserResult/edit?workshopId=${workshopId}&workshopUserIds='+ids,
				area : [720, 570],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
        });
	});

	$(function(){
		$('#search').on('click',function(){
			$.ajaxQuery('listWorkshopUserResultForm','tabContent');
		});
	});
	
	function reset(){
		$('input[name=realName]').val('');
		$('input[name=deptName]').val('');
		$.ajaxQuery('listWorkshopUserResultForm','tabContent');
	}
	
    function addall_checkbox(){
        var $check_input = $(".checkboxs .m-checkbox-tick input")
        $check_input.on("click" ,function(){
            var Fa_strongC = $(this).parent().attr("class");
            if(Fa_strongC=="on"){
                $(".m-checkbox-tick strong").addClass('on');
            }else{
                $(".m-checkbox-tick strong").removeClass('on');
            }
        });
    }
    
    function reloadListWorkshopUserResult(orders){
    	var _orders = orders||'CREATE_TIME.DESC';
    	$('#tabContent').load('${ctx}/workshopUserResult','workshopId='+$('#currentWokshopId').val()+'&orders='+_orders);
    }
	
</script>

</@workshopUsersDirective>

