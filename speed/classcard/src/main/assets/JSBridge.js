(function (win) {
    var hasOwnProperty = Object.prototype.hasOwnProperty;
    var JSBridge = win.JSBridge || (win.JSBridge = {});
    var JSBRIDGE_PROTOCOL = 'JSBridge';
    var Inner = {
        callbacks: {},
        call: function (obj, method, params, callback) {
            console.log(obj+" "+method+" "+params+" "+callback);
            var port = Util.getPort();
            console.log(port);
            this.callbacks[port] = callback;
            var uri=Util.getUri(obj,method,params,port);
            console.log(uri);
            window.prompt(uri, "");
        },
        onFinish: function (port, jsonObj){
            var callback = this.callbacks[port];
            callback && callback(jsonObj);
            delete this.callbacks[port];
        },
    };
    var Util = {
        getPort: function () {
            return Math.floor(Math.random() * (1 << 30));
        },
        getUri:function(obj, method, params, port){
            params = this.getParam(params);
            var uri = JSBRIDGE_PROTOCOL + '://' + obj + ':' + port + '/' + method + '?' + params;
            return uri;
        },
        getParam:function(obj){
            if (obj && typeof obj === 'object') {
                return JSON.stringify(obj);
            } else {
                return obj || '';
            }
        }
    };
    for (var key in Inner) {
        if (!hasOwnProperty.call(JSBridge, key)) {
            JSBridge[key] = Inner[key];
        }
    }
})(window);



 //注册回调函数，第一次连接时调用 初始化函数
       connectWebViewJavascriptBridge(function(bridge) {
           bridge.init(function(message, responseCallback) {
               console.log('JS got a message', message);
               var data = {
                   'Javascript Responds': 'Wee!'
               };
               console.log('JS responding with', data);
               responseCallback(data);
           });
           bridge.registerHandler("getMac", function(data, responseCallback) {
             document.getElementById("show").innerHTML = ("data from Java: = " + data);
             var responseData = "Javascript Says Right back aka!";
               responseCallback(responseData);
           });
       })
       //注册事件监听
  function connectWebViewJavascriptBridge(callback) {
           if (window.WebViewJavascriptBridge) {
               callback(WebViewJavascriptBridge)
           } else {
               document.addEventListener(
                   'WebViewJavascriptBridgeReady'
                   , function() {
                       callback(WebViewJavascriptBridge)
                   },
                   false
               );
           }
       }

       function getMac(){
       //调用本地java方法
                  window.WebViewJavascriptBridge.callHandler(
                      'callMac'
                      , {'param': "getMac"}
                      , function(responseData) {
                    document.getElementById("show").innerHTML = ("data from Java: = " + data);
                      }
                  );
       }
