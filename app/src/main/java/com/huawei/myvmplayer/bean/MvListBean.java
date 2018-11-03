package com.huawei.myvmplayer.bean;

import java.util.List;
/**
 * Created by x00378851 on 2018/8/22.
 */
public class MvListBean {

    private int totalCount;
    private List<VideoBean> videos;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<VideoBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoBean> videos) {
        this.videos = videos;
    }
}
