package com.example.hovanly.baseproject.ui.commons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base ViewHolder
 */
public abstract class BaseViewHolder<DATA> extends RecyclerView.ViewHolder {
    protected Context context;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(DATA data);

}
