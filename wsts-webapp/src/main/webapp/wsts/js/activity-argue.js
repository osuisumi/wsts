$(document).ready(function(){

	activityArgue.fn.init();
})
//定义研修模块js
var activityArgue = $(window).activityArgue || {};

activityArgue.fn = {
    init : function(){
        var _this = this;
        //发起辩论操作,  注：可以传入父级参数,如"#documentParent"
        _this.publish_argue();
        
    },
    /*-- 发起辩论操作 --*/
    publish_argue : function(documentParents){
        var pb_argue_parent = ".ag-pb-lst";
        //判断传入的父级参数是否存在，存在则用传入参数绑定事件，不存在则用默认父级绑定
        if(documentParents == "" || documentParents == 'undefined' || documentParents == null || documentParents == undefined) {
        }else {
            pb_argue_parent = documentParents;
        }

        //获取元素           
        var $pb_parent = $(pb_argue_parent),  //外层父级
            pb_mod = ".am-pb-mod",  //模块
            vp_mod = ".am-pb-mod-vp",
            ep_mod = '.am-pb-mod-ep',
            vptArr = ["正","反","甲","乙","丙","丁"],  //论点文字
            //vp_length = 2;  //论点默认个数
        	vp_length = $('.am-pb-mod-vp').size();

        //添加论点
        add_viewpoint();
        //删除论点
        delete_viewpoint();
        //添加论点补充说明
        add_explain();
        //删除论点补充说明
        delete_explain();

        /* 添加论点 */
        function add_viewpoint(){
            //论点模块html
            var epHtml = $('<li class="m-addElement-item am-pb-mod am-pb-mod-vp">'+
                            '<div class="ltxt c-txt">'+
                                '<em>*</em><span>正方论点：</span>'+
                            '</div>'+
                            '<div class="c-center">'+
                                '<div class="m-pbMod-ipt">'+
                                	'<input class="order" type="hidden" name="" value="">'+
                                    '<input type="text" required name="" class="u-pbIpt" placeholder="一句话描述论点">'+
                                '</div>'+
                            '</div>'+
                            '<div class="c-side">'+
                                '<a href="javascript:void(0);" class="au-nbtn au-add-ep">+补充说明</a>'+
                                '<a href="javascript:void(0);" class="au-nbtn au-delete-vp">×删除</a>'+
                            '</div>'+
                        '</li>');

            //添加论点
            $pb_parent.on("click",'.au-add-vp',function(){
                //判断论点个数是否超过4个
                if(vp_length < 4){
                    //插入论点模块html
                    var addepHtml = epHtml.clone();
                    //设置参数
                    var index = $pb_parent.find('.am-pb-mod-vp').size();
                    addepHtml.find('.order').attr('name','debate.arguments['+index+'].orderNo').val(index);
                    addepHtml.find('.u-pbIpt').attr('name','debate.arguments['+index+'].description');
                    $(this).parents(pb_mod).before(addepHtml);
                    //获取论点模块
                    $vp_mod = $pb_parent.find(vp_mod);
                    //添加论点后，论点个数+1
                    vp_length++;
                    //alert(vp_length);
                    for(var i = 0; i < vp_length; i++){
                        //设置
                        $vp_mod.eq(i).find('.c-txt span').text(vptArr[i + 2] + '方论点：');
                    };
                }else {
                    //超过4个论点弹出提醒
                    alert("观点不能超过4个");
                }
            });
        };

        /* 删除论点 */
        function delete_viewpoint(){
            //删除论点模块
            $pb_parent.on("click",'.au-delete-vp',function(){
                var _ts = $(this),
                    $vp_mod = _ts.parents(vp_mod),
                    $ep_mod = $vp_mod.next(ep_mod);

                //判断论点个数，小于等于2个，这不删除
                if(vp_length > 2) {
                    //论点数-1
                    vp_length--;
                    //alert(vp_length);
                    //删除论点模块
                    $vp_mod.remove();
                    //判断需要删除的论点是否有补充说明模块，有则也一起删除
                    if($ep_mod){
                        $ep_mod.remove();
                    };
                    //判断论点个数，设置文字
                    for(i = 0; i < vp_length; i++){
                        //论点数大于2个时，采用“甲乙丙丁的方式”
                        if(vp_length > 2){
                            $(vp_mod).eq(i).find(".c-txt span").text(vptArr[i+2] + "方论点：");
                        //论点数等于2个时，采用“正反的方式”
                        }else {
                            $(vp_mod).eq(i).find(".c-txt span").text(vptArr[i] + "方论点：");
                        };
                    };
                //论点等于2个时，不能删除论点
                }else {
                    alert("论点不能少于2个");
                };
            });
        };

        /* 添加论点补充说明 */            
        function add_explain(){
            //补充说明模块
            var exHtml = $('<li class="m-addElement-item am-pb-mod am-pb-mod-ep">'+
                            '<div class="c-center">'+
                                '<div class="m-pbMod-ipt">'+
                                    '<textarea name="" id="" class="u-textarea" placeholder="补充说明，（选填）"></textarea>'+
                                '</div>'+
                            '</div>'+
                            '<div class="c-side">'+
                                '<a href="javascript:void(0);" class="au-nbtn au-delete-ep">×删除</a>'+
                            '</div>'+
                        '</li>');

            //添加补充说明模块
            $pb_parent.on("click",'.au-add-ep',function(){
            	var index = $pb_parent.find('.am-pb-mod-vp').index($(this).closest('.am-pb-mod-vp'));
                var _ts = $(this),
                    $pb_mod = _ts.parents(pb_mod);
                //设置参数
                var preAdd = exHtml.clone();
                if($(this).attr('content')){
                	preAdd.find('textarea').val($(this).attr('content'));
                }
                preAdd.find('textarea').attr('name','debate.arguments['+index+'].supplementExplanation');
                //插入补充说明模块html
                $pb_mod.after(preAdd);
                //隐藏补充说明按钮
                _ts.hide();
            });
        };
        
        /* 删除论点补充说明 */            
        function delete_explain(){
            //删除补充说明模块
            $pb_parent.on("click",'.au-delete-ep',function(){
                var _ts = $(this),
                    $pb_mod = _ts.parents(pb_mod),
                    $vp_mod = $pb_mod.prev(vp_mod);
                //删除
                $pb_mod.remove();
                //显示补充说明按钮
                $vp_mod.find('.au-add-ep').show();
            });
        };
        
    }

};


