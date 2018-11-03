package com.huawei.myvmplayer.fragement.mvpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.adapter.MvItemPageAdapter;
import com.huawei.myvmplayer.bean.VideoBean;

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
public class MVPageItemFragment extends Fragment implements MVPageitemContract.View {

    private View rootView;
    @Bind(R.id.rv_mvitem)
    RecyclerView rvMvitem;
    private String code;
    private List<VideoBean> videoBeanList;
    private MvItemPageAdapter mvItemPageAdapter;
    private MVPageItemPresenter presenter;
    private int screenWidth;
    private int screenHeight;


    public static MVPageItemFragment getInstance(String code) {
        MVPageItemFragment mvPageItemFragment = new MVPageItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        mvPageItemFragment.setArguments(bundle);
        return mvPageItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        code = bundle.getString("code", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mv_item, container, false);
            ButterKnife.bind(this, rootView);
            videoBeanList = new ArrayList<>();
            obserView(640, 360);
            initView();
            presenter = new MVPageItemPresenter(this);
            presenter.getData(0, 10, code);
        }
        return rootView;
    }

    private void initView() {
        rvMvitem.setLayoutManager(new LinearLayoutManager(getActivity()));
        mvItemPageAdapter = new MvItemPageAdapter(videoBeanList, getActivity(), screenWidth, screenHeight);
        rvMvitem.setAdapter(mvItemPageAdapter);

        rvMvitem.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setData(List<VideoBean> videoBeanLists) {
        videoBeanList.addAll(videoBeanLists);
        mvItemPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setError(String msg) {

    }

    protected void obserView(int width, int height) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = screenWidth * height / width;
    }
}
