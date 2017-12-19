package com.example.hovanly.baseproject.ui.commons;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Ly Ho V. on December 19, 2017
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    @AfterViews
    public abstract void init();
}
