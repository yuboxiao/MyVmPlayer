package com.huawei.myvmplayer.fragement.homepage;

import com.huawei.myvmplayer.base.BasePresenter;
import com.huawei.myvmplayer.bean.VideoBean;

import java.util.List;

/**
 * Created by x00378851 on 2018/8/22.
 */

public interface HomeContract {

    /**
     *  Presenter 层接口,用于加载数据
     */
    interface Presenter extends BasePresenter{
        //TODO 扩展其他业务逻辑
    }

    /**
     * view 层接口，用于更新界面的回调
     */
    interface View {
        /**
         * 设置主页视图层数据加载的回调
         * @param videoBeans
         */
        void setData(List<VideoBean> videoBeans);

        /**
         * 设置主页视图层数据加载失败事的回调
         * @param requestCode
         * @param e
         */
        void onError(int requestCode, Exception e);
    }
}
