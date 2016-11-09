package com.mei.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mei.R;
import com.mei.utils.Utils;

import java.util.ArrayList;

/**
 * Desc:    照片墙展示类
 * Date:    2016/8/5 16:57
 * Email:   frank.xiong@zeusis.com
 */
public class WallActivity extends Activity {

    private GridView mPhotoWall;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_wall);
        mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);

        mList = Utils.getAllImagePathsByFolder(getIntent().getStringExtra("folderPath"));
        WallAdapter adapter = new WallAdapter(this, mList);
        mPhotoWall.setAdapter(adapter);
        mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(WallActivity.this, PhotoActivity.class);
                it.putExtra("filePath", mList.get(position));
                startActivity(it);
            }
        });
    }

}
