package com.example.androidsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {
    private List<BookVO> list = new ArrayList<BookVO>();
    // 반드시 overriding을 해야하는 method가 존재.

    public void addItem(BookVO vo){     // 이녀석에 의해서 위에 선언한 list에 데이터들이 슈슉슉휴귝슉 들어가게 되고
        list.add(vo);
    }

    @Override
    public int getCount() {
        // 총 몇개의 component를 그려야 하는지를 retrun
        return list.size();           // 총 몇개가 그려지는가가 이녀석에 의해 결정된다 (내가 가지고 있는 리스트갯수만큼만 그려!)
    }

    @Override
    public Object getItem(int i) {
        // 몇번째 데이터를 화면에 출력할지를 결정.
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        // 순번을 표시
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null){   // INFLATER라는 객체를 만드는 코드입니다
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 생성된 View객체에 XML Layout을 설정.
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);      // Imageview, Textview, Textview 3개 만드는거

        }
        // 출력할 View Component의 reference를 획독.
        ImageView iv = (ImageView)view.findViewById(R.id.customIV);
        TextView tv1 = (TextView)view.findViewById(R.id.customTv1);
        TextView tv2 = (TextView)view.findViewById(R.id.customTv2);

        BookVO vo = list.get(i);    // 화면에 출력할 데이터를 가져와요!

        iv.setImageDrawable(vo.getDrawable());
        tv1.setText(vo.getBtitle());
        tv2.setText(vo.getBauthor());

        return view;
    }
}