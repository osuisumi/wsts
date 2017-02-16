<#macro content current=''>
	<div class="m-WSdet-where">
		<a href="${PropertiesLoader.get('wsts.list.workshop') }"><i></i>首页</a>
		<#nested>
		<ins>&gt;</ins>
		<span>${current}</span>
	</div>
</#macro>