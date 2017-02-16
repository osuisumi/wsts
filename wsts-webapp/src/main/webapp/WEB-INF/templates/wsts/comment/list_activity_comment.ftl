<div class="ag-comment-layout">
<#import "/common/image.ftl" as image/>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<@commentsDirective getAttitude=true relationId=relationId page=(pageBounds.page)!1 limit=10  orders=(orders)!'CREATE_TIME.DESC'>
	<div class="am-coment-tp">
		<div class="c-sttc1">
			已有&nbsp;<strong>${(paginator.totalCount)!0}</strong>&nbsp;条评论
		</div>
		<!--
		<div class="am-slt-sort">
			<a href="javascript:void(0);" class="z-crt">时间</a>
			<a href="javascript:void(0);">评论数</a>
			<a href="javascript:void(0);">点赞数</a>
		</div>
		-->
	</div>
	<div class="ag-comment-main">
			<ul class="ag-cmt-lst ag-cmt-lst-p" relationId='${relationId}'>
				<#if comments?? && comments?size &gt;0 >
					<#list comments as comment>
						<li class="am-cmt-block main" commentId="${comment.id}">
							<div class="c-info">
								<a href="#" class="au-cmt-headimg"> <@image.imageFtl url="${(comment.creator.avatar)! }" default="${app_path}/images/defaultAvatarImg.png" /> </a>
								<p class="tp">
									<a href="#" class="name">${(comment.creator.realName)!}</a>
									<span class="time">${TimeUtils.formatDate(comment.createTime,'yyyy-MM-dd HH:mm')}</span>
								</p>
								<p class="cmt-dt">
									${(comment.content)!}
								</p>
								<div class="ag-opa">
									<#if 'support' = (attitudeUserMap[comment.id].attitude)!''>
										<a onclick="attitudeCancelSupport('${comment.id}',this)"><i class="au-praise un-praise"></i>取消赞(<span id="supportNum_${comment.id}">${(attitudeNumMap[comment.id].participateNum)!0}</span>)</a>
									<#else>
										<a href="javascript:void(0);" onclick="attitudeSupport('${comment.id}',this)" class="au-praise"> <i class="au-praise-ico"></i>赞同<b>（<span id="supportNum_${comment.id}">${(attitudeNumMap[comment.id].participateNum)!0}</span>）</b> </a>	
									</#if>
									<i class="au-opa-dot"></i>
									<a href="javascript:void(0);" class="au-comment"> <i class="au-comment-ico"></i>评论<b>（<span id="childNum_${comment.id}">${(comment.childNum)!}</span>）</b> </a>
									<#if comment.creator.id == Session.loginer.id>
									<i class="au-opa-dot"></i>
									<a href="javascript:void(0);" class="au-dlt"> <i class="au-dlt-ico"></i>删除 </a>
									</#if>
								</div>
							</div>
							<div class="ag-is-comment">
								<i class="au-comment-trg"></i>
								
								<div id="${comment.id}_childComment"></div>
								<!--
								<div class="am-unfold-block">
									<a href="javascript:void(0);" class="au-unfold"> 展开全部评论<i class="au-ico"></i> </a>
								</div>
								-->
								<div class="am-isComment-box am-ipt-mod saveChildComentDiv">
									<textarea id="" class="au-textarea" placeholder="我也说一句"></textarea>
									<div class="am-cmtBtn-block f-cb">
										<!--<a href="javascript:void(0);" class="au-face"></a>-->
										<a href="javascript:void(0);" onclick="saveChildComment('${comment.id}',this)"  class="au-cmtPublish-btn au-confirm-btn1">发表</a>
									</div>
								</div>
							</div>
						</li>
					</#list>
				<#else>
                	<div class="ag-no-content ag-no-comment">
                        <p>还没有学员评论，快成为第一个评论的人~</p>
                    </div>
				</#if>
			</ul>
			<form id="listActivityCommentForm"  method="post" action="${ctx!}/comment/activity/${relationId}" >
				<input type="hidden" name="relationId" value="${relationId!}">	
				<input type="hidden" name="orders" value="${orders!'CREATE_TIME.DESC'}" >
				<div id="activityCommentPage" class="m-laypage"></div>
				<#if paginator??>
					<#import "/common/pagination_ajax.ftl" as p/>
					<@p.paginationAjaxFtl formId="listActivityCommentForm" divId="activityCommentPage" paginator=paginator contentId="commentsDiv"/>
				</#if>
			</form>
	</div>
</div>
</@commentsDirective>
<div style="display:none">
	<li id="childCommentModel" class="am-cmt-block">
		<div class="c-info">
			<a href="#" class="au-cmt-headimg"> <img src="${app_path}/images/defaultAvatarImg.png" alt=""> </a>
			<p class="tp">
				<a href="#" class="name">${(Session.loginer.realName)!}</a>
				<span class="time">片刻之前</span>
				<span class="m-discuss-com">
                <a class="au-dlt dis-dlt">
                    <i class="au-dlt-ico"></i>删除
                </a>
            </span>
			</p>
			<p class="cmt-dt">
				
			</p>
		</div>
	</li>
</div>

<script>
	$(function(){
		activityJs.fn.init();
	});
	
	function saveComment(textarea,relationId){
		var content = textarea.val();
		if($.trim(content) == ''){
			alert('发表内容不能为空');
			return;
		};
		
		$.post('${ctx}/comment',{
			'content':content,
			'relation.id':relationId
		},function(response){
			if(response.responseCode == '00'){
				alert('发表成功');
				loadComments(relationId);
				textarea.val('');
				$('body').click();
			}
		});
	}
	
	function saveChildComment(commentId,a){
		var saveChildComentDiv = $(a).closest('.saveChildComentDiv');
		var textarea = saveChildComentDiv.find('textarea');
		var content = textarea.val();
		if($.trim(content) == ''){
			alert('发表内容不能为空');
			return;
		}
		
		$.post('${ctx}/comment',{
			'content':content,
			'mainId':commentId,
			'relation.id':'${relationId}'
		},function(response){
			if(response.responseCode == '00'){
				alert('保存成功');
				var count = $('#childNum_'+commentId).text();
				$('#childNum_'+commentId).text(parseInt(count) + 1);
				appendFromModel(content,commentId,a,response.responseData);
				textarea.val('');
			}
		});
	}
	
	function appendFromModel(content,commentId,a,c){
		var li = $('#childCommentModel').clone();
		li.attr('id','');
		li.find('.cmt-dt').text(content);
		li.attr('commentid',c.id);
		$('#'+commentId+'_childComment').find('ul').append(li);
	}
	
	//点赞
	function attitudeSupport(commentId,a){
			$.post("${ctx}/attitudes",{
				"attitude":"support",
				"relation.id":commentId,
				"relation.type":"comment"
			},function(response){
				if(response.responseCode == '00'){
					var count = $('#supportNum_'+commentId).text();
					//$('#supportNum_'+commentId).text(parseInt(count) + 1);
					alert('点赞成功');
					$(a).replaceWith('<a onclick="attitudeCancelSupport(\''+commentId+'\',this)"><i class="au-praise un-praise"></i>取消赞(<span id="supportNum_'+commentId+'">'+(parseInt(count)+1)+'</span>)</a>');
					//添加到赞列表
					appendSupportUser(commentId,a);
				}else{
					alert('已经赞过');
				}
			});
	}
	
	//取消赞
    function attitudeCancelSupport(id,a){
    	$.post('${ctx}/attitudes',{
    		"_method":'DELETE',
    		"relationId":id,
    		"attitude":'support'
    	},function(response){
    		if(response.responseCode == '00'){
    			alert('取消成功');
    			var count = $('#supportNum_'+id).text();
				$(a).replaceWith('<a onclick="attitudeSupport(\''+id+'\',this)"><i class="au-praise un-praise"></i>赞(<span id="supportNum_'+id+'">'+(parseInt(count)-1)+'</span>)</a>');
				removeSupportUser(id,a);
    		}
    	});
    }
	
</script>