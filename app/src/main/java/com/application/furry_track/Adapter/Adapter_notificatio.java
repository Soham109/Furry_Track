package com.application.furry_track.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.application.furry_track.R;
import com.application.furry_track.get_set.notification_get_set;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import dmax.dialog.SpotsDialog;


public class Adapter_notificatio extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentActivity context;
    private LayoutInflater inflater;
    ArrayList<notification_get_set> item_list_add_contact = new ArrayList<>();
    public String result;
    SpotsDialog dialog_pro;
    int positiond = 0;

    public Adapter_notificatio(FragmentActivity activity, ArrayList<notification_get_set> data) {

        this.context = activity;
        inflater = LayoutInflater.from(context);
        item_list_add_contact = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_notification, parent, false);
        MyHolder holder = new MyHolder(view);


        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;

        myHolder.titlesss.setText(item_list_add_contact.get(position).getTitle());
        myHolder.desc.setText(item_list_add_contact.get(position).getDescription());


        try {


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate date = LocalDate.parse(item_list_add_contact.get(position).getTodate().replace("null", ""), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH));
                myHolder.txt_time.setText(DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH).format(date));
            }


        } catch (Exception e) {

        }
        //  myHolder.txt_time.setText(item_list_add_contact.get(position).getDateOfRegistration());
        // myHolder.date_time.setText(current.n_date);


       /* myHolder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positiond = position;

                delete_catlog(item_list_add_contact.get(position).NotificationId);
            }
        });
*/
      /*  Glide.with(context).load(item_list_add_contact.get(position).notification_image)
                //.placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(myHolder.img_image);
*/

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return item_list_add_contact.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView titlesss, desc, txt_time;//,date_time;
        ImageView img_image, image_delete;

        public notification_get_set feed;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            //  textFishName= (TextView) itemView.findViewById(R.id.textFishName);

            titlesss = itemView.findViewById(R.id.titlesss);
            desc = itemView.findViewById(R.id.desc);
            //  date_time=  itemView.findViewById(R.id.date_time);
            img_image = itemView.findViewById(R.id.img_image);
            image_delete = itemView.findViewById(R.id.image_delete);
            txt_time = itemView.findViewById(R.id.txt_time);


        }


        public void toast(String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        }


    }


    /*****************************     delete catlog        *******************************************/


    ////////*********************************************************
    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }

}
