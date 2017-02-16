(function($){
	
	$.uploadImage = function(element,config){
		this.element = element;
		this.config = config;
		
		this.init();
	};
	
	
	var $uI = $.uploadImage;
	
	$uI.fn = $uI.prototype = {
		version:'0.0.1'
	};
	
	$uI.fn.extend = $uI.extend = $.extend;
	
	$uI.fn.extend({
		init:function(){
			var _this = this;
    		var uploader = WebUploader.create({
        		swf : $('#ctx').val() + '/common/js/webuploader/Uploader.swf',
        		server : _this.config.url,
        		pick :_this.element,
        		resize : true,
        		duplicate : false,
        	});
        	
        	
        	uploader.on('fileQueued', function(file) {
        		uploader.upload();
        		//保存缩略图
			    uploader.makeThumb( file, function( error, src ) {
			        if ( error ) {
			            _this.config.image.attr('alt','预览失败');
			            return;
			        }
			        _this.config.image.src = src;
			    });
        		
        	});
        	
        	uploader.on('uploadSuccess',function(file,response){
        		if(response.responseCode =='00'){
        			serverFile = response.responseData;
        			//上传成功，显示缩略图
        			_this.config.image.attr('src',_this.config.image.src);
        			//设置参数
        			if(_this.config.afterSuccess){
        				_this.config.afterSuccess(serverFile);
        			}
        		}
        	});
		},	
		
		
	}); 
	
	
	$.fn.uploadImage = function(config){
		var uI = new $uI($(this),config);
	};
})(jQuery);
