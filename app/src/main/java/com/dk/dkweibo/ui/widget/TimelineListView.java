package com.dk.dkweibo.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.dk.dkweibo.support.utils.LogUtil;

/**
 * Created by feng on 2015/10/10.
 */
public class TimelineListView extends RecyclerView {
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    private boolean isShowTop = true;
    private boolean isShowBottom = false;


    public TimelineListView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(VERTICAL);
        this.setLayoutManager(mLayoutManager);
    }


    public TimelineListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TimelineListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    @Override
    public void onScrolled(int dx, int dy) {
        int lastPosition=mLayoutManager.findLastCompletelyVisibleItemPosition();
        if (lastPosition == this.getChildCount()-1) {
            if (!isShowBottom) {
                isShowBottom=true;
            }
        } else {
            isShowBottom = false;
        }

        int firstPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if ( firstPosition== 0) {
            if (!isShowTop) {
                isShowTop=true;
            }
        } else {
            isShowTop=false;
        }
    }

    @Override
    public void onScrollStateChanged(int state) {

    }

    private boolean refreshable = true;
    private boolean shouldLoadMore = false;

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof LinearLayoutManager))
            throw new IllegalStateException("You should input LinearLayoutManager!");
        super.setLayoutManager(layout);
    }

    public final boolean isRefreshable() {
        return refreshable;
    }

    public final void setRefreshable(boolean refreshable) {
        this.refreshable = refreshable;
    }

    public final boolean isShouldLoadMore() {
        return shouldLoadMore;
    }

    public final void setShouldLoadMore(boolean shouldLoadMore) {
        this.shouldLoadMore = shouldLoadMore;
    }

    public boolean isShowTop(){
        return isShowTop;
    }

    public boolean isShowBottom(){
        return isShowBottom;
    }
}
