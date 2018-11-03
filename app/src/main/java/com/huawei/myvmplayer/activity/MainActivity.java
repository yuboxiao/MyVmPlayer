package com.huawei.myvmplayer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.huawei.myvmplayer.R;
import com.huawei.myvmplayer.fragement.homepage.HomeFragment;
import com.huawei.myvmplayer.fragement.mvpage.MvFragment;
import com.huawei.myvmplayer.fragement.vbangpage.VbangFragment;
import com.huawei.myvmplayer.fragement.yuedanpage.YueDanFragment;
import com.huawei.myvmplayer.utils.LogUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    //@Bind(R.id.toolBar)
    //Toolbar mTooBar;
    @Bind(R.id.fl_content)
    FrameLayout fl_content;

    private SparseArray<Fragment> mSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置toolbar代替ActionBar
        //setSupportActionBar(mTooBar);

        mSparseArray = new SparseArray<>();

        mSparseArray.put(R.id.bottom01, new HomeFragment());
        mSparseArray.put(R.id.bottom02, new MvFragment());
        mSparseArray.put(R.id.bottom03, new VbangFragment());
        mSparseArray.put(R.id.bottom04, new YueDanFragment());

        //绑定bottomBar到Activity
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener() {

            //当菜单条目被触发
            @Override
            public void onMenuTabSelected(int menuItemId) {
                LogUtils.e(TAG, "====>onMenuTabSelected: " + menuItemId);
                //切换MenuTab的时候，将videoplayer停止
                JZVideoPlayer.releaseAllVideos();
                setFragment(menuItemId);
            }

            //当菜单条目再次被触发
            @Override
            public void onMenuTabReSelected(int menuItemId) {
                LogUtils.e(TAG, "====>onMenuTabReSelected: " + menuItemId);
            }
        });
    }

    /**
     * 在这里根据MenuItemId改变Fragement内容
     */
    private void setFragment(int menuItemId) {
        FragmentTransaction transaction = null;

        //获取当前显示的Fragment
        Fragment curFragment = mSparseArray.get(menuItemId);
        if (curFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            //判断当前fragment是否被添加到activity当中
            if (curFragment.isAdded()) {
                //判断当前fragment是否
                if (curFragment.isVisible()) {
                    //do nothing
                } else {
                    //当前已经被添加但是还没有显示出来
                    transaction.show(curFragment);
                }
            } else {
                //如果当前没有被加入到activity当中
                transaction.replace(R.id.fl_content, curFragment);
            }
        } else {
            LogUtils.e(TAG, "setFragment: getCurFragment error!!!" + menuItemId);
        }

        //这里已经要记得提交事务
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e(TAG, "=======>onDestroy");
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    protected void onDestroy() {
        LogUtils.e(TAG, "=======>onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
