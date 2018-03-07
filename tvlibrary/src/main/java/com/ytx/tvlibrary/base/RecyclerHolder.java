package com.ytx.tvlibrary.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * description:  RecyclerHolder
 * update: 2017/3/30
 * version:
 * date: 2017/3/30 16:30
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    public RecyclerHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<View>();
        //TODO 自适应框架 autolayout 的支持
        AutoUtils.autoSize(itemView);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public RecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * @param viewId
     * @param url
     * @return
     */
    public RecyclerHolder setImageByUrl(Context context, int viewId, String url) {
        ImageView allView = getView(viewId);
        Glide.with(context).load(url).into(allView);
        return this;
    }
}