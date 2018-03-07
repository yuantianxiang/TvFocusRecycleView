package com.studyproject.yuantianxiang.tvlistgridview.adapter;

import android.support.v7.widget.RecyclerView;

import com.studyproject.yuantianxiang.tvlistgridview.R;
import com.studyproject.yuantianxiang.tvlistgridview.bean.Person;
import com.ytx.tvlibrary.base.BaseRecyclerAdapter;
import com.ytx.tvlibrary.base.RecyclerHolder;

import java.util.Collection;

/**
 * Created by Crz-Pc on 2018/2/5.
 */

public class RecyclerListAdapter extends BaseRecyclerAdapter<Person> {


    public RecyclerListAdapter(RecyclerView v, Collection<Person> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(RecyclerHolder holder, Person item, int position, boolean isScrolling) {
        holder.setText(R.id.item_name, item.getName());
    }
}

