package com.lenovohit.yuzhijun.inject.module;

import com.lenovohit.yuzhijun.network.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuzhijun on 2016/4/28.
 */
@Module
public class ApiServiceModule {

    private static final String BASE_URL =  "http://ip.taobao.com/";//"http://demo.xizhang.com/testappwebapi/api/";
    private static final int DEFAULT_TIMEOUT = 8;
    private Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClientBuilder(){
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClientBuilder = new OkHttpClient();
        httpClientBuilder.newBuilder()
//                .addInterceptor(interceptor)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return httpClientBuilder;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient OkHttpClientBuilder){
        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientBuilder)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
