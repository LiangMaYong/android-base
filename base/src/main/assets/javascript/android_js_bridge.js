/**
* +---------------------------------
* | Android Javascript Injection
* +---------------------------------
* | Author: LiangMaYong <ibeam@qq.com>
* +---------------------------------
**/
function AndroidJsBridge()
{
    var deviceInfo;

    this.init = function(info){
       deviceInfo = eval('(' + info + ')');
    };

    this.getDeviceInfo = function(){
       return deviceInfo;
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////// Action
    ///////////////////////////////////////////////////////////////////////////////////////////////

    this.toast = function(msg){
       this.action("toast?toast="+msg);
    };

    this.open = function(url){
       this.action("open?url="+url);
    };

    this.logcat = function(tag){
       this.action("logcat?tag="+ (tag != null ? tag:""));
    };

    this.success = function(msg){
       this.action("success?msg="+msg);
    };

    this.danger = function(msg){
       this.action("danger?msg="+msg);
    };

    this.warning = function(msg){
       this.action("warning?msg="+msg);
    };

    this.finish = function(){
       this.action("finish");
    };

    this.back = function(){
       this.action("back");
    };

    this.back_finish = function(){
       this.action("back_finish");
    };

    this.reload = function(){
       this.action("reload");
    };

    this.forward = function(){
       this.action("forward");
    };

    this.hide_toolbar = function(){
       this.action("hide_toolbar");
    };

    this.show_toolbar = function(){
       this.action("show_toolbar");
    };

    this.action = function(action){
       window.location.href ="action:"+action;
    };
}
var &jsBridge& = new AndroidJsBridge();

