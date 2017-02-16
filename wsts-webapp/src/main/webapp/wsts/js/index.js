function closeLayer(){
	var index = parent.layer.getFrameIndex(window.name); 
	parent.layer.close(index); 
}

function downloadFile(id, fileName, type, relationId) {
	$('#downloadFileForm input[name="id"]').val(id);
	$('#downloadFileForm input[name="fileName"]').val(fileName);
	$('#downloadFileForm input[name="fileRelations[0].type"]').val(type);
	$('#downloadFileForm input[name="fileRelations[0].relation.id"]').val(relationId);
	//goLogin(function(){
		$('#downloadFileForm').submit();
	//});
}

function updateFile(id, fileName) {
	$('#updateFileForm input[name="id"]').val(id);
	$('#updateFileForm input[name="fileName"]').val(fileName);
	$.post('/file/updateFileInfo.do', $('#updateFileForm').serialize());
}

function deleteFileRelation(fileId, relationId, relationType) {
	$('#deleteFileRelationForm input[name="fileId"]').val(fileId);
	$('#deleteFileRelationForm input[name="relation.id"]').val(relationId);
	$('#deleteFileRelationForm input[name="type"]').val(relationType);
	$.post('/file/deleteFileRelation.do', $('#deleteFileRelationForm').serialize());
}

function deleteFileInfo(fileId) {
	$('#deleteFileInfoForm input[name="id"]').val(fileId);
	$.post('/file/deleteFileInfo.do', $('#deleteFileInfoForm').serialize());
}

function createCourseIndex(){
	window.location.href = '/make/course?orders=CREATE_TIME.DESC'
}

function previewFile(fileId){
	mylayerFn.open({
        type: 2,
        title: '预览文件',
        fix: true,
        area: [$(window).width() * 99 / 100, $(window).height() * 99 / 100],
        content: '/file/previewFile?fileId='+fileId,
        shadeClose: true
    });
}

function loadComments(relationId){
	$('#commentsDiv').load($('#ctx').val()+'/comment/activity/'+relationId,'orders=CREATE_TIME.DESC');
}

function goEditUser(userId){
	mylayerFn.open({
        type: 2,
        title: '个人资料',
        fix: false,
        area: [650, $(window).height()],
        content: 'wsts/userInfo/'+userId+'/edit',
        shadeClose: true
    });
};

function goChangePassword(){
	mylayerFn.open({
        type: 2,
        title: '修改密码',
        fix: false,
        area: [620, 480],
        content: '/wsts/account/edit_password',
    });
}

function downLoadTemplate(fileName){
	$('#downloadTemplateFileForm input[name="fileName"]').val(fileName);
	$('#downloadTemplateFileForm').submit();
}


//文件夹导航
function FileResourceNAV(){
		this.bars = new Array();
		this.push = function(frId,frName,callBack){
			var bar = new Object();
			bar.frId = frId;
			bar.frName = frName;
			this.bars.push(bar);
			if(callBack){
				callBack();
			}
		};
		
		this.pop = function(callBack){
			var result = this.bars.pop();
			if(callBack){
				callBack();
			};
			return result;
		};
		
		this.getLast = function(){
			if(this.bars.length<=0){
				return undefined;
			}else{
				return this.bars[this.bars.length-1];
			}
		};
		
		this.jump = function(frId,callBack){
			var flg = true;
			while(flg){
				if(this.bars.length<=0){
					return;
				}
				if(this.bars[this.bars.length-1].frId != frId){
					this.pop();
				}else{
					flg = false;
				}
			}
			if(callBack){
				callBack();
			}
		};
		
		this.flush = function(){
			this.bars = new Array();
		};
	}
