package com.example.hovanly.baseproject.api.core;

/**
 * @author BiNC, Ly Ho V.
 */
public class ApiError {
    private int code;
    private String name;
    private String message;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ApiError() {
        // No-op
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
