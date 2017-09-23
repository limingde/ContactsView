package com.dd.ddapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.ddapplication.R;
import com.dd.ddapplication.base.DoubleListViewAdapter;
import com.dd.ddapplication.beans.Brand;
import com.dd.ddapplication.ui.DoubleListView;

/**
 * Created by Administrator on 2017/9/16.
 */
public class BrandsAdapter extends DoubleListViewAdapter<String,Brand> {
    private Context mContext;
    public BrandsAdapter(Context context){
        mContext = context;
    }
    @Override
    public View getLeftView(int i, Brand data, View view, ViewGroup viewGroup) {
        View  root = LayoutInflater.from(mContext).inflate(R.layout.item_layout,null);
        TextView textView = root.findViewById(R.id.tv_name);
        textView.setText(data.name);
        return textView;
    }

    @Override
    public View getLeftHeaderView(int i, String tag, View view, ViewGroup viewGroup) {
        View  root = LayoutInflater.from(mContext).inflate(R.layout.item_layout,null);
        TextView textView = root.findViewById(R.id.tv_name);
        textView.setText(tag);
        return textView;
    }

    @Override
    public View getRightView(int i, String tag, View view, ViewGroup viewGroup) {
        View  root = LayoutInflater.from(mContext).inflate(R.layout.item_layout,null);
        TextView textView = root.findViewById(R.id.tv_name);
        textView.setText(tag);
        if(isSelectPosition(i)){
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(Color.BLACK);
        }
        return textView;
    }
}
