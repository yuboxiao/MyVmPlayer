package com.huawei.myvmplayer.toupin.listener;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by x00378851 on 2018/8/28.
 * 添加两个回调函数,用于当设备被发现和被删除的时候，需要执行的动作
 */

public interface DeviceListChangeListener {

    /**
     * 当设备被发现的时候，回调此接口
     * @param device
     */
    void onDeviceAdd(Device device);

    /**
     * 当设备被删除的时候，回调此接口
     * @param device
     */
    void onDeviceRemove(Device device);

}
