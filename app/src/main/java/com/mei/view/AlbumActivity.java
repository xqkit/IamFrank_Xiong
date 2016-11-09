package com.mei.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mei.R;
import com.mei.model.AlbumModel;
import com.mei.presenter.AlbumPresenter;
import com.mei.utils.Utils;

import java.util.ArrayList;

/**
 * Desc:    相册界面
 * Date:    2016/8/5 11:56
 * Email:   frank.xiong@zeusis.com
 */
public class AlbumActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_album);

        GridView listView = (GridView) findViewById(R.id.select_img_listView);
        //判断sd卡是否存在
        if (!Utils.isSDcardOK()) {
            Utils.showToast(this, "SD卡不可用。");
            return;
        }
        //使用contentProvider获取遍历所有图片
        final ArrayList<AlbumModel> list = new ArrayList<>();
        AlbumPresenter albumPresenter = new AlbumPresenter();
        list.addAll(albumPresenter.getImagePathsByContentProvider(this));
        AlbumAdapter adapter = new AlbumAdapter(this, list);
        listView.setAdapter(adapter);
        //给listview设置条目点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AlbumActivity.this, WallActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("folderPath", list.get(position).getPathName());
                startActivity(intent);
            }
        });

    }
}
