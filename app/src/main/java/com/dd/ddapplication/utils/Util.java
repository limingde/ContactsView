package com.dd.ddapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;

import com.dd.ddapplication.MainApplication;

/**
 * Created by Administrator on 2017/9/16.
 */
public class Util {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = MainApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 资源中获取字符串
     * @param resId 资源ID
     * @return
     */
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    /**
     * 从资源中获取Drawable
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        try {
            return getResources().getDrawable(resId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取资源管理对象
     * @return
     */
    public static Resources getResources() {
        return MainApplication.getInstance().getResources();
    }

    public static View inflate(Context context,@LayoutRes int layout){
       return LayoutInflater.from(context).inflate(layout,null);
    }
}
