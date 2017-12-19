package com.example.hovanly.baseproject.api.core;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author BiNC, Ly Ho V.
 */
public class ApiCallback<T> implements Callback<T> {
    public ApiCallback() {
        // No-op
    }

    public void success(T t) {
        // No-op
    }

    public void failure(ApiError apiError) {
        // No-op
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            success(response.body());
        } else {
            onErrorBody(response);
        }
    }

    private void onErrorBody(Response<T> response) {
        Gson gson = new Gson();
        ApiError apiError = new ApiError();
        apiError.setCode(response.code());
        if (response.errorBody() != null) {
            try {
                String body = response.errorBody().string();
                apiError = gson.fromJson(body, ApiError.class);
                failure(apiError);
            } catch (IOException e) {
                apiError.setMessage(response.message());
            }
        } else {
            apiError.setMessage(response.message());
        }
        failure(apiError);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        failure(new ApiError(HttpURLConnection.HTTP_CLIENT_TIMEOUT, t.getMessage()));
    }
}
