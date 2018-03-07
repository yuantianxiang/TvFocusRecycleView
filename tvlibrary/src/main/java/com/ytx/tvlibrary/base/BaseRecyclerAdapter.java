package com.ytx.tvlibrary.base;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

import static com.ytx.tvlibrary.base.DoctorRecycleView.LIST_GRID;
import static com.ytx.tvlibrary.base.DoctorRecycleView.LIST_H;
import static com.ytx.tvlibrary.base.DoctorRecycleView.LIST_V;

/**
 * description: BaseRecyclerAdapter
 * update: 2017/3/30
 * version:
 * date: 2017/3/30 16:28
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    protected final RecyclerView RV;
    protected final int mItemLayoutId;
    protected List<T> realDatas;
    protected boolean isScrolling;
    protected Context cxt;
    //点击
    private OnItemClickListener listener;
    private OnItemLongClickListener longlistener;
    private OnItemFocusChangeListener itemFocusChangeListener;
    private OnRVFocusChangeListener rvFocusChangeListener;
    private SelectAndUpdataItemUi selectAndUpdataItemUi;

    //list_h list_v 记录选中位置
    private View oldTopView;

    public BaseRecyclerAdapter(RecyclerView v, Collection<T> datas, int itemLayoutId) {
        realDatas = (List<T>) datas;
        mItemLayoutId = itemLayoutId;
        cxt = v.getContext();
        this.RV = v;

    }

    public View getOldView() {
        return oldTopView;
    }

    /**
     * 返回数量
     */
    @Override
    public int getItemCount() {
        if (realDatas == null) return 0;
        return realDatas.size();
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View root = inflater.inflate(mItemLayoutId, parent, false);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.itemView.setFocusable(true);
        setNextFocus(holder, position);
        convert(holder, realDatas.get(position), position, isScrolling);
        holder.itemView.setOnClickListener(getOnClickListener(position));
        holder.itemView.setOnFocusChangeListener(getOnFocusClickListener(holder, position));
        //如果是 list列表 则记录选中位置 0为选中状态
        if (record_position() && selectAndUpdataItemUi != null) {
            if (null == oldTopView && position == 0) {
                selectAndUpdataItemUi.SelectAndUpdataItemUi(holder.itemView, true);

            } else {
                selectAndUpdataItemUi.SelectAndUpdataItemUi(holder.itemView, false);
            }
        }
        RV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (rvFocusChangeListener != null) {
                        Log.e("fuckyou", "fuckyou baby1");
                        if (record_position() && null != oldTopView) {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    oldTopView.requestFocus();
                                }
                            });
                            rvFocusChangeListener.OnRVocusChangeListener(view, b, false);
                            return;
                        }
                        rvFocusChangeListener.OnRVocusChangeListener(view, b, true);
                    }
                }
            }
        });
    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder      viewholder
     * @param item        javabean
     * @param isScrolling RecyclerView是否正在滚动
     */
    public abstract void convert(RecyclerHolder holder, T item, int position, boolean isScrolling);


    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, realDatas.get(position), position);
                }
            }
        };
    }

    protected int select_focus_position;

    public View.OnFocusChangeListener getOnFocusClickListener(final RecyclerHolder holder, final int position) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (itemFocusChangeListener == null || v == null) {
                    return;
                }
                if (!hasFocus) {
                    itemFocusChangeListener.OnItemFocusChangeListener(v, holder, realDatas.get(position), position, hasFocus);
                    return;
                }
                if (realDatas == null || realDatas.size() == 0 || RV.getLayoutManager() == null)
                    return;
                RecyclerView.LayoutManager layoutManager = RV.getLayoutManager();

                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);

                    int spanCount = gridManager.getSpanCount();

                    int currentLine = (position / spanCount) + 1;
                    int maxLine = realDatas.size() % spanCount == 0 ? realDatas.size() / spanCount : ((realDatas.size() / spanCount) + 1);
                    int oldLine = (select_focus_position / spanCount) + 1;
                    Log.e("aaaa", currentLine + "  " + maxLine + " " + oldLine + " " + select_focus_position + "  " + position);
                    if (currentLine != 0 && maxLine - currentLine > 0 && currentLine != oldLine && spanCount != 1) {
                        if (currentLine > oldLine) {
                            RV.scrollBy(0, 100);
                        } else {
                            RV.scrollBy(0, -100);
                        }
                    }
                    select_focus_position = position;
                }
                itemFocusChangeListener.OnItemFocusChangeListener(v, holder, realDatas.get(position), position, hasFocus);
                if (hasFocus && record_position() && selectAndUpdataItemUi != null) {
                    if (null == getOldView()) {
                        selectAndUpdataItemUi.SelectAndUpdataItemUi(v, true);
                    } else {
                        selectAndUpdataItemUi.SelectAndUpdataItemUi(getOldView(), false);
                        selectAndUpdataItemUi.SelectAndUpdataItemUi(v, true);
                    }
                }
                if (record_position()) {
                    oldTopView = v;
                }
            }
        };
    }

    /**
     * 刷新notify
     */
    public void refresh(Collection<T> datas) {
        realDatas = (List<T>) datas;
        select_focus_position = 0;
        if (oldTopView != null) {
            oldTopView = null;
        }
        notifyDataSetChanged();

        if (realDatas != null && realDatas.size() > 0) {
            RV.scrollToPosition(0);
        }
    }

    public void refreshItemRange(Collection<T> datas, int positionStart, int itemCount) {
        realDatas = (List<T>) datas;
        notifyItemRangeChanged(positionStart, itemCount);
    }

    //监听
    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public void setOnItemLongClickLiistener(OnItemLongClickListener l) {
        longlistener = l;
    }

    //item 焦点改变
    public void setOnItemFocusChangeListener(OnItemFocusChangeListener l) {
        itemFocusChangeListener = l;
    }

    //recycleview整体焦点改变
    public void setOnRVFocusChangeListener(OnRVFocusChangeListener l) {
        rvFocusChangeListener = l;
    }

    public void setSelectAndUpdataItemUi(SelectAndUpdataItemUi selectAndUpdataItemUi) {
        this.selectAndUpdataItemUi = selectAndUpdataItemUi;
    }

    //监听
    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, Object data, int position);
    }

    public interface OnItemFocusChangeListener {
        void OnItemFocusChangeListener(View view, RecyclerHolder holder, Object data, int position, boolean hasFocus);
    }

    public interface OnRVFocusChangeListener {
        void OnRVocusChangeListener(View view, boolean hasFocus, boolean first);
    }

    //todo 跟新 list选中样式
    public interface SelectAndUpdataItemUi {
        void SelectAndUpdataItemUi(View view, boolean ifSelect);
    }

    /**
     * 判断是否需要记录位置
     */
    private boolean record_position() {
        if (((DoctorRecycleView) RV).record_position) {
            int type = ((DoctorRecycleView) RV).type;
            if (type==LIST_V||type==LIST_H) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置nextfocus位置
     */
    private void setNextFocus(RecyclerHolder holder, int position) {
        int column = ((DoctorRecycleView) RV).column;
        int type = ((DoctorRecycleView) RV).type;
        switch (type) {
            case LIST_GRID:
                if (column > 0) {
                    /**
                     * 在第一行的时候设置向上的焦点
                     */
                    if (position / column == 0 && ((DoctorRecycleView) RV).nextFocusUp != 0) {
                        holder.itemView.setNextFocusUpId(((DoctorRecycleView) RV).nextFocusUp);
                    } else {
                        holder.itemView.setNextFocusUpId(0);
                    }
                    /**
                     * 在最左边一列的时候 设置左边的焦点
                     */
                    if (position % column == 0 && ((DoctorRecycleView) RV).nextFocusLeft != 0) {
                        holder.itemView.setNextFocusLeftId(((DoctorRecycleView) RV).nextFocusLeft);
                    } else {
                        holder.itemView.setNextFocusLeftId(0);
                    }
                }
                break;

            case LIST_V:
                if (((DoctorRecycleView) RV).nextFocusRight != 0) {
                    holder.itemView.setNextFocusRightId(((DoctorRecycleView) RV).nextFocusRight);
                }
                //Todo  list v 的时候焦点在最后一行 不可向下点击
                if (!((DoctorRecycleView) RV).can_loadmore && (position == realDatas.size() - 1)) {
                    holder.itemView.setNextFocusDownId(RV.getId());
                } else {
                    holder.itemView.setNextFocusDownId(0);
                }
                break;
            case LIST_H:
                if (((DoctorRecycleView) RV).nextFocusDown != 0) {
                    holder.itemView.setNextFocusDownId(((DoctorRecycleView) RV).nextFocusDown);
                } else {
                    holder.itemView.setNextFocusDownId(0);
                }
                //list_h 的时候在最左边的时候 如果没有设置有nextfocusLeft 则不可向左移动焦点
                if (((DoctorRecycleView) RV).nextFocusLeft != 0) {
                    holder.itemView.setNextFocusLeftId(((DoctorRecycleView) RV).nextFocusLeft);
                } else {
                    if (position == 0) {
                        holder.itemView.setNextFocusLeftId(RV.getId());
                    } else {
                        holder.itemView.setNextFocusLeftId(0);
                    }
                }
                //list_h 的时候在最右边边的时候 如果没有设置有nextfocusRight 则不可向右移动焦点
                if (((DoctorRecycleView) RV).nextFocusRight != 0) {
                    holder.itemView.setNextFocusRightId(((DoctorRecycleView) RV).nextFocusRight);
                } else {
                    if (position == realDatas.size() - 1) {
                        holder.itemView.setNextFocusRightId(RV.getId());
                    } else {
                        holder.itemView.setNextFocusRightId(0);
                    }
                }
                break;

        }
    }
}