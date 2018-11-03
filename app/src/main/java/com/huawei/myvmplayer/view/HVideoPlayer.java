package com.huawei.myvmplayer.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.utils.Util;

import java.util.List;
import java.util.Random;

import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayerStandard;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.DanmakuFactory;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class HVideoPlayer extends JZVideoPlayerStandard {

    private boolean showDanmaku;

    //弹幕View
    private static DanmakuView danmakuView;

    private static DanmakuContext danmakuContext;

    private static BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    private static Context mContext;
    private EditText mEditText;
    private ImageView mSendImage;
    private ProgressBar mProgress;
    private OnSendMsgListener mSendListener;
    private ImageView mRewardBtn;
    private OnPayListener mShowPay;
    private OnFullScreenListener mOnFullScreenListener;


    public HVideoPlayer(Context context) {
        super(context);
        System.out.println("HVideoPlayer context" + context);
    }

    public HVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("HVideoPlayer context" + context);
    }

    //为啥被调用两次,看下源码
    @Override
    public void init(final Context context) {
        super.init(context);
        System.out.println("====>context"+ context);
        initView(context);
    }

    private void initView(Context context) {
        System.out.println("======>initView");
        this.mContext = context;
        mEditText = findViewById(R.id.msg_edittext);
        mSendImage = findViewById(R.id.send_img);
        mRewardBtn = findViewById(R.id.reward_img);

        mSendImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditText.getText().toString();
                System.out.println("111====>content " + content);
               /* System.out.println("=====>" + objects[0]);
                System.out.println("=====>" + objects[1]);
                System.out.println("=====>" + objects[2]);
                System.out.println("=====>" + objects[3]);
                System.out.println("=====>" + objects[4]);
                System.out.println("=====>" + objects[5]);*/

           /*     mSendListener = (OnSendMsgListener) objects[1];
                mShowPay = (OnPayListener) objects[2];
                mOnFullScreenListener = (OnFullScreenListener) objects[3];
                HVideoPlayer hVideoPlayer = (HVideoPlayer) objects[4];
                danmakuContext = (DanmakuContext) objects[5];
                mOnFullScreenListener.onFullScreen(hVideoPlayer);*/
                //此处的mOnFullScreenListener怎么传递给DMActivity????
                //此处的mSendListener为空
                //mSendListener.sendMsg(content);
                addDanmaku(content, true);
                mEditText.setText("");
            }
        });

    /*    mRewardBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowPay.showPay();
            }
        });*/
    }


    @Override
    public void startDismissControlViewTimer() {
        //重写父类方法，防止自动隐藏播放器工具栏。如需要自动隐藏请删除此方法或调用super.startDismissControlViewTimer();
    }


    /**
     * 初始化弹幕
     */
    private void initDanmu() {
        ViewGroup vp = (ViewGroup) (JZUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);

        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, sp2px(48), 0, sp2px(48));
        danmakuView = new DanmakuView(mContext);
        vp.addView(danmakuView, lp);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                //generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);

    }


    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
        //重写onError 视频播放错误的时候隐藏弹幕
        if (what != 38 && what != -38) {
            hideDanmu();
        }
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    public void addDanmaku(String content, boolean withBorder) {
        DanmakuFactory danmakuFactory = null;
        BaseDanmaku danmaku = null;
        if (danmakuContext != null) {
            danmakuFactory = danmakuContext.mDanmakuFactory;
            danmaku = danmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        } else {

            System.out.println("======>danmakuContext is null");
        }

        if (danmaku == null || danmakuView == null) {
            return;
        }
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(1000);
                    int size = Util.danmuku().size();
                    List<String> danmuContent = Util.danmuku();
                    int idx = new Random().nextInt(size - 1);
                    if (idx < size) {
                        String content = danmuContent.get(idx);
                        addDanmaku(content, false);
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public void setOnSendMsgListener(OnSendMsgListener lis) {
        this.mSendListener = lis;
        System.out.println("=====>this.mSendListener" + this.mSendListener);
    }

    public void setOnPayListener(OnPayListener lis) {
        this.mShowPay = lis;
    }

    public void danmaResume() {
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    public void danmaDes() {
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    //全屏幕后病没有再次调用啊
    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        //强制全屏
        FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        //添加全屏下的参数
        if (objects.length != 1) {
            System.out.println("====>走不到这里吧111");
            mSendListener = (OnSendMsgListener) objects[1];
            mShowPay = (OnPayListener) objects[2];
            mOnFullScreenListener = (OnFullScreenListener) objects[3];
        }
        System.out.println("====>currentScreen" + currentScreen);
        //super.setUp(url, screen, objects[0], mSendListener, mShowPay, mOnFullScreenListener, this, danmakuContext);
        System.out.println("====>currentScreen" + currentScreen);
        //全屏下展示弹幕
        /*if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            System.out.println("====>走不到这里吧");
            initDanmu();
            mEditText.setVisibility(View.VISIBLE);
            mSendImage.setVisibility(View.VISIBLE);
            mOnFullScreenListener.onFullScreen(this);
        } else if (currentScreen == SCREEN_WINDOW_NORMAL
                || currentScreen == SCREEN_WINDOW_LIST) {
            mEditText.setVisibility(View.INVISIBLE);
            mSendImage.setVisibility(View.INVISIBLE);
        }*/
        initDanmu();
        mEditText.setVisibility(VISIBLE);
        mSendImage.setVisibility(VISIBLE);

        //点击返回按钮隐藏弹幕
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDanmu();
                backPress();
            }
        });

        //重写全屏按钮点击事件
        fullscreenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentState == CURRENT_STATE_AUTO_COMPLETE) return;
                if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                    hideDanmu();
                    backPress();
                } else {
                    //全屏
                    startWindowFullscreen();
                }
            }
        });
    }


    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
       /* System.out.println("=====>" + objects[0]);
        System.out.println("=====>" + objects[1]);
        System.out.println("=====>" + objects[2]);
        System.out.println("=====>" + objects[3]);
        System.out.println("=====>" + objects[4]);*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.custom_video_player;
    }


    /**
     * 判断当前是否是全屏
     *
     * @return
     */
    public boolean isFullScreen() {
        return currentScreen == SCREEN_WINDOW_FULLSCREEN ? true : false;
    }

    /**
     * 隐藏弹幕
     */
    public void hideDanmu() {
        ViewGroup vp = (ViewGroup) (JZUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        if (danmakuView != null) {
            danmakuView.release();
            showDanmaku = false;
            vp.removeView(danmakuView);
            danmakuView = null;
        }
    }


    @Override
    public void onVideoSizeChanged() {
//        super.onVideoSizeChanged();

    }

    //全屏事件监听，返回全屏播放器对象
    public void setOnFullScreenListener(OnFullScreenListener onFullScreenListener) {
        this.mOnFullScreenListener = onFullScreenListener;
    }

    /**
     * 发送消息
     */
    public interface OnSendMsgListener {
        void sendMsg(String msg);
    }

    /**
     * 支付回调
     */
    public interface OnPayListener {
        void showPay();
    }

    /**
     * 全屏回调
     */
    public interface OnFullScreenListener {
        void onFullScreen(HVideoPlayer hVideoPlayer);
    }

    /**
     * 当状态变成为开始播放的时候
     */
    @Override
    public void onStatePlaying() {
        System.out.println("======>onStatePlaying");
        generateSomeDanmaku();
        super.onStatePlaying();
    }

    @Override
    public void startVideo() {
        System.out.println("======>startVideo");
        super.startVideo();

    }


}
