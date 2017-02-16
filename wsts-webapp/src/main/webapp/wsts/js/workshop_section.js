function option_check(edit_Sib, inputTime, start_YY) {//日期与select下被选中的option一样
	edit_Sib.find(inputTime).find('option').each(function(index, el) {
		var this_val = $(this).val();
		if (this_val == start_YY) {
			$(this).prop('selected', true);
			$(this).attr('selected','selected');
		}
	});
}

function button_edit(buttonedit) {//研修任务阶段的编辑g-train-lst
	$(".g-train-lst").on("click", buttonedit, function() {
		var edit_Pa = $(this).parents(".m-guid-lst").find(".dt-tl"), edit_Sib = edit_Pa.siblings('.edit-dt-tl'), input_tl = edit_Sib.find('.inner-edit-tl'), aad_confirm = edit_Sib.find('.aad-confirm'), m_start_Y = edit_Sib.find(".m-start-Y"), m_start_M = edit_Sib.find(".m-start-M"), m_startY = m_start_Y.find(".simulateSelect-text"), m_startM = m_start_M.find(".simulateSelect-text"), m_start_YY = m_startY.text(), m_start_MM = m_startM.text(), m_end_Y = edit_Sib.find(".m-end-Y"), m_end_M = edit_Sib.find(".m-end-M"), m_endY = m_end_Y.find(".simulateSelect-text"), m_endM = m_end_M.find(".simulateSelect-text"), m_end_YY = m_endY.text(), m_end_MM = m_endM.text(), start_YY = edit_Pa.find(".start-Y").text(), start_MM = edit_Pa.find(".start-M").text(), end_YY = edit_Pa.find(".end-Y").text(), end_MM = edit_Pa.find(".end-M").text(), inner_tl = $(this).siblings('.inner-tl').text();

		var unfind_num = $(".g-train-lst .m-guid-lst").find(".edit-dt-tl.canfind").length;
		if (unfind_num >= 1) {
			alert("你还在编辑标题，请先编辑该标题");
			$(".m-guid-lst").find(".inner-edit-tl").focus();
		} else {
			edit_Sib.addClass('canfind').removeClass('unfind');
			edit_Pa.addClass('unfind').removeClass('canfind');
		}

		input_tl.val(inner_tl).focus();
		m_startY.text(start_YY);
		m_startM.text(start_MM);
		m_endY.text(end_YY);
		m_endM.text(end_MM);
		//使每个select对应的被选中的option和u-date里的日期一样
		option_check(edit_Sib, ".m-start-Y", start_YY);
		option_check(edit_Sib, ".m-start-M", start_MM);
		option_check(edit_Sib, ".m-end-Y", end_YY);
		option_check(edit_Sib, ".m-end-M", end_MM);
		$(".m-selectbox select").simulateSelectBox();

	});

	//章节保存按钮
	$(".g-train-lst").on("click", ".aad-confirm", function() {
		var _this = this;
		var confirm_Pa = $(this).parents(".edit-dt-tl"), confirm_Sib = confirm_Pa.siblings('.dt-tl'), input_tl = confirm_Pa.find('.inner-edit-tl').val(), inner_tl = confirm_Sib.find('.inner-tl'), m_start_Y = confirm_Pa.find(".m-start-Y"), m_start_M = confirm_Pa.find(".m-start-M"), m_startY = m_start_Y.find(".simulateSelect-text"), m_startM = m_start_M.find(".simulateSelect-text"), m_start_YY = m_startY.text(), m_start_MM = m_startM.text(), m_end_Y = confirm_Pa.find(".m-end-Y"), m_end_M = confirm_Pa.find(".m-end-M"), m_endY = m_end_Y.find(".simulateSelect-text"), m_endM = m_end_M.find(".simulateSelect-text"), m_end_YY = m_endY.text(), m_end_MM = m_endM.text(), start_Y = confirm_Sib.find(".start-Y"), start_M = confirm_Sib.find(".start-M"), end_Y = confirm_Sib.find(".end-Y"), end_M = confirm_Sib.find(".end-M");
		start_Y.text(m_start_YY);
		start_M.text(m_start_MM);
		end_Y.text(m_end_YY);
		end_M.text(m_end_MM);
		confirm_Pa.addClass('unfind').removeClass('canfind');
		confirm_Sib.addClass('canfind').removeClass('unfind');
		
		var workshopId = $('#currentWorkshopId').val();
		var start =  m_start_YY + "-" + m_start_MM;
		var end = m_end_YY + "-" + m_end_MM;
		var sortNum = $(".g-train-lst li").size() + 1;
		var addActivityBtn = $(this).closest('li').find('.Add-new');
		var sectionId = $(this).closest('li').attr('sectionId');
		
		if ($.trim(input_tl) == "") {
			confirm_Pa.addClass('canfind').removeClass('unfind');
			confirm_Sib.addClass('unfind').removeClass('canfind');
			$(".m-guid-lst").find(".inner-edit-tl").focus();
			alert('标题不能为空');
			return;
		}
		
		var url = $('#ctx').val()+'/'+$('#role').val()+'_'+$('#currentWokshopId').val()+'/workshopSection';
		var method = "POST";
		var id = "";
		if(sectionId){
			url = $('#ctx').val()+'/'+$('#role').val()+'_'+$('#currentWokshopId').val()+'/workshopSection/'+sectionId;
			method = "PUT";
			id = sectionId;
		}
		
		$.post(url,{
			"_method":method,
			"id":id,
			'workshopId':workshopId,
			'title':input_tl,
			'startTime':start,
			'endTime':end,
			'sortNum':sortNum,
			'source':'workshop_section_js'
		},function(response){
			if(response.responseCode == '00'){
				if(response.responseData){
					$(_this).closest('li').attr('sectionId',response.responseData.id);
				};
				$(_this).closest('li').find('option').removeAttr('selected');
				inner_tl.text(input_tl);
				addActivityBtn.show();
			}else{
				if(response.responseMsg){
					alert(response.responseMsg);
				}else{
					alert('创建失败');
				}
				confirm_Pa.addClass('canfind').removeClass('unfind');
				confirm_Sib.addClass('unfind').removeClass('canfind');
				$(".m-guid-lst").find(".inner-edit-tl").focus();
			}
		});
		
		/*
		if ($.trim(input_tl) == "") {
			confirm_Pa.addClass('canfind').removeClass('unfind');
			confirm_Sib.addClass('unfind').removeClass('canfind');
			$(".m-guid-lst").find(".inner-edit-tl").focus();

		} else {
			inner_tl.text(input_tl);
		}*/

	});
	
	$(".g-train-lst").on("click", ".add-del", function() {//取消编辑
		var del_Pa = $(this).parents(".edit-dt-tl"), del_Sib = del_Pa.siblings('.dt-tl'), inner_tlText = del_Sib.find('.inner-tl').text();
		if ($.trim(inner_tlText) == "") {
			$(this).parents(".m-guid-lst").parent("li").remove();
		} else {
			del_Pa.addClass('unfind').removeClass('canfind');
			del_Sib.addClass('canfind').removeClass('unfind');
		}

	});

}

function array_num() {//编排阶段的顺序
	$(".g-train-lst li").each(function(index, el) {
		var arrayNum = $(this).index() + 1
		$(this).find(".u-order").children('b').text("0" + arrayNum + "");
		if (arrayNum >= 10) {
			$(this).find(".u-order").children('b').text(arrayNum);
		}
	});

}

function del_stage() {//删除阶段
	$(".g-train-lst").on("click", ".button.button-del", function() {
		var _this = this;
		var activityNum = $(_this).closest('li').find('.activityDd').size();
		var message = activityNum >0?'删除该阶段将删除该阶段下所有活动，确定删除？':'确定删除该阶段？';
		confirm(message,function(){
			var sectionId = $(_this).closest('li').attr('sectionId');
			$.post($('#ctx').val()+'/'+$('#role').val()+'_'+$('#currentWokshopId').val()+'/workshopSection/'+sectionId,{
				"_method":"DELETE"
			},function(response){
				if(response.responseCode == '00'){
					$(_this).parents("li").remove();
					array_num();
					var array_Li = $(".g-train-lst li").length;
					if (array_Li == 1) {
						$(".u-ico.ico3").addClass('unfind');
					} else {
						$(".u-ico.ico3").removeClass('unfind');
					}
				}else{
					alert('删除失败');
				}
			});			

		});
	});

}

function add_new_Array() {//添加新阶段
	$(".g-manage-train-ready").on("click", ".add-button.add-new-stage", function() {
		var unfind_num = $(".g-train-lst .m-guid-lst").find(".edit-dt-tl.canfind").length;
		if (unfind_num >= 1) {
			alert("你还在编辑标题，请先编辑该标题");
			$(".m-guid-lst").find(".inner-edit-tl").focus();
		} else {
			$(this).parents(".g-manage-train-ready").find(".g-train-lst").append($(".g-train-lst-clone").children('li').clone());

			var inner_tlEmpty = $(".m-train-lst-clone").find(".inner-tl").text();
			if ($.trim(inner_tlEmpty) == "") {
				$(".m-train-lst-clone").find(".dt-tl").addClass('unfind').removeClass('canfind');
				$(".m-train-lst-clone").find(".edit-dt-tl").removeClass('unfind').addClass('canfind');
			}
			$(".m-guid-lst").find(".inner-edit-tl").focus();
			array_num();
		}
		var array_Li = $(".g-train-lst li").length;
		if (array_Li == 1) {
			$(".u-ico.ico3").addClass('unfind');
		} else {
			$(".u-ico.ico3").removeClass('unfind');
		}

		//模拟下拉框
		$(".m-selectbox select").simulateSelectBox();
		new_option(".m-start-Y");
		new_option(".m-start-M");
		new_option(".m-end-Y");
		new_option(".m-end-M");

	});

}

function new_option(mstartY) {
	$(".g-manage-train-ready").on("click", mstartY, function() {
		var this_val = $(this).find(".simulateSelect-text").text();
		option_check($(this), "select", this_val);
	})
}