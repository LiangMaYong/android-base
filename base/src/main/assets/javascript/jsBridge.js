/**
* +---------------------------------
* | Android Javascript Injection
* +---------------------------------
* | Author: LiangMaYong <ibeam@qq.com>
* +---------------------------------
**/
function JsBridge()
{
    var deviceInfo;
    var userInfo;

    this.init = function(info){
       deviceInfo = eval('(' + info + ')');
    };

    this.auth = function(info){
       userInfo = eval('(' + info + ')');
    };

    this.getUserInfo = function(){
       return userInfo;
    };

    this.getDeviceInfo = function(){
       return deviceInfo;
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    this.toolbar_message = function(msg,textColor,bgColor){
       window.location.href ="toolbar-message:"+msg+"?textColor="+textColor+"&bgColor="+bgColor;
    };

    this.toast = function(msg){
       window.location.href ="toast:"+msg;
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

    this.open = function(url){
       this.action("open?url="+url);
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

    this.logcat = function(tag){
       window.location.href ="logcat:"+ (tag != null ? tag:"");
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
var &jsBridge& = new JsBridge();
