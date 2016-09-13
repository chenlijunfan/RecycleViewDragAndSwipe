package com.recycleviewdragandswipe.app.recycleviewdragandswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.recycleviewdragandswipe.app.recycleviewdragandswipe.adapter.MyAdapter;
import com.recycleviewdragandswipe.app.recycleviewdragandswipe.info.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UserInfo> userInfos = null;
    private MyAdapter adapter;
    private DefaultItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_update).setOnClickListener(onClickListener);

        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置recycleview的布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MyAdapter(userInfos);
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnCheckedChangeListener(onCheckedChangeListener);
        recyclerView.setAdapter(adapter);

        userInfos = new ArrayList<>();
        for(int i=0; i<40; i++){
            boolean isChecked = i%2 == 0;
            userInfos.add(new UserInfo(isChecked,"张"+i, isChecked ? "男":"女") );
        }
        adapter.notifyDataSetChanged(userInfos);

        itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.setSwipeEnable(true);
        itemTouchHelper.setDragEnable(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_update){
                userInfos.get(3).setName("瞎JB乱嗨");
                userInfos.get(3).setCheck(false);
                adapter.notifyDataSetChanged(userInfos);
            }
        }
    };

    private MyAdapter.OnItemClickListener onItemClickListener = new MyAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(MainActivity.this,"第"+(position+1)+"行被点击了",Toast.LENGTH_SHORT).show();
        }
    };


    private MyAdapter.OnCheckedChangeListener onCheckedChangeListener = new MyAdapter.OnCheckedChangeListener() {
        @Override
        public void onItemCheckedChange(CompoundButton view, int position, boolean checked) {
            Toast.makeText(MainActivity.this,"第"+(position+1)+"行  "+(checked ? "选中":"未选中"), Toast.LENGTH_SHORT).show();
        }
    };

    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {


        @Override
        public void onSwipe(int adapterPosition) {
            if (userInfos != null){
                userInfos.remove(adapterPosition);
                //Toast.makeText(MainActivity.this,"第"+adapterPosition+"行被删除了",Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged(userInfos);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if(userInfos != null){
                int temp=-1;
                if(srcPosition > targetPosition){
                    //向上互换位置时，要将两个位置大小互换，因为swap方法只支持小大互换
                    temp = srcPosition;
                    srcPosition = targetPosition;
                    targetPosition = temp;
                }
                //将list中的相应两个位置互换
                Collections.swap(userInfos, srcPosition, targetPosition);
                adapter.notifyDataSetChanged(userInfos);
                Toast.makeText(MainActivity.this,"第"+srcPosition+"行与第"+targetPosition+"行互换位置",Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

}
