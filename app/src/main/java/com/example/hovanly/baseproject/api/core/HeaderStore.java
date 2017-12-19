package com.example.hovanly.baseproject.api.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.util.Map;

/**
 * @author BiNC, Ly Ho V.
 */
public class HeaderStore {
    public static final String ACCESS_TOKEN_NAME = "access_token";
    private static final String HEADER_STORE_NAME = "HEADER_STORE_NAME";
    private static HeaderStore sSessionStore;
    private static SharedPreferences sSharedPreferences;

    private HeaderStore() {
        // No-op
    }

    public static synchronized HeaderStore getInstance(Context context) {
        sSharedPreferences = context.getSharedPreferences(HEADER_STORE_NAME, Context.MODE_PRIVATE);
        if (sSessionStore == null) {
            sSessionStore = new HeaderStore();
        }
        return sSessionStore;
    }

    public void addHeader(String key, String value) {
        sSharedPreferences.edit().putString(key, value).apply();
    }

    public void removeHeader(String key) {
        sSharedPreferences.edit().remove(key).apply();
    }

    public void clearHeader() {
        sSharedPreferences.edit().clear().apply();
    }

    public void addAuth(String name, String pass) {
        String userCredentials = String.format("%s:%s", name, pass);
        String basicAuth = String.format("%s%s", "Basic ", Base64.encodeToString(userCredentials.getBytes(), Base64.DEFAULT));
        sSharedPreferences.edit().putString("Authorization", basicAuth).apply();
    }

    public Map getHeader() {
        return sSharedPreferences.getAll();
    }
}
