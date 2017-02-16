<#macro content>
<div class="edit-dt-tl">
	<input type="text" class="inner-edit-tl" placeholder="请输入标题"/>
	<div class="edit-time">
		<span class="m-selectbox m-start-Y"> <strong><span class="simulateSelect-text">2016</span><i class="trg"></i></strong>
			<select class="selectCourseType">
				<#assign year=(.now?string('yyyy'))?number>
				<#list [0,1,2,3,4] as addY>
					<option >${year+addY}</option>
				</#list>
			</select> </span>
		<span class="time-txt">年</span>
		<span class="m-selectbox m-start-M"> <strong><span class="simulateSelect-text">12</span><i class="trg"></i></strong>
			<select class="selectCourseType">
				<option >01</option>
				<option >02</option>
				<option >03</option>
				<option >04</option>
				<option >05</option>
				<option >06</option>
				<option >07</option>
				<option >08</option>
				<option >09</option>
				<option >10</option>
				<option >11</option>
				<option >12</option>
			</select> </span>
		<span class="time-txt">月</span>
		<span class="border"></span>
		<span class="m-selectbox m-end-Y"> <strong><span class="simulateSelect-text">2016</span><i class="trg"></i></strong>
			<select class="selectCourseType">
				<#assign year=(.now?string('yyyy'))?number>
				<#list [0,1,2,3,4] as addY>
					<option >${year+addY}</option>
				</#list>
			</select> </span>
		<span class="time-txt">年</span>
		<span class="m-selectbox m-end-M"> <strong><span class="simulateSelect-text">12</span><i class="trg"></i></strong>
			<select class="selectCourseType">
				<option >01</option>
				<option >02</option>
				<option >03</option>
				<option >04</option>
				<option >05</option>
				<option >06</option>
				<option >07</option>
				<option >08</option>
				<option >09</option>
				<option >10</option>
				<option >11</option>
				<option >12</option>
			</select> </span>
		<span class="time-txt">月</span>

	</div>
	<div class="m-button">
		<div class="add-button aad-confirm">
			保存
		</div>
		<div class="add-button add-del">
			取消
		</div>
	</div>
</div>
</#macro>