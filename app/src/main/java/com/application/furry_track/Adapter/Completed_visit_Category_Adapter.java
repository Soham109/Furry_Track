package com.application.furry_track.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.application.furry_track.R;
import com.application.furry_track.get_set.completed_visit_category_get_set;

import java.util.ArrayList;


@SuppressLint("ViewHolder")
public class Completed_visit_Category_Adapter extends RecyclerView.Adapter<Completed_visit_Category_Adapter.ViewHolder> {

    Activity context;
    ArrayList<completed_visit_category_get_set> data = new ArrayList<>();
    private int selectedPosition = RecyclerView.NO_POSITION;
    private LayoutInflater inflater;


    // ImageLoader imageLoader;

    public Completed_visit_Category_Adapter(Activity context, ArrayList<completed_visit_category_get_set> arraylist) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        data = arraylist;


    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Notify the adapter that the data set has changed to apply color changes
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_vehicle, null);
        View view = inflater.inflate(R.layout.row_category, parent, false);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_v_no.setText(data.get(position).getSchoolCategory().replace("null", ""));

        if (position == selectedPosition) {
            holder.txt_v_no.setBackground(context.getDrawable(R.drawable.green_layout));
            holder.txt_v_no.setTextColor(context.getColor(R.color.white));

        } else {
            // Reset the background color and text color for non-selected items
            holder.txt_v_no.setBackground(context.getDrawable(R.drawable.round_white_color));
            holder.txt_v_no.setTextColor(context.getColor(R.color.dashboard_text));
        }



    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_v_no;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txt_v_no = itemView.findViewById(R.id.txt_v_no);


        }
    }


}