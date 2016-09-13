package com.recycleviewdragandswipe.app.recycleviewdragandswipe.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.recycleviewdragandswipe.app.recycleviewdragandswipe.R;
import com.recycleviewdragandswipe.app.recycleviewdragandswipe.info.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MainContentViewHolder> {

    /**
     * item状态监听
     */
    private OnCheckedChangeListener mOnCheckedChangeListener;

    /**
     * item点击监听
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * item中信息列表
     */
    private List<UserInfo> userInfos;

    private ItemTouchHelper itemTouchHelper;



    public MyAdapter(List<UserInfo> userInfos){
        this.userInfos = userInfos;
    }

   public void notifyDataSetChanged(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
        super.notifyDataSetChanged();
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    @Override
    public MyAdapter.MainContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.MainContentViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return userInfos == null ? 0:userInfos.size();
    }

    public UserInfo getData(int position) {
        return userInfos.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnCheckedChangeListener {
        void onItemCheckedChange(CompoundButton view, int position, boolean checked);
    }

    class MainContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnTouchListener{


        //姓名，性别的TextView
        private TextView mTvName,mTvSex;

        //触摸图片
        private ImageView mIvTouch;

        private CheckBox mCbCheck;

        public MainContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTvName = (TextView)itemView.findViewById(R.id.tv_name);
            mTvSex = (TextView)itemView.findViewById(R.id.tv_sex);
            mIvTouch = (ImageView)itemView.findViewById(R.id.iv_touch);
            mCbCheck = (CheckBox)itemView.findViewById(R.id.cb_item_check);
            mCbCheck.setOnClickListener(this);
            mIvTouch.setOnTouchListener(this);
        }

        public void setData(){
            UserInfo userInfo = getData(getAdapterPosition());
            mTvName.setText(userInfo.getName());
            mTvSex.setText(userInfo.getSex());
            mCbCheck.setChecked(userInfo.isCheck());
        }

        @Override
        public void onClick(View v) {
            if (v == itemView && itemTouchHelper != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            } else if (v == mCbCheck && mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onItemCheckedChange(mCbCheck, getAdapterPosition(), mCbCheck.isChecked());
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v == mIvTouch)
                itemTouchHelper.startDrag(this);
            return false;
        }
    }
}
