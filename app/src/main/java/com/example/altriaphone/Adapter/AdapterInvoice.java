package com.example.altriaphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altriaphone.Activity.DetailActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterInvoice extends RecyclerView.Adapter<AdapterInvoice.ViewHolder> {
    private ArrayList<Product> items;
    ArrayList<Integer> IDs, counts;
    ArrayList<String> names, phones, addresses;

    private Context context;

    public AdapterInvoice(ArrayList<Product> items, ArrayList<Integer> IDs, ArrayList<Integer> counts, ArrayList<String> names, ArrayList<String> phones, ArrayList<String> addresses, Context context) {
        this.items = items;
        this.IDs = IDs;
        this.counts = counts;
        this.names = names;
        this.phones = phones;
        this.addresses = addresses;
        this.context = context;
    }

    public void setFilterList(ArrayList<Product> filterList){
        this.items = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product item = items.get(position);
        String imageUrl = item.getImg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_foreground);
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", item);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
        holder.id.setText("Mã đơn hàng: " + String.valueOf(IDs.get(position)));
        holder.name_and_Phone.setText(names.get(position) + " | " + phones.get(position));
        holder.quantity.setText("SL: "+ String.valueOf(counts.get(position)));
        holder.nameProduct.setText(item.getName());
        holder.address.setText(addresses.get(position));
    }
    @NonNull
    @Override
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_invoice, parent, false);
        return new ViewHolder(view);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id, nameProduct, quantity, name_and_Phone, address;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.inv_img);
            id = itemView.findViewById(R.id.inv_id);
            nameProduct = itemView.findViewById(R.id.inv_nameProduct);
            quantity = itemView.findViewById(R.id.inv_quantity);
            name_and_Phone = itemView.findViewById(R.id.inv_name_and_Phone);
            address = itemView.findViewById(R.id.inv_address);
        }
    }
}
