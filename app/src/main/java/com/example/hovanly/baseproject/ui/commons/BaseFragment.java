package com.example.hovanly.baseproject.ui.commons;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Ly Ho V. on December 19, 2017
 */
@EFragment
public abstract class BaseFragment extends Fragment {
    @AfterViews
    abstract void init();
}
