package com.huawei.myvmplayer.base;

/**
 * Created by x00378851 on 2018/8/23.
 */

public interface BasePresenter {
    /**
     * 分页加载
     * @param offset
     * @param size
     */
    void getData(int offset, int size);
}
