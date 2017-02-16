<#macro content>
	<#global hasMasterRole=SecurityUtils.getSubject().hasRole('master_'+wsid)>
	<#global hasMemberRole=SecurityUtils.getSubject().hasRole('member_'+wsid)>
	<#global hasStudentRole=(SecurityUtils.getSubject().hasRole('student_'+wsid) || SecurityUtils.getSubject().hasRole('train_inspector'))>
	<#if hasMasterRole>
		<#global role="master">
	<#elseif hasMemberRole>
		<#global role="member">
	<#elseif hasStudentRole>
		<#global role="student">
	<#else>
		<#global role="guest">
	</#if>
</#macro>