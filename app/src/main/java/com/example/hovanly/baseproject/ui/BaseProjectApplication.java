package com.example.hovanly.baseproject.ui;

import android.app.Application;

import com.example.hovanly.baseproject.BuildConfig;
import com.example.hovanly.baseproject.api.core.ApiClient;
import com.example.hovanly.baseproject.api.core.ApiConfig;

/**
 * Created by Ly Ho V. on December 19, 2017
 */

public class BaseProjectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
    }

    private void initRetrofit() {
        ApiConfig apiConfig = new ApiConfig.ApiConfigBuilder()
                .setContext(getApplicationContext())
                .setBaseHostUrl(BuildConfig.HOST_API)
                .build();
        ApiClient.getInstance().init(apiConfig);
    }
}
