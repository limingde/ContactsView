package com.dd.ddapplication.base;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.dd.ddapplication.beans.BaseBean;
import com.dd.ddapplication.ui.DoubleListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/16.
 */
public abstract class DoubleListViewAdapter<T,D> {
    private HashMap<Integer,D> linkMap = new HashMap<>();
    private HashMap<Integer,T> linkMap_ = new HashMap<>();
    private Map<Integer,Integer> rightRefactToLeft = new LinkedHashMap<>();//左右位置的一个映射关系  key 是在右边的位置 Value 是在左边的位置
    public  int getLeftCount(){
        return linkMap.size() + linkMap_.size();
    }
    public  int getRightCount(){
        return mData == null ? 0 : mData.size();
    }
    public  View getLeftView(int i, View view, ViewGroup viewGroup){
        if(linkMap.containsKey(i)){
            return getLeftView(i,linkMap.get(i),view,viewGroup);
        }
        if(linkMap_.containsKey(i)){
            return  getLeftHeaderView(i,linkMap_.get(i),view,viewGroup);
        }

        return null;
    }

    private void pushToMap(){
        int pos = 0;
        if(mData != null){
            for(int i= 0; i < mData.size(); i ++){
                BaseBean<T,D> item = mData.get(i);
                linkMap_.put(pos,item.tag);
                rightRefactToLeft.put(i,pos);
                pos ++;
                if(item.data != null && item.data.size() > 0){
                    for(int j = 0; j < item.data.size();j ++){
                        linkMap.put(pos,item.data.get(j));
                        pos ++;
                    }
                }
            }
        }
    }
    public abstract View getLeftView(int i, D data,View view, ViewGroup viewGroup);
    public abstract View getLeftHeaderView(int i, T tag,View view, ViewGroup viewGroup);

    public  View getRightView(int pos, View view, ViewGroup viewGroup){
        T tag = null;
        if(mData != null && pos >= 0 && pos < mData.size() ){
            BaseBean<T,D> item = mData.get(pos);
            if(item != null){
                tag = item.tag;
            }
        }
        return getRightView(pos,tag,view,viewGroup);
    }
    public abstract View getRightView(int i,T tag, View view, ViewGroup viewGroup);

    private List<BaseBean<T,D>> mData;
    public final void setData(List<BaseBean<T,D>> list){
        mData = list;
        pushToMap();
        notifyDataSetChanged();
    }

    private List<DoubleListView.DataOrbsever> mDataOrbsevers = new ArrayList<>();
    public void regesterOrbsever(DoubleListView.DataOrbsever orbsever){
        if(orbsever != null){
            mDataOrbsevers.add(orbsever);
        }
    }
    public void clearOrbsever(){
        mDataOrbsevers.clear();
    }
    public void unRegesterOrbsever(DoubleListView.DataOrbsever orbsever){
        if(orbsever != null){
            mDataOrbsevers.remove(orbsever);
        }
    }
    private int mSelection = 0;
    public void notifyDataSetChanged(){
        for(DoubleListView.DataOrbsever orbsever : mDataOrbsevers){
            orbsever.onDataChanged();
        }
    }
    public void setSelection(int selection,boolean isClick){
        mSelection = selection;
        notifyDataSetChanged();
    }
    public boolean isSelectPosition(int pos){
        return mSelection == pos;
    }

    public int getleftPosition(int right){
        if( rightRefactToLeft.containsKey(right)){
            int leftPos = rightRefactToLeft.get(right);
            return leftPos;
        }
        return -1;
    }
    public int getRightPosition(int leftpos) {
        if(leftpos <= 0){
            return 0;
        }
        int index = -1;
        int lastPos = 0;
        int lastright = 0;
        Iterator<Map.Entry<Integer,Integer>> iterator = rightRefactToLeft.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,Integer> entry = iterator.next();
            int pos = entry.getValue();
            if(leftpos >= lastPos && leftpos < pos){
                return lastright;
            }
            lastright =  entry.getKey();
            lastPos = pos;
        }

        return lastright ;
    }

    public void onLeftItemClic(AdapterView<?> adapterView, View view, int i, long l){

    }
    public void onRightItemClic(AdapterView<?> adapterView, View view, int i, long l){

    }

}

