package com.mei.presenter;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.mei.utils.Utils;

/**
 * Desc:    触摸的监听事件
 * Date:    2016/8/5 16:52
 * Email:   frank.xiong@zeusis.com
 */
public class MyTouchListener implements View.OnTouchListener {

    private int mode = 0;// 初始状态
    private ImageView iv1;
    private float startDis;
    private PointF midPoint;
    private static final int MODE_DRAG = 1;//拖拉照片模式
    private static final int MODE_ZOOM = 2;//放大缩小照片模式
    private PointF startPoint = new PointF();//用于记录开始时候的坐标位置
    private Matrix matrix = new Matrix();
    private Matrix currentMatrix = new Matrix();

    public MyTouchListener(ImageView iv1){
        this.iv1 = iv1;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mode = MODE_DRAG;
                currentMatrix.set(iv1.getImageMatrix());
                startPoint.set(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG) {
                    float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                    float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                    // 在没有移动之前的位置上进行移动
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                } else if (mode == MODE_ZOOM) {
                    float endDis = Utils.distance(event);// 结束距离
                    if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        float scale = endDis / startDis;// 得到缩放倍数
                        matrix.set(currentMatrix);
                        matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
            // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                startDis = Utils.distance(event);
                if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                    midPoint = Utils.mid(event);
                    currentMatrix.set(iv1.getImageMatrix());
                }
                break;
        }
        iv1.setImageMatrix(matrix);
        return true;
    }
}
