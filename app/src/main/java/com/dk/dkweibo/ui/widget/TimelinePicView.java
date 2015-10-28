package com.dk.dkweibo.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    private OnePicView onePicView;
    private NinePicView ninePicView;


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
        onePicView = new OnePicView(context);
        ninePicView = new NinePicView(context);
        addView(onePicView);
        addView(ninePicView);
    }

    public void setImageUrls(List<String> imageUrls) {
        int size = 0;
        if (imageUrls == null || (size = imageUrls.size()) == 0) {
            this.setVisibility(GONE);
            return;
        }
        this.setVisibility(VISIBLE);
        if (size == 1) {
            onePicView.setImageUrl(imageUrls.get(0));
            onePicView.setVisibility(VISIBLE);
            ninePicView.setVisibility(GONE);
        } else {
            ninePicView.setImageUrls(imageUrls);
            ninePicView.setVisibility(VISIBLE);
            onePicView.setVisibility(GONE);
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

        private PicView[] picViews;

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
            picViews = new PicView[9];
            line1 = new LinearLayout(context);
            line2 = new LinearLayout(context);
            line3 = new LinearLayout(context);
            addView(line1);
            addView(line2);
            addView(line3);
            for (int i = 0; i < picViews.length; i++) {
                picViews[i] = new PicView(context);
                picViews[i].setLayoutParams(params);
                if (i < 3) {
                    line1.addView(picViews[i]);
                } else if (i < 6) {
                    line2.addView(picViews[i]);
                } else {
                    line3.addView(picViews[i]);
                }
            }
        }

        public void setImageUrls(List<String> urls) {
            int count = 0;
            for (int i = 0; i < 9; i++) {
                if (i < urls.size()) {
                    picViews[i].setImageURI(Uri.parse(urls.get(i)));
                    count++;
                } else {
                    if (count == 0) {
                        line1.setVisibility(GONE);
                        line2.setVisibility(GONE);
                        line3.setVisibility(GONE);
                    } else if (count <= 3) {
                        line1.setVisibility(VISIBLE);
                        line2.setVisibility(GONE);
                        line3.setVisibility(GONE);
                        //当不足一行时，后面的空图片不显示
                        while (count < 3) {
                            picViews[count].setVisibility(GONE);
                            count++;
                        }
                    } else if (count < 6) {
                        line1.setVisibility(VISIBLE);
                        line2.setVisibility(VISIBLE);
                        line3.setVisibility(GONE);
                        while (count < 6) {
                            picViews[count].setVisibility(GONE);
                            count++;
                        }
                    } else {
                        line1.setVisibility(VISIBLE);
                        line2.setVisibility(VISIBLE);
                        line3.setVisibility(VISIBLE);
                        while (count < 9) {
                            picViews[count].setVisibility(GONE);
                            count++;
                        }
                    }
                    break;
                }
            }

            if (urls.size() > 0) {
                this.setVisibility(VISIBLE);
            }

        }

        private void addOnePic(LinearLayout layout, String url) {
            SimpleDraweeView draweeView = new PicView(context);
            draweeView.setLayoutParams(params);
            draweeView.setImageURI(Uri.parse(url));
            layout.addView(draweeView);
        }

    }

    class PicView extends SimpleDraweeView {
        Context context;

        public PicView(Context context, GenericDraweeHierarchy hierarchy) {
            super(context, hierarchy);
            this.initView();
        }

        public PicView(Context context) {
            super(context);
            this.context = context;
            this.initView();
        }


        public PicView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            this.initView();
        }

        public PicView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.context = context;
            this.initView();
        }

        private void initView() {
            this.setScaleType(ScaleType.FIT_CENTER);
            this.setImageDrawable(new ColorDrawable(Color.GRAY));
        }
    }
}
