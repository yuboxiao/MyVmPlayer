package com.huawei.myvmplayer.utils;

import android.util.Log;

/**
 * Created by Ding on 2016/12/17.
 */
public class LogUtils {

    private static final boolean ENABLE = true;
    public static final String TAG_PREFIX = "MyVmPlayer";

    public static void e(String tag, String msg) {
        if (ENABLE)
            Log.e(TAG_PREFIX + tag, msg);
    }

    public static void e(Class cls, String msg) {
        if (ENABLE)
            Log.e(TAG_PREFIX + cls.getSimpleName(), msg);
    }
}
