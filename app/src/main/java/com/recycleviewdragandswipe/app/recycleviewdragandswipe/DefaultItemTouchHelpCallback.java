package com.recycleviewdragandswipe.app.recycleviewdragandswipe;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DefaultItemTouchHelpCallback extends ItemTouchHelper.Callback {

    /**
     * Item操作的回调
     */
    private OnItemTouchCallbackListener onItemTouchCallbackListener;

    /**
     * 是否可以拖拽
     */
    private boolean isCanDrag = false;

    /**
     * 是否可以被滑动
     */
    private boolean isCanSwipe = false;

    public DefaultItemTouchHelpCallback(OnItemTouchCallbackListener onItemTouchCallbackListener){
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    /**
     * 设置Item操作得回调，去更新UI和数据源
     * @param onItemTouchCallbackListener
     */
    public void setOnItemTouchCallbackListener(OnItemTouchCallbackListener onItemTouchCallbackListener){
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    /**
     * 设置是否可以被拖拽
     * @param canDrag 是true，否false
     */
    public void setDragEnable(boolean canDrag){
        isCanDrag = canDrag;
    }

    /**
     * 设置是否可以被滑动
     * @param canSwipe 是true，否false
     */
    public void setSwipeEnable(boolean canSwipe){
        isCanSwipe = canSwipe;
    }

    /**
     * 当item拖拽或者滑动的时候告诉我们滑动的或者拖拽的方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){//GridLayoutManager
            //flag如果值是0，相当于这个功能被关闭
            int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = 0;
            return makeMovementFlags(dragFlag,swipeFlag);
        }else if(layoutManager instanceof LinearLayoutManager){//LinearLayoutManager
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();

            int dragFlag = 0;
            int swipeFlag = 0;
            //为了方便理解，相当于分为横着的ListView和竖着的ListView
            if(orientation == LinearLayoutManager.HORIZONTAL){//如果是横向布局
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }else if(orientation == LinearLayoutManager.VERTICAL){//如果是竖向的布局，相当于是ListView
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag,swipeFlag);
        }
        return 0;
    }

    /**
     * 当item被拖拽的时候被回调
     * @param recyclerView  recycleview
     * @param viewHolder    拖拽的ViewHolder
     * @param target        目的地的viewHolder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
            target) {
        if(onItemTouchCallbackListener != null){
            return onItemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        }
        return false;
    }

    /**
     * 是否可以长按拖拽排序
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return isCanDrag;
    }

    /**
     * Item是否可以被滑动（H:左右滑动，V上下滑动）
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwipe;
    }

    /**
     * 当item被滑动删除的时候
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(onItemTouchCallbackListener != null){
            onItemTouchCallbackListener.onSwipe(viewHolder.getAdapterPosition());
        }
    }

    public interface OnItemTouchCallbackListener{

        /**
         * 当某个Item被滑动删除的时候
         * @param adapterPosition
         */
        void onSwipe(int adapterPosition);

        /**
         * 当两个Item位置互换的时候被调回
         * @param srcPosition       拖拽的item的position
         * @param targetPosition    目的地的Item的position
         * @return                  开发者处理了操作应该返回true，开发者没有处理就返回false
         */
        boolean onMove(int srcPosition, int targetPosition);
    }
}
