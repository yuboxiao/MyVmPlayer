package com.huawei.myvmplayer.fragement.mvpage;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.huawei.myvmplayer.bean.AreaBean;
import com.huawei.myvmplayer.http.BaseCallBack;
import com.huawei.myvmplayer.http.HttpManager;
import com.huawei.myvmplayer.utils.LogUtils;
import com.huawei.myvmplayer.utils.URLProviderUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by x00378851 on 2018/8/22.
 */
public class MVPagePresenter implements MVPageContract.Presenter {

    private MVPageContract.View view;

    public MVPagePresenter(MVPageContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(int offest, int size) {
        HttpManager.getHttpManager().get(URLProviderUtil.getMVareaUrl(),
                new BaseCallBack<String>() {

            @Override
            public void onFailure(int code, Exception e) {
                view.onError(e.getLocalizedMessage());
            }

            @Override
            public void onSuccess(String response) {
                JsonParser jsonParser = new JsonParser();
                try {
                    JsonElement jsonElement = jsonParser.parse(response);
                    List<AreaBean> areaBeanArrayList = new ArrayList<>();
                    JsonArray asJsonArray = jsonElement.getAsJsonArray();
                    Iterator<JsonElement> iterator = asJsonArray.iterator();

                    while (iterator.hasNext()) {
                        JsonElement element = iterator.next();
                        AreaBean areaBean = new Gson().fromJson(element, AreaBean.class);
                        areaBeanArrayList.add(areaBean);
                    }
                    view.setData(areaBeanArrayList);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    view.onError(e.getLocalizedMessage());
                }
            }
        });
    }
}
