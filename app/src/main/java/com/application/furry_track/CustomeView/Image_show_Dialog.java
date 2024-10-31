package com.application.furry_track.CustomeView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.application.furry_track.R;


public class Image_show_Dialog {
    static Dialog dialog_add;
    public static void show_big_Image(String img_ur, Activity context) {
        final ImageView img_big_image, img_close;
        // dialog_add = new Dialog(getActivity(), android.R.style.Theme_Light);
        dialog_add = new Dialog(context, R.style.AppTheme);
        dialog_add.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_add.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_add.setContentView(R.layout.dialog_image);
        dialog_add.setCancelable(true);

        img_big_image = dialog_add.findViewById(R.id.img_big_image);
        img_close = dialog_add.findViewById(R.id.img_close);
        Glide.with(context)
                .load(img_ur)
                //.asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.no_image)
                .into(img_big_image);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_add.dismiss();
            }
        });
        // img_big_image.setImage(ImageSource.uri(p_main_url));
        dialog_add.show();
    }
}
