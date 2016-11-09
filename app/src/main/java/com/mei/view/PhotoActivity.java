package com.mei.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.mei.presenter.MyTouchListener;
import com.mei.R;

/**
 * Desc:    单张照片展示
 * Date:    2016/8/5 16:22
 * Email:   frank.xiong@zeusis.com
 */
public class PhotoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);
        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        ImageView imageView = (ImageView) findViewById(R.id.iv1);
        imageView.setImageURI(Uri.parse(filePath));
        imageView.setOnTouchListener(new MyTouchListener(imageView));
    }
}
