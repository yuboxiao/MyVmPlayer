package com.huawei.myvmplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.activity.DMActivity;
import com.huawei.myvmplayer.bean.VideoBean;
import com.huawei.myvmplayer.utils.Util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by x00378851 on 2018/8/24.
 */

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<VideoBean> videoBeanList;
    private Activity mActivity;
    private int mWidth, mHeight;
    private LayoutInflater mLayoutInflater;

    /**
     * 3种类型
     */
    /**
     * 类型1：首页的图片轮播
     */
    public static final int HOME_BANNER = 0;
    /**
     * 类型2：直播首页推荐
     */
    public static final int HOME_RECOMMEND_LIVE = 1;
    /**
     * 类型3：点播首页推荐
     */
    public static final int HOME_RECOMMEND_REQUESTED = 2;

    /**
     * 当前类型
     */
    public int currentType = HOME_BANNER;

    public HomePageAdapter(List<VideoBean> videoBeanList, Activity activity, int mWidth, int mHeight) {
        this.videoBeanList = videoBeanList;
        this.mActivity = activity;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case HOME_BANNER:
                viewHolder = new HomePageBannerHolder(mActivity, mLayoutInflater.inflate(R.layout.homepage_banner, parent, false));
                break;
            case HOME_RECOMMEND_LIVE:
                viewHolder = new HomeRecommendLiveHolder(mActivity, mLayoutInflater.inflate(R.layout.homepage_recommend, parent, false));
                break;
            case HOME_RECOMMEND_REQUESTED:
                viewHolder = new HomeRecommendLiveHolder(mActivity, mLayoutInflater.inflate(R.layout.homepage_recommend, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case HOME_BANNER:
                currentType = HOME_BANNER;
                break;
            case HOME_RECOMMEND_LIVE:
                currentType = HOME_RECOMMEND_LIVE;
                break;
            case HOME_RECOMMEND_REQUESTED:
                currentType = HOME_RECOMMEND_REQUESTED;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOME_BANNER) {
            HomePageBannerHolder hpbHolder = (HomePageBannerHolder) holder;
            hpbHolder.setData(videoBeanList);
        } else if (getItemViewType(position) == HOME_RECOMMEND_LIVE) {
            HomeRecommendLiveHolder hrlHolder = (HomeRecommendLiveHolder) holder;
            //这里mock一下数据
            List<String> imgUrls = new ArrayList<>();
            imgUrls.add("http://img1.c.yinyuetai.com/others/mobile_front_page/180820/0/-M-3bc16bde839c0e44b4eeb1fdb8dd1616_0x0.jpg");
            imgUrls.add("http://img0.c.yinyuetai.com/others/mobile_front_page/180821/0/-M-04b60880502717f1fc60de4d058a900f_0x0.jpg");
            imgUrls.add("http://img2.c.yinyuetai.com/others/mobile_front_page/180820/0/-M-06879a87857423d3753830874474fe02_0x0.jpg");
            imgUrls.add("http://img0.c.yinyuetai.com/others/mobile_front_page/180820/0/-M-211ba53305d68037ba23f0fd565c2085_0x0.jpg");
            hrlHolder.setData(imgUrls);
        } else if (getItemViewType(position) == HOME_RECOMMEND_REQUESTED) {
            HomeRecommendLiveHolder hrlHolder = (HomeRecommendLiveHolder) holder;
            //这里mock一下数据
            List<String> imgUrls = new ArrayList<>();
            imgUrls.add("http://img1.c.yinyuetai.com/others/mobile_front_page/180820/0/-M-3bc16bde839c0e44b4eeb1fdb8dd1616_0x0.jpg");
            imgUrls.add("http://img0.c.yinyuetai.com/others/mobile_front_page/180821/0/-M-04b60880502717f1fc60de4d058a900f_0x0.jpg");
            imgUrls.add("http://img2.c.yinyuetai.com/others/mobile_front_page/180820/0/-M-06879a87857423d3753830874474fe02_0x0.jpg");
            imgUrls.add("http://img0.c.yinyuetai.com/others/mobile_front_page/180820/0/-M-211ba53305d68037ba23f0fd565c2085_0x0.jpg");
            hrlHolder.setData(imgUrls);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class HomePageBannerHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private Banner banner;

        public HomePageBannerHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = itemView.findViewById(R.id.banner);
        }

        public void setData(List<VideoBean> videoBeanList) {
            //设置Banner的数据
            //得到图片地址的集合
            List imageUrls = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            imageUrls.clear();
            titles.clear();

            for (VideoBean videoBean: videoBeanList) {
                imageUrls.add(videoBean.getPosterPic());
                titles.add(videoBean.getTitle());
            }

            //加载两张本地图片
            imageUrls.add(R.drawable.cctv_1);
            titles.add("CCTV1");
            banner.setBannerTitles(titles);
            banner.setImages(imageUrls).setImageLoader(new Util.GlideImageLoader()).start();
            banner.setDelayTime(3000);
            banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);

            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(mActivity, DMActivity.class);
                    mActivity.startActivity(intent);
                    //注意这里的position是从1开始的
                    //Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public class HomeRecommendLiveHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private RecyclerView dapeiqs_rv;
        private TextView tvTitle;

        public HomeRecommendLiveHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            dapeiqs_rv = itemView.findViewById(R.id.dapeiqs_rv);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        public void setData(List<String> dapeiqs6data) {
            //1.已有数据
            //2.设置适配器
            HomeLiveAdapter adapter = new HomeLiveAdapter(mContext, dapeiqs6data);
            dapeiqs_rv.setAdapter(adapter);

            //recycleView不仅要设置适配器还要设置布局管理者,否则图片不显示
            //LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            //这里要注意下，布局是什么样子的
            //StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
            GridLayoutManager manager = new GridLayoutManager(mContext, 2);
            dapeiqs_rv.setLayoutManager(manager);
        }
    }
}
