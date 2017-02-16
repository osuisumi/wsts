<#include "/wsts/include/layout.ftl"/>
<#global wsid=workshopId>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#assign jsArray=['selectUser']/>
<@layout jsArray=jsArray>
<@workshopUsersDirective withActionInfo='Y' role='member' states='passed,apply,apply_quit' workshopId=workshopId realName=realName!'' deptName=deptName!'' limit=10 page=(pageBounds.page)!1>
<div class="g-auto">
	<#import "/wsts/include/banner.ftl" as banner/>
	<@banner.content current="管理成员" />
	
	<form id="listWorkshopUserForm" action="${ctx}/workshopUser" method="get">
	<div class="m-WSMe-Manage">
			<input type="hidden" name="workshopId" value="${workshopId!}">
			<div class="m-WSMe-search">
				<label> <span>姓名：</span>
					<input name="realName" value="${(realName)!}" type="text" />
				</label>
				<label> <span>学校：</span>
					<input name="deptName" value="${(deptName)!}" type="text" class="school"/>
				</label>
				<div id="reset" class="button reset">
					<i></i>重置
				</div>
				<div id="search" class="button search">
					<i></i>查询
				</div>
			</div>
		<div class="m-WSMe-cont">
			<div class="m-WSMe-cont-tl">
				<p>
					共有<span>
						${(paginator.totalCount)!}
					</span>个成员
				</p>
				<#if role="master">
					<div id="create" class="add-button">
						+添加成员
					</div>
				</#if>
			</div>
				<table class="m-WS-train-table m-WSMe-table">
						<tr>
							<!--<th class="checkboxs"><label class="m-checkbox-tick"> <strong> <i class="ico"></i>
								<input type="checkbox" name="subjectName" value="">
								</strong> </label></th>-->
							<th class="name">姓名</th>
							<th class="unit">所在单位</th>
							<th class="task">发起任务</th>
							<th class="problem" >回答问题</th>
							<th >上传资源</th>
							<th class="comment" >评论</th>
							<th >通知简报</th>
							<th class="status" >状态</th>
							<th class="operate">操作</th>
						</tr>
					<#if workshopUsers??>
						<#list workshopUsers as wu>
							<tr workshopUserId=${(wu.id)!}>
								<!--<td><label class="m-checkbox-tick"> <strong> <i class="ico"></i>
									<input type="checkbox" name="subjectName" value="">
									</strong> </label></td>-->
								<td>${(wu.user.realName)!}</td>
								<td>${(wu.userInfo.department.deptName)!}</td>
								<td>${(wu.actionInfo.activityNum)!}</td>
								<td>${(wu.actionInfo.faqAnswerNum)!}</td>
								<td>${(wu.actionInfo.uploadResourceNum)!}</td>
								<td>${(wu.actionInfo.commentsNum)!}</td>
								<td>${(wu.actionInfo.announcementNum)!}</td>
								<#if wu.role == 'member'>
									<#if ((wu.state)!'') == 'passed'>
										<td class="adding"><i></i>正式成员</td>
									<#elseif ((wu.state)!'') == 'apply'>
										<td class="examing"><i></i>审核中</td>
									<#elseif ((wu.state)!'') == 'apply_quit'>
										<td class="adding"><i></i>申请退出</td>
									</#if>
									<td class="add">
										<#if role="master">
											<#if ((wu.state)!'') == 'passed'>
												<a class="del" href="javascript:;">删除</a>
											<#elseif ((wu.state)!'') == 'apply'>
												 <a href="javascript:;" onclick="changeState('passed','${(wu.id)!}','${(wu.user.id)!}')" class="add-pass"><i></i>通过</a>
                                   				 <a href="javascript:;" onclick="changeState('nopass','${(wu.id)!}','${(wu.user.id)!}')" class="add-unpass"><i></i>不通过</a>
                                   			<#elseif ((wu.state)!'') == 'apply_quit'>
                                   				<a href="javascript:;" onclick="changeState('quited','${(wu.id)!}','${(wu.user.id)!}')" class="add-pass"><i></i>通过</a>
                                   				<a href="javascript:;" onclick="changeState('passed','${(wu.id)!}','${(wu.user.id)!}')" class="add-unpass"><i></i>不通过</a>
											</#if>
										<#else>
											<a>-</a>
										</#if>
									</td>
								<#elseif wu.role == 'master'>
									<td class="add-transfer"><i></i>坊主</td>
									<td class="transfer"><a href="javascript:;">-</a></td>
								</#if>
							</tr>
						</#list>
					</#if>
				</table>
				<#import "/common/pagination.ftl" as p/>
				<@p.paginationFtl formId="listWorkshopUserForm" divId="workshopUserPage" paginator=paginator />
			<div class="m-MS-page">
				<div id="workshopUserPage" class="m-laypage"></div>
			</div>
		</div>
	</div>
	</form>
</div>
</@workshopUsersDirective>
<script>
	$(function(){
		$('#create').on('click',function(){
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '添加成员',
				content : '${ctx}/workshopUser/create?workshopId=${workshopId}',
				area : [680, 600],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});
		
		//查询按钮
		 $('#search').on('click',function(){
		 	$('#listWorkshopUserForm').submit();
		 });
		 
		 //重置按钮
		 $('#reset').on('click',function(){
		 	window.location.reload();
		 });
		 
		 //删除
		 $('.del').on('click',function(){
		 	var _this = this;
		 	confirm('确定删除该成员吗?',function(){
		 		var wuId = $(_this).closest('tr').attr('workshopUserId');
		 		$.post('${ctx}/workshopUser',{
		 			'_method':'DELETE',
		 			'id':wuId,
		 		},function(response){
		 			if(response.responseCode =='00'){
		 				alert('删除成功',function(){
		 					$(_this).closest('tr').remove();
		 				});
		 			}
		 		});
		 	});
		 });
	});
	
	function changeState(state,id,userId){
		var message = state == 'passed'?'确定通过申请？':'确定拒绝申请？';
		confirm(message,function(){
			$.post('${ctx}/workshopUser',{
				'_method':'put',
				'id':id,
				'user.id':userId,
				'state':state
			},function(response){
				if(response.responseCode == '00'){
					alert('操作成功',function(){
						window.location.reload();
					});
				}else{
					alert('操作失败');
				}
			});
		});
	}
	
</script>
</@layout>