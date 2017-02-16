<@commentsDirective mainId=mainId relationId=relationId orders='CREATE_TIME'>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/common/image.ftl" as image/>
<#if comments??>
	<ul class="aag-cmt-lst">
		<#list comments as comment>
			<li class="am-cmt-block" commentid="${comment.id}">
				<div class="c-info">
					<a href="#" class="au-cmt-headimg"> <@image.imageFtl url="${(comment.creator.avatar)! }" default="${app_path}/images/defaultAvatarImg.png" /> </a>
					<p class="tp">
						<a href="#" class="name">${(comment.creator.realName)!}</a>
						<span class="time">${TimeUtils.formatDate(comment.createTime,'yyyy-MM-dd HH:mm')}</span>
						<#if comment.creator.id == Session.loginer.id>
						<span class="m-discuss-com">
			                <a class="au-dlt dis-dlt">
			                    <i class="au-dlt-ico"></i>删除
			                </a>
			            </span>
			            </#if>
					</p>
					<p class="cmt-dt">
						${(comment.content)!}
					</p>
				</div>
			</li>
		</#list>
	</ul>
</#if>
<script>
	$(function(){
		$("#${mainId}_childComment").on("click",'.au-dlt',function(){
			var _this = $(this);
			confirm('确定删除该回复？',function(){
				$.post('${ctx!}/comment',{
					'id':_this.parents('li').attr('commentid'),
					'isDeleted':'Y',
					'_method':'DELETE'
				},function(data){
					if(data.responseCode == '00'){
						alert("删除成功!");
						var replyCount = _this.closest('.main').find('.replyCount').text().trim();
						_this.closest('.main').find('.replyCount').text(parseInt(replyCount)-1);
						_this.closest('li').remove();
					}
				});
			});
		});
	});
	
</script>
</@commentsDirective>