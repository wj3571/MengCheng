package com.core.service;

import android.text.TextUtils;
import android.util.Log;

import com.core.service.adapter.StringValueAdapter;
import com.core.utils.persistence.DeviceUtil;
import com.core.utils.persistence.FastData;
import com.core.utils.persistence.ToastUtil;
import com.google.gson.GsonBuilder;
import com.mengcheng.application.MengChengApp;


import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Api service
 * Created by JieGuo on 2017/3/16.
 */

public class Api {
    private ApiService apiService;
    private int maxStale = 60 * 60 * 24;
    private static final String PLATFORM = "android";
    private static final String CLIENT = "store";

    Api() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        addCache(httpClientBuilder);
        addCheckNetworkChain(httpClientBuilder);

        httpClientBuilder.networkInterceptors()
                .add(chain -> {
                    Request.Builder req = chain.request().newBuilder();
                    req.addHeader("Accept-Charset", "utf-8");
                    req.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            //.addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("x-platform", PLATFORM)
                            .addHeader("x-client", CLIENT);

                    addAuthorizationHeader(req);
                    Response response = chain.proceed(req.build());
                    return response.newBuilder().build();
                });
        httpClientBuilder.addInterceptor(
                new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                )
        );



        httpClientBuilder.addNetworkInterceptor(chain -> {

            Response response = chain.proceed(chain.request());
            if (response.isSuccessful()) {
                String responseBody = response.peekBody(response.body().contentLength()).string();
                if (!TextUtils.isEmpty(responseBody) && response.code() == 200) {
                    Flowable.just(responseBody)
                            .map(s -> {
                                Pattern pattern = Pattern.compile("\\\"login\\\":\\\"(\\w+)\\\"");
                                Matcher matcher = pattern.matcher(s);
                                if (matcher.find() && matcher.groupCount() == 1) {
                                    return matcher.group(1);
                                }
                                return "";
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> {
                                if (TextUtils.isEmpty(s)) {

                                } else if ("true".equals(s)) {
                                    FastData.putLogin(Boolean.valueOf(s));
                                } else {
                                    FastData.putLogin(Boolean.valueOf(s));
                                    FastData.setToken("");
                                }
                                Log.d("TEST", "is login : " + String.valueOf(s));
                            }, throwable -> {
                                Log.e("code_pattern", "error", throwable);
                            });
                }
            }
            return response;
        });


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, new StringValueAdapter());

        Retrofit retrofit = retrofitBuilder
                .baseUrl("www.***.com" + "/")
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    protected void addCheckNetworkChain(OkHttpClient.Builder httpClientBuilder) {
        httpClientBuilder.addInterceptor(chain -> {

            Request request = chain.request();
            if (!DeviceUtil.isNetworkAvailable()) {

                Maybe.just("没有可用网络, 请连接网络后重试")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ToastUtil::show, throwable -> {
                            Log.e("Debug", "error", throwable);
                        });

                // @notice  由于服务器不支持缓存，所以这里其实是没有起作用，等服务器起作用就可以了
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            return chain.proceed(request)
                    .newBuilder()
                    .build();
        });

    }

    protected void addCache(OkHttpClient.Builder httpClientBuilder) {
        File httpCacheDirectory = new File(MengChengApp.getInstanse().getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        httpClientBuilder.cache(cache);
    }

    protected void addAuthorizationHeader(Request.Builder req) {
        if (!TextUtils.isEmpty(FastData.getToken())) {
            String authorizationHeader = String.format(Locale.CHINA, "Bearer %s", FastData.getToken());
            req.addHeader("Authorization", authorizationHeader);
            Log.d("OkHttp", "Authorization: " + authorizationHeader);
        }
    }

    static Api api = null;

    public static ApiService getInstance() {

        if (api == null) {
            api = new Api();
        }
        return api.getService();
    }

    public ApiService getService() {
        return apiService;
    }

}
