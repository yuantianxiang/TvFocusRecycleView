package com.ytx.tvlibrary.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.ytx.tvlibrary.R;

/**
 * Created by yuantianxiang on 2018/2/22.
 */

public class DoctorRecycleView extends RecyclerView {
    private String TAG = "DoctorRecycleView";
    public static final int LIST_V=1;
    public static final int LIST_H=2;
    public static final int LIST_GRID=3;
    //recycleview 类型
    public int type;
    //grid多少列
    public Integer column = 0;
    //距离本控件 上下左右的焦点id
    public int nextFocusLeft, nextFocusUp, nextFocusRight, nextFocusDown;
    //list 的时候 记录选中位置
    public boolean record_position = false;
    //是否加载更多
    public boolean can_loadmore = false;


    public DoctorRecycleView(Context context) {
        super(context);
    }

    public DoctorRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DoctorRecycleView);
        type =ta.getInteger(R.styleable.DoctorRecycleView_type,0) ;
        nextFocusLeft = ta.getResourceId(R.styleable.DoctorRecycleView_nextFocusLeft, 0);
        nextFocusUp = ta.getResourceId(R.styleable.DoctorRecycleView_nextFocusUp, 0);
        nextFocusRight = ta.getResourceId(R.styleable.DoctorRecycleView_nextFocusRight, 0);
        nextFocusDown = ta.getResourceId(R.styleable.DoctorRecycleView_nextFocusDown, 0);
        record_position = ta.getBoolean(R.styleable.DoctorRecycleView_oldView, false);
        can_loadmore = ta.getBoolean(R.styleable.DoctorRecycleView_can_loadmore, false);
        switch (type) {
            case LIST_V:
                break;

            case LIST_H:
                break;

            case LIST_GRID:
                column = ta.getInteger(R.styleable.DoctorRecycleView_column, 0);
                break;

        }
        ta.recycle();
    }

    public DoctorRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键
                Log.e(TAG, "KEYCODE_DPAD_DOWN");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
