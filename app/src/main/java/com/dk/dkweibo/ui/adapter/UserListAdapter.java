package com.dk.dkweibo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.UserBean;
import com.dk.dkweibo.support.utils.GlobalContext;

import java.util.List;

/**
 * Created by feng on 2015/8/28.
 */
public class UserListAdapter extends BaseAdapter {
    private List<UserBean> userList;
    private LayoutInflater inflater;
    private Context mContext;


    public UserListAdapter(List<UserBean> userList){
        this.userList = userList;
        this.mContext = GlobalContext.getInstance();
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (userList==null){
            return 0;
        }
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView==null){
            holder= new ViewHolder();
            RelativeLayout layout = (RelativeLayout) inflater
                    .inflate(R.layout.layout_user,null);
            holder.ivUserHead = (ImageView) layout.findViewById(R.id.iv_list_user_head);
            holder.tvUserName = (TextView) layout.findViewById(R.id.tv_list_user_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ivUserHead.setImageDrawable(userList.get(position).getUserHead());
        holder.tvUserName.setText(userList.get(position).getUserName());
        return convertView;
    }

    private class ViewHolder{
        ImageView ivUserHead;
        TextView tvUserName;
    }
}
