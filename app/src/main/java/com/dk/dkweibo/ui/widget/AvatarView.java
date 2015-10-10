package com.dk.dkweibo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by feng on 2015/10/10.
 */
public class AvatarView extends SimpleDraweeView {

    public AvatarView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
