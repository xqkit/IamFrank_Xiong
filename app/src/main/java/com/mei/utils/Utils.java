package com.mei.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.Toast;

import com.mei.model.AlbumModel;

import java.io.File;
import java.util.ArrayList;

/**
 * Desc:    工具类
 * Date:    2016/8/5 11:44
 * Email:   frank.xiong@zeusis.com
 */
public class Utils {

    private static int screenW;//屏幕宽
    private static int screenH;//屏幕高
    private static float screenDensity;//屏幕密度

    /**
     * 判断SD卡是否存在
     *
     * @return 存在则为真，不存在为false
     */
    public static boolean isSDcardOK() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的路径
     *
     * @return 返回路径的字符串
     */
    public static String getSDcardRoot() {
        if (isSDcardOK()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 弹toast
     *
     * @param context 上下文
     * @param msg     所弹的文字
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取字符串中某个字符串出现的次数
     *
     * @param res        字符串
     * @param findString 匹配的字符串
     * @return 次数
     */
    public static int countMatches(String res, String findString) {

        if (TextUtils.isEmpty(res) || TextUtils.isEmpty(findString)) {
            return 0;
        }

        return (res.length() - res.replace(findString, "").length()) / findString.length();
    }

    /**
     * 判断该文件是否是一个图片
     *
     * @param fileName 文件名
     * @return 结果
     */
    public static boolean isImage(String fileName) {
        //TODO 判断方法过于狭隘，有待修改
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    /**
     * 根据完整路径，获取最后一级路径，并拼上文件数用以显示。
     *
     * @param item 相册
     * @return
     */
    public static String getPathNameToShow(AlbumModel item) {
        String absolutePath = item.getPathName();
        int lastSeparator = absolutePath.lastIndexOf(File.separator);
        return absolutePath.substring(lastSeparator + 1) + "(" + item.getFileCount() + ")";
    }

    /**
     * 获取目录中图片的个数
     *
     * @param folder 文件夹
     * @return 文件个数
     */
    public static int getImageCount(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            if (isImage(file.getName())) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取目录中最新的一张图片的绝对路径
     *
     * @param folder 文件
     * @return 文件的绝对路径
     */
    public static String getFirstImagePath(File folder) {
        File[] files = folder.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            File file = files[i];
            if (isImage(file.getName())) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 初始化屏幕参数
     *
     * @param activity 所传入的activity类
     */
    public static void initScreen(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
        screenDensity = metric.density;
    }

    public static int getScreenW() {
        return screenW;
    }

    public static int getScreenH() {
        return screenH;
    }

    public static float getScreenDensity() {
        return screenDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * getScreenDensity() + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    /**
     * 计算两个手指间的距离
     */
    public static float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        /** 使用勾股定理返回两点之间的距离 */
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算两个手指间的中间点
     */
    public static PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

    /**
     * 获取当前路径下的所有图片
     *
     * @param folderPath 文件夹路径
     * @return 图片路径集合
     */
    public static ArrayList<String> getAllImagePathsByFolder(String folderPath) {
        File folder = new File(folderPath);
        String[] allFileNames = folder.list();
        if (allFileNames == null || allFileNames.length == 0) {
            return null;
        }
        ArrayList<String> imageFilePaths = new ArrayList<>();
        for (int i = allFileNames.length - 1; i >= 0; i--) {
            if (isImage(allFileNames[i])) {
                imageFilePaths.add(folderPath + File.separator + allFileNames[i]);
            }
        }
        return imageFilePaths;
    }
}
