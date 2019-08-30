package com.example.androidsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

public class Adapter extends PagerAdapter {


    private int[] images = {R.drawable.blackjean, R.drawable.blackjeantotal, R.drawable.hi};    // 이미지를 담을 공간 // 각각이미지 위치
    private LayoutInflater inflater;
    private Context context;

    public  Adapter(Context context){           // Adapter클래스에서 생성자를 만들어주면 됩니다.
        this.context = context;
    }

    @Override
    public int getCount()              //이미지의 전체의 갯수
    {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == ((LinearLayout) object);     // object를 LinearLayout형태로 형변환 했을때 view 와 같은지에 대한 내용을 반환
//        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){                   // 가각의 아이템을 인스턴스화, 가르키는 이미지의 위치
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  // inflater 초기화
        View v = inflater.inflate(R.layout.slider, container, false);   // view 초기화 // 여기서 레이아웃은 slider
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);   // imageView 초기화 // 아까만든 imageView 불러오기
        TextView textView = (TextView) v.findViewById(R.id.textView);       // textView생성 및 초기화
        imageView.setImageResource(images[position]);                       // 현재가리키는 이미지가 몇번째인지
        textView.setText((position + 1)+  "번째 이미지입니다.");
        container.addView(v);                                               // 컨테이너에 해당 뷰를 추가해 주시고
        return v;                                                           // 뷰를 반환
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){      // 아이템 객체를 파괴하는, (할당을 해지하는)
        container.invalidate();
    }
}