<#macro workshopTime workshop>	
	<#if workshop.type = 'train'>
		<#if (workshop.timePeriod.startTime)??>
			<#global workshopStartTime=workshop.timePeriod.startTime>
		</#if>
		<#if (workshop.timePeriod.endTime)??>
			<#global workshopEndTime=workshop.timePeriod.endTime>
		</#if>
	</#if>
</#macro>