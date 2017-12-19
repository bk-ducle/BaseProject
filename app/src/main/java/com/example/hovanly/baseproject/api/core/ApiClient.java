package com.example.hovanly.baseproject.api.core;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.example.hovanly.baseproject.BuildConfig;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author BiNC, Ly Ho V.
 */
public class ApiClient {
    private static final int TIME_OUT = 10000;
    private static final String HEADER_UA = "User-Agent";
    private static ApiClient sApiClient;
    private ApiConfig mApiConfig;
    private ApiService mApiService;

    private ApiClient() {
        // No-op
    }

    public static ApiClient getInstance() {
        if (sApiClient == null) {
            sApiClient = new ApiClient();
        }
        return sApiClient;
    }

    public ApiService getApiService() {
        onCheckConfig();
        return mApiService;
    }

    private HeaderStore getHeaderStore() {
        onCheckConfig();
        return HeaderStore.getInstance(mApiConfig.getContext());
    }

    public void init(final ApiConfig apiConfig) {
        mApiConfig = apiConfig;
        onCheckConfig();
        // initialize OkHttpClient
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.BUILD_TYPE.equals("release") ? HttpLoggingInterceptor.Level.NONE : HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Map<String, String> headers = getHeaderStore().getHeader();
                Request.Builder requestBuilder = chain.request().newBuilder();
                if (headers != null && headers.size() > 0) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        requestBuilder.addHeader(entry.getKey().trim(), entry.getValue().trim());
                    }
                }
                requestBuilder.addHeader(HEADER_UA, createUserAgent());
                return chain.proceed(requestBuilder.build());
            }
        });
        builder.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = builder.build();
        // Config retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(apiConfig.getBaseHostUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    /**
     * Validate config api
     */
    private void onCheckConfig() {
        try {
            if (mApiConfig == null) {
                throw new ApiException("Please init config first!");
            }
            if (mApiConfig.getContext() == null) {
                throw new ApiException("Please add getApplication to config");
            }
            if (TextUtils.isEmpty(mApiConfig.getBaseHostUrl())) {
                throw new ApiException("Please add base host Api to config");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create UserAgent
     *
     * @return
     */
    private String createUserAgent() {
        PackageManager pm = mApiConfig.getContext().getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(mApiConfig.getContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // No-op
        }
        return String.format(Locale.US, "%s %s/%s", System.getProperty("http.agent"), mApiConfig.getContext().getPackageName(), versionName);
    }
}
