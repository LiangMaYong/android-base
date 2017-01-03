/**
* +---------------------------------
* | Android Javascript Injection
* +---------------------------------
* | Author: LiangMaYong <ibeam@qq.com>
* +---------------------------------
* | toast:test?indedex=lmy&
* +---------------------------------
**/
function JsBridge()
{
    var deviceInfo;

    this.init = function(info){
       deviceInfo = eval('(' + info + ')');
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
       window.location.href ="action:finish";
    };

    this.back = function(){
       window.location.href ="action:back";
    };

    this.open = function(url){
       window.location.href ="action:open?url="+url;
    };

    this.reload = function(){
       window.location.href ="action:reload";
    };

    this.forward = function(){
       window.location.href ="action:forward";
    };

    this.action = function(action){
       window.location.href ="action:"+action;
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
var &jsBridge& = new JsBridge();
