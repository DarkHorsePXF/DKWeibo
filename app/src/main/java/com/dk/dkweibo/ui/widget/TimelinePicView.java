package com.dk.dkweibo.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dk.dkweibo.support.GlobalContext;
import com.dk.dkweibo.support.utils.DisplayUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by feng on 2015/10/24.
 */
public class TimelinePicView extends FrameLayout {
    private Context context;

    public TimelinePicView(Context context) {
        this(context, null);
    }

    public TimelinePicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimelinePicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initLayout();
    }

    private void initLayout() {

    }

    public void setImageUrls(List<String> imageUrls) {
        int size = 0;
        if (imageUrls == null || (size = imageUrls.size()) == 0) {
            this.setVisibility(GONE);
            return;
        }


        this.setVisibility(VISIBLE);
        if (size == 1) {
            OnePicView picView = new OnePicView(context);
            picView.setImageUrl(imageUrls.get(0));
            addView(picView);
        }  else {
            NinePicView picView=new NinePicView(context);
            picView.setImageUrls(imageUrls);
            addView(picView);
        }
    }

    class OnePicView extends LinearLayout {
        private int MAX_WIDTH;

        //layout_margin=5dp
        private int MARGIN = 5;

        private PicView picView;
        private Context context;

        public OnePicView(Context context) {
            this(context, null);
        }

        public OnePicView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public OnePicView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context = context;
            this.init();
        }

        private void init() {
            setGravity(Gravity.CENTER);

            MARGIN = DisplayUtil.dp2px(MARGIN);
            MAX_WIDTH = context.getResources().getDisplayMetrics().widthPixels / 3;
//            MAX_WIDTH=getRootView().getMeasuredWidth()/3;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MAX_WIDTH, MAX_WIDTH);
            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);

            picView = new PicView(this.context);
            picView.setLayoutParams(params);
            addView(picView);
        }

        void setImageUrl(String url) {
            picView.setImageURI(Uri.parse(url));
        }
    }

    class NinePicView extends LinearLayout {
        private Context context;

        private LinearLayout line1;
        private LinearLayout line2;
        private LinearLayout line3;


        private final int PIC_MARGIN = DisplayUtil.dp2px(3);
        ;
        private final int LAYOUT_WIDTH = (int) (GlobalContext.getInstance().getResources().getDisplayMetrics().widthPixels * 1.0 * (7 / 10.0));
        private final int PIC_WIDTH = (LAYOUT_WIDTH / 3) - 2 * PIC_MARGIN;
        private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PIC_WIDTH, PIC_WIDTH);

        public NinePicView(Context context) {
            this(context, null);
        }

        public NinePicView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public NinePicView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context = context;
            init();
        }

        private void init() {
            this.setLayoutParams(new LayoutParams(LAYOUT_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.setVisibility(GONE);
            this.setOrientation(VERTICAL);
            params.setMargins(PIC_MARGIN, PIC_MARGIN, PIC_MARGIN, PIC_MARGIN);
        }

        public void setImageUrls(List<String> urls) {
            if (urls.size() <= 3) {
                line1 = new LinearLayout(context);
                for (int i = 0; i < urls.size(); i++) {
                    addOnePic(line1, urls.get(i));
                }
                this.addView(line1);
            } else if (urls.size() <= 6) {
                line1 = new LinearLayout(context);
                line2 = new LinearLayout(context);
                for (int i = 0; i < urls.size(); i++) {
                    if (i < 3) {
                        addOnePic(line1, urls.get(i));
                    } else {
                        addOnePic(line2, urls.get(i));
                    }
                }
                addView(line1);
                addView(line2);

            } else {
                line1 = new LinearLayout(context);
                line2 = new LinearLayout(context);
                line3 = new LinearLayout(context);
                for (int i = 0; i < urls.size(); i++) {
                    if (i < 3) {
                        addOnePic(line1, urls.get(i));
                    } else if (i < 6) {
                        addOnePic(line2, urls.get(i));
                    } else {
                        addOnePic(line3, urls.get(i));
                    }
                }
                addView(line1);
                addView(line2);
                addView(line3);
            }
            if (urls.size()>0){
                this.setVisibility(VISIBLE);
            }
        }

        private void addOnePic(LinearLayout layout, String url) {
            SimpleDraweeView draweeView = new PicView(context);
            draweeView.setLayoutParams(params);
            draweeView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            draweeView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            draweeView.setImageURI(Uri.parse(url));
            layout.addView(draweeView);
        }

    }

    class PicView extends SimpleDraweeView{
        Context context;
        public PicView(Context context, GenericDraweeHierarchy hierarchy) {
            super(context, hierarchy);
            this.initView();
        }

        public PicView(Context context) {
            super(context);
            this.initView();
        }



        public PicView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.initView();
        }

        public PicView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.initView();
        }

        private void initView() {
            this.setScaleType(ScaleType.FIT_CENTER);
            this.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
