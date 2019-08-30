package com.example.androidsample;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class BookVO {

    private String bimgurl;	// 도서 이미지
    private String btitle;	// 도서 제목
    private String bauthor;	// 도서 저자
    private String bprice;	// 도서 가격
    private Drawable drawable;  // 따로 데이터를뽑아서 추리는게아니라 imgurl을가지고 여기다 추가할거란말이죠
                                // 도서 이미지에 대한 drawable 객체

    public BookVO(String bimgurl, String btitle, String bauthor, String bprice) {
        this.bimgurl = bimgurl;
        this.btitle = btitle;
        this.bauthor = bauthor;
        this.bprice = bprice;
    }

    public String getBimgurl() {
        return bimgurl;
    }

    public BookVO() {

    }

    public void setBimgurl(String bimgurl) {
        this.bimgurl = bimgurl;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void drawbleFromURL(){
        Drawable d = null;
        try {
            InputStream is = (InputStream)new URL(bimgurl).getContent();
            d = Drawable.createFromStream(is,"NAME");
            this.drawable = d;
        } catch(Exception e){
            Log.i("Error", e.toString());
        }

    }
}
