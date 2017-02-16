<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#global wsid=relationId>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#assign jsArray=['validate','ueditor']/>
<@layout jsArray=jsArray>
<div class="g-auto">
	<#import "/wsts/include/banner.ftl" as banner/>
	<@banner.content current="通知公告"/>
	<div class="m-WS-notice">
		<div class="m-WS-notice-tl">
			<h2>通知公告</h2>
			<#if role='master' || role='member'>
				<span id="create" class="add-notice"><i></i>发布公告</span>				
			</#if>
		</div>
		<@announcementsDirective relationId=relationId limit=10 page=(pageBounds.page)!1 orders='IS_TOP.DESC' type="workshop_announcement">
			<ul class="m-WS-report-count">
				<#if announcements?? && announcements?size &gt; 0 >
					<#list announcements as announcement>
						<li announcementId="${announcement.id}">
							<p class="g-WS-reslut-tl">
								<span class="txt"><i></i><a href="${ctx}/announcement/${announcement.id}">${announcement.title!} </a></span>
								<#if ((announcement.isTop)!'') == 'Y'>
									<span class="had-up">已置顶</span>
								</#if>
								<span class="time">${TimeUtils.formatDate(announcement.createTime,'yyyy/MM/dd')}</span>
								<#if role='master' || role='member'>
									<#if announcement.creator.id = ((Session.loginer.id)!'')>
										<span class="button del"><i class="u-delet"></i>删除</span>
										<span class="button edit"><i class="u-edit"></i>编辑</span>
									</#if>
									<#if ((announcement.isTop)!'') == 'Y'>
										<span announcementId="${announcement.id}" class="button untop"><i class="u-down"></i>取消置顶</span>
									<#else>
										<span announcementId="${announcement.id}" class="button top"><i  class="u-up"></i>置顶</span>
									</#if>
								</#if>
							</p>
						</li>
					</#list>
				<#else>
                	<#import "/common/noContent.ftl" as  nc>
                	<@nc.noContentFtl msg='暂时没有通知公告' />
				</#if>
			</ul>
			<form id="listAnnouncementForm" action="${ctx}/announcement">
				<input type="hidden" value="${relationId}" name="relationId">
				<input type="hidden" value="${limit!}" name="limit">
				<div id="announcementPage" class="m-laypage"></div>
				<#import "/common/pagination.ftl" as p/>
				<@p.paginationFtl formId="listAnnouncementForm" divId="announcementPage" paginator=paginator />
			</form>
		</@announcementsDirective>
	</div>

</div>

<script>
	$(function(){
		$('#create').on('click',function(){
			mylayerFn.open({
						id : '999',
						type : 2,
						title : '发布通知',
						content : '${ctx}/announcement/create?relationId=${relationId}',
						area : [800, '600'],
						offset : ['auto', 'auto'],
						fix : false,
						shadeClose : false,
					});
				
		});
			
		$('.top').on('click',function(){
			var announcementId = $(this).attr('announcementId');
			$.post('${ctx}/announcement/update',{
				'_method':'PUT',
				'id':announcementId,
				'isTop':'Y',
			},function(response){
				if(response.responseCode == '00'){
					alert('置顶成功',function(){
						window.location.reload();
					});
				}

			});
		});
		
		$('.untop').on('click',function(){
			var announcementId = $(this).attr('announcementId');
			$.post('${ctx}/announcement/update',{
				'_method':'PUT',
				'id':announcementId,
				'isTop':'N',
			},function(response){
				if(response.responseCode == '00'){
					alert('取消置顶成功',function(){
						window.location.reload();
					});
				}
			});
		});
		
		$('.del').on('click',function(){
			var _this = this;
			confirm('确定要删除该通知吗？',function(){
				var announcementId = $(_this).closest('li').attr('announcementId');
				$.post('${ctx}/announcement',{
					'_method':'DELETE',
					'id':announcementId,
				},function(response){
					if(response.responseCode == '00'){
						alert('删除成功',function(){
							$(_this).closest('li').remove();
						});
					}
				});
			});

		});
		
		$('.edit').on('click',function(){
			var announcementId = $(this).closest('li').attr('announcementId');	
				mylayerFn.open({
					id : '999',
					type : 2,
					title : '编辑通知',
					content : '${ctx}/announcement/'+announcementId+'/edit',
					area : [800, '600'],
					offset : ['auto', 'auto'],
					fix : false,
					shadeClose : false,
				});
		});
		
	});


</script>

</@layout>