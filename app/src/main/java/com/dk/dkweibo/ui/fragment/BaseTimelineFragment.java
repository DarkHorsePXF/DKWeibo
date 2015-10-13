package com.dk.dkweibo.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dk.dkweibo.R;
import com.dk.dkweibo.support.utils.DisplayUtil;
import com.dk.dkweibo.support.utils.ToastUtil;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by feng on 2015/10/5.
 */
public class BaseTimelineFragment extends Fragment{
    private final int MSG_REFRESH_COMPLETE = 0x11;

    private PtrFrameLayout ptrFrameLayout;
    private Context context;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_REFRESH_COMPLETE:{
                    refreshComplete();
                    ToastUtil.shortToast("刷新完成");
                    break;
                }
                default:
                    break;

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=container.getContext();
        View view=inflater.inflate(R.layout.fragment_timeline,container,false);

        ptrFrameLayout= (PtrFrameLayout) view.findViewById(R.id.ptr_layout);


        final MaterialHeader header=new MaterialHeader(context);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dp2px(context,15), 0, DisplayUtil.dp2px(context,10));
        header.setPtrFrameLayout(ptrFrameLayout);

        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh(true);
            }
        },150);
        setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                ToastUtil.shortToast("开始刷新！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            handler.sendEmptyMessage(MSG_REFRESH_COMPLETE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        return view;
    }

    protected void refreshComplete(){
        if (ptrFrameLayout!=null){
            ptrFrameLayout.refreshComplete();
        }
    }


    public void setPtrHandler(PtrHandler ptrHandler){
        if (ptrHandler!=null){
            ptrFrameLayout.setPtrHandler(ptrHandler);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
