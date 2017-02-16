<div class="m-training-tb">
	<table class="m-tb-score">
		<tbody>
			<tr>
				<th width="25%">日期</th>
				<th width="50%">得分项</th>
				<th width="25%">积分<i class="u-ico-hg" style="display:none"></i></th>
			</tr>
			<@pointRecordsDirective relationId=workshopId userId=Session.loginer.id pointStrategyRelationId="wsts">
				<#list pointRecords as pr>
					<tr>
						<td>${TimeUtils.formatDate(pr.createTime,'yyyy/MM/dd HH:mm')}</td>
						<td>${(pr.pointStrategy.summary)!}</td>
						<td class="num">${(pr.pointStrategy.point)!}</td>
					</tr>
				</#list>
			</@pointRecordsDirective>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="3"><span class="u-score">累计积分：<b class="sumPoint"></b></span></td>
			</tr>
		</tfoot>
	</table>
</div>

<script>
	$(function(){
		$('.sumPoint').text($('#myPoint').val());
		
		//设置是否合格
		var totalPoint=parseInt($('#myPoint').val());
		var qualifiedPoint =parseInt($('#qualifiedPoint').val());
		var workshopResult = $('#workshopResult').val();
		if(totalPoint>=qualifiedPoint){
			if(workshopResult == 'qualified' || workshopResult == 'excellent'){
				$('.u-ico-hg').show();
			}
		}
	});
	
</script>