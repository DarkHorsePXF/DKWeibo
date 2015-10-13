package com.dk.dkweibo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by feng on 2015/10/11.
 */
public class RetweetStatusTextView extends TextView{
    public RetweetStatusTextView(Context context) {
        super(context);
    }

    public RetweetStatusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RetweetStatusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRetweetUsernameAndText(String username,String text){
        StringBuilder sb=new StringBuilder();
        sb.append("@"+username);
        sb.append(" : ");
        sb.append(text);
        setText(sb.toString());
    }

}
