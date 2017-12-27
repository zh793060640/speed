package com.zhanghao.core.base;

import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhanghao.core.R;
import com.zhanghao.core.api.RetrofitClient;
import com.zhanghao.core.ui.WebViewJavaScriptFunction;
import com.zhanghao.core.ui.X5WebView;
import java.util.ArrayList;

/**
 * 作者： zhanghao on 2017/10/9.
 * 功能：${des}
 */

public class BaseWebActivity extends BaseActivity<BasePresenter, BaseModel> {
    public static final String EXTRL_URL = "url";
    public X5WebView webView;
    public ArrayList<String> loadHistoryUrls = new ArrayList<String>();
    protected String curwebUrl = "";
    public String reDirectUrl = "";
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        webView = findView(R.id.x5webview);
        myTitleBar.setTitle("x5webview");
        addJavaScript();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (reDirectUrl.equals("")) {
                    reDirectUrl = url;
                }
                if (!"".equals(url) && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view.loadUrl(url);
                }
                loadHistoryUrls.add(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                curwebUrl = url;
            }

        });
        loadUrl("http://www.baidu.com/");
    }

    private void addJavaScript() {
        webView.addJavascriptInterface(new WebViewJavaScriptFunction() {
            @Override
            public void onJsFunctionCalled(String tag) {

            }
            @JavascriptInterface
            public void test (){
            }
        }, "speed");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    public void loadUrl(final String url) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (curwebUrl.startsWith(RetrofitClient.baseUrl)) {
            loadHistoryUrls.remove(curwebUrl);
            loadUrl("javascript:canBack()");
        } else {
            if (!webView.getUrl().equals(extraIntent.getStringExtra(EXTRL_URL)) && !reDirectUrl.equals(webView.getUrl())) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            } else {
                finish();
            }
        }
    }
}
