<#macro content>
<ul class="g-train-lst-clone">
	<li class="m-train-lst-clone">
		<dl class="m-guid-lst m-WSdet-tl">
			<dt>
				<div class="dt-tl">
					<span class="inner-tl"></span>
					<span class="u-date"><span class="start-Y">2015</span>年<span class="start-M">3</span>月～<span class="end-Y">2016</span>年<span class="end-M">10</span>月</span>
					<span class="button button-del"><i class="u-delete"></i>删除</span>
					<span class="button button-edit"><i class="u-edit"></i>编辑</span>
				</div>
				<div class="edit-dt-tl">
					<input type="text" class="inner-edit-tl" placeholder="请输入标题" />
					<div class="edit-time">
						<span class="m-selectbox m-start-Y"> <strong><span class="simulateSelect-text">${.now?string('yyyy')}</span><i class="trg"></i></strong>
							<select class="selectCourseType">
								<#assign year=(.now?string('yyyy'))?number>
								<#list [0,1,2,3,4] as addY>
									<option <#if addY_index = 0>selected="selected"</#if> >${year+addY}</option>
								</#list>
							</select> </span>
						<span class="time-txt">年</span>
						<span class="m-selectbox m-start-M"> <strong><span class="simulateSelect-text">1</span><i class="trg"></i></strong>
							<select class="selectCourseType">
								<option selected="selected">01</option>
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
								<option>12</option>
							</select> </span>
						<span class="time-txt">月</span>
						<span class="border"></span>
						<span class="m-selectbox m-end-Y"> <strong><span class="simulateSelect-text">${.now?string('yyyy')}</span><i class="trg"></i></strong>
							<select class="selectCourseType">
								<#assign year=(.now?string('yyyy'))?number>
								<#list [0,1,2,3,4] as addY>
									<option <#if addY_index = 0>selected="selected"</#if> >${year+addY}</option>
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
								<option selected="selected">12</option>
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
				<span class="u-order"><b>00</b>阶段</span>
				<i class="u-ico ico3"></i>
			</dt>

		</dl>
		
		<div class="Add-new" style="display:none">
			<a href="javascript:;" class="add-new-train">+ 添加新任务</a>
		</div>
		
	</li>

</ul>

</#macro>
