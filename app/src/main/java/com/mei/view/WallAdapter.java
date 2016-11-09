package com.mei.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mei.R;
import com.mei.presenter.ImageLoaderPresenter;
import com.mei.utils.Utils;

import java.util.ArrayList;

/**
 * Desc:    照片墙适配器
 * Date:    2016/8/5 17:06
 * Email:   frank.xiong@zeusis.com
 */
public class WallAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> imagePathList = null;
    private ImageLoaderPresenter mLoader;

    public WallAdapter(Context context, ArrayList<String> imagePathList) {
        this.context = context;
        this.imagePathList = imagePathList;
        mLoader = new ImageLoaderPresenter(Utils.getScreenW(), Utils.getScreenH());

    }

    @Override
    public int getCount() {
        return imagePathList == null ? 0 : imagePathList.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String filePath = (String) getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.photo_wall_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.photo_wall_item_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setTag(filePath);
        mLoader.loadImage(4, filePath, holder.imageView);
        return convertView;
    }
    private class ViewHolder {
        ImageView imageView;
    }
}
