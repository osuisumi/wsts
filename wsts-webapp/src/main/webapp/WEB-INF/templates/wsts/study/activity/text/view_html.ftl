<#macro viewHtmlFtl textInfoId aid relationId>
	<#assign cid=CSAIdObject.getCSAIdObject().cid>
	<#assign hasTeachRole=SecurityUtils.getSubject().hasRole('course_teacher_'+cid)>
	<@textInfoUser textInfoId=textInfoId  activityId=aid relationId=relationId>
		<#assign textInfoRelation=textInfoUser.textInfoRelation>
		<#assign textInfo=textInfoRelation.textInfo>
		<#assign activity=(activityResult.activity)!>
		<#global inCurrentDate=TimeUtils.inCurrentDate((activity.timePeriod)!'', (workshop.timePeriod)!'')> 
		<div id="viewHtmlDiv" class="g-study-dt">
			<#if !hasTeachRole>
				<div class="g-study-prompt">
					<p>
						<#assign view_num=(activity.attributeMap['view_num'].attrValue)!'0'>
						<#if view_num == ''>
							<#assign view_num='0'>
						</#if>
						<#if (view_num?number == 0)>
							观看文档即可完成活动
						<#else>
							要求观看文档<span>${view_num }</span>次
						</#if>
						<i>/</i>
						您已观看<span>${(textInfoUser.viewNum)!0 }</span>次
					</p>
	            	<i class="close">X</i>
	            </div>
	        </#if>
	        <div class="g-studyAct-box spc">
	            <div class="g-studyAct-time">
	                <div class="am-main-r">
						<#assign timePeriods=[]>
						<#assign timePeriods = timePeriods + [(activity.timePeriod)!]>
						<#assign timePeriods = timePeriods + [(workshop.timePeriod)!]>
						<#import "/wsts/common/show_time.ftl" as st /> 
						<@st.showTimeFtl timePeriods=timePeriods label="活动" />
	                </div>    
	            </div>
	            <h2 class="title">${(textInfo.title)!}</h2>
	            <#if 'link' == (textInfo.type)!''>
	            	<a href="javascript:;" class="u-full-screen" id="fullScreen"></a>
	            	<iframe src="${textInfo.content }" width="100%" height="440px;"></iframe>
	            	<div id="fullScreenWrap">
			            <div class="fullScreen-shadow"></div>
			            <iframe src="${textInfo.content }" frameborder="0"></iframe>
			            <a href="javascript:;" class="close" title="关闭"></a>
			        </div>
			    <#elseif 'file' == (textInfo.type)!''>
			        <a href="javascript:;" class="u-full-screen" id="fullScreen"></a>
			        <div id="pdfwrap" style="width:100%;height:500px;">
                                           
                    </div>
			        <div id="fullScreenWrap">
			            <div class="fullScreen-shadow"></div>
			            <div id="iframeBox">
			                <div id="pdfframe" class="pdfframe"></div>
			            </div>
			            <a href="javascript:;" class="close" title="关闭"></a>
			        </div>
                    <script>
                  		//加载pdf
                   		PDFObject.embed('${FileUtils.getFileUrl(textInfo.contentMap.pdf_url)}', "#pdfwrap");
                   		PDFObject.embed('${FileUtils.getFileUrl(textInfo.contentMap.pdf_url)}', "#pdfframe");
                    </script>
	            <#else>
	            	<ul>
		                <li class=" m-topic-studyct">
		                    ${(textInfo.content)!}
		                </li> 
		            </ul> 
	            </#if>
	        </div>
	    </div>
	    <#if inCurrentDate>
	    	<script>
	    		$(function(){
	    			var interval = '${(activity.attributeMap[incInterval])!""}';
	    			if(interval == null || interval == ''){
	    				interval = 1;
	    			}else{
	    				interval = parseFloat(interval);
	    			}
	    			$('#viewHtmlDiv').oneTime(interval * 60 + 's', 'A', function() {
	    				$.put('${ctx}/${aid}/study/course/textInfo/user/updateAttempt', 'id=${textInfoUser.id}');
	    			});
	    		})
	    	</script>
	    </#if>
	</@textInfoUser>
	<script>
		$(function(){
			fullScreen();
		});
		
		function fullScreen(){
		    $("#fullScreen").on('click',function(){
		        $('#fullScreenWrap').show();
		        $("#clPageBody").css('overflow','hidden');
		    });
	
		    $("#fullScreenWrap .close").on("click",function(){
		        $("#fullScreenWrap").hide();
		        $("#clPageBody").css('overflow','auto');
		    });
		}
	</script>
</#macro>