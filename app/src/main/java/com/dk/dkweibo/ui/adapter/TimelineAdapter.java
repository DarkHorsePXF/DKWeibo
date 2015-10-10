package com.dk.dkweibo.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.Status;
import com.dk.dkweibo.support.utils.LogUtil;
import com.dk.dkweibo.support.utils.ToastUtil;
import com.dk.dkweibo.ui.widget.AvatarView;
import com.dk.dkweibo.ui.widget.StatusTextView;

import java.util.List;

/**
 * Created by feng on 2015/10/8.
 */
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Status> statusList;

    public TimelineAdapter(Context context,List<Status> statusList){
        this.mContext=context;
        this.statusList=statusList;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public StatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StatusHolder(inflater.inflate(R.layout.list_item_timeline,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StatusHolder){
            StatusHolder statusHolder= (StatusHolder) holder;
            Status status=statusList.get(position);
            Uri uri=Uri.parse(status.getUser().getProfileUserHeadUrl());
            statusHolder.avatarView.setImageURI(uri);
            statusHolder.statusTextView.setText(status.getText());
            if (status.getRetweet_count()!=0)
                statusHolder.tvRetweet.setText(status.getRetweet_count()+"");
            if (status.getComments_count()!=0)
                statusHolder.tvCommend.setText(status.getComments_count()+"");
            if (status.getAgree_count()!=0)
                statusHolder.tvAgree.setText(status.getAgree_count()+"");
            statusHolder.tvUsername.setText(status.getUser().getUserName());

        }
    }


    @Override
    public int getItemCount() {
        return statusList==null?0:statusList.size();
    }

    private class StatusHolder extends RecyclerView.ViewHolder{
        AvatarView avatarView;
        StatusTextView statusTextView;
        LinearLayout llCommend;
        LinearLayout llRetweet;
        LinearLayout llAgree;
        TextView tvCommend;
        TextView tvRetweet;
        TextView tvAgree;
        TextView tvUsername;
        TextView tvPublishTime;
        TextView tvFromDevice;


        public StatusHolder(View itemView) {
            super(itemView);

            avatarView= (AvatarView) itemView.findViewById(R.id.iv_user_head);
            statusTextView= (StatusTextView) itemView.findViewById(R.id.tv_status_text);
            tvUsername= (TextView) itemView.findViewById(R.id.tv_user_name);
            tvPublishTime= (TextView) itemView.findViewById(R.id.tv_publish_time);
            tvFromDevice= (TextView) itemView.findViewById(R.id.tv_from_device);
            llCommend = (LinearLayout) itemView.findViewById(R.id.ll_comment);
            llRetweet = (LinearLayout) itemView.findViewById(R.id.ll_retweet);
            llAgree = (LinearLayout) itemView.findViewById(R.id.ll_agree);
            tvAgree= (TextView) itemView.findViewById(R.id.tv_agree);
            tvCommend= (TextView) itemView.findViewById(R.id.tv_comment);
            tvRetweet= (TextView) itemView.findViewById(R.id.tv_retweet);

            View.OnClickListener listener=new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Status status=statusList.get(getAdapterPosition());
                    switch (v.getId()){
                        case R.id.iv_user_head:
                            ToastUtil.shortToast(status.getUser().getUserName());
                            break;
                        case R.id.tv_status_text:
                            LogUtil.v("click", status.getText());
                            break;
                        case R.id.ll_agree:
                            ToastUtil.shortToast("赞同:"+status.getUser().getUserName());
                            break;
                        case R.id.ll_retweet:
                            ToastUtil.shortToast("转发:"+status.getUser().getUserName());
                            break;
                        case R.id.ll_comment:
                            ToastUtil.shortToast("评论:"+status.getUser().getUserName());
                            break;

                    }
                }
            };

            avatarView.setOnClickListener(listener);
            statusTextView.setOnClickListener(listener);
            llCommend.setOnClickListener(listener);
            llRetweet.setOnClickListener(listener);
            llAgree.setOnClickListener(listener);

        }
    }

}
