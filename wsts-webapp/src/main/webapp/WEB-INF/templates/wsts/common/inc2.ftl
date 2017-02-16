<#macro incFtl cssArray=[] jsArray=[]>
	<#assign path="/wsts"> 
	<#global app_path=PropertiesLoader.get('app.wsts.path') >
	<link rel="stylesheet" href="${app_path}/css/reset.css">
	<link rel="stylesheet" href="${app_path}/css/activity-common.css">
	<link rel="stylesheet" href="${app_path}/css/workshop.css">
	
	<script type="text/javascript" src="${ctx }/common/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx }/common/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${path}/js/common.js" charset="utf-8"></script>
	<script type="text/javascript" src="${path}/js/sip-common.js" charset="utf-8"></script>
	<script type="text/javascript" src="${path}/js/index.js" charset="utf-8"></script>
	<script type="text/javascript" src="${app_path }/js/laypage/laypage.js"></script>
	<script type="text/javascript" src="${path}/js/activity-common.js" charset="utf-8"></script>
	<link rel="stylesheet" href="${ctx }/common/js/mylayer/v1.0/skin/default/mylayer.css">
	<script type="text/javascript" src="${ctx }/common/js/mylayer/v1.0/mylayer.js"></script>
	
	<#include '/wsts/common/validate_js.ftl'/>
	
	<#if jsArray??>
		<#list jsArray as js>
			<#if js=='ueditor'>
				<script type="text/javascript" src="${ctx }/common/js/ueditor/ueditor.config.js"></script>
				<script type="text/javascript" src="${ctx }/common/js/ueditor/ueditor.all.js"></script>
				<script type="text/javascript" src="${ctx }/common/js/ueditor/lang/zh-cn/zh-cn.js"></script>
				<script type="text/javascript" src="${ctx }/common/js/ueditorUtils.js"></script>
			<#elseif js=='webuploader'>
				<#include '/wsts/common/webuploader_js.ftl'/>
			<#elseif js=='flowplayer'>
				<#include '/wsts/common/flowplayer_js.ftl'/>
			<#elseif js=='activity'>
				<script type="text/javascript" src="${path}/js/tag.js" charset="utf-8"></script>
				<script type="text/javascript" src="${path }/js/addCourse.js"></script>
				<script type="text/javascript" src="${path }/js/courseLearning.js"></script>
				<script type="text/javascript" src="${path }/js/activity-file.js"></script>
				<script type="text/javascript" src="${path }/js/addTopic.js"></script>
				<script type="text/javascript" src="${path }/js/evaluateStar.js"></script>
				<script type="text/javascript" src="${path }/js/addTestQuestion.js"></script>
				<script type="text/javascript" src="${path }/js/activity-argue.js"></script>
			<#elseif js=='selectUser'>
				<link rel="stylesheet" href="${path}/css/userSelect.css">
				<script type="text/javascript" src="${path}/js/selectUser.js" charset="utf-8"></script>
			<#elseif js=='datePicker'>
				<script type="text/javascript" src="${ctx }/common/js/My97DatePicker/WdatePicker.js"></script>
			<#else>
				<script type="text/javascript" src="${js}"></script>
			</#if>
		</#list>
	</#if>
	<#if cssArray??>
		<#list cssArray as css>
			<link rel="stylesheet" href="${css}">	
		</#list>
	</#if>
	
</#macro>
