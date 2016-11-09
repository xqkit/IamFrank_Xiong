package com.mei.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mei.R;
import com.mei.model.AlbumModel;
import com.mei.presenter.ImageLoaderPresenter;
import com.mei.utils.Utils;

import java.util.ArrayList;


/**
 * Desc:
 * Date:    2016/8/5 14:05
 * Email:   frank.xiong@zeusis.com
 */
public class AlbumAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AlbumModel> list;
    private ImageLoaderPresenter mLoader;

    public AlbumAdapter(Context context, ArrayList<AlbumModel> list) {
        this.context = context;
        this.list = list;
        mLoader = new ImageLoaderPresenter(Utils.getScreenW(), Utils.getScreenH());
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.photo_album_item, null);
            holder = new ViewHolder();
            holder.firstImageIV = (ImageView) convertView.findViewById(R.id.select_img_gridView_img);
            holder.pathNameTV = (TextView) convertView.findViewById(R.id.select_img_gridView_path);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String filePath = list.get(position).getFirstImagePath();
        holder.firstImageIV.setTag(filePath);
        holder.pathNameTV.setText(Utils.getPathNameToShow(list.get(position)));
        mLoader.loadImage(4, filePath, holder.firstImageIV);
        return convertView;
    }

    private class ViewHolder {
        ImageView firstImageIV;
        TextView pathNameTV;
    }
}
