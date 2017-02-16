<#include "/wsts/include/layout.ftl"/>
<#import "/common/image.ftl" as image/>
<#global wsid=relationId>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<#assign jsArray=['validate','ueditor']/>
<@layout jsArray=jsArray>
<@briefingDirective relationId=relationId!'' type='workshop_briefing' page=page!1  limit=10 orders="IS_TOP.DESC,CREATE_TIME.DESC">
	<div class="g-auto">
		<#import "/wsts/include/banner.ftl" as banner/>
		<@banner.content current="研修简报列表">
		</@banner.content>
	    <div class="m-WS-notice m-WS-briefing">
	        <div class="m-WS-notice-tl g-WSdet-list">
	            <h2><i class="u-train-icon"></i>研修简报</h2>
	            <#if role='master' || role='member'>
	            	<span id="create" class="add-notice"><i></i>发布简报</span>
	            </#if>
	        </div>
	        <ul class="m-WS-report-count">
		        <#if briefings?? && briefings?size &gt; 0 >
		        	<#list briefings as briefing>
			            <li id="${(briefing.id)!}">
			                <p class="g-WS-reslut-tl">
			                    <span class="txt"><i></i><a href="${ctx}/briefing/${briefing.id}">${(briefing.title)!} </a></span>
								<#if ((briefing.isTop)!'') == 'Y'>
									<span class="had-up">已置顶</span>
								</#if>
			                    <span class="time">${TimeUtils.formatDate(briefing.createTime,'yyyy/MM/dd')}</span>
			                    <#if role='master' || role='member'>
				                    <span class="button deleteBriefing"><i class="u-delet"></i>删除</span>
				                    <span class="button editBriefing"><i class="u-edit"></i>编辑</span>
				                    <#if ((briefing.isTop)!'') == 'Y'>
				                    	<span class="button untoTopBriefing"><i class="u-down"></i>取消置顶</span>
				                    <#else>
					                    <span class="button toTopBriefing"><i class="u-up"></i>置顶</span>
				                    </#if>
			                    </#if>
			                </p>
			            </li>
		           	</#list>
		        <#else>
                	<#import "/common/noContent.ftl" as  nc>
                	<@nc.noContentFtl msg='暂时没有简报' />
	            </#if>
	        </ul>  
			<form id="listBriefingList" action="${ctx!}/briefing" >	
				<input type="hidden" name="relationId" value="${relationId!}">
				<input type="hidden" name="orders" value="IS_TOP.DESC,CREATE_TIME.DESC">
				<div id="myCoursePage" class="m-laypage"></div>
				<#if paginator??>
				<#import "/common/pagination.ftl" as p/>
				<@p.paginationFtl formId="listBriefingList" divId="myCoursePage" paginator=paginator! />
				</#if>
			</form>
	        <div id="myCoursePage" class="m-laypage"></div>                  
	    </div>
	</div>
</@briefingDirective>
<script>
	$(function(){
	
		//新增
		$('#create').on('click',function(){
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '发布简报',
				content : '${ctx!}/briefing/create?relationId=${relationId!}',
				area : [900, 700],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});
		
		//修改
		$('.editBriefing').on('click',function(){
			var id = $(this).parents('li').attr('id');
			mylayerFn.open({
				id : '999',
				type : 2,
				title : '修改简报',
				content : '${ctx!}/briefing/edit?id='+id,
				area : [900, 700],
				offset : ['auto', 'auto'],
				fix : false,
				shadeClose : false,
			});
		});			

		//删除
		$('.deleteBriefing').on('click',function(){
			var id = $(this).parents('li').attr('id');
			if(id.length <= 0){
				return;
			}
			confirm('确定要删除此简报?', function(){
				$.ajaxDelete('${ctx!}/briefing?id='+id,'' , function(data){
					if(data.responseCode == '00'){
						alert('删除成功');
						window.location.reload();
					}	
				});
			});
		});		
	
		//置顶
		$('.toTopBriefing').on('click',function(){
			var id = $(this).parents('li').attr('id');
			if(id.length <= 0){
				return;
			}
			$.post('${ctx!}/briefing',
				'_method=PUT&id='+id+'&isTop=Y' , 
				function(data){
					if(data.responseCode == '00'){
						window.location.reload();
					}	
				}
			);
		});	
		
		//取消置顶
		$('.untoTopBriefing').on('click',function(){
			var id = $(this).parents('li').attr('id');
			if(id.length <= 0){
				return;
			}
			$.post('${ctx!}/briefing',{
				'_method':'PUT',
				'id':id,
				'isTop':'N',
			},function(response){
				if(response.responseCode == '00'){
					window.location.reload();
					alert('取消置顶成功');
				}
			});
		});
		
			
	});

	

</script>

</@layout>