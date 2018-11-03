package com.huawei.myvmplayer.utils;

import android.util.Log;

public class URLProviderUtil {

    /**
     * 此开关可控制是否要Mocky获取数据源
     */
    private static final boolean IS_DATA_FROM_MOCKY = false;

    public static String getMainPageUrl(int offset, int size) {

        String url;

        if (IS_DATA_FROM_MOCKY) {
            url = "http://www.mocky.io/v2/5b7d52883300005f004a025e";
            return url;
        }

        url = "http://mapi.yinyuetai.com/suggestions/front_page.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&offset=" + offset
                + "&size=" + size
                + "&v=4&rn=640*540";
        Log.i("Main_url", url);
        return url;
    }

    public static String getMVareaUrl() {
        String url = "http://mapi.yinyuetai.com/video/get_mv_areas.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}";
        Log.i("MV_Area", url);
        return url;
    }

    public static String getMVListUrl(String area, int offset, int size) {
        String url = "http://mapi.yinyuetai.com/video/list.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&area=" + area
                + "&offset=" + offset
                + "&size=" + size;
        Log.i("MvList_url", url);
        return url;
    }

    public static String getMainPageYueDanUrl(int offset, int size) {
        String url = "http://mapi.yinyuetai.com/playlist/list.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&offset=" + offset
                + "&size=" + size;
        Log.i("MainPage_YueDan_url", url);
        return url;
    }

    /**
     * <p>获取音乐节目列表</p>
     *
     * @param artistIds
     * @param offset
     * @param size
     * @return
     */
    public static String getYinYueProgramList(String artistIds, int offset, int size) {
        String url = "http://mapi.yinyuetai.com/playlist/show.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&offset=" + offset
                + "&size=" + size
                + "&artistIds=" + artistIds;
        Log.i("YinYue_ProgramList_url", url);
        return url;
    }

    /**
     * 获取V榜地址
     *
     * @return
     */
    public static String getVChartAreasUrl() {
        String url = "http://mapi.yinyuetai.com/vchart/get_vchart_areas.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}";
        Log.i("VChart_Areas_url", url);
        return url;
    }

    /**
     * 获取V榜的周期
     *
     * @return
     */
    public static String getVChartPeriodUrl(String area) {
        String url = "http://mapi.yinyuetai.com/vchart/period.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&area=" + area;
        return url;
    }

    /**
     * 获取V榜列表
     *
     * @param area
     * @param dateCode
     * @return
     */
    public static String getVChartListUrl(String area, int dateCode) {
        String url = "http://mapi.yinyuetai.com/vchart/show.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&area=" + area
                + "&datecode=" + dateCode;
        return url;
    }

    /**
     * 获取相关MV
     *
     * @param id
     * @return
     */
    public static String getRelativeVideoListUrl(int id) {
        String url = "http://mapi.yinyuetai.com/video/show.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&relatedVideos=true"
                + "&id=" + id;
        Log.i("Relative_VideoList_url", url);
        return url;
    }

    /**
     * 通过id 获取某人的悦单
     *
     * @param id
     * @return
     */
    public static String getPeopleYueDanList(int id) {
        String url = "http://mapi.yinyuetai.com/playlist/show.json?deviceinfo="
                + "{\"aid\":\"10201036\",\"os\":\"Android\","
                + "\"ov\":" + "\"" + Util.getSystemversion() + "\"" + ","
                + "\"rn\":\"480*800\","
                + "\"dn\":" + "\"" + Util.getPhoneModel() + "\"" + ","
                + "\"cr\":\"46000\","
                + "\"as\":"
                + "\"WIFI\","
                + "\"uid\":"
                + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
                + "\"clid\":110025000}"
                + "&id=" + id;
        return url;
    }


}
