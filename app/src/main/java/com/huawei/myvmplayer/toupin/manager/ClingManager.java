package com.huawei.myvmplayer.toupin.manager;

import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDAServiceType;

/**
 * Created by x00378851 on 2018/8/28.
 * 代理类,所有对服务的操作都由此类代理完成
 */

public class ClingManager {

    /**
     * 设备类型为媒体渲染器
     */
    public static DeviceType DMR_DEVICE_TYPE = new UDADeviceType("MediaRenderer");

    /**
     * 获取设备的服务类型为控制类型
     */
    public static final ServiceType AV_TRANSPORT_SERVICE = new UDAServiceType("AVTransport");
}
