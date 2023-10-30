package com.example.altriaphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altriaphone.Activity.DetailActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterSearchPhone extends RecyclerView.Adapter<AdapterSearchPhone.ViewHolder> {
    private ArrayList<Product> items;
    private Context context;
    private int NUM_SHOW;

    public AdapterSearchPhone(ArrayList<Product> items, Context context, int NUM_SHOW) {
        this.items = items;
        this.context = context;
        this.NUM_SHOW = NUM_SHOW;
    }
    public void setFilterList(ArrayList<Product> filterList){
        this.items = filterList;
        notifyDataSetChanged();
    }
    public void setNumShow(int i) { this.NUM_SHOW = i; }

    @Override
    public int getItemCount() {
        return Math.min(items.size(), NUM_SHOW);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product item = items.get(position);
        String strSize = String.valueOf(item.getSize()) + "\"";
        if (!item.getBrand().equals("Apple")){
            holder.brandName.setText(item.getBrand() + " " + item.getName());
        }
        else{
            holder.brandName.setText(item.getName());
        }
        holder.price.setText(NumberFormat.getNumberInstance().format(item.getPrice()));

        String imageUrl = item.getImg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", item);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_product_list, parent, false);
        return new ViewHolder(view);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView brandName, size, ram, rom, price;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            brandName = itemView.findViewById(R.id.brand_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
