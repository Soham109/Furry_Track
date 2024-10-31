package com.application.furry_track.CustomeView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;


import com.application.furry_track.R;

import java.util.ArrayList;
import java.util.Locale;



public class MyEditTextView_speech extends AppCompatEditText {


    final int DRAWABLE_RIGHT = 2;

    Intent speechRecognizerIntent;
    private SpeechRecognizer speechRecognizer;

    public MyEditTextView_speech(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    public MyEditTextView_speech(Context context, AttributeSet attrs) {
        super(context, attrs);

        MyEditTextView_speech.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mic_black_off, 0);

        //   MyEditTextView_speech.this.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);


        MyEditTextView_speech.this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null) {
                        //The x-axis coordinates of this click event, if > current control width - control right spacing - drawable actual display size
                        if (motionEvent.getX() >= (getWidth() - getPaddingRight() - drawableRight.getIntrinsicWidth())) {
                            //  MyEditTextView_speech.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mic_black_off, 0);


                            speechRecognizer.stopListening();

                        }
                    }
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //micButton.setImageResource(R.drawable.ic_mic_black_24dp);
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null) {
                        //The x-axis coordinates of this click event, if > current control width - control right spacing - drawable actual display size
                        if (motionEvent.getX() >= (getWidth() - getPaddingRight() - drawableRight.getIntrinsicWidth())) {
                            MyEditTextView_speech.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mic_black_24dp, 0);
                            speechRecognizer.startListening(speechRecognizerIntent);
                        }
                    }
                }
                return false;
            }
        });


/*
        setDrawableClickListener(new Base_Activity.DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case LEFT:
                        //Do something here
                        break;

                    case RIGHT:
                        Toast.makeText(context, "ffffffffffffffffffffffff", Toast.LENGTH_SHORT).show();
                        //Do something here
                        break;

                    default:
                        break;
                }
            }

        });*/
        init();
    }

    public MyEditTextView_speech(Context context) {
        super(context);
        init();
    }


    public void init() {


        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        setTypeface(tf);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());


        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 25000);
        /*speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getContext().getString(R.string.app_name));*/
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {


            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                    Toast.makeText(getContext(), "Listening...", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {
                    MyEditTextView_speech.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mic_black_off, 0);

                }

                @Override
                public void onError(int i) {
                    MyEditTextView_speech.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mic_black_off, 0);
                }

                @Override
                public void onResults(Bundle bundle) {
                    //     micButton.setImageResource(R.drawable.ic_mic_black_off);
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    //     txt.setText(data.get(0));
                    MyEditTextView_speech.this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mic_black_off, 0);
                    MyEditTextView_speech.this.setText(data.get(0) + "");

                    // Toast.makeText(getContext(), data.get(0) + "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });

        } catch (Exception e) {
            String d = "sdf";
        }

    }


  /*  public interface OnDropArrowClickListener {
        void onDropArrowClick();
    }
*/
    //  private OnDropArrowClickListener onDropArrowClickListener;

    /*public void setOnDropArrowClickListener(OnDropArrowClickListener onDropArrowClickListener) {
        this.onDropArrowClickListener = onDropArrowClickListener;
    }*/
}