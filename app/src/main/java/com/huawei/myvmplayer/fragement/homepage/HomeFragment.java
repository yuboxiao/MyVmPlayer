package com.huawei.myvmplayer.fragement.homepage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.adapter.HomePageAdapter;
import com.huawei.myvmplayer.base.BaseFragment;
import com.huawei.myvmplayer.bean.VideoBean;
import com.huawei.myvmplayer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;

/**
 * Created by x00378851 on 2018/8/22.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private static final String TAG = "HomeFragment";

    private HomeContract.Presenter mIHomePresent;
    private HomePageAdapter myHomeAdapter;
    private List<VideoBean> videoBeanlist;
    //有时间的话调下下拉刷新
    //@Bind(R.id.srf_fresh)
    //SwipeRefreshLayout srFresh;

    @Bind(R.id.rv_home)
    RecyclerView rvHome;

    @Override
    public View initView() {

        mRootView = View.inflate(mActivity, R.layout.fragment_home, null);
        mIHomePresent = new HomePresenter(this);
        videoBeanlist = new ArrayList<>();
        obserView(540, 640);
        /**TODO 看下此处如何修改***/
        ButterKnife.bind(this, mRootView);

        rvHome.setLayoutManager(new LinearLayoutManager(mActivity));
        if (myHomeAdapter == null) {
            myHomeAdapter = new HomePageAdapter(videoBeanlist, mActivity, screenHeight, screenWidth);
        } else {
            myHomeAdapter.notifyDataSetChanged();
        }
        rvHome.setAdapter(myHomeAdapter);

        rvHome.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            /**
             * 当RecycleView从屏幕上移出时，需要移出videoView,防止后台后还会继续运行
             * @param view
             */
            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer jzvd = view.findViewById(R.id.vp_content);
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                    JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                        JZVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
        return mRootView;
    }

    @Override
    public void initData() {
        //加载网络数据
        mIHomePresent.getData(0, 10);
    }

    @Override
    public void setData(List<VideoBean> videoBeans) {
        LogUtils.e(TAG, "====>setData videoBeans " + videoBeans.toString());
        videoBeanlist.addAll(videoBeans);
        LogUtils.e(TAG, "====>videoBeanlist.size " + videoBeanlist.size());
        myHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int requestCode, Exception e) {
        LogUtils.e(TAG, "====>onError");
    }


    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e(TAG, "===========================>>>>>>ON PAUSE");
        //JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroyView() {
        LogUtils.e(TAG, "===========================>>>>>>ON DestroyView");
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        LogUtils.e(TAG, "===========================>>>>>>ON Destroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtils.e(TAG, "===========================>>>>>>ON Detach");
        super.onDetach();
    }
}
