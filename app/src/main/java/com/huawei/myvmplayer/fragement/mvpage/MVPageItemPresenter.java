package com.huawei.myvmplayer.fragement.mvpage;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.myvmplayer.bean.MvListBean;
import com.huawei.myvmplayer.http.BaseCallBack;
import com.huawei.myvmplayer.http.HttpManager;
import com.huawei.myvmplayer.utils.URLProviderUtil;

/**
 * Created by x00378851 on 2018/8/22.
 */
public class MVPageItemPresenter implements MVPageitemContract.Presenter {

    private static final String TAG ="MVPageItemPresenter" ;
    private MVPageitemContract.View view;

    public MVPageItemPresenter(MVPageitemContract.View view) {
        this.view = view;
     }

    @Override
    public void getData(int offest, int size) {

    }

    @Override
    public void getData(int moffest, int size, String areaCode) {
        Log.i(TAG, "getData: "+ URLProviderUtil.getMVListUrl(areaCode, moffest, size));
        HttpManager.getHttpManager().get(URLProviderUtil.getMVListUrl(areaCode, moffest, size), new BaseCallBack<String>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.setError(e.getLocalizedMessage());
            }

            @Override
            public void onSuccess(String response) {
                if (response!=null){
                    try {
                        MvListBean mvListBean = new Gson().fromJson(response, MvListBean.class);
                        view.setData(mvListBean.getVideos());
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        view.setError(e.getLocalizedMessage());
                    }

                }else {
                    view.setError("");
                }
            }
        });
    }
}
