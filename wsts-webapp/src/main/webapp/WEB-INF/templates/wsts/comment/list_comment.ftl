<style>
	.m-WSdisc-sent .m-submit .m-link .at .at-who.canfind{
		display:block
	}
	
</style>

<#global wsid=comment.relation.id>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r>
<@r.content />
<#import "../../../../common/image.ftl" as image/>
<#if role != 'guest'>
<div class="g-WSdiscuss-cont">
<form id="commentForm" method="post" action="${ctx!}/comment">
	<input id="relationId" type="hidden" name="relation.id" value="${(comment.relation.id)!''}">
	<input type="hidden" name="relation.type" value="workshop_comment">
	<span id="targetParam"></span>
    <div class="m-WSdisc-sent">
        <i class="u-sent-arrow"></i>
        <@image.imageFtl url="${(ThreadContext.getUser().getAvatar())!}" default="${app_path}/images/defaultAvatarImg.png" class="who"/>
        <div class="m-pbMod-ipt" style="position:relative">
            <textarea id="zyjl"  name="content" class="u-textarea title-input" placeholder=" 通过@发信给团队成员"></textarea>
        </div>
        <div class="m-submit">
            <div class="m-link">
                <!--<a class="pho"></a>
                <a class="fill"></a>-->
				<div href="javascript:;" class="at">
						<ul class="at-who">
							<li class="icon"></li>
							<li class="close">
								x
							</li>
							<li>
								<label class="m-srh">
									<input type="text" id="searchUser" class="ipt" placeholder="搜索">
									<i class="u-srh1-ico"></i> </label>
							</li>
							<@workshopUsersDirective workshopId=wsid userIdNotEqual=(Session.loginer.id)!>
								<#if workshopUsers??>
									<#list workshopUsers as wu>
										<li uid="${(wu.userInfo.id)!}" uname="${(wu.userInfo.realName)!}" class="someone"><a href="javascript:;">${(wu.userInfo.realName)!}</a></li>
									</#list>
								</#if>
							</@workshopUsersDirective>
						</ul>
				</div>
            </div>
            <a onclick="saveComment()" class="m-ask">提问</a>                                   
        </div>
    </div>
</form>
<@commentsDirective relationId=(comment.relation.id)!'' getAttitude=true targetId=(targetId[0])! creatorOrTarget=(creatorOrTarget[0])! page=(pageBounds.page)!1 limit=(pageBounds.limit)!10 orders=orders!'CREATE_TIME.DESC'>
    <div class="m-WSdisc-ct">
    	<@commentNumDirective relationId=(comment.relation.id)!''>
        <h3 class="m-ct-tl">
            <span <#if ((creatorOrTarget[0])!'') == ThreadContext.getUser().getId()>class="crt"</#if> onclick="reloadComment()">所有<ins class="replyCount">(${(allCommentCount)!0})</ins></span>
            <span <#if ((targetId[0])!'') == ThreadContext.getUser().getId()>class="crt"</#if> onclick="reloadComment('to_me')">@我的<ins  class="replyCount">(${(toMeCommentCount)!0})</ins></span>
        </h3>
        </@commentNumDirective>
        <#if comments?? && (comments?size>0)>
        <ul class="m-discuss-lst ag-cmt-lst-p">
	        <#if comments??>
				<#list comments as comment>
                <li class="am-cmt-block" id=${(comment.id)!}>
                    <div class="m-WSdisc-time">
                        <strong>${TimeUtils.formatDate(comment.createTime,'dd')}</strong>
                        <p>${TimeUtils.formatDate(comment.createTime,'yyyy-MM')}</p>
                    </div>
                    <div class="m-WSdisc-circle"></div>                                          
                    <div class="c-info">
                        <a class="au-cmt-headimg">
                            <@image.imageFtl url="${(comment.getCreator().getAvatar())!}" default="${app_path}/images/defaultAvatarImg.png" />
                        </a>
                        <p class="tp">
                            <a class="name">${(comment.creator.realName)!}</a>
                            <span class="time">${TimeUtils.prettyTime(comment.createTime)!}</span>
                        </p>
                        <p class="cmt-dt">${(comment.content)!}</p>
                        <div class="ag-opa">
                            <a class="replay">
                                <i class="au-comment-ico"></i>回复
                            	<b>（<ii class="replyCount">${(comment.childNum)!}</ii>）</b>
                            </a>
                            <i class="au-opa-dot"></i>  
                            <a class="comment_attituUsers au-praise">
                                <i class="au-praise-ico"></i><ii class="comment_support_text">赞同</ii>
                               	 <b>（<ii class="comment_support_num">${(attitudeNumMap[comment.id].participateNum)!0}</ii>）</b>
                            </a>
                            <#if role == 'master' || role == 'member' || (comment.creator.id) == ThreadContext.getUser().getId()>
	                            <i class="au-opa-dot"></i>
	                            <a class="au-dlt">
	                                <i class="au-dlt-ico"></i>删除
	                            </a>
                            </#if>
                        </div>     
                    </div>
    
                    <div class="ag-is-comment">
                        <i class="au-comment-trg"></i>
                        <ul class="aag-cmt-lst" id="childCommentContent_${comment.id}">
 
                        </ul>
                        <div class="am-isComment-box am-ipt-mod m-marg">
                            <textarea class="au-textarea u-pad" ></textarea>                                                                                                
                            <i class="u-pen"></i>
                            <div class="am-cmtBtn-block f-cb">
                                <!--<a class="au-face"></a>-->
                                <a class="au-cmtPublish-btn au-confirm-btn1">发表</a>
                            </div>
                        </div>
                        
                    </div>
                </li>
				</#list>
			</#if>         
        </ul>
        <#else>
			<div class="g-no-notice-Con">
	            <p class="txt">暂时没数据！</p>
	        </div>
        </#if>
	<form id="listCommentForm"  method="post" action="${ctx!}/${role!}_${wsid}/comment" >
		<input type="hidden" name="relation.id" value="${(comment.relation.id)!}">
		<input type="hidden" name="targetId" value="${(targetId[0])!}">
		<input type="hidden" name="creatorOrTarget" value="${(creatorOrTarget[0])!}">
		<div id="commentPage" class="m-laypage"></div>
	<#if paginator??>
		<#import "../../common/pagination_ajax.ftl" as p/>
		<@p.paginationAjaxFtl formId="listCommentForm" divId="commentPage" paginator=paginator contentId="tabContent"/>
	</#if>
	</form>
    </div> 
</@commentsDirective>                  
</div>
<#else>
		<div class="g-no-notice-Con">
            <p class="txt">您不在此工作坊内无权查看该页面！</p>
        </div>
</#if>

<div style="display:none">
<li class="am-cmt-block" id="childTemplate" >
    <div class="c-info">
        <a class="au-cmt-headimg">
            <img/>
        </a>
        <p class="tp">
            <a class="name">xxx</a>
            <span class="time">xxx</span>
            <span class="m-discuss-com">
                <a class="au-dlt dis-dlt">
                    <i class="au-dlt-ico"></i>删除
                </a>
            </span>
        </p>
        <p class="cmt-dt content">xxx</p>
    </div>
</li>
</div>

<script>

	$(function(){
		activityJs.fn.init();
		//加载子回复
       var $comment_lst = $(".ag-cmt-lst-p"),
            $comment_block = $comment_lst.children("li"),
            $a_comment_ico = $comment_block.find(".replay"),
            $a_comment_mod = $comment_block.find(".ag-is-comment");
        //遍历评论列表，获取索引
        $comment_block.each(function(){
            var _ts = $(this),
                $comment_ico = _ts.find(".replay"),
                $is_comment_mod = _ts.find(".ag-is-comment"),
                t_height = $is_comment_mod.innerHeight();
            $comment_ico.on("click",function(){
                //判断是否打开评论列表
                var _this = $(this);
                if(_this.hasClass("z-crt")){
                    //关闭评论列表
                    _this.removeClass("z-crt");
                    $is_comment_mod.hide();
                }else {
                    //打开评论列表
	              	var mainId = _this.closest('li').attr('id');
	              	if($('#childCommentContent_'+mainId).find('li').length <= 0){
		                $.get('${ctx}/comment/entities',{
		                	'paramMap[mainId]':mainId
		                },function(childs){
		                	$.each(childs,function(i,n){
		                		var childContentDiv = $('#childCommentContent_'+mainId);
		                		var child = $('#childTemplate').clone();
		                		child.find('.name').text(n.creator.realName);
		                		var createTime = new Date(n.createTime);
		                		child.find('.time').text(createTime.getFullYear() + '-'+(createTime.getMonth()+1) + '-' + createTime.getDate());
		                		child.find('.content').text(n.content);
		                		child.attr('id',n.id);
		                		if(n.creator.avatar){
		                			child.find('img').attr('src','${FileUtils.getFileUrl("")}'+n.creator.avatar);
		                		}else{
		                			child.find('img').attr('src','${app_path}/images/defaultAvatarImg.png');
		                		}
		                		var loginId = "${ThreadContext.getUser().getId()}";
		                		if(n.creator.id != loginId){
		                			child.find('.au-dlt').remove();
		                		}
		                		childContentDiv.append(child);
		                	});
		                     $a_comment_mod.hide();
		                     $a_comment_ico.removeClass("z-crt");
		                     _this.addClass("z-crt");
		                     $is_comment_mod.find(".au-comment-trg").css("left", Math.ceil(_this.position().left) - 25);
		                     $is_comment_mod.show();
		                });
	              	}else{
	              		$a_comment_ico.removeClass("z-crt");
	              		_this.addClass("z-crt");
	              		$is_comment_mod.show();
	              	}
                }
            });
        });
		
		
		//删除
		$(".ag-cmt-lst-p").on("click",'.au-dlt',function(){
			var _this = $(this);
			confirm('确定删除该回复？',function(){
				$.post('${ctx!}/comment',{
					'id':_this.parents('li').attr('id'),
					'isDeleted':'Y',
					'_method':'DELETE'
				},function(data){
					if(data.responseCode == '00'){
						alert("删除成功!");
						var replyCount = _this.parents('.ag-is-comment').prev().find('.replyCount').text().trim();
						_this.parents('.ag-is-comment').prev().find('.replyCount').text(parseInt(replyCount)-1);
						_this.closest('li').remove();
					}
				});
			});
		});
		
		//回复
		$(".au-cmtPublish-btn").on("click",function(){
			var _this = $(this);
			var parentId  = _this.parents('.ag-is-comment').closest('li').attr('id').trim();
			var content = _this.parents('.am-isComment-box ').find('.au-textarea').val().trim();
			if(content.length <= 0){
				alert('回复内容不能为空');
				return false;
			}
			$.post('${ctx!}/comment',{
				'_method':'POST',
				'relation.id':'${comment.relation.id!""}',
				'relation.type':'workshop_comment',
				'mainId':parentId,
				'parentId':parentId,
				'content':content
				
			},function(data){
				if(data.responseCode == '00'){
					alert("提问成功!");
										
					var createTime = new Date(data.responseData.createTime); 
					var imgSrc = '${(ThreadContext.getUser().getAvatar())!""}';
					if(imgSrc.trim().length <= 0){
						imgSrc = '${app_path}/images/defaultAvatarImg.png';
					}
					
					var $li = $('#childTemplate').clone(true);
					$li.attr('id',data.responseData.id);
					$li.css('display','block');
					$li.find('.name').text(data.responseData.creator.realName);
					$li.find('.time').text(createTime.format('yyyy-MM'));
					$li.find('.cmt-dt').text(data.responseData.content);
					$li.find('.au-cmt-headimg img').attr('src', imgSrc);
					_this.parents('.ag-is-comment').find('.aag-cmt-lst').prepend($li);
					var replyCount = _this.parents('.ag-is-comment').prev().find('.replyCount').text().trim();
					_this.parents('.ag-is-comment').prev().find('.replyCount').text(parseInt(replyCount)+1);
					_this.parents('.am-isComment-box ').find('.au-textarea').val('');
				}
			});
		});
		
		//改变赞同状态
		$(".comment_attituUsers").on("click",function(){
			var _this = $(this);
			var isSupport = _this.hasClass('un-praise');
			var commentId = _this.parents('li').attr('id');
			if(isSupport == true){
				$.post('${ctx!}/attitudes',{
					'attitude':'support',
					'relationId':commentId,
					'_method':'DELETE' 
				},function(response){
					if(response.responseCode == '00'){
						_this.removeClass('un-praise');
						_this.find('ii.comment_support_num').text(parseInt(_this.find('ii.comment_support_num').text())-1);
						_this.find('ii.comment_support_text').text('赞同');
						alert('赞同已取消!');
					}
				});
			}else{
				$.post('${ctx!}/attitudes',{
					'attitude':'support',
					'relation.id':commentId,
					'relation.type':'workshop_comment',
					'_method':'POST' 
				},function(response){
					if(response.responseCode == '00'){
						_this.addClass('un-praise');
						_this.find('ii.comment_support_num').text(parseInt(_this.find('ii.comment_support_num').text())+1);
						_this.find('ii.comment_support_text').text('取消赞同');
						alert('赞同成功!');
					}else{
						alert('已赞');
					}
				});
			}
		});
		
		courseDisc_bg();
		
		close_who();
		
		$('#searchUser').on('keyup', function(event){
			var _this = this;
	      	if(event.which != 13 && event.which != 27 && event.which != 38 && event.which != 40){
	      		$.ajax({
					url : '${ctx}/workshopUser/entities',
					data : 'paramMap[workshopId]=${wsid}&paramMap[realName]='+$(_this).val(),
					type : 'GET',
					success : function(data) {
						$('.at-who .someone').remove();
						$.each(data, function(i, n) {
							var $li = $('<li uid="'+n.id+'" uname="'+n.realName+'" class="someone"><a href="javascript:;">'+n.realName+'</a></li>');
							$li.on('click',function(){
								setUser($li);
							});
							$('.at-who').append($li);
						});
					}
				}); 
	      	}
	    	
	    }).on('click',function(event){
	    	 event.preventDefault();
	        _this.show();
	    });
		
		
		$('.at li.someone').on('click',function(){
			setUser($(this));
		});
	});
	
	//评论框的聚焦失焦事件
	function courseDisc_bg(){ 
	    var $textarea = $(".ag-is-comment .am-isComment-box .au-textarea");
	    $textarea.val("回复")
	    $textarea.on("focus",function(){
	        var $this = $(this)
	        var discus_val = $this.val();
	        if($.trim(discus_val)=="回复"){
	            $this.val("");
	            $this.siblings('.u-pen').removeClass('u-pen')
	            $this.removeClass('u-pad');
	            $this.parent(".am-isComment-box").removeClass('m-marg');
	        }
	    });  
	};
	
	//保存
	function saveComment(){
		if(!$('#commentForm').validate().form()){
			return false;
		}
		if($('#commentForm textarea[name=content]').val().trim().length <= 0){
			return false;
		}
		
		//保存之前检查targetParam
		var targetParam = $('#targetParam');
		var content = $('#commentForm textarea[name=content]').val().trim();
		
		$.each(targetParam.find('input'),function(i,n){
			var userName = $(n).attr('uName');
			if(content.indexOf('@'+userName)<0){
				$(n).remove();
			}
		});
		
		var data = $.ajaxSubmit('commentForm');
		var json = $.parseJSON(data);
		if(json.responseCode == '00'){
			alert("保存成功!");
			$.ajaxQuery('listCommentForm','tabContent');
			targetParam.empty();
		}
	};
	
	//重新加载
	function reloadComment(type){
		if(type == 'to_me'){
			$('#listCommentForm input[name="targetId"]').val('${ThreadContext.getUser().getId()}');
			$('#listCommentForm input[name="creatorOrTarget"]').val('');
		}else{
			$('#listCommentForm input[name="targetId"]').val('');
			$('#listCommentForm input[name="creatorOrTarget"]').val('${ThreadContext.getUser().getId()}');
		}
		$('#listCommentForm input[name="page"]').val('1');
		$.ajaxQuery('listCommentForm','tabContent');
		
	};
	
	//展开全部评论
	function expandAllComment(a){
		var _this = $(a);
		var lis = _this.parents('.ag-is-comment').find('.aag-cmt-lst li');
		lis.each(function(){
			$(this).css('display','block');
		});
		_this.parents('.am-unfold-block').remove();
	};
	
	//日期格式化
	Date.prototype.format = function(format){
		var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
		}
		
		if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		}
		
		for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) {
		format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
		}
		return format;
	};
	
	//监听提问
	$('#commentForm textarea[name=content]').bind('keyup', function(event) {
		if(event.shiftKey==true && event.keyCode ==50){
			$(".at-who").addClass('canfind');
			$(".at-who").css('top','-100px');
			$(".at-who").addClass('keyCall');
		}
	});
	
	function setUser(user){
		var userId = user.eq(0).attr('uid');
		var userName = user.eq(0).attr('uname');
		//如果已经添加，不作处理
		if($('#targetParam input[value='+userId+']').size()<=0){
			$('#targetParam').append('<input uName="'+userName+'" type="hidden" name="targetId" value="'+userId+'">');
		}
		if($(".at-who").hasClass("keyCall")){
			$('#zyjl').val($('#zyjl').val() + userName + ":");
		}else{
			$('#zyjl').val($('#zyjl').val() +"@"+ userName + ":");
		}
      	$(".at-who").addClass('unfind');
	    $(".at-who").removeClass('canfind');
	    $(".at-who").css('top','-20px');
	    $('#searchUser').val('');
	};
	
	function close_who(){ //关闭@
	    $("body").on("click",".at-who .close",function(){
	       $(this).parents(".at-who").addClass('unfind');
	       $(this).parents(".at-who").removeClass('canfind');
	       $(this).parents(".at-who").removeClass('keyCall');
	       $(this).parents(".at-who").css('top','-20px');
	       $('#searchUser').val('');
	    });
	    $("body").on("mouseover",".m-link .at",function(){
	       $(this).find('.at-who').removeClass('unfind');
	    });
	};
	
</script>
