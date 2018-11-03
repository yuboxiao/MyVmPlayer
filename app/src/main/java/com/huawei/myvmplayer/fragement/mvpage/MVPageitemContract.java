package com.huawei.myvmplayer.fragement.mvpage;

import com.huawei.myvmplayer.base.BasePresenter;
import com.huawei.myvmplayer.bean.VideoBean;

import java.util.List;

/**
 * Created by x00378851 on 2018/8/22.
 */
public interface MVPageitemContract {
    interface Presenter extends BasePresenter {
        void getData(int moffest, int size, String areaCode);
    }

    interface View {
        void setData(List<VideoBean> videoBeanList);

        void setError(String msg);
    }
}
