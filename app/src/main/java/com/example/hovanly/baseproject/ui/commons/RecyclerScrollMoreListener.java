package com.example.hovanly.baseproject.ui.commons;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Ly Ho V. on 08 December 2017
 */
public class RecyclerScrollMoreListener extends RecyclerView.OnScrollListener {
    private OnLoadMoreListener mLoadMoreListener;
    private int mCurrentPage = 0;
    private int mPreviousTotalItemCount = 0;
    private boolean mIsLoading = true;
    private int mVisibleThreshold = 5;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecyclerScrollMoreListener(RecyclerView.LayoutManager layoutManager, int currentPage, int visibleThreshold, OnLoadMoreListener loadMoreListener) {
        mLayoutManager = layoutManager;
        mLoadMoreListener = loadMoreListener;
        mVisibleThreshold = visibleThreshold;
        mCurrentPage = currentPage;
    }

    /**
     * handler for gitLayout
     *
     * @param lastVisibleItemPositions
     * @return
     */
    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (mLoadMoreListener != null) {
            int lastVisibleItemPosition = 0;
            int totalItemCount = mLayoutManager.getItemCount();
            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            } else if (mLayoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            }
            if (totalItemCount < mPreviousTotalItemCount) {
                mCurrentPage = 0;
                mPreviousTotalItemCount = totalItemCount;
                mIsLoading = totalItemCount == 0;
            }

            if (mIsLoading && (totalItemCount > mPreviousTotalItemCount)) {
                mIsLoading = false;
                mPreviousTotalItemCount = totalItemCount;
            }

            if (!mIsLoading && (lastVisibleItemPosition + mVisibleThreshold) > totalItemCount) {
                mCurrentPage++;
                mLoadMoreListener.onLoadMore(mCurrentPage, mVisibleThreshold);
                mIsLoading = true;
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page, int visibleThreshold);
    }
}
