package com.huawei.myvmplayer.fragement.homepage;

import com.huawei.myvmplayer.bean.VideoBean;
import com.huawei.myvmplayer.http.BaseCallBack;
import com.huawei.myvmplayer.http.HttpManager;
import com.huawei.myvmplayer.utils.LogUtils;
import com.huawei.myvmplayer.utils.URLProviderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x00378851 on 2018/8/22.
 */

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";
    private HomeContract.View mIView;

    public HomePresenter(HomeContract.View view) {
        this.mIView = view;
    }

    /**
     * 加载业务数据
     *
     * @param offset
     * @param size
     */
    @Override
    public void getData(int offset, int size) {
        String mainPageUrl = URLProviderUtil.getMainPageUrl(offset, size);
        HttpManager httpManager = HttpManager.getHttpManager();

        httpManager.get(mainPageUrl, new BaseCallBack<List<VideoBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                LogUtils.e(TAG, "======>onFailure !!!！");

                if (mIView != null) {
                    mIView.onError(code, e);
                }
            }

            @Override
            public void onSuccess(List<VideoBean> videoBeans) {
                LogUtils.e(TAG, "======> getData successfully ！！！！！" + videoBeans);

                List<VideoBean> goodVideo = new ArrayList<>();
                goodVideo.clear();

                //请注意，我们在这里需要过滤下服务器返回的不能播放的类型
                for (VideoBean videoBean : videoBeans) {
                    if ((videoBean.getType().equalsIgnoreCase("VIDEO"))
                            || (videoBean.getType().equalsIgnoreCase("PROGRAM"))
                            || (videoBean.getType().equalsIgnoreCase("fanart"))) {
                        goodVideo.add(videoBean);
                    }
                }

                if (mIView != null) {
                    mIView.setData(goodVideo);
                }
            }
        });
    }
}
