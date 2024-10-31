package com.application.furry_track.CustomeView;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;


public class custome_Textview extends AppCompatTextView {

    public custome_Textview(Context context) {
        super(context);
    }


    public custome_Textview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public custome_Textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans_Semibold.ttf");



        setTypeface(tf);

    }
}
