# TvFocusRecycleView
Gradle依赖
compile 'com.github.yuantianxiang:TvFocusRecycleView:v1.0'


集成方式：
<com.ytx.tvlibrary.base.DoctorRecycleView
                android:id="@+id/recycle_listH"
                android:descendantFocusability="beforeDescendants"
                android:layout_width="match_parent"
                android:layout_height="100px"
                app:nextFocusLeft="@+id/recycle_list"
                app:nextFocusDown="@+id/recycle_grid"
                app:oldView="yes"
                app:type="list_h"/>
 <com.ytx.tvlibrary.base.DoctorRecycleView
                android:id="@+id/recycle_grid"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:descendantFocusability="beforeDescendants"
                app:column="3"
                app:nextFocusLeft="@+id/recycle_list"
                app:type="list_grid" />
                
                
  自定义属性
  一 type  1，纵向list  list_v  2，横向list list_h   3，grid  list_grid
  二 当type为list_grid的时候  要加入column字段 列数
  三 nextFocusXXX 是指定 此控件上下左右 的焦点id  解决 焦点丢失的问题。
  四 android:descendantFocusability="beforeDescendants"  必须添加。
  五  app:oldView="yes"  当type为 list_h list_v 的时候 记录上次选中位置。
  
  
  使用方式
  一 编写adapter的时候需要继承BaseRecyclerAdapter
  例如 
public class RecyclerListAdapter extends BaseRecyclerAdapter<Person> {


    public RecyclerListAdapter(RecyclerView v, Collection<Person> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(RecyclerHolder holder, Person item, int position, boolean isScrolling) {
        holder.setText(R.id.item_name, item.getName());
    }
}
 只在convert 方法中 处理业务即可。
 二 监听回调
 //整体recycleview控件获取焦点 默认第一个子 获取焦点
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
        
        //item 获取焦点的时候  业务处理
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
        // recycleview 选中样式回调  选中状态 设置item样式 不选中状态 同样设置
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
        
        
        
  
  
