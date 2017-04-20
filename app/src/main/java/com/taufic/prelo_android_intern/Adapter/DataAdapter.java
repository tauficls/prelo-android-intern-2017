package com.taufic.prelo_android_intern.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taufic.prelo_android_intern.DetailActivity;
import com.taufic.prelo_android_intern.Item.DataItem;
import com.taufic.prelo_android_intern.R;

import java.util.ArrayList;

/**
 * Created by taufic on 4/12/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<DataItem> data;
    private Context context;
    private String id;
    public DataAdapter(Context context, ArrayList<DataItem> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View listItem = layoutInflater.inflate(R.layout.listview, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameText.setText(data.get(position).getName());
        holder.costText.setText(data.get(position).getCost());
        String url = data.get(position).getUrlPhoto();
        id = data.get(position).getId();
        Glide.with(context).load(url).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("product_id", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameText;
        public TextView costText;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_love);
            this.nameText = (TextView) itemView.findViewById(R.id.name_love);
            this.costText = (TextView) itemView.findViewById(R.id.cost_love);
        }
    }

}
