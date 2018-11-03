package com.huawei.myvmplayer.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.myvmplayer.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by x00378851 on 2018/8/22.
 */

public abstract class BaseFragment extends Fragment {

    //private static final String TAG = "BaseFragment";

    public View mRootView;
    public Activity mActivity;
    protected int screenWidth, screenHeight;

    protected void obserView(int width, int height) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = screenWidth * height / width;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里初始化View,获取Context对象
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView();


        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //在这里可以初始化数据
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public abstract View initView();

    public abstract void initData();
}
