///<jscompress sourcefile="common.js" />
$(document).ready(function(){
    //公共模块js初始化
    commonJs.fn.init();
    //初始化占位符
    $("input,textarea").placeholder();
    //模拟下拉框
    $(".m-selectbox select").simulateSelectBox();
    //点选按钮模拟
    $('.m-radio-tick input').bindCheckboxRadioSimulate();
    //多选按钮模拟
    $('.m-checkbox-tick input').bindCheckboxRadioSimulate();
})
//定义公共模块js
var commonJs = $(window).commonJs || {};

commonJs.fn = {
    init : function(){
        var _this = this;
        //自定义下拉框
        //_this.simulateSelectBox();
        //返回顶部
        _this.toTop();
        //搜索框动态效果
        _this.searchAnimate();
    },
    //弹出框
    aJumpLayer : function(layer){
        var layer = $(layer),
            width = layer.innerWidth(),
            height = layer.innerHeight();
        layer.show().css({'margin-top':-height/2+'px','margin-left':-width/2+'px'});
        $('.m-blackbg').show();

        /*clickBtn.bind('click',function(){
            layer.show().css({'margin-top':-height/2+'px','margin-left':-width/2+'px'});
            $('.m-blackbg').show().css("opacity","0.5");

        })*/
        layer.find('.u-confirm-btn').bind('click',function(){
            layer.hide();
            $('.m-blackbg').hide();
        })
        layer.find('.u-cancel-btn').bind('click',function(){
            layer.hide();
            $('.m-blackbg').hide();
        })
        layer.find('.u-close-btn').bind('click',function(){
            layer.hide();
            $('.m-blackbg').hide();
        })
    },
    //打开绝对定位弹出框
    openABlayer : function(abLayer){
        var $layersLayout = $(abLayer),
            $whiteBg = $(".m-blackbg"),
            scrollTop = $(window).scrollTop();
        //打开弹出框
        $layersLayout.show().css('top',scrollTop + 100 + 'px');
        $whiteBg.show();
        //关闭弹出框
        this.closeABlayer($layersLayout,$whiteBg);

    },
    //关闭绝对定位弹出框
    closeABlayer : function($layersLayout,$whiteBg){
        var $colseBtn = $layersLayout.find(".u-close-btn"),
            $cancelBtn = $layersLayout.find(".u-cancel-btn"),
            $canfirmlBtn = $layersLayout.find(".u-confirm-btn");
        $colseBtn.on("click",function(){
            $layersLayout.hide();
            $whiteBg.hide();
        });
        $cancelBtn.on("click",function(){
            $layersLayout.hide();
            $whiteBg.hide();
        });
        $canfirmlBtn.on("click",function(){
            $layersLayout.hide();
            $whiteBg.hide();
        });
    },
    //返回顶部
    toTop : function(){
        //点击返回顶部
        $(document).on("click","#toTop",function(){
            $('html,body').animate({
                scrollTop: 0
            },100);
            return false;
        })
    },
    //搜索框动态效果
    searchAnimate : function(){
        //判断是否为IE浏览器，如果为IE浏览器，则使用jquery动画方式，反之采用css动画
        var navigatorName = "Microsoft Internet Explorer";  
        var isIE = false;   
        if( navigator.appName == navigatorName ){   
            isIE = true;   
            IE_fn();
        }else{   
            //alert("not ie"); 
            noIE_fn();  
        }   

        //IE浏览器使用jquery动画方式
        function IE_fn(){
            $(".m-srh .ipt").on('focus',function(){
                var $this = $(this),
                icon = $this.next();
                //如果value值为空，执行动画
                if($this.val() == ''){
                    $this.stop().animate({
                        'width' : 146 + 'px',
                        'padding-left' : 12 + 'px'
                    },500);
                    icon.stop().animate({
                        'right': 10 + 'px'
                    },500);
                }
            });
            //判断失去焦点时，用户是否输入文字
            $(".m-srh .ipt").on('blur',function(){
                var $this = $(this),
                    icon = $this.next();
                //如果value值为空，执行缩回动画，反之不执行
                if($this.val() == ''){
                    $this.stop().animate({
                        'width' : 60 + 'px',
                        'padding-left' : 98 + 'px'
                    },500);
                    icon.stop().animate({
                        'right': 100 + 'px'
                    },500);
                }
            });    
        };
        //非IE浏览器使用css3动画
        function noIE_fn(){
            $(".m-srh .ipt").on('blur',function(){
                var srhBox = $(this).parent();
                //如果value值为空，执行缩回动画，反之不执行
                if($(this).val() == ''){
                    srhBox.removeClass('hasValue');
                }else {
                    srhBox.addClass('hasValue');
                }
            });
        };
    },
    //课程指引提示 
    guideHint : function(guideHtml,nextGuideFn){
        /*
            guideHtml   : 指引提示html段，必有参数
            nextGuideFn : 存在下一步指引时，执行的方法
        */

        //默认没有下一步指引
        var nextGuide = false;
        //判断是否存在下一步指引
        if(nextGuideFn == "" || nextGuideFn == 'undefined' || nextGuideFn == null || nextGuideFn == undefined) {
        }else {
            nextGuide = true;
        }
        //插入指引html段
        $("body").append(guideHtml);

        //点击关闭
        $(".g-guide-hint .confirm").on('click',function(){
            var $this = $(this);
            //关闭指引
            $this.parents(".g-guide-hint").remove();
            $(".m-guideShade").remove();
            //如果存在下一步，执行下一步方法
            if(nextGuide){
                nextGuideFn();
            }
        });
    }
};




/*--start----多个同类名选项卡----start---*/
$.fn.extend({
    myTab : function(options)
    {
        var defaults = 
        {
            pars    : '.myTab',   //最外层父级
            tabNav  : '.tabNav',  //标签导航
            li      : 'li',       //标签
            tabCon  : '.tabCon',  //区域父级
            tabList : '.tabList', //t区域模块
            cur     : 'cur',      //选中类
            eType   : 'click',    //事件
            page    : 0 //默认显示第几个模块
        }
        var options = $.extend(defaults,options),
        _ts = $(this),
        tabBtn  = _ts.find(options.tabNav).find(options.li);
        tabList  = _ts.find(options.tabCon).find(options.tabList);
        this.each(function(){
            tabBtn.removeClass(options.cur);
            tabBtn.eq(options.page).addClass(options.cur);
            tabList.hide();
            tabList.eq(options.page).show();
            tabBtn.eq(options.page).show();
            tabBtn.on(options.eType,function(){
                var index = $(this).parents(options.tabNav).find(options.li).index(this);
                $(this).addClass(options.cur).siblings().removeClass(options.cur);
                $(this).parents(options.pars).find(options.tabCon).find(options.tabList).eq(index).css({'display':'block'}).siblings().css({'display':'none'});
            })
        })
        return this;
    }
});
/*--end-----多个同类名选项卡---end---*/


/*--start-----下拉多选款select按钮模拟---start---*/
(function ($) {
    $.fn.simulateSelectBox = function (options) {
        var settings = {//默认参数
            selectText: '.simulateSelect-text',
            byValue: null
        };
        //$.extend(true, settings, options);
        var options = $.extend(true,settings,options),
            _ts = this,
            selectText = options.selectText, //下拉框模拟文字class
            byValue = options.byValue;  //传入value值，重置默认选中


        return _ts.each(function(){
            var $this = $(this);
            //清除其他选中
            $this.find('option').prop('selected',false);
            //alert($this.find('option:selected').text());
            //判断是否传入value值
            if(byValue == "" || byValue == null || byValue == undefined){
                $this.find('option').eq(0).prop('selected',true);
            }else {
                //console.log(byValue);
                //编辑选项，匹配传入的value值
                for(var i = 0; i < $this.find('option').length; i++){
                    if($this.find('option').eq(i).val() == byValue) {
                        //设置传入的value值选项为默认选中
                        $this.find('option').eq(i).prop('selected',true);
                    }
                }
            }
            //改变模拟下拉框的文字
            $this.parent().find(selectText).text($this.find('option[selected="selected"]').text());
            $this.parent().find(selectText).text($this.find('option:selected').text());
        //点击下拉改变
        }).on('change',function(){
            //改变模拟下拉框的文字
            $(this).parent().find(selectText).text($(this).find('option:selected').text());
        });

    };
})(jQuery);
/*--end-----下拉多选款select按钮模拟---end---*/

/*--start-----radio单选与checkbox多选按钮的模拟---start---*/
(function ($) {
    $.fn.bindCheckboxRadioSimulate = function (options) {
        var settings = {
            className: 'on',
            onclick: null,
            checkbox_checked_fn: function (obj) {
                obj.parent().addClass(this.className);
            },
            checkbox_nochecked_fn: function (obj) {
                obj.parent().removeClass(this.className);
            },
            radio_checked_fn: function (obj) {
                obj.parent().addClass(this.className);
            },
            radio_nochecked_fn: function (obj) {
                obj.parent().removeClass(this.className);
            }
        };
        $.extend(true, settings, options);

        //input判断执行
        function inputJudge_fn(obj_this) {
           
            var $this = obj_this,
                $type;
            if ($this.attr('type') != undefined) {
                $type = $this.attr('type');
                if ($type == 'checkbox') {//if=多选按钮
                    if ($this.prop("checked")) {
                        settings.checkbox_checked_fn($this);
                    } else {
                        settings.checkbox_nochecked_fn($this);
                    }
                } else if ($type == 'radio') {//if=单选按钮
                    var $thisName;
                    if ($this.attr('name') != undefined) {
                        $thisName = $this.attr('name');
                        if ($this.prop("checked")) {
                            settings.radio_checked_fn($this);
                            $("input[name='" + $thisName + "']").not($this).each(function () {
                                settings.radio_nochecked_fn($(this));
                            });
                        } else {
                            settings.radio_nochecked_fn($this);
                        }
                    }
                }
            }
        }
        return this.each(function () {
            inputJudge_fn($(this));
        }).click(function () {
            inputJudge_fn($(this));
            if (settings.onclick) {
                settings.onclick(this, {
                    isChecked: $(this).prop("checked"),//返回是否选中
                    objThis: $(this)//返回自己
                });
            }
        });
    };
})(jQuery);
/*--end-----radio单选与checkbox多选按钮的模拟---end---*/

/*---start---------placeholder 占位符----------start--- */
; (function(f, h, $) {
    var a = 'placeholder' in h.createElement('input'),
    d = 'placeholder' in h.createElement('textarea'),
    i = $.fn,
    c = $.valHooks,
    k,
    j;
    if (a && d) {
        j = i.placeholder = function() {
            return this
        };
        j.input = j.textarea = true
    } else {
        j = i.placeholder = function() {
            var l = this;
            l.filter((a ? 'textarea': ':input') + '[placeholder]').not('.placeholder').bind({
                'focus.placeholder': b,
                'blur.placeholder': e
            }).data('placeholder-enabled', true).trigger('blur.placeholder');
            return l
        };
        j.input = a;
        j.textarea = d;
        k = {
            get: function(m) {
                var l = $(m);
                return l.data('placeholder-enabled') && l.hasClass('placeholder') ? '': m.value
            },
            set: function(m, n) {
                var l = $(m);
                if (!l.data('placeholder-enabled')) {
                    return m.value = n
                }
                if (n == '') {
                    m.value = n;
                    if (m != h.activeElement) {
                        e.call(m)
                    }
                } else {
                    if (l.hasClass('placeholder')) {
                        b.call(m, true, n) || (m.value = n)
                    } else {
                        m.value = n
                    }
                }
                return l
            }
        };
        a || (c.input = k);
        d || (c.textarea = k);
        $(function() {
            $(h).delegate('form', 'submit.placeholder', 
            function() {
                var l = $('.placeholder', this).each(b);
                setTimeout(function() {
                    l.each(e)
                },
                10)
            })
        });
        $(f).bind('beforeunload.placeholder', 
        function() {
            $('.placeholder').each(function() {
                this.value = ''
            })
        })
    }
    function g(m) {
        var l = {},
        n = /^jQuery\d+$/;
        $.each(m.attributes, 
        function(p, o) {
            if (o.specified && !n.test(o.name)) {
                l[o.name] = o.value
            }
        });
        return l
    }
    function b(m, n) {
        var l = this,
        o = $(l);
        if (l.value == o.attr('placeholder') && o.hasClass('placeholder')) {
            if (o.data('placeholder-password')) {
                o = o.hide().next().show().attr('id', o.removeAttr('id').data('placeholder-id'));
                if (m === true) {
                    return o[0].value = n
                }
                o.focus()
            } else {
                l.value = '';
                o.removeClass('placeholder');
                l == h.activeElement && l.select()
            }
        }
    }
    function e() {
        var q,
        l = this,
        p = $(l),
        m = p,
        o = this.id;
        if (l.value == '') {
            if (l.type == 'password') {
                if (!p.data('placeholder-textinput')) {
                    try {
                        q = p.clone().attr({
                            type: 'text'
                        })
                    } catch(n) {
                        q = $('<input>').attr($.extend(g(this), {
                            type: 'text'
                        }))
                    }
                    q.removeAttr('name').data({
                        'placeholder-password': true,
                        'placeholder-id': o
                    }).bind('focus.placeholder', b);
                    p.data({
                        'placeholder-textinput': q,
                        'placeholder-id': o
                    }).before(q)
                }
                p = p.removeAttr('id').hide().prev().attr('id', o).show()
            }
            p.addClass('placeholder');
            p[0].value = p.attr('placeholder')
        } else {
            p.removeClass('placeholder')
        }
    }
} (this, document, jQuery));
/*---end---------placeholder 占位符----------end--- */
///<jscompress sourcefile="sip-common.js" />
String.prototype.trim= function(){  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};
String.prototype.equalsIgnoreCase = function(str){
	return this.toLowerCase() == str.toLowerCase();
}
$.extend({
	ajaxQuery:function(formId,divId,callback,type){
		if(type == null || type == ''){
			type = 'get';
		}
		$.ajax({
			url:$("#"+formId).attr("action"),
			data:$("#"+formId).serialize(),
			type:type,
			success:function(data){
				$("#"+divId).html(data);
				if(callback!=undefined){
					var $callback = callback;
					if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
					$callback();
				}
			}
		});		
	},
	ajaxSubmit:function(formId){
		var data = $("#"+formId).serialize();
		var method = $("#"+formId).attr("method");
		if (method == 'delete' || method == 'DELETE' || method == 'put' || method == 'PUT') {
			data = '_method='+method+'&'+data;
		}
		var rData = $.ajax({
			url:$("#"+formId).attr("action"),
			data:data,
			type:'post',
			async:false,
			beforeSend:function(){
				ajaxLoading();
			},
			success:function(data){
				 
			},
			complete:function(){
				ajaxEnd();
			}
		}).responseText;
		return rData;
	},
	ajaxDelete:function(url, data, callback){
		$.ajax({
			url:url,
			type:'post',
			data:'_method=DELETE&'+data,
			success:function(data){
				if(callback!=undefined){
					var $callback = callback;
					if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
					$callback(data);
				}
			}
		});
	},
	put:function(url, data, callback){
		$.ajax({
			url:url,
			type:'post',
			data:'_method=PUT&'+data,
			success:function(data){
				if(callback!=undefined){
					var $callback = callback;
					if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
					$callback(data);
				}
			}
		});
	}
});

function ajaxLoading(){
    mylayerFn.loading({
        id: 999,
        content: '正在加载中...',
        shade: [0.4,"#ccc"]
    });
}

function ajaxEnd(){
	 mylayerFn.close(999);
}

function assignParam(formId,objectId){
	$.each($('#'+formId+' :input'),function(){
		$(this).val($('#'+$(this).attr('id')+'_'+objectId).text());
	});
}

function checkAllBox(formId, obj){
	$(obj).click(function(){
		if($(this).prop("checked")){
			$('#'+formId+' :checkbox').each(function(){
				$(this).prop("checked",true);			
			});
		}else{
			$('#'+formId+' :checkbox').each(function(){
				$(this).prop("checked",false);			
			});
		}
	});
}

//txt:鏂囨湰妗唈query瀵硅薄
//limit:闄愬埗鐨勫瓧鏁�
//isbyte:true:瑙唋imit涓哄瓧鑺傛暟锛沠alse:瑙唋imit涓哄瓧绗︽暟
//cb锛氬洖璋冨嚱鏁帮紝鍙傛暟涓哄彲杈撳叆鐨勫瓧鏁�
function initLimit(txt,limit,isbyte,cb){
	txt.keyup(function(){
		var str=txt.val();
		var charLen;
		var byteLen=0;
		if(isbyte){
			for(var i=0;i<str.length;i++){
				if(str.charCodeAt(i)>255){
					byteLen+=2;
				}else{
					byteLen++;
				}
			}
			charLen = Math.floor((limit-byteLen)/2);
		}else{
			byteLen=str.length;
			charLen=limit-byteLen;
		}
		cb(charLen);
	});	
}

function guid(){
	var guid = (G() + G() + "" + G() + "" + G() + "" + 
			G() + "" + G() + G() + G()).toUpperCase();
	return guid;
}
function G() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}

function hintJumpLayer(){
	$('.m-blackbg').show();
	var layer = $('.g-layer-warning');
	var width = layer.innerWidth(),
    height = layer.innerHeight();
    layer.show().css({'margin-top':-height/2+'px','margin-left':-width/2+'px'});
}

/*window.msg = function(txt, confirmFn){
	layer.msg(txt, {time: 1500}, confirmFn);
};*/

window.alert = function(txt, confirmFn, time){
	//layer.alert(txt, confirmFn);
	if(time == null || time == ''){
		time = 1500;
	}
	mylayerFn.msg(txt, {time: time}, confirmFn);
};

window.confirm = function(txt, confirmFn, cancelFn){
	mylayerFn.confirm({
        content: txt,
        icon: 3,
        yesFn: confirmFn,
        cancelFn: cancelFn
    });
};

function getByteLength(value){
	var length = value.trim().length; 
    for(var i = 0; i < value.length; i++){      
        if(value.charCodeAt(i) > 127){      
        	length = length+2;      
        }      
    }
    return length;
}

function getSuffix(fileName){
	var index = fileName.lastIndexOf(".");
	return fileName.substring(index+1,fileName.length);
}

function getOuterHtml(obj) {
    var box = $('<div></div>');
    for (var i = 0; i < obj.length; i ++) {
        box.append($(obj[i]).clone());
    }
    return box.html();
}

function isMatchJson(data){
	if(data.match("^\{(.+:.+,*){1,}\}$")){
		return true;
	}else{
		return false;
	}
}
///<jscompress sourcefile="index.js" />
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

///<jscompress sourcefile="activity-common.js" />
$(document).ready(function(){

	activityJs.fn.init();
})
//定义研修模块js
var activityJs = $(window).activityJs || {};

activityJs.fn = {
	init : function(){
		var _this = this;
		//添加标签
        _this.set_tag();
        //发布页高级选项
		_this.high_options();
		//自定义下拉框
        _this.drop_down_box();
        //倒计时
        _this.countDown();
         //展开评论回复框
        _this.commentOpa(".am-comment-box");
        _this.commentOpa(".am-isComment-box");
        //评论列表效果
        _this.commentShow();
	},
	/*-------添加标签-----------*/
    set_tag : function(){
        var $tag_parents = $(".am-add-tag");
        $tag_parents.each(function(){

            var _ts = $(this);
                $ipt_parents = _ts.find(".am-tagipt"),
                $ipt = $ipt_parents.find(".au-ipt"),
                $hint_lst = $ipt_parents.find(".l-slt-lst"),
                $add_btn = _ts.find(".au-add-tag"),
                $tag_lst = _ts.find(".am-tag-lst");

            //显示标签提示框  
            activityJs.fn.tag_hint_show($ipt,$hint_lst);  
            activityJs.fn.add_tag($ipt,$add_btn,$tag_lst);  
            activityJs.fn.delete_tag($tag_lst);  
        })
        
        
    },
    //显示标签提示框
    tag_hint_show : function($ipt,$hint_lst){
        //输入框获取焦点
        $ipt.on("focus",function(){
            if( !$(this).val()=="" ){

                $hint_lst.show();
                
            }

        })
        $ipt.on("keyup",function(){

            //判断输入文字是，提示框显示
            if( !$(this).val()=="" ){

                $hint_lst.show();

            }else {
                
                $hint_lst.hide();
            }
        })


        this.tag_hint_hide($ipt,$hint_lst);
    },
    //关闭标签提示框
    tag_hint_hide : function($ipt,$hint_lst){
        //获取
        $(document).on("click",function(e){
            var target = $(e.target); 
            //判断是否点击的是标签提示框和输入框，如果不是，隐藏标签提示框
            if(target.closest($hint_lst).length == 0 && target.closest($ipt).length == 0){ 

                $hint_lst.hide();
            }

        }); 
        //选择提示框选项关闭提示框
        $hint_lst.on("click","a",function(){

            $hint_lst.hide();
            $ipt.val($(this).text());
        })

    },
    //添加标签
    add_tag : function($ipt,$add_btn,$tag_lst){
        $add_btn.on("click",function(){
            var ss = false;
            var lengths = $tag_lst.children().length;
            //判断输入框是否为空
            if($ipt.val() != ""){
               //遍历标签列表
                for(var i = 0; i < lengths; i++){
                    //如果已有相同标签
                    if($ipt.val() == $tag_lst.children().eq(i).find(".txt").text()){
                        alert("已有相同标签");
                        $ipt.val('');
                        $ipt.focus();
                        ss = true;
                    }

                }
                //如果没有相同标签，添加新的标签
                if(!ss){
                    $tag_lst.append(
                        '<li>'+
                            '<span class="txt">'+$ipt.val()+'</span>'+
                            '<a href="javascript:void(0);" class="dlt" title="删除标签">×</a>'+
                        '</li>'
                    );
                    //添加标签后，清除输入框中的文字
                    $ipt.val("");
                }
            }
        })

    },
    //删除标签
    delete_tag : function($tag_lst){
        $tag_lst.on("click",".dlt",function(){
            $(this).parent().remove();
        })
    },
    //显示隐藏高级选项
    high_options : function(){
        var $high_options = $(".ag-high-options"),
            $high_option_btn = $high_options.find(".au-high-option"),
            $high_option_lst = $high_options.find(".ag-high-lst");

        $high_option_btn.on("click",function(){
            _ts = $(this);

            if(_ts.hasClass("z-crt")){

                _ts.removeClass("z-crt");
                $high_option_lst.hide();
            }else {

                _ts.addClass("z-crt");
                $high_option_lst.show();
            }
        })
    },

    /*--------自定义下拉框----------*/
    drop_down_box : function(){
    	var $dd_box = $(".am-slt-lst");

		$dd_box.each(function(){
			var _ts = $(this),
				$show_block = _ts.children(".show"),
				$show_txt = $show_block.children(".txt"),
				$lst = _ts.children(".lst"),
				$dd = $lst.children("a"),
				cur = "z-crt";

			//点击按钮选择选项
			$show_block.on("click",function(){
				//判断是否打开
				var $this = $(this);
				if($this.hasClass(cur)){

					$lst.hide();
					$this.removeClass(cur);
				}else {
					$lst.show();
					$this.addClass(cur);
				}

				//判断是否点击其他地方
	            $(document).on("click",function(e){ 
	                var target = $(e.target); 
	                if(target.closest(_ts).length == 0){ 
	                    $lst.hide();
						$show_block.removeClass(cur);
	                } 
	            }); 
	            //点击选项关闭
				_ts.on("click",".lst a",function(){
					$lst.hide();
					$show_block.removeClass(cur);
					$show_txt.text($(this).text());
					$(this).addClass(cur).siblings().removeClass(cur);
				})
			})			
		});
    },
    //倒计时
    countDown : function(){
        var $countDownBox = $(".am-count-down"),
            //$endTime = new Date($countDownBox.find(".endTime")),
            $endTime = $countDownBox.find(".endTime"),
            $day = $countDownBox.find(".day"),
            $hour = $countDownBox.find(".hour"),
            $minute = $countDownBox.find(".minute"),
            $second = $countDownBox.find(".second");
        var endTime = new Date($endTime.val()); //结束日期
        var nowTime = new Date(); //当前日期
        var leftTime = parseInt((endTime.getTime()-nowTime.getTime())/1000); //计算剩下的时间 parseInt()为javascript中的取整
        var leftDay = parseInt(leftTime/(24*60*60)); //计算剩下的天数
        var leftHours = parseInt(leftTime/(60*60)%24); //计算小时
        var leftMinutes = parseInt(leftTime/60%60); //计算分钟
        var leftSeconds =  parseInt(leftTime%60); //计算分钟
            
        $day.text(leftDay);//设置天
        $hour.text(leftHours);//设置小时
        $minute.text(leftMinutes);//设置分钟
        $second.text(leftSeconds);//设置秒
        //判断天数是否小于10
        if(leftDay < 10) {
            $day.text("0" + leftDay);
        }
        //判断小时是否小于10
        if(leftHours < 10) {
            $hour.text("0" + leftHours);
        }
        //判断分钟是否小于10
        if(leftMinutes < 10) {
            $minute.text("0" + leftMinutes);
        }
        //判断秒钟是否小于10
        if(leftSeconds < 10) {
            $second.text("0" + leftSeconds);
        }
        //判断是否到了结束时间
        if(leftTime < 1){
            clearTimeout(countDownTimer);
            $day.text("00");
            $hour.text("00");
            $minute.text("00");
            $second.text("00");
        }  
        var countDownTimer = setInterval(function(){
            var nowTime = new Date(); //当前日期
            var leftTime = parseInt((endTime.getTime()-nowTime.getTime())/1000); //计算剩下的时间 parseInt()为javascript中的取整
            var leftDay = parseInt(leftTime/(24*60*60)); //计算剩下的天数
            var leftHours = parseInt(leftTime/(60*60)%24); //计算小时
            var leftMinutes = parseInt(leftTime/60%60); //计算分钟
            var leftSeconds =  parseInt(leftTime%60); //计算分钟
            $day.text(leftDay);
            $hour.text(leftHours);
            $minute.text(leftMinutes);
            $second.text(leftSeconds);
            if(leftDay < 10) {
                $day.text("0" + leftDay);
            }
            if(leftHours < 10) {
                $hour.text("0" + leftHours);
            }
            if(leftMinutes < 10) {
                $minute.text("0" + leftMinutes);
            }
            if(leftSeconds < 10) {
                $second.text("0" + leftSeconds);
            }
            //判断是否到了结束时间
            if(leftTime < 1){
                clearTimeout(countDownTimer);
                $day.text("00");
                $hour.text("00");
                $minute.text("00");
                $second.text("00");
            } 
        },1000);
    },
    //评论框效果
    commentOpa : function(commentBox){
        var commentBox = commentBox,
            commentTextarea = commentBox + " " + ".au-textarea",
            u_height = 76,//输入框展开后的输入框高度
            s_height = 22;//输入框收起后的输入框高度
        //获取焦点时展开输入框
        $(commentTextarea).on('focus',function(){
            var _ts = $(this),
                $part = _ts.parents(commentBox),
                $placeholder = $part.find(".comment-placeholder"),
                $opa_row = $part.find(".am-cmtBtn-block"),
                $face_btn = $part.find(".au-face"),
                $confirm_btn = $part.find(".au-cmtPublish-btn");
            //隐藏占位符
            $placeholder.stop().animate({opacity : '0'},200,function(){
                $(this).hide();
            });
            //展开文本输入框
            _ts.stop().animate({
                height : u_height + 'px'
            },200,function(){
                $opa_row.show();
            });
            //点击其他地方，收缩文本输入框
            $(document).on("click",function(e){ 
                var target = $(e.target); 
                //event.stopPropagation();
                //判断输入框是否已经输入文字
                if(target.closest(commentBox).length == 0){ 
                    //console.log(1);
                    if(_ts.val()==""){
                        $placeholder.stop().animate({opacity : '1'},200,function(){
                            $(this).show();
                        });
                        _ts.stop().animate({height : s_height + "px"},100,function(){
                            $opa_row.hide();
                        });
                    };
                }; 
            }); 
        });
    },
    //评论列表效果
    commentShow : function(){
        var $comment_lst = $(".ag-cmt-lst-p"),
            $comment_block = $comment_lst.children("li"),
            $a_comment_ico = $comment_block.find(".au-comment"),
            $a_comment_mod = $comment_block.find(".ag-is-comment");
        //遍历评论列表，获取索引
        $comment_block.each(function(){
            var _ts = $(this),
                $comment_ico = _ts.find(".au-comment"),
                $is_comment_mod = _ts.find(".ag-is-comment"),
                t_height = $is_comment_mod.innerHeight();

            $comment_ico.on("click",function(){
            	//zhuderun add
            	var relationId = $(this).closest('ul').attr('relationId');
				var commentId = $(this).closest('li').attr('commentId');
				var childCommentDiv = $('#'+commentId+'_childComment');
				if(childCommentDiv.find('li').size()<=0){
					childCommentDiv.load($('#ctx').val()+'/comment/activity/child','mainId='+commentId+'&relationId='+relationId);
				};
				//zhuderun add end
                //判断是否打开评论列表
                var _this = $(this);
                if(_this.hasClass("z-crt")){
                    //关闭评论列表
                    _this.removeClass("z-crt");
                    $is_comment_mod.hide();

                }else {
                    //打开评论列表
                     $a_comment_mod.hide();
                     $a_comment_ico.removeClass("z-crt");
                     _this.addClass("z-crt");
                     $is_comment_mod.find(".au-comment-trg").css("left", Math.ceil(_this.position().left) - 25);
                     $is_comment_mod.show();
                     //activityJs.fn.commentOpa($(".am-isComment-box"));
                }
            })
        })
    },
    //弹出框
    aJumpLayer : function(layer){
        var layer = $(layer),
            width = layer.innerWidth(),
            height = layer.innerHeight();
        layer.show().css({'margin-top':-height/2+'px','margin-left':-width/2+'px'});
        $('.ag-blackbg').show().css("opacity","0.5");

        /*clickBtn.bind('click',function(){
            layer.show().css({'margin-top':-height/2+'px','margin-left':-width/2+'px'});
            $('.m-blackbg').show().css("opacity","0.5");

        })*/
        layer.find('.au-confirm-btn').bind('click',function(){
            layer.hide();
            $('.ag-blackbg').hide().css("opacity","1");
        })
        layer.find('.au-cancel-btn').bind('click',function(){
            layer.hide();
            $('.ag-blackbg').hide().css("opacity","1");
        })
        layer.find('.au-close-btn').bind('click',function(){
            layer.hide();
            $('.ag-blackbg').hide().css("opacity","1");
        })
    },
    //打开绝对定位弹出框
    openABlayer : function(abLayer){
        var $layersLayout = $(abLayer),
            $whiteBg = $(".ag-whitebg"),
            scrollTop = $(window).scrollTop();
        //打开弹出框
        $layersLayout.show().css('top',scrollTop + 100 + 'px');
        $whiteBg.show();
        //关闭弹出框
        this.closeABlayer($layersLayout,$whiteBg);

    },
    //关闭绝对定位弹出框
    closeABlayer : function($layersLayout,$whiteBg){
        var $colseBtn = $layersLayout.find(".au-closelayer-ico"),
            $cancelBtn = $layersLayout.find(".au-cancel-btm");
        $colseBtn.on("click",function(){
            $layersLayout.hide();
            $whiteBg.hide();
        });
        $cancelBtn.on("click",function(){
            $layersLayout.hide();
            $whiteBg.hide();
        });
    }
};




/*--start----多个同类名选项卡----start---*/
$.fn.extend({
    myTab : function(options)
    {
        var defaults = 
        {
            pars    : '.myTab',   //最外层父级
            tabNav  : '.tabNav',  //标签导航
            li      : 'li',       //标签
            tabCon  : '.tabCon',  //区域父级
            tabList : '.tabList', //t区域模块
            cur     : 'cur',      //选中类
            eType   : 'click',    //事件
            page    : 0 //默认显示第几个模块
        }
        var options = $.extend(defaults,options),
        _ts = $(this),
        tabBtn  = _ts.find(options.tabNav).find(options.li);
        tabList  = _ts.find(options.tabCon).find(options.tabList);
        this.each(function(){
            tabBtn.removeClass(options.cur);
            tabBtn.eq(options.page).addClass(options.cur);
            tabList.hide();
            tabList.eq(options.page).show();
            tabBtn.eq(options.page).show();
            tabBtn.on(options.eType,function(){
                var index = $(this).parents(options.tabNav).find(options.li).index(this);
                $(this).addClass(options.cur).siblings().removeClass(options.cur);
                $(this).parents(options.pars).find(options.tabCon).find(options.tabList).eq(index).css({'display':'block'}).siblings().css({'display':'none'});
            })
        })
        return this;
    }
})
/*--end-----多个同类名选项卡---end---*/
