<#macro noContentFtl msg top='' height='' minHeight=''>
<div class="m-no-res" style="<#if top != ''>margin-top:${top }px</#if><#if height != ''>height:${height }px</#if>" <#if minHeight!=''>min-height=${minHeight}px</#if> >
       <div class="u-nores-bg">
       	<p>
			<#if msg != ''>
				${msg}
			<#else>
				暂无数据
			</#if>
		</p>
	</div>
</div>
</#macro>