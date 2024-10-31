package com.application.furry_track.CustomeView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;



public class custome_Textview_medium extends AppCompatTextView {

    public custome_Textview_medium(Context context) {
        super(context);
    }


    public custome_Textview_medium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public custome_Textview_medium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans_Semibold.ttf");



        setTypeface(tf);

    }
}
