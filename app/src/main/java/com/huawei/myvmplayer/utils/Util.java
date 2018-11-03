package com.huawei.myvmplayer.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x00378851 on 2018/8/22.
 */

public class Util {

    public static String getSystemversion(){
        return android.os.Build.VERSION.RELEASE;
    }
    public static String getPhoneModel(){
        return android.os.Build.MODEL;
    }


    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Glide 加载图片
            Glide.with(context).load(path).into(imageView);
        }
    }

    /**
     * 模拟一些弹幕内容
     * @return
     */
    public static List<String> danmuku() {
        List<String> danmuExample = new ArrayList<>();
        danmuExample.add("瞬间爆炸~");
        danmuExample.add("图样图森破");
        danmuExample.add("正义的伙伴");
        danmuExample.add("你已经来不及走了");
        danmuExample.add("植物缺少氮磷钾，必须要用金坷垃");
        danmuExample.add("王的奥义");
        danmuExample.add("旋转跳跃我闭着眼");
        danmuExample.add("仰望星空派");
        danmuExample.add("要成为我的master吗~");
        danmuExample.add("朱军画质");

        return danmuExample;
    }
}
