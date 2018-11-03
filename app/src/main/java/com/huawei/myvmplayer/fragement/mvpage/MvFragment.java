package com.huawei.myvmplayer.fragement.mvpage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.adapter.MyViewPageAdapter;
import com.huawei.myvmplayer.bean.AreaBean;
import com.huawei.myvmplayer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * Created by x00378851 on 2018/8/22.
 */
public class MvFragment extends Fragment implements MVPageContract.View {

    private static final String TAG = "MvFragment";

    protected View rootView;
    private Activity mActivity;
    private MVPageContract.Presenter mIPresent;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPage)
    ViewPager viewPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mv, container, false);
            ButterKnife.bind(this, rootView);
            mIPresent = new MVPagePresenter(this);
            mIPresent.getData(0, 10);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void setData(List<AreaBean> areaBeanArrayList) {
        for (int i = 0; i < areaBeanArrayList.size(); i++) {
            fragmentList.add(MVPageItemFragment.getInstance(areaBeanArrayList.get(i).code));
        }
        initView(areaBeanArrayList);
    }

    @Override
    public void onError(String msg) {
    }

    private void initView(List<AreaBean> areaBeanArrayList) {
        MyViewPageAdapter viewPageAdapter = new MyViewPageAdapter(getFragmentManager(), fragmentList, areaBeanArrayList);
        viewPage.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPage);

        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e(TAG, "===>change postion : " + position + "and releaseAllVideos!!!");
                JZVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
