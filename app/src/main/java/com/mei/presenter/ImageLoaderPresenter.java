package com.mei.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mei.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Desc:    加载图片
 * Date:    2016/8/5 15:30
 * Email:   frank.xiong@zeusis.com
 */
public class ImageLoaderPresenter {

    private int screenW, screenH;
    private LruCache<String, Bitmap> imageCache;//图片缓存
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Handler handler = new Handler();

    public ImageLoaderPresenter(int screenH, int screenW) {

        this.screenH = screenH;
        this.screenW = screenW;
        //获取应用程序的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //设置图片缓存大小为最大可用内存大小的1/8;
        int cacheSize = maxMemory / 8;
        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    public Bitmap loadDrawable(final int smallRate, final String filePath,
                               final ImageCallback callback) {
        //如果缓存过就从缓存中获取数据
        if (!TextUtils.isEmpty(filePath) && null != imageCache.get(filePath)) {
            return imageCache.get(filePath);
        }
        //如果缓存没有就从sd卡中读取
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, opt);
                //获取这个图片的原始高度和宽带
                int picHeight = opt.outHeight;
                int picWidth = opt.outWidth;
                //读取图片失败时直接返回
                if (picHeight == 0 || picWidth == 0) {
                    return;
                }
                //初始压缩比例
                opt.inSampleSize = smallRate;
                //根据屏的大小和图片大小计算出缩放比例
                if (picWidth > picHeight) {
                    if (picWidth > screenW && screenW != 0) {
                        opt.inSampleSize *= picWidth / screenW;
                    }
                } else {
                    if (picHeight > screenH && screenH != 0) {
                        opt.inSampleSize *= picHeight / screenH;
                    }
                }
                //再生成一个有像素的，经过缩放了的bitmap
                opt.inJustDecodeBounds = false;
                final Bitmap bmp = BitmapFactory.decodeFile(filePath, opt);
                //存入map
                imageCache.put(filePath, bmp);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.imageLoaded(bmp);
                    }
                });
            }
        });
        return null;
    }

    /**
     * 异步读取sd卡图片，并按指定的比例进行压缩(最大不超过屏幕像素数)
     */
    public void loadImage(int smallRate, final String filePath, final ImageView imageView) {
        Bitmap bmp = loadDrawable(smallRate, filePath, new ImageCallback() {
            @Override
            public void imageLoaded(Bitmap bmp) {
                if (imageView.getTag().equals(filePath)) {
                    if (bmp != null) {
                        imageView.setImageBitmap(bmp);
                    } else {
                        imageView.setImageResource(R.drawable.ab);
                    }
                }
            }
        });
        if (bmp != null) {
            if (imageView.getTag().equals(filePath)) {
                imageView.setImageBitmap(bmp);
            }
        } else {
            imageView.setImageResource(R.drawable.ab);
        }
    }

    /**
     * 对外界开放的回调接口
     */
    public interface ImageCallback {
        //注意：此方法是用来设置目标对象的资源
        void imageLoaded(Bitmap imageDrawable);
    }
}
