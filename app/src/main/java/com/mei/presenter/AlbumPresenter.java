package com.mei.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.mei.model.AlbumModel;
import com.mei.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Desc:    操作相册
 * Date:    2016/8/5 14:31
 * Email:   frank.xiong@zeusis.com
 */
public class AlbumPresenter {
    private static Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
    private static String key_DATA = MediaStore.Images.Media.DATA;

    /**
     * 使用ContentProvider读取SD卡所有图片
     *
     * @param context 上下文
     * @return 图片的集合
     */
    public static ArrayList<AlbumModel> getImagePathsByContentProvider(Context context) {

        ContentResolver mContentResolver = context.getContentResolver();
        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
        ArrayList<AlbumModel> list = null;
        if (cursor != null) {
            if (cursor.moveToLast()) {
                //路径缓存，防止多次扫描同一目录
                HashSet<String> cachePath = new HashSet<String>();
                list = new ArrayList<>();
                while (true) {
                    // 获取图片的路径
                    String imagePath = cursor.getString(0);
                    File parentFile = new File(imagePath).getParentFile();
                    String parentPath = parentFile.getAbsolutePath();
                    //不扫描重复路径
                    if (!cachePath.contains(parentPath)) {
                        list.add(new AlbumModel(parentPath, Utils.getImageCount(parentFile),
                                Utils.getFirstImagePath(parentFile)));
                        cachePath.add(parentPath);
                    }
                    if (!cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }
        return list;
    }
}
