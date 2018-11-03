package com.huawei.myvmplayer.toupin.listener;


import com.huawei.myvmplayer.toupin.manager.ClingManager;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

/**
 * Created by x00378851 on 2018/8/28.
 */

public class BrowseRegistryListener extends DefaultRegistryListener {

    private DeviceListChangeListener mOnDeviceListChangedListener;
    private AndroidUpnpService mUpnpService;

    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        System.out.println("======>remoteDeviceDiscoveryStarted");
        deviceAdded(device);
    }

    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, final RemoteDevice device, final Exception ex) {
        System.out.println("======>remoteDeviceDiscoveryFailed");
        deviceRemoved(device);
    }

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        //这里可以获取路由器的型号
        //Technologies Co., Ltd. Router WS851
        System.out.println("======>remoteDeviceAdded" + device.getDisplayString());
        deviceAdded(device);
    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        System.out.println("======>remoteDeviceRemoved");
        deviceRemoved(device);
    }

    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        System.out.println("======>localDeviceAdded");
        deviceAdded(device);
    }

    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        System.out.println("======>localDeviceRemoved");
        deviceRemoved(device);
    }

    public void deviceAdded(final Device device) {
        System.out.println("=====>deviceAdded " + device.getDetails().getFriendlyName() + device.getDetails().getSerialNumber());
        if (!device.getType().equals(ClingManager.DMR_DEVICE_TYPE)) {
            System.out.println("=====>deviceAdded called, but not match");
            return;
        }

        if (mOnDeviceListChangedListener != null) {
            //先要去掉重复的
            for(Device deviceTmp: mUpnpService.getRegistry().getDevices()) {
                if (deviceTmp.equals(device)) {
                    //回调设备添加接口
                    mOnDeviceListChangedListener.onDeviceAdd(device);
                } else {
                    System.out.println("=====>add same device" + device.getDetails().getFriendlyName());
                }
            }
        }
    }

    public void deviceRemoved(final Device device) {
        System.out.println("=====>deviceRemoved");
    }

    public void setOnDeviceListChangeListener(DeviceListChangeListener onDeviceListChangedListener) {
        System.out.println("=====>setOnDeviceListChangedListener");
        mOnDeviceListChangedListener = onDeviceListChangedListener;
    }


    public void setUpnpService(AndroidUpnpService upnpService) {
        this.mUpnpService = upnpService;
    }
}
