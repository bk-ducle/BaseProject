package com.example.hovanly.baseproject.api.core;

import android.content.Context;

import lombok.Data;

/**
 * @author BiNC, Ly Ho V.
 */
@Data
public class ApiConfig {
    private Context context;
    private String baseHostUrl;

    private ApiConfig() {
        // No-op
    }

    public static class ApiConfigBuilder {
        private ApiConfig apiConfig;

        public ApiConfigBuilder() {
            apiConfig = new ApiConfig();
        }

        public ApiConfigBuilder setContext(Context context) {
            apiConfig.setContext(context);
            return this;
        }

        public ApiConfigBuilder setBaseHostUrl(String baseHostUrl) {
            apiConfig.setBaseHostUrl(baseHostUrl);
            return this;
        }

        public ApiConfig build() {
            return apiConfig;
        }
    }
}
