package com.huawei.myvmplayer.fragement.mvpage;

import com.huawei.myvmplayer.base.BasePresenter;
import com.huawei.myvmplayer.bean.AreaBean;

import java.util.List;

/**
 * Created by x00378851 on 2018/8/22.
 */
public interface MVPageContract {

    /**
     *  Presenter 层接口,用于加载数据
     */
    interface Presenter extends BasePresenter{}

    /**
     * view 层接口，用于更新界面的回调
     */
    interface View {
        void setData(List<AreaBean> areaBeanArrayList);
        void onError(String msg);
    }
}
