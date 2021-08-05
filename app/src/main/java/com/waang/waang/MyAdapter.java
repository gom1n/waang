package com.waang.waang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    // 파이어베이스 데이터베이스 연동  //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<com.waang.waang.MyItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public com.waang.waang.MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        String name, address, phone;

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;
        LinearLayout tv = (LinearLayout)convertView.findViewById(R.id.tv);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        com.waang.waang.MyItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageDrawable(myItem.getIcon());
        tv_name.setText(myItem.getName());

        // x 버튼 클릭 시
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItems.remove(position);
                notifyDataSetChanged();
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            String name;
            @Override
            public void onClick(View view) {
                //name = ccName[pos];
            }
        });

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(Drawable img, String name) {

        com.waang.waang.MyItem mItem = new com.waang.waang.MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setName(name);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }

}