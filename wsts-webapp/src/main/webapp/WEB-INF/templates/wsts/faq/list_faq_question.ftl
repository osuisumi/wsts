<#global wsid=relationId>
<#global app_path=PropertiesLoader.get('app.wsts.path') >
<#import "/wsts/common/role.ftl" as r>
<@r.content />
<#import "../../../../common/image.ftl" as image/>
<#if role != 'guest'>
<div class="g-workshop-con">
    <div class="g-ask-reply">
	    	<#if (isStatePage!'N') != 'Y'>
		        <div class="m-put-question">
		            <h4 class="tit">我要提问题：</h4>
		            <div class="m-community-disc">
		                <div class="m-rpl-comment">
		                    <div class="m-pbMod-ipt">
		                        <textarea name="content" id="faqQuestionContent" class="u-textarea" placeholder="输入问题"></textarea>
		                    </div>
							<ul id="similarQuestion" class="m-cont-point">
								
							</ul>
		                </div>
		            </div>
		            <div class="u-opa">
		                <a class="notes" onclick="attentionalItemView('open')"><i class="u-ico-notes"></i>提问注意事项</a>
		                <a class="u-btn-com u-sub">提问</a>
		            </div>
		            <div class="m-notes-lst" <#if (attentionalItemView!'') ='close'>style="display:none"</#if> >
		                <dl>
		                    <dt>
		                        <h4 class="tt">提问注意事项</h4>
		                        <a class="know" onclick="attentionalItemView('close')">我知道了</a>
		                    </dt>
		                    <dd>1、学员每天可以免费提出1个问题，从第二个问题起，每个问题扣除1点研修积分；</dd>
		                    <dd>2、提问前请先搜索是否已经有同类问题吧。这样您就省心少打字。</dd>
		                    <dd>3、禁止发布本工作坊课题研修无关的信息，一经发现将一律清理并扣研修积分。</dd>
		                    <dd>4、不在培训或工作坊开展时间内将不进行积分计算；</dd>
		                </dl>
		            </div>
		        </div>
	        </#if>
<@faqDirective relationId=(relationId)!'' page=(pageBounds.page)!1 limit=(pageBounds.limit)!10 orders=orders!'CREATE_TIME.DESC' type=type!'all' id=id!>
        <div class="g-all-question" id="allQuestion">
        	<#if (isStatePage!'N') != 'Y'>
	            <div class="m-tit-tab">
	                <a id="AllCount" onclick="loadFaqQuestion('${relationId!}','all')">全部<span>（${(allQuestionCount)!0}）</span></a>
	                <a id="myQuestionCount" onclick="loadFaqQuestion('${relationId!}','my')">我的提问<span>（${(myQuestionCount)!0}）</span></a>
	            </div>
	        </#if>
            <div class="m-con-tab">
                <ul class="m-qst-lst">
                <#if faqQuestions?? && faqQuestions?size &gt; 0 >
					<#list faqQuestions as faqQuestion>
                    <li id="${(faqQuestion.id)!}">
                        <div class="m-user">
                            <@image.imageFtl url="${(faqQuestion.creator.avatar)!}" default="${app_path}/images/defaultAvatarImg.png"/>
                            <span class="name">${(faqQuestion.creator.realName)!}</span>
                            <span class="time">${(TimeUtils.prettyTime(faqQuestion.createTime)!'')?replace(' ','')}</span>
                        </div>
						<h3 class="u-tit">${(faqQuestion.content)!}<i class="u-ico-qst"></i><i class="u-ico-horn"></i>
							<div class="ag-opa">
								<!--<a href="javascript:void(0);" class="au-alter au-editComment-btn"> <i class="au-alter-ico"></i>编辑 </a>
								<i class="au-opa-dot"></i>-->
								<#if role == 'master' || role == 'member' || ((faqQuestion.creator.id)!'') == Session.loginer.id>
									<a href="javascript:void(0);" onclick="delfaq(this)" class="au-dlt"> <i class="au-dlt-ico"></i>删除 </a>
								</#if>
							</div>
						</h3>
                        <#if ((faqQuestion.faqAnswers)?size) gt 0 >
                        <div class="u-answer-num"><i class="u-ico-edit"></i><b>${(faqQuestion.faqAnswers)?size!0}</b>条答案<i class="u-ico-dp"></i></div>
                        <ul class="m-reply-lst">
                        	<#list (faqQuestion.faqAnswers) as faqAnswer>
                            <li class="reply-last">
                                <div class="u-top">
                                    <span class="u-people">
										<@image.imageFtl url="${(faqAnswer.creator.avatar)!}" default="${app_path}/images/defaultAvatarImg.png"/>
                                        <span class="name">${(faqAnswer.creator.realName)!}</span>
                                        <span class="date">${TimeUtils.formatDate(faqAnswer.createTime,'yyyy-MM-dd')}</span>
                                    </span>
                                    <!--<span class="u-reply-mark best">最佳答案</span>-->
                                </div>
                                <div class="u-content">
                                    <p>${(faqAnswer.content)!}</p>
                                </div>
                                <div class="ag-opa">                                          
                                	<#if role == 'master' || role == 'member' || ((faqAnswer.creator.id)!'') == Session.loginer.id>
	                                    <a href="javascript:void(0);" onclick="delfaqa('${faqAnswer.id}')" class="au-dlt">
	                                        <i class="au-dlt-ico"></i>删除
	                                    </a>
                                    </#if>
                                </div> 
                                <#if (faqAnswer_index + 1) == ((faqQuestion.faqAnswers)?size) && ((faqQuestion.creator.id)! != (ThreadContext.getUser().getId()))>
	                                <div class="m-rpl-comment">
	                                    <div class="m-pbMod-ipt">
	                                        <textarea name="" class="u-textarea" placeholder="我有更好的答案"></textarea>
	                                    </div>
	                                    <div class="u-opa">
	                                        <!--<a class="u-face"></a>-->
	                                        <a class="u-btn-sub">发表</a>
	                                    </div>
	                                </div>
                                </#if>
                            </li>
                            </#list>
                        </ul>
		                <#else>
		                	<#if (faqQuestion.creator.id)! != (ThreadContext.getUser().getId())>
				                <ul class="m-reply-lst">
				                    <li class="reply-last">
				                        <div class="m-rpl-comment">
				                            <div class="m-pbMod-ipt">
				                                <textarea name="" id="" class="u-textarea" placeholder="我有更好的答案"></textarea>
				                            </div>
				                            <div class="u-opa">
				                                <a class="u-face"></a>
				                                <a class="u-btn-sub">发表</a>
				                            </div>
				                        </div>
				                    </li>
				                </ul>
			                <#elseif ((faqQuestion.creator.id)! == (ThreadContext.getUser().getId()))>
				                <ul class="m-reply-lst">
				                    <li class="reply-last">
				                        <div class="m-rpl-comment">
				                            <div class="m-pbMod-ipt">
				                            </div>
				                            <div class="u-opa">
				                            </div>
				                        </div>
				                    </li>
				                </ul>
			                </#if>
                        </#if>
                    </li>
                	</#list>
                <#else>
					<div class="g-no-notice-Con">
                        <p class="txt">暂时没数据！</p>
                    </div>
				</#if>
                </ul>
            </div>
        </div>
    </div>
</@faqDirective>
<form id="listFaqQuestionForm"  method="post" action="${ctx!}/faq_question" >
	<input type="hidden" name="attentionalItemView" value="${attentionalItemView!}">
	<input type="hidden" name="id" value="${id!}">
	<input type="hidden" name="_method" value="get">
	<input type="hidden" name="orders" value="CREATE_TIME.DESC">
	<input class="limit" type="hidden" name="limit" value="10" >
	<input type="hidden" name="type" value="${type!}">
	<input type="hidden" name="relationId" value="${relationId!}">
	<div id="myCoursePage" class="m-laypage"></div>
<#if paginator??>
	<#import "../../common/pagination_ajax.ftl" as p/>
	<@p.paginationAjaxFtl formId="listFaqQuestionForm" divId="myCoursePage" paginator=paginator contentId="tabContent"/>
</#if>
</form>
</div>
<#else>
		<div class="g-no-notice-Con">
            <p class="txt">您不在此工作坊内无权查看该页面！</p>
        </div>
</#if>

<script>
	$(function(){
		
		//提问回复框
		reply();
	
		//模拟百度搜索
		textarea_txt(".m-put-question .u-textarea");
		
		//
		if('${(type)!""}' == 'my'){
			$('#myQuestionCount').addClass('z-crt');
		}else{
			$('#AllCount').addClass('z-crt');
		}
		
		//创建提问
		$(".u-btn-com.u-sub").on("click",function(){
			var content = $('#faqQuestionContent').val().trim();
			if(content.length <= 0){
				alert('提问内容不能为空');
				return false;
			}
			$.post('${ctx!}/${role}_${wsid}/faq_question',{
				'_method':'POST',
				'content':content,
				'relation.id':'${relationId!""}',
				'relation.type':'workshop_question'
			},function(data){
				if(data.responseCode == '00'){
					alert("提问成功!");
					loadFaqQuestion('${relationId!}','all');
				}else{
					alert(data.responseMsg);
				}
			});
		});
		
		//创建答案
		$(".u-opa .u-btn-sub").on("click",function(){
			var _this = $(this);
			var questionId = _this.parents('.m-reply-lst').closest('li').attr('id');
			var content = _this.parents('.m-reply-lst').find('.m-pbMod-ipt .u-textarea').val().trim();
			if(content.length <= 0){
				alert('答案内容不能为空！');
				return false;
			}
			$.post('${ctx!}/${role}_${wsid}/faq_answer',{
				'_method':'POST',
				'content':content,
				'questionId':questionId
			},function(data){
				if(data.responseCode == '00'){
					alert("回答成功!");
					loadFaqQuestion('${relationId!}','all');
				}
			});
		});
		
		
	});
	
	function loadFaqQuestion(relationId ,type,id){
		if(id){
			$('#listFaqQuestionForm input[name=id]').val(id);
		}else{
			$('#listFaqQuestionForm input[name=id]').val('');
		}
		$('#listFaqQuestionForm input[name=type]').val(type);
		$.ajaxQuery('listFaqQuestionForm', 'tabContent');
	};
	
   function reply(){
        var par = $(".m-rpl-comment"),
            textarea = par.find("textarea");

        textarea.on("focus",function(){
            var _ts = $(this),
                siblingsTxt = _ts.parents(".m-qst-lst > li").siblings().find(".m-rpl-comment").find("textarea");
            _ts.addClass("focus");
            _ts.parents(".m-rpl-comment").addClass("z-crt");
            if(siblingsTxt.val() == ""){
                siblingsTxt.removeClass("z-crt");
            }
        });
        textarea.on("blur",function(){
            var _ts = $(this);
            if(_ts.val() == ""){
                _ts.parents(".m-rpl-comment").removeClass("z-crt");
                _ts.removeClass("focus");
            }
        });
    };
    
    //显示注意事项
    function attentionalItemView(type){
    	if(type == "close"){
    		$('.m-notes-lst').hide();
    		$('#listFaqQuestionForm input[name=attentionalItemView]').val('close');
    	}
    	if(type == "open"){
    		$('.m-notes-lst').show();
    		$('#listFaqQuestionForm input[name=attentionalItemView]').val('open');
    	}
    };
    
   function ask_know(){ //提问注意事项
        $(".m-put-question .u-opa .notes").on("click",function(){
           $(this).parents(".u-opa").siblings('.m-ask-notesList').addClass('canfind');
        });
        $('.m-ask-notesList .know').on("click",function(){
            $(this).parents(".m-ask-notesList").removeClass('canfind')

        });

   };
   
   function textarea_txt(textlabel){ //模拟百度搜索提示
            var textareaTXT = $(textlabel);
            var txt= textareaTXT.parent(".m-pbMod-ipt").siblings('.m-cont-point');
            textareaTXT.on("keyup",function(){
                var textareaTXT_cont = textareaTXT.val();
                if($.trim(textareaTXT_cont)!=""){
                	$('#similarQuestion').empty();
                	//重新设置问题列表
                	$.post('${ctx}/${role}_${wsid}/faq_question/entities',{
                		'_method':'get',
                		'content':textareaTXT_cont,
                		'relation.id':'${wsid}',
                		'relation.type':'workshop_question',
                	},function(response){
                		$.each(response,function(i,n){
                			var similarQuestion = $('<li><a href="javascript:;">'+n.content+'</a></li>');
                			similarQuestion.on('click',function(){
                				loadFaqQuestion('${relationId}','',n.id);
                			});
                			$('#similarQuestion').append(similarQuestion);
                		});
                		txt.addClass('canfind');
                	});
                }else{
                    txt.removeClass('canfind');
                }
            });

            $(document).on("click",function(e){ 
                var target = $(e.target); 
                if(target.closest(".u-textarea").length == 0){ //失去焦点
                    
                    if(target.closest(".m-cont-point").length == 0){
                        txt.removeClass('canfind');
                    }else{
                        var textareaTXT_cont = textareaTXT.val();
                        // alert(textareaTXT_cont)
                        txt.find("li").each(function(index, el) {
                            var this_txt = $(this).text();
                            // alert(this_txt)
                            if($.trim(textareaTXT_cont)==$.trim(this_txt)){
                                txt.removeClass('canfind');
                            }
                            
                        });
                        // txt.addClass('canfind');
                    }

                }else{ //获得焦点
                    var textareaTXT_cont = textareaTXT.val();
                    if($.trim(textareaTXT_cont)!=""){
                        txt.addClass('canfind');

                    }else{
                        txt.removeClass('canfind');
                    }
                }

            }); 

            $("body").on("click",".m-cont-point li",function(){
                 var Li_txt = $(this).text();
                 var Li_txtP = $(this).parent(".m-cont-point");
                 var u_textarea = Li_txtP.siblings('.m-pbMod-ipt').find(".u-textarea");
                 u_textarea.val(Li_txt);
                 Li_txtP.removeClass('canfind');
                 
            })


        }
    
    function delfaq(a){
    	confirm('确定删除该提问?',function(){
    		var id = $(a).closest('li').attr('id');
    		$.ajaxDelete('${ctx}/faq_question?id='+id,null,function(response){
    			if(response.responseCode =='00'){
    				alert('删除成功',function(){
    					loadFaqQuestion();
    				});
    			}else{
    				alert('删除失败');
    			}
    			
    		});
    	});
    }
    
    function delfaqa(id){
    	confirm('确定删除该回答？',function(){
    		$.ajaxDelete('${ctx}/faq_answer?id='+id,null,function(response){
    			if(response.responseCode == '00'){
    				alert('删除成功',function(){
	    				loadFaqQuestion();
	    			});
    			}else{
    				alert('操作失败');
    			}
    		});
    	});
    }
</script>