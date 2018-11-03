package com.huawei.myvmplayer.toupin.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.toupin.apapter.DeviceListAdapter;
import com.huawei.myvmplayer.toupin.bean.DLANPlayState;
import com.huawei.myvmplayer.toupin.config.Config;
import com.huawei.myvmplayer.toupin.listener.BrowseRegistryListener;
import com.huawei.myvmplayer.toupin.listener.DeviceListChangeListener;
import com.huawei.myvmplayer.toupin.manager.ClingManager;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.avtransport.callback.Play;
import org.fourthline.cling.support.avtransport.callback.SetAVTransportURI;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.VideoItem;
import org.seamless.util.MimeType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BrowserActivity extends AppCompatActivity implements View.OnClickListener {

    // 监听设备发现，当一个新设备出现在网络时，会回调它
    private BrowseRegistryListener registryListener = new BrowseRegistryListener();

    private AndroidUpnpService upnpService;

    private ListView mListView;
    private DeviceListAdapter mAdapter;
    private TextView mTVSelected;
    private Button mBtnPlay;

    private Device mSelectedDevice;


    private static final String DIDL_LITE_FOOTER = "</DIDL-Lite>";
    private static final String DIDL_LITE_HEADER = "<?xml version=\"1.0\"?>" + "<DIDL-Lite " + "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" " +
            "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " + "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" " +
            "xmlns:dlna=\"urn:schemas-dlna-org:metadata-1-0/\">";


    private ServiceConnection serviceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            System.out.println("======>onServiceConnected");
            upnpService = (AndroidUpnpService) service;

            // 添加监听
            upnpService.getRegistry().addListener(registryListener);

            //设置upnpService给监听器，去遍历设备列表免得添加重复了
            registryListener.setUpnpService(upnpService);

            // 搜索所有的设备
            upnpService.getControlPoint().search();
        }

        public void onServiceDisconnected(ComponentName className) {
            upnpService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        initView();
        bindService();
        initListener();
    }

    private void initListener() {
        mBtnPlay.setOnClickListener(this);

        registryListener.setOnDeviceListChangeListener(new DeviceListChangeListener() {

            @Override
            public void onDeviceAdd(final Device device) {
                System.out.println("=====>registryListener onDeviceAdded");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.add(device);
                    }
                });
            }

            @Override
            public void onDeviceRemove(final Device device) {
                System.out.println("=====>registryListener onDeviceRemove");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.remove(device);
                    }
                });
            }
        });

        //设置每一项被点击后的事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device item = mAdapter.getItem(position);
                if (item != null) {
                    String selectedDeviceName = String.format(getString(R.string.selectedText), item.getDetails().getFriendlyName());
                    //设置一个被选中的设备进行保存
                    mSelectedDevice = item;
                    mTVSelected.setText(selectedDeviceName);
                }
            }
        });
    }

    private void bindService() {
        bindService(new Intent(this, AndroidUpnpServiceImpl.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        mTVSelected = (TextView) findViewById(R.id.tv_selected);
        mListView = (ListView) findViewById(R.id.lv_devices);
        mBtnPlay = (Button) findViewById(R.id.bt_play);

        mAdapter = new DeviceListAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (upnpService != null) {
            upnpService.getRegistry().removeListener(registryListener);
        }
        unbindService(serviceConnection);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                System.out.println("====>click play");
                play();
                break;
        }
    }

    private void play() {

        if (mSelectedDevice == null) {
            System.out.println("=====>mSelectedDevice is null");
            return;
        }

        //1.获取一个AV_TRANSPORT_SERVICE,通过选中设备的getService方法
        final Service service = mSelectedDevice.findService(ClingManager.AV_TRANSPORT_SERVICE);
        if (service == null) {
            System.out.println("=====>service is null");
            return;
        }
        //2.获取一个控制点
        final ControlPoint controlPoint = upnpService.getControlPoint();
        if (controlPoint == null) {
            System.out.println("=====>controlPoint is null");
            return;
        }
        setAVTransportURI(Config.TEST_URL);
        controlPoint.execute(new Play(service) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                System.out.println("====>execute success");
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                System.out.println("====>execute failure");
            }
        });
    }

    private String createItemMetadata(DIDLObject item) {
        StringBuilder metadata = new StringBuilder();
        metadata.append(DIDL_LITE_HEADER);

        metadata.append(String.format("<item id=\"%s\" parentID=\"%s\" restricted=\"%s\">", item.getId(), item.getParentID(), item.isRestricted() ? "1" : "0"));

        metadata.append(String.format("<dc:title>%s</dc:title>", item.getTitle()));
        String creator = item.getCreator();
        if (creator != null) {
            creator = creator.replaceAll("<", "_");
            creator = creator.replaceAll(">", "_");
        }
        metadata.append(String.format("<upnp:artist>%s</upnp:artist>", creator));

        metadata.append(String.format("<upnp:class>%s</upnp:class>", item.getClazz().getValue()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        metadata.append(String.format("<dc:date>%s</dc:date>", time));

        // metadata.append(String.format("<upnp:album>%s</upnp:album>",
        // item.get);

        // <res protocolInfo="http-get:*:audio/mpeg:*"
        // resolution="640x478">http://192.168.1.104:8088/Music/07.我醒著做夢.mp3</res>

        Res res = item.getFirstResource();
        if (res != null) {
            // protocol info
            String protocolinfo = "";
            ProtocolInfo pi = res.getProtocolInfo();
            if (pi != null) {
                protocolinfo = String.format("protocolInfo=\"%s:%s:%s:%s\"", pi.getProtocol(), pi.getNetwork(), pi.getContentFormatMimeType(), pi
                        .getAdditionalInfo());
            }
            System.out.println("=====>protocolinfo: " + protocolinfo);

            // resolution, extra info, not adding yet
            String resolution = "";
            if (res.getResolution() != null && res.getResolution().length() > 0) {
                resolution = String.format("resolution=\"%s\"", res.getResolution());
            }

            // duration
            String duration = "";
            if (res.getDuration() != null && res.getDuration().length() > 0) {
                duration = String.format("duration=\"%s\"", res.getDuration());
            }

            // res begin
            //            metadata.append(String.format("<res %s>", protocolinfo)); // no resolution & duration yet
            metadata.append(String.format("<res %s %s %s>", protocolinfo, resolution, duration));

            // url
            String url = res.getValue();
            metadata.append(url);

            // res end
            metadata.append("</res>");
        }
        metadata.append("</item>");

        metadata.append(DIDL_LITE_FOOTER);

        return metadata.toString();
    }

    private String pushMediaToRender(String url, String id, String name, String duration) {
        long size = 0;
        long bitrate = 0;
        Res res = new Res(new MimeType(ProtocolInfo.WILDCARD, ProtocolInfo.WILDCARD), size, url);

        String creator = "unknow";
        String resolution = "unknow";
        VideoItem videoItem = new VideoItem(id, "0", name, creator, res);

        String metadata = createItemMetadata(videoItem);
        System.out.println("====>metadata: " + metadata);
        return metadata;
    }
    /**
     * 设置片源，用于首次播放
     *
     * @param url   片源地址
     */
    private void setAVTransportURI(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        String metadata = pushMediaToRender(url, "id", "name", "0");

        //1.获取一个AV_TRANSPORT_SERVICE,通过选中设备的getService方法
        final Service service = mSelectedDevice.findService(ClingManager.AV_TRANSPORT_SERVICE);
        if (service == null) {
            System.out.println("=====>service is null");
            return;
        }
        //2.获取一个控制点
        final ControlPoint controlPoint = upnpService.getControlPoint();
        if (controlPoint == null) {
            System.out.println("=====>controlPoint is null");
            return;
        }

        controlPoint.execute(new SetAVTransportURI(service, url, metadata) {
            @Override
            public void success(ActionInvocation invocation) {
                System.out.println("=====>SetAVTransportURI success");
                super.success(invocation);
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                System.out.println("=====>SetAVTransportURI failure");
            }
        });
    }
}
