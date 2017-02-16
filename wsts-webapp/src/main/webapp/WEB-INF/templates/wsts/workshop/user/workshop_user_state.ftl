<#macro wuState workshopId userId>
	<@workshopUserDirective workshopId=workshopId userId=userId>
		<#global state = (workshopUser.state)!''/>
	</@workshopUserDirective>
</#macro>