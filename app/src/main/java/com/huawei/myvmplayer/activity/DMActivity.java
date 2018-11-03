package com.huawei.myvmplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.view.HVideoPlayer;

import cn.jzvd.JZUserAction;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by x00378851 on 2018/8/27.
 */

public class DMActivity extends AppCompatActivity {
    private HVideoPlayer mVideoPlayerStandard;
    private int mRoomId;
    private EditText mMsgEditText;
    private String mBeginTime;

    //全屏下播放器对象
    public HVideoPlayer mFullScreenPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmdetail);
        initView();
    }

    private void initView() {
        mVideoPlayerStandard = (HVideoPlayer) findViewById(R.id.custom_videoplayer_standard);
        //mMsgEditText = (EditText) findViewById(R.id.message);

        //Button sendBtn = (Button) findViewById(R.id.sendMsg);

     /*   sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgContent = mMsgEditText.getText().toString();
                System.out.println("====>msgContent:" + msgContent);
                sendMessage(msgContent);
            }
        });*/

        mVideoPlayerStandard.setOnSendMsgListener(new MyOnSendMsgListener());

        mVideoPlayerStandard.setOnPayListener(new HVideoPlayer.OnPayListener() {
            @Override
            public void showPay() {
                Toast.makeText(DMActivity.this,"赞赏",Toast.LENGTH_SHORT).show();
            }
        });
        mVideoPlayerStandard.setOnFullScreenListener(new HVideoPlayer.OnFullScreenListener() {
            @Override
            public void onFullScreen(HVideoPlayer hVideoPlayer) {
                mFullScreenPlayer = hVideoPlayer;

            }
        });

        mVideoPlayerStandard.setJzUserAction(new JZUserAction() {
            @Override
            public void onEvent(int type, Object url, int screen, Object... objects) {
                switch (type){
                    case JZVideoPlayer.CURRENT_STATE_PLAYING:
                        System.out.println("=====>CURRENT_STATE_PLAYING");
                        break;
                    case JZVideoPlayer.CURRENT_STATE_PAUSE:
                        System.out.println("=====>CURRENT_STATE_PAUSE");
                        break;

                }
            }
        });
        //rtmp://203.207.99.19:1935/live/CCTV5
        //http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8
        //http://ivi.bupt.edu.cn/hls/cctv1.m3u8
        mVideoPlayerStandard.setUp("http://116.77.73.77/otv/tw/live/channel88/800.m3u8"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "CCTV1");
        mVideoPlayerStandard.startButton.performClick();
    }

    //add by xiaoyubo
    private class MyOnSendMsgListener implements HVideoPlayer.OnSendMsgListener{

        @Override
        public void sendMsg(String msg) {
            System.out.println("====>msg:" + msg);
            sendMessage(msg);
        }
    }

    private void sendMessage(String msgContent) {
        //判断是否全屏，全屏则显示弹幕
        if (mFullScreenPlayer != null) {
            mFullScreenPlayer.addDanmaku(msgContent, true);
        }
    }


    @Override
    public void onBackPressed() {
        System.out.println("=====>onBackPressed");
        if (JZVideoPlayer.backPress()) {
            //隐藏弹幕
            if (mFullScreenPlayer != null) {
                mFullScreenPlayer.hideDanmu();
                mFullScreenPlayer=null;
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        System.out.println("=====>onDestroy");
        super.onDestroy();
        mVideoPlayerStandard.danmaDes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("=====>onPause");
        JZVideoPlayer.releaseAllVideos();
        if (mFullScreenPlayer != null) {
            mFullScreenPlayer.hideDanmu();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("=====>onResume");
        mVideoPlayerStandard.danmaResume();
    }

}
