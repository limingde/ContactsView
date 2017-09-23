package com.dd.ddapplication.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.dd.ddapplication.R;
import com.dd.ddapplication.base.DoubleListViewAdapter;
/**
 * Created by Administrator on 2017/9/16.
 */
public class DoubleListView extends LinearLayout implements AdapterView.OnItemClickListener {
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;
    private DoubleListViewAdapter mDoubleListViewAdapter;

    private DataOrbsever mDataOrbsever = new DataOrbsever(){

        @Override
        public void onDataChanged() {
            if(mLeftAdapter != null){
                mLeftAdapter.notifyDataSetChanged();
            }
            if(mRightAdapter != null){
                mRightAdapter.notifyDataSetChanged();
            }
        }
    };
    public void setAdapter(DoubleListViewAdapter adapter) {
        mDoubleListViewAdapter = adapter;
        mLeftAdapter = new LeftAdapter(adapter);
        mRightAdapter = new RightAdapter(adapter);
        regesterOrbsever(adapter);
        mListViewLeft.setAdapter(mLeftAdapter);
        mListViewRight.setAdapter(mRightAdapter);
    }

    private void regesterOrbsever(DoubleListViewAdapter adapter){
        if(adapter != null){
            adapter.clearOrbsever();
            adapter.regesterOrbsever(mDataOrbsever);
        }
    }
    public DoubleListView(Context context) {
        this(context, null);
    }

    public void addHeaderView(View view) {
        mListViewLeft.addHeaderView(view);
    }

    public void addFooterView(View view) {
        mListViewLeft.addFooterView(view);
    }

    public void setLeftDividerHeight(int height){
        mListViewLeft.setDividerHeight(height);
    }
    public void setRightDividerHeight(int height){
        mListViewRight.setDividerHeight(height);
    }

    public void setLeftDivider(Drawable divider){
        mListViewLeft.setDivider(divider);
    }

    public void setRightDivider(Drawable divider){
        mListViewRight.setDivider(divider);
    }

    public void setRightCoverToLeft(){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mListViewLeft.setLayoutParams(lp);
    }

    public void setleftViewLeftOfRightView(){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mListViewLeft.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        lp.addRule(RelativeLayout.LEFT_OF,R.id.lv_right);
        mListViewLeft.setLayoutParams(lp);
    }

    public void setRightViewWidth(int width){
        ViewGroup.LayoutParams lp = mListViewRight.getLayoutParams();
        if(lp == null){
            lp = new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.MATCH_PARENT);
        }
        lp.width = width;
        mListViewRight.setLayoutParams(lp);
    }
    public DoubleListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private boolean mIsClickCategory ;
    private int last_category = 0;

    private ListView mListViewLeft;
    private ListView mListViewRight;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.double_list_view_layout, this);
        mListViewLeft = findViewById(R.id.lv_left);
        mListViewRight = findViewById(R.id.lv_right);
        initView();
    }

    private void initView() {
        mListViewLeft.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (mIsClickCategory) {
                    mIsClickCategory = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!mIsClickCategory) {
                    int cindex = -1;
                    firstVisibleItem = firstVisibleItem - mListViewLeft.getHeaderViewsCount();
                    if(mDoubleListViewAdapter != null){
                        cindex = mDoubleListViewAdapter.getRightPosition(firstVisibleItem);
                    }
                    if (cindex != -1 && last_category != cindex) {
                        if(mDoubleListViewAdapter != null){
                            mDoubleListViewAdapter.setSelection(cindex,false);
                            mListViewRight.setSelection(cindex);
                        }
                    }

                    last_category = cindex;
                }
            }
        });
        mListViewLeft.setOnItemClickListener(this);
        mListViewRight.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(mDoubleListViewAdapter != null){
            if(adapterView == mListViewLeft){
                mDoubleListViewAdapter.onLeftItemClic(adapterView,view,i,l);
            } else if(adapterView == mListViewRight){
                mIsClickCategory = true;

                mDoubleListViewAdapter.setSelection(i,true);
                int pos = mDoubleListViewAdapter.getleftPosition(i);
                int realPos = pos + mListViewLeft.getHeaderViewsCount();
                if(realPos <= 0){
                    realPos = 0;
                }
                mListViewLeft.setSelection(realPos);
                mDoubleListViewAdapter.onRightItemClic(adapterView,view,i,l);
            }
        }
    }

    private static final class LeftAdapter extends BaseAdapter {

        public LeftAdapter(DoubleListViewAdapter adpter) {
            mDoubleListViewAdapter = adpter;
        }

        private DoubleListViewAdapter mDoubleListViewAdapter;

        @Override
        public int getCount() {
            return mDoubleListViewAdapter == null ? 0 : mDoubleListViewAdapter.getLeftCount();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return mDoubleListViewAdapter == null ? view : mDoubleListViewAdapter.getLeftView(i, view, viewGroup);
        }
    }

    private static final class RightAdapter extends BaseAdapter {
        private DoubleListViewAdapter mDoubleListViewAdapter;

        public RightAdapter(DoubleListViewAdapter adpter) {
            mDoubleListViewAdapter = adpter;
        }

        @Override
        public int getCount() {
            return mDoubleListViewAdapter == null ? 0 : mDoubleListViewAdapter.getRightCount();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return mDoubleListViewAdapter == null ? view : mDoubleListViewAdapter.getRightView(i, view, viewGroup);
        }
    }

    public interface DataOrbsever{
        public void onDataChanged();
    }
}
