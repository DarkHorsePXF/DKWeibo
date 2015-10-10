package com.dk.dkweibo.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.Status;
import com.dk.dkweibo.support.GlobalContext;
import com.dk.dkweibo.support.api.StatusesAPI;
import com.dk.dkweibo.support.utils.LogUtil;
import com.dk.dkweibo.support.utils.ToastUtil;
import com.dk.dkweibo.ui.adapter.TimelineAdapter;
import com.dk.dkweibo.ui.widget.TimelineListView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2015/10/5.
 */
public class HomeTimelineFragment extends BaseTimelineFragment{

    private TimelineAdapter timelineAdapter;
    private LinearLayoutManager layoutManager;
    private TimelineListView lvTimeline;
    private List<Status> statusList;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        mContext=container.getContext();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {
        statusList=new ArrayList<>();

        //初始化下拉刷新时的动作
        super.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return lvTimeline.isShowTop();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                StatusesAPI statusesAPI=new StatusesAPI(getActivity());
                statusesAPI.doGetHomeTimeline(
                        GlobalContext.getInstance().getRequeseQueue(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    statusList.clear();
                                    JSONArray jsonArray=response.getJSONArray("statuses");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json=jsonArray.getJSONObject(i);
                                        Status status=Status.getStatusFromJson(json);
                                        statusList.add(status);
                                        LogUtil.v("status",status.toString());
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ToastUtil.shortToast(GlobalContext.getInstance().getApplicationContext().getString(R.string.network_request_error));
                                }finally {
                                    timelineAdapter.notifyDataSetChanged();
                                    HomeTimelineFragment.super.refreshComplete();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                HomeTimelineFragment.super.refreshComplete();
                                ToastUtil.shortToast(GlobalContext.getInstance().getApplicationContext().getString(R.string.network_request_error));
                            }
                        }
                );
            }
        });

        //初始化RecyclerView
        lvTimeline = (TimelineListView) getView().findViewById(R.id.rv_timeline);
//        layoutManager=new LinearLayoutManager(mContext);
//        lvTimeline.setLayoutManager(layoutManager);
        timelineAdapter =new TimelineAdapter(mContext,statusList);
        lvTimeline.setAdapter(timelineAdapter);

    }
}
