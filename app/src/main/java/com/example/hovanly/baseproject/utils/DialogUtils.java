package com.example.hovanly.baseproject.utils;

/**
 * QuangTD on 11/9/2017.
 */

public class DialogUtils {
    private DialogUtils() {
        //no-op
    }

    public interface DialogConfirmCallback {
        void onClickPositiveButton();

        void onClickNegativeButton();
    }

    public interface DialogAlertCallback {
        void onClickPositive();
    }
}
