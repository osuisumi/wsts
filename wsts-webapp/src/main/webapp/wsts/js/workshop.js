$(function(){
    turn();             //搜索框伸缩

    //搜索框伸缩
    function turn(){
        var par = $("#mSrh"),
            ipt = par.children(".ipt"),
            tips = par.children(".tips"),
            ico = par.children(".u-ico-srh");
        ipt.on("focus",function(){
            var _ts = $(this);
            if(!(par.hasClass("focus"))){
                par.addClass("focus");
            }else{
                return ;
            }
        });
        ipt.on("blur",function(){
            var _ts = $(this),
                value = _ts.val();
                if(value != ""){
                    return false;
                }else{
                    par.removeClass("focus");
                }
        });
    }
})