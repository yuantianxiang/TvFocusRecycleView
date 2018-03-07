package com.studyproject.yuantianxiang.tvlistgridview.adapter;

import android.support.v7.widget.RecyclerView;

import com.studyproject.yuantianxiang.tvlistgridview.R;
import com.studyproject.yuantianxiang.tvlistgridview.bean.Company;
import com.ytx.tvlibrary.base.BaseRecyclerAdapter;
import com.ytx.tvlibrary.base.RecyclerHolder;

import java.util.Collection;

/**
 * Created by Crz-Pc on 2018/2/5.
 */

public class RecyclerListHAdapter extends BaseRecyclerAdapter<Company> {


    public RecyclerListHAdapter(RecyclerView v, Collection<Company> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(RecyclerHolder holder, Company item, int position, boolean isScrolling) {
        holder.setText(R.id.item_name, item.getName());
    }
}

