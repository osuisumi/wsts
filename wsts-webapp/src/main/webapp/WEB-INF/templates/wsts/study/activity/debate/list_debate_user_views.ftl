<#import "/common/image.ftl" as image/>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#global wsid=(WsIdObject.getWsIdObject().wsid)!''>
<#import "/wsts/common/role.ftl" as r/>
<@r.content/>
<div class="ag-comment-layout">
	<div class="am-coment-tp">
		<div class="c-sttc1">
			<!--已有&nbsp;<strong>298</strong>&nbsp;条评论-->
			<select name="" id="agType" class="am-select-o">
				<option value="">全部观点</option>
			</select>
		</div>
		<div class="am-slt-sort">
			<a href="javascript:void(0);" order="CREATE_TIME.DESC" <#if (orders!'CREATE_TIME.DESC')="CREATE_TIME.DESC">class="z-crt"</#if> >时间</a>
			<a href="javascript:void(0);" order="COMMENTS_NUM.DESC" <#if (orders!'CREATE_TIME.DESC')='COMMENTS_NUM.DESC'>class="z-crt"</#if> >评论数</a>
			<a href="javascript:void(0);" order="SUPPORT_NUM.DESC" <#if (orders!'CREATE_TIME.DESC')='SUPPORT_NUM.DESC'>class="z-crt"</#if> >点赞数</a>
		</div>
		<!--
		<select name="" id="" class="am-select-o">
			<option value="">全部观点</option>
			<option value="">甲方观点</option>
			<option value="">乙方观点</option>
			<option value="">丙方观点</option>
			<option value="">丁方观点</option>
		</select>
		-->
	</div>
	<@debateUserViewsDirective debateRelationId=(debateUser.debateRelation.id)!'' page=(pageBounds.page)!1 limit=10 argumentId=(debateUser.argument.id)!'' orders=(orders)!'CREATE_TIME.DESC' >
	<div class="ag-comment-main">
		<ul class="ag-cmt-lst ag-cmt-lst-p">
			<#if debateUserViews?? && debateUserViews?size &gt; 0>
				<#list debateUserViews as debateUserView>
					<li class="am-cmt-block main" argumentId=${debateUserView.debateUser.argument.id} commentId="${(debateUserView.id)!}" creator="${(debateUserView.creator.id)!}" debateUserId="${(debateUserView.debateUser.id)!}">
						<div class="c-info">
							<a href="#" class="au-cmt-headimg"> 
								<@image.imageFtl url="${(workshop.imageUrl)! }" default="${app_path}/images/defaultAvatarImg.png" />
							</a>
							<p class="tp">
								<a href="#" class="name">${(debateUserView.creator.realName)!}</a>
								<span class="au-idt-type "></span>
								<span class="time">${TimeUtils.formatDate(debateUserView.createTime,'yyyy-MM-dd HH:mm')}</span>
							</p>
							<p class="cmt-dt">
								${(debateUserView.viewsContent)!}
							</p>
							<div class="ag-opa">
								<#if attitudeMap??>
									<#if attitudeMap[debateUserView.id]?? && attitudeMap[debateUserView.id].attitude = 'support'>
										<a href="javascript:void(0);" onclick="alert('已赞');" class="au-praise z-crt"> <i class="au-praise-ico"></i>已赞<b>（<span id="supportNum_${debateUserView.id}">${(debateUserView.supportNum)!0}</span>）</b> </a>
									<#else>
										<a href="javascript:void(0);" onclick="attitudeSupport('${debateUserView.id}',this)" class="au-praise z-crt"> <i class="au-praise-ico"></i>赞同<b>（<span id="supportNum_${debateUserView.id}">${(debateUserView.supportNum)!0}</span>）</b> </a>
									</#if>
								<#else>
									<a href="javascript:void(0);" onclick="attitudeSupport('${debateUserView.id}',this)" class="au-praise z-crt"> <i class="au-praise-ico"></i>赞同<b>（<span id="supportNum_${debateUserView.id}">${(debateUserView.supportNum)!0}</span>）</b> </a>
								</#if>
								<i class="au-opa-dot"></i>
								<a href="javascript:void(0);" class="debateComment"> <i class="au-comment-ico"></i>评论<b>（<span class="replyCount">${debateUserView.commentsNum}</span>）</b> </a>
								<#if role == 'master' || role =='member' || ((debateUserView.creator.id)!'') == Session.loginer.id>
									<i class="au-opa-dot"></i>
                                    <a href="javascript:void(0);" class="au-dlt">
                                        <i class="au-dlt-ico"></i>删除
                                    </a>
								</#if>
							</div>
						</div>
						<div class="ag-is-comment">
							<i class="au-comment-trg"></i>
							
							<div id="${debateUserView.id}_childComment"></div>
							<#--
							<div class="am-unfold-block">
								<a href="javascript:void(0);" class="au-unfold"> 展开全部评论<i class="au-ico"></i> </a>
							</div>
							-->
							<div class="am-isComment-box am-ipt-mod saveDebateCommentDiv" viewId="${debateUserView.id}">
								<textarea id="" class="au-textarea" placeholder="我也说一句"></textarea>
								<div class="am-cmtBtn-block f-cb">
									<!--<a href="javascript:void(0);" class="au-face"></a>-->
									<a href="javascript:void(0);" onclick="saveDebateComment(this)" class="au-cmtPublish-btn au-confirm-btn1">发表</a>
								</div>
							</div>
						</div>
					</li>
				</#list>
			</#if>
		</ul>
		<form id="listDebateUserViewsForm"  method="post" action="${ctx!}/debateUserViews" >
			<input type="hidden" name="argument.id" value="${(debateUser.argument.id)!}">	
			<input type="hidden" name="orders" value="${orders!'CREATE_TIME.DESC'}" >
			<input type="hidden" name="debateRelation.id" value="${(debateUser.debateRelation.id)!}">
			<div id="debateUserViewsPage" class="m-laypage"></div>
			<#if paginator??>
				<#import "/common/pagination_ajax.ftl" as p/>
				<@p.paginationAjaxFtl formId="listDebateUserViewsForm" divId="debateUserViewsPage" paginator=paginator contentId="debateUserViewsDiv"/>
			</#if>
		</form>
	</div>
	<script>
		$(function(){
			activityJs.fn.init();
			$.each($('.au-idt-type'),function(i,n){
				var argumentId = $(n).closest('li').attr('argumentId');
				$(n).text(argumentChineseNoMap[argumentId].content+'方');
				$(n).addClass('type'+(argumentChineseNoMap[argumentId].index+1));
			});
			
			//初始化下拉查询条件
			var select  = $('#agType');
			$.each(argumentChineseNoMap,function(key,value){
				select.append('<option value="'+key+'">'+value.content+'方观点</option>');
			});
			select.val('${(debateUser.argument.id)!""}');
			
			//下拉绑定事件
			select.on('change',function(){
				listDebateUserViews($(this).val(),'${(orders)!"CREATE_TIME.DESC"}');
			});
			
			//排序按钮事件绑定
			$('.am-slt-sort a').on('click',function(){
				listDebateUserViews('${(debateUser.argument.id)!}',$(this).attr('order'));
			});
			
			//删除
			$('.au-dlt').on('click',function(){
				var li = $(this).closest('li');
				var id = li.attr('commentid');
				var creator = li.attr('creator');
				var debateUserId = li.attr('debateUserId');
				confirm('确定删除该观点？',function(){
					$.ajaxDelete('${ctx}/debateUserViews/delete/'+id+'?creator.id='+creator + '&debateUser.id='+debateUserId,null,function(response){
						if(response.responseCode == '00'){
							alert('删除成功',function(){
								listDebateUserViews();
							});
						}
					});
				});
			});
			
			
        var $comment_lst = $(".ag-cmt-lst-p"),
            $comment_block = $comment_lst.children("li"),
            $a_comment_ico = $comment_block.find(".debateComment"),
            $a_comment_mod = $comment_block.find(".ag-is-comment");
        //遍历评论列表，获取索引
        $comment_block.each(function(){
            var _ts = $(this),
                $comment_ico = _ts.find(".debateComment"),
                $is_comment_mod = _ts.find(".ag-is-comment"),
                t_height = $is_comment_mod.innerHeight();

            $comment_ico.on("click",function(){
            	//zhuderun add
            	var relationId = $(this).closest('li').attr('commentId');
				var commentId = $(this).closest('li').attr('commentId');
				var childCommentDiv = $('#'+commentId+'_childComment');
				if(childCommentDiv.find('li').size()<=0){
					childCommentDiv.load($('#ctx').val()+'/comment/activity/child','mainId='+commentId+'&relationId='+relationId);
				};
				//zhuderun add end
                //判断是否打开评论列表
                var _this = $(this);
                if(_this.hasClass("z-crt")){
                    //关闭评论列表
                    _this.removeClass("z-crt");
                    $is_comment_mod.hide();

                }else {
                    //打开评论列表
                     $a_comment_mod.hide();
                     $a_comment_ico.removeClass("z-crt");
                     _this.addClass("z-crt");
                     $is_comment_mod.find(".au-comment-trg").css("left", Math.ceil(_this.position().left) - 25);
                     $is_comment_mod.show();
                     //activityJs.fn.commentOpa($(".am-isComment-box"));
                }
            });
        });
		});
		
		function saveDebateComment(a){
			var saveDebateCommentDiv = $(a).closest('.saveDebateCommentDiv');
			var textarea = saveDebateCommentDiv.find('textarea');
			var viewId = saveDebateCommentDiv.attr('viewId');
			var content = textarea.val();
			if($.trim(content) == ''){
				alert('发表内容不能为空');
				return;
			}
			
			$.post('${ctx}/comment',{
				'content':content,
				'mainId':viewId,
				'relation.id':viewId,
				'relation.type':'debate_user_views'
			},function(response){
				if(response.responseCode == '00'){
					alert('保存成功');
					$('#'+viewId+'_childComment').load($('#ctx').val()+'/comment/activity/child','mainId='+viewId+'&relationId='+viewId);
					textarea.val('');
				}
			});
		}
		
		//点赞
		function attitudeSupport(commentId,a){
				$.post("${ctx}/attitudes",{
					"attitude":"support",
					"relation.id":commentId,
					"relation.type":"debate_user_views"
				},function(response){
					if(response.responseCode == '00'){
						var count = $('#supportNum_'+commentId).text();
						alert('点赞成功');
						$(a).replaceWith('<a onclick="alert(\'已赞\');"><i class="au-praise un-praise"></i>已赞（<span id="supportNum_'+commentId+'">'+(parseInt(count)+1)+'</span>）</a>');
					}else{
						alert('已经赞过');
					}
				});
		}
		
		
	</script>
	
	</@debateUserViewsDirective>
</div>