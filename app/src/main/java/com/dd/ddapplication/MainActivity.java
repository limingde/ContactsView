package com.dd.ddapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.dd.ddapplication.beans.BaseBean;
import com.dd.ddapplication.beans.Brand;
import com.dd.ddapplication.ui.DoubleListView;
import com.dd.ddapplication.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private DoubleListView mDoubleListView;
    private BrandsAdapter mBrandsAdapter;
    private void initView(){
        mDoubleListView = (DoubleListView) findViewById(R.id.lv_content);
        mDoubleListView.setRightViewWidth(Util.dip2px(80));
        mDoubleListView.setleftViewLeftOfRightView();
        mBrandsAdapter =  new BrandsAdapter(this);
        mDoubleListView.addHeaderView(Util.inflate(this,R.layout.item_layout_header));
        mDoubleListView.addFooterView(Util.inflate(this,R.layout.item_layout_footer));
        mDoubleListView.setAdapter(mBrandsAdapter);
        mBrandsAdapter.setData(getData());

    }

    public List<BaseBean<String,Brand>> getData(){
        List<BaseBean<String,Brand>> datas = new ArrayList<>();
        for(int i= 0;i < 30;i ++){
            BaseBean<String,Brand> item = new BaseBean<>();
            item.tag = "右边: " + i;
            item.data = new ArrayList<>();
            for(int j= 0;j < 1000;j ++){
               Brand brand = new Brand();
                brand.img = "";
                brand.name = "这里是左边的view，我这里需要显示很长的内容 item: " + j ;
                item.data.add(brand);
            }
            datas.add(item);
        }
        return datas;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
