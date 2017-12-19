package com.example.hovanly.baseproject.ui.commons;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Ly Ho V. on December 19, 2017
 */
@EFragment
public abstract class BaseDialog extends DialogFragment {
    @AfterViews
    protected abstract void init();

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = getWidthDialog();
            params.height = getHeightDialog();
            params.gravity = getGravity();
            window.setAttributes(params);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        if (dialog.getWindow() != null) {
            dialog.setCanceledOnTouchOutside(canTouchOutSide());
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismiss();
    }

    public void show(FragmentManager manager) {
        if (manager != null) {
            super.show(manager, null);
        }
    }

    protected int getWidthDialog() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    protected int getHeightDialog() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public int getGravity() {
        return Gravity.CENTER;
    }

    public boolean canTouchOutSide() {
        return true;
    }
}
