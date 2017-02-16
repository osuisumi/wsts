<#macro incFtl cssArray=[] jsArray=[]>
	<#assign path="/wsts"> 
	<#global app_path=PropertiesLoader.get('app.wsts.path') >
	
	<link rel="Shortcut Icon" href="${app_path }/images/favicon.ico">
	<!-- common -->
	<link rel="stylesheet" href="${ctx }/common/css/common.base.min.css">

 	<link rel="stylesheet" href="${app_path }/css/app.min.css">
	<script type="text/javascript" src="${ctx }/common/js/common.base.min.js"></script>
	
	<script type="text/javascript" src="${app_path }/js/laypage/laypage.js"></script>
	
	<script type="text/javascript" src="${path }/js/wsts.min.js"></script>
	
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
