package com.zhanghao.core.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhanghao.core.utils.ACache;
import com.zhanghao.core.utils.AppManager;
import com.zhanghao.core.utils.FileUtils;
import com.zhanghao.core.utils.LogUtils;
import com.zhanghao.core.utils.NetWorkStateUtils;
import com.zhanghao.core.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者： zhanghao on 2017/7/12.
 * 功能：${des}
 */

public class RetrofitClient {
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 7;
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static Retrofit retrofit = null;
    public static int READ_TIMEOUT = 60;
    public static int WRIT_TIMEOUT = 60;
    public static int CONNECT_TIMEOUT = 60;
    public static String baseUrl = "";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRIT_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);

    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //适配rxjava2.0
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static void init(String Url) {
        baseUrl = Url;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }

    public static OkHttpClient getOkHttpClient() {
        SSLContext sslContext = Utils.getSSLContext(AppManager.I().getApplicationContext());
        //缓存
        File cacheFile = new File(FileUtils.getCachePath(AppManager.I().getApplicationContext()), "apiServer");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient client = httpClient
                // 日志拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(cacheInterceptor)
                .cache(cache)
                .addInterceptor(setToken())
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .build();
        return client;
    }

    public static Interceptor setToken() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("signature", "dXNlclR5cGU9MiZ1c2VySWQ9ODk0MTYmdGltZXN0YW1wPTE1MDgzMjE3NjQmbm9uY2U9MTAz.aac486dd7eb5c790cd87cf66ba52a97c")
                        .addQueryParameter("token", "4574f1a9b99a30e20660b0117ea5ecbc")
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    //日志
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    public static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String url = request.url().toString(); //获取请求URL
            Buffer buffer = new Buffer();
            String params = buffer.readString(Charset.forName("UTF-8")); //获取请求参数
            if (!NetWorkStateUtils.isConnected(AppManager.I().getApplicationContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                LogUtils.d("no network");
            }
            if (request.method() == "GET") {
                return doCacheForGet(chain, request);
            } else if (request.method() == "POST") {
                request.body().writeTo(buffer);
                return doCacheForPost(chain, request, url, params);
            }
            Response response = chain.proceed(request);

            if (NetWorkStateUtils.isConnected(AppManager.I().getApplicationContext())) {
                int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                LogUtils.d("has network maxAge=" + maxAge);
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                LogUtils.d("network error");
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                LogUtils.d("has maxStale=" + maxStale);
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
                LogUtils.d("response build maxStale=" + maxStale);
            }

            return response;
        }
    };

    public static class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static Response doCacheForPost(Interceptor.Chain chain, Request request, String url, String params) throws IOException {
        Response response;
        String cacheControl = request.cacheControl().toString();

        if (TextUtils.isEmpty(cacheControl)) {
            Response proceed = chain.proceed(request);
            String s = proceed.body().string();
            return proceed.newBuilder()
                    .body(ResponseBody.create(proceed.body().contentType(), s))
                    .build();
        }
        if (NetWorkStateUtils.isConnected(AppManager.I().currentActivity())) {
            int maxAge = (int) CACHE_STALE_SEC;
            //如果网络正常，执行请求。
            Response originalResponse = chain.proceed(request);
            //获取MediaType，用于重新构建ResponseBody
            MediaType type = originalResponse.body().contentType();
            //获取body字节即响应，用于存入数据库和重新构建ResponseBody
            String bs = originalResponse.body().string();
            response = originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    //重新构建body，原因在于body只能调用一次，之后就关闭了。
                    .body(ResponseBody.create(type, bs))
                    .build();
            //将响应插入数据库
            ACache aCache = ACache.get(AppManager.I().currentActivity());
            Log.d("OkHttp", "将数据插入缓存:" + url + "---" + bs);
            aCache.put(url + params, bs);
        } else {
            //没有网络的时候，由于Okhttp没有缓存post请求，所以不要调用chain.proceed(request)，会导致连接不上服务器而抛出异常（504）
            ACache aCache = ACache.get(AppManager.I().currentActivity());
            String b = aCache.getAsString(url + params); //读出响应
            if (b == null) {
                b = "";
            }
            Log.d("OkHttp", "取出数据:" + url + "---" + b);
            int maxStale = (int) CACHE_STALE_SEC;
            //构建一个新的response响应结果
            response = new Response.Builder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .body(ResponseBody.create(MediaType.parse("application/json"), b.getBytes()))
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .build();
        }
        return response;
    }

    private static Response doCacheForGet(Interceptor.Chain chain, Request request) throws IOException {
        String cacheControl = request.cacheControl().toString();
        if (!NetWorkStateUtils.isConnected(AppManager.I().getApplicationContext())) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        String bs = originalResponse.body().string();
        if (NetWorkStateUtils.isConnected(AppManager.I().getApplicationContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .body(ResponseBody.create(originalResponse.body().contentType(),bs))
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .body(ResponseBody.create(originalResponse.body().contentType(),bs))
                    .removeHeader("Pragma")
                    .build();
        }
    }
    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkStateUtils.isConnected(AppManager.I().getApplicationContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }
}
