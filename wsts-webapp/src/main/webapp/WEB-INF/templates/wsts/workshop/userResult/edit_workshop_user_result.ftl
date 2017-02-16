<form id="updateWorkshopUserResultForm" action="" method="put">
	<div class="g-WSsend-news-box g-WS-appraise-layer">
		<input type="hidden" id="workshopUserIds" name="workshopUserIds" value="${workshopUserIds}">
		<input type="hidden" name="workshopId" value="${workshopId}">
		<ul class="m-appraise-study">
			<li>
				<div class="check-appraise">
					<label class="m-radio-tick"> <strong> <i class="ico"></i>
						<input type="radio" name="evaluate" value="qualified">
						</strong> </label>
					<span class="check-result">合格</span>
				</div>
				<p class="check-comment">
					学员在遵守培训时间，按要求完成各项任务，在工作中对知识的实际应用能力一般。
				</p>
			</li>
			<li>
				<div class="check-appraise">
					<label class="m-radio-tick"> <strong> <i class="ico"></i>
						<input type="radio" name="evaluate" value="excellent">
						</strong> </label>
					<span class="check-result">优秀</span>
				</div>
				<p class="check-comment">
					学员积极参加本次培训，愿意与他人分享经验和观点，主动提出建设性意见，在工作
					中对知识的实际应用能力良好。
				</p>
			</li>
			<li>
				<div class="check-appraise">
					<label class="m-radio-tick"> <strong> <i class="ico"></i>
						<input type="radio" name="evaluate" value="fail">
						</strong> </label>
					<span class="check-result">不合格</span>
				</div>
				<p class="check-comment">
					学员未能遵守培训时间，同时没有按要求完成各项任务，在工作中对知识的实际应用能
					力表现较差。
				</p>
			</li>
	
		</ul>
	
		<div class="m-addElement-btn">
			<a href="javascript:void(0);" class="btn u-inverse-btn u-cancelLayer-btn mylayer-cancel">取消</a>
			<a onclick="saveEvaluate()" href="javascript:void(0);"  class="btn u-main-btn" id="confirmLayer">批量评价</a>
		</div>
	</div>
</form>
<script>
	$(function(){
		$('input[type="radio"]').bindCheckboxRadioSimulate();
	});
	
	function saveEvaluate(){
		var evaluate = $('.on input[name=evaluate]').val();
		$.post('${ctx}/workshopUserResult/evaluate',{
			"workshopUserIds":$('#workshopUserIds').val(),
			"workshopResult":evaluate
		},function(response){
			if(response.responseCode == '00'){
				alert('操作成功',function(){
					mylayerFn.closelayer($('#updateWorkshopUserResultForm'));
					reloadListWorkshopUserResult('CREATE_TIME.DESC');
				});
			}
		});
	}
</script>