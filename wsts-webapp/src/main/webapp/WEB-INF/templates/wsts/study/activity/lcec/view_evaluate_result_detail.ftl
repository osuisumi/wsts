<@lcecEvaluateDirective lcecId=lcecId evaluateItemId=itemId getEvaluateItemSubmission='Y' getEvaluateRelation='Y'>
<div class="g-layer-bd">
		<div class="g-ipt-box">
			<p class="m-layer-contx setcolor">
				共<span class="u-color">${(evaluateRelation.submitNum)!0}</span>人参与评分，评分如下：
			</p>
			<div class="score-list">
				<ul class="list-ul">
					<#if evaluateItemSubmissions??>
						<#list evaluateItemSubmissions as evaluateItemSubmission>
							<li>
								${(evaluateItemSubmission.score)!}
							</li>
						</#list>
					</#if>
				</ul>
			</div>
		</div>
	</div>
</@lcecEvaluateDirective>