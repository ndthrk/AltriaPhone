package com.example.altriaphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altriaphone.Activity.ListProductsActivity;
import com.example.altriaphone.R;

import java.util.ArrayList;
import java.util.Map;

public class AdapterBrand extends RecyclerView.Adapter<AdapterBrand.ViewHolder> {
    private Map<String, ArrayList<Integer>> mapBrands;
    private ArrayList<String> brands;
    private Context context;

    public AdapterBrand(Map<String, ArrayList<Integer>> mapBrands, ArrayList<String> brands, Context context) {
        this.mapBrands = mapBrands;
        this.brands = brands;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return mapBrands.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String brand = brands.get(position);
        String num = mapBrands.get(brand).size() + " 📱";
        holder.brandName.setText(brand);
        holder.numPhones.setText(num);
        int resourceId = context.getResources().getIdentifier("logo_"+brand.toLowerCase(), "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.imageView.setImageResource(resourceId);
        } else {
            resourceId = context.getResources().getIdentifier("logo_tmobile", "drawable", context.getPackageName());
            holder.imageView.setImageResource(resourceId);
        }
        holder.brandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID",mapBrands.get(brand));
                context.startActivity(intent);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID",mapBrands.get(brand));
                context.startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_brand, parent, false);
        return new ViewHolder(view);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView brandName, numPhones;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_logo);
            brandName = itemView.findViewById(R.id.tv_brand_name);
            numPhones = itemView.findViewById(R.id.tv_num_products);
        }
    }
}
