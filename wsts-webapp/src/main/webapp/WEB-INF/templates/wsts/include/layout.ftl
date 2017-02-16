<#macro layout cssArray=[] jsArray=[]>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
		<meta name="author" content="smile@kang.cool">
		<meta name="description" content="hello">
		<meta name="keywords" content="a,b,c">
		<meta http-equiv="Window-target" content="_top">
		<#import "../common/inc.ftl" as inc />
		<@inc.incFtl cssArray=cssArray jsArray=jsArray/>
		<title>工作坊平台</title>
	</head>
	<body id="workshop">
		<div class="g-wrap">
			<div class="g-header-frame">
				<#import "top.ftl" as top />
				<@top.topFtl />
			</div>
			<div id="content" class="g-frame">
				<#nested>
			</div>
			<div class="g-footer-frame">
				<#import "${app_path }/include/footer.ftl" as footer />
				<@footer.footerFtl />
			</div>
		</div>
	</body>
	<form id="downloadFileForm" action="/file/downloadFile" method="post" target="_blank">
		<input type="hidden" name="id">
		<input type="hidden" name="fileName">
		<input type="hidden" name="fileRelations[0].type"> 
		<input type="hidden" name="fileRelations[0].relation.id"> 
	</form>
	<form id="updateFileForm" target="_blank">
		<input type="hidden" name="id">
		<input type="hidden" name="fileName">
	</form>
	<form id="deleteFileRelationForm" target="_blank">
		<input type="hidden" name="fileId">
		<input type="hidden" name="relation.id">
		<input type="hidden" name="type">
	</form>
	<form id="deleteFileInfoForm" target="_blank">
		<input type="hidden" name="id">
	</form>
	<form id="downloadTemplateFileForm" action="/template_file/download" method="post" target="_blank">
		<input type="hidden" name="fileName">
	</form>
</html>
</#macro>
