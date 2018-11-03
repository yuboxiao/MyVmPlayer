package com.huawei.myvmplayer.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.huawei.myvmplayer.R;

import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by x00378851 on 2018/8/25.
 */

public class MyJzVideoPlayer extends JZVideoPlayerStandard {


    public MyJzVideoPlayer(Context context) {
        super(context);
    }

    public MyJzVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public void onClick(View v) {
        ViewGroup vp = (JZUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View childAt = vp.getChildAt(0);

        int i = v.getId();
        if (i == cn.jzvd.R.id.fullscreen) {
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                if (!childAt.isShown()) {
                    childAt.setVisibility(VISIBLE);
                }
            } else {
                //click goto fullscreen
                if (childAt.isShown()) {
                    childAt.setVisibility(INVISIBLE);
                }
            }
        } else if (i == cn.jzvd.R.id.back) {
            if (!childAt.isShown()) {
                childAt.setVisibility(VISIBLE);
            }
        }
        super.onClick(v);
    }

    @Override
    public void startVideo() {
        super.startVideo();
        System.out.println("========>on startVideo");
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        ViewGroup vp = (JZUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View childAt = vp.getChildAt(0);
        if (!childAt.isShown()) {
            childAt.setVisibility(VISIBLE);
        }
    }
}
