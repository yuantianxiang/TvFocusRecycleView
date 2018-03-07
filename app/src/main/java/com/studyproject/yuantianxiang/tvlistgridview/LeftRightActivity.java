package com.studyproject.yuantianxiang.tvlistgridview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.studyproject.yuantianxiang.tvlistgridview.adapter.RecyclerGridAdapter;
import com.studyproject.yuantianxiang.tvlistgridview.adapter.RecyclerListAdapter;
import com.studyproject.yuantianxiang.tvlistgridview.bean.Person;
import com.ytx.tvlibrary.base.BaseRecyclerAdapter;
import com.ytx.tvlibrary.base.DoctorRecycleView;
import com.ytx.tvlibrary.base.RecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 左右布局 实例
 */

public class LeftRightActivity extends AppCompatActivity {

    DoctorRecycleView recycleList;
    DoctorRecycleView recycleGrid;

    RecyclerGridAdapter adapter_grid;
    RecyclerListAdapter adapter_list;

    List<Person> list = new ArrayList<>();
    List<Person> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftright);
        recycleList = (DoctorRecycleView) findViewById(R.id.recycle_list);
        recycleGrid = (DoctorRecycleView) findViewById(R.id.recycle_grid);
        initdata();
    }

    /**
     * 初始化数据
     */
    private void initdata() {
        for (int i = 0; i < 20; i++) {
            list.add(new Person("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519291896584&di=1e2f4f76e1f25efe61f7065daf315e16&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201602%2F25%2F20160225215733_e2XvT.jpeg", "测试用户" + i, 0 + 20));
        }
        for (int i = 0; i < 20; i++) {
            list2.add(new Person("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519291896584&di=1e2f4f76e1f25efe61f7065daf315e16&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201602%2F25%2F20160225215733_e2XvT.jpeg", "测试用户" + i, 0 + 20));
        }
        initList();
        initGrid();
    }

    private void getData(int position) {
        if (list2.size() > 0) {
            list2.clear();
        }
        for (int i = 0; i < 20; i++) {
            list2.add(new Person("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519291896584&di=1e2f4f76e1f25efe61f7065daf315e16&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201602%2F25%2F20160225215733_e2XvT.jpeg", "测试用户" + position, position + 20));
        }
    }


    private void initList() {
        GridLayoutManager mgr = new GridLayoutManager(this, 1);
        recycleList.setLayoutManager(mgr);
        adapter_list = new RecyclerListAdapter(recycleList, list, R.layout.item_list);
        recycleList.setAdapter(adapter_list);
        adapter_list.setOnRVFocusChangeListener(new BaseRecyclerAdapter.OnRVFocusChangeListener() {
            @Override
            public void OnRVocusChangeListener(View view, boolean hasFocus, boolean first) {
                if (hasFocus == true) {
                    if (first) {
                        if (recycleList.getChildAt(0) != null)
                            recycleList.getChildAt(0).requestFocus();
                    }
                }
            }
        });
        adapter_list.setOnItemFocusChangeListener(new BaseRecyclerAdapter.OnItemFocusChangeListener() {
            @Override
            public void OnItemFocusChangeListener(View view, RecyclerHolder holder, Object data, final int position, boolean hasFocus) {
                if (hasFocus) {
                    //更新右侧
                    getData(position);
                    adapter_grid.refresh(list2);
                }
            }
        });
        adapter_list.setSelectAndUpdataItemUi(new BaseRecyclerAdapter.SelectAndUpdataItemUi() {
            @Override
            public void SelectAndUpdataItemUi(View view, boolean ifSelect) {
                if (ifSelect){
                    ((TextView)view.findViewById(R.id.item_name)).setTextColor(Color.YELLOW);
                }else {
                    ((TextView)view.findViewById(R.id.item_name)).setTextColor(Color.WHITE);
                }
            }
        });


    }

    private void initGrid() {
        GridLayoutManager mgr = new GridLayoutManager(this, 3);
        recycleGrid.setLayoutManager(mgr);
        adapter_grid = new RecyclerGridAdapter(recycleGrid, list2, R.layout.item_grid);
        recycleGrid.setAdapter(adapter_grid);
        adapter_grid.setOnRVFocusChangeListener(new BaseRecyclerAdapter.OnRVFocusChangeListener() {
            @Override
            public void OnRVocusChangeListener(View view, boolean hasFocus, boolean first) {
                if (hasFocus) {
                    if (recycleGrid.getChildCount() > 0) {
                        recycleGrid.getChildAt(0).requestFocus();
                    }
                }
            }
        });
        adapter_grid.setOnItemFocusChangeListener(new BaseRecyclerAdapter.OnItemFocusChangeListener() {
            @Override
            public void OnItemFocusChangeListener(View view, RecyclerHolder holder, Object data, int position, boolean hasFocus) {
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        recycleGrid.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    private void showOnFocusAnimation(View v, float scale) {
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.02f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.02f);
        ObjectAnimator.ofPropertyValuesHolder(v, pvhY, pvhZ).setDuration(100).start();
        v.bringToFront();
    }
}
