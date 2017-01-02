/**
* +---------------------------------
* | Android Javascript Injection
* +---------------------------------
* | Author: LiangMaYong <ibeam@qq.com>
* +---------------------------------
**/
function JsBridge()
{
    this.toast = function(msg){
       window.location.href ="toast:"+msg;
    };
}
var #jsBridge# = new JsBridge();
