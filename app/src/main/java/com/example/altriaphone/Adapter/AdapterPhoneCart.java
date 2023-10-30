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
import com.example.altriaphone.CartFrangment;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterPhoneCart extends RecyclerView.Adapter<AdapterPhoneCart.ViewHolder> {
    private Context context;
    ArrayList<Product> listCart;
    ArrayList<Integer> counts;

    public AdapterPhoneCart(ArrayList<Product> listCart, ArrayList<Integer> counts, Context context) {
        this.listCart = listCart;
        this.counts = counts;
        this.context = context;
    }
    public void updateTotal(){
        double sum = 0, total;
        int n = 0,dis = 0;
        for(int i =0; i < getItemCount(); i++){
            sum += listCart.get(i).getPrice() * counts.get(i);
            n += counts.get(i);
        }
        CartFrangment.sub.setText(NumberFormat.getNumberInstance().format(sum));
        if (n > 0){ dis = 2; }
        if (n > 2){ dis = 5; }
        if (n > 4){ dis = 8; }
        if (n > 10){ dis = 15; }

        if (n == 0) CartFrangment.discount.setText("");
        else CartFrangment.discount.setText("-"+dis+"%");
        total = (int)((sum * (100-dis))/100000) * 1000;
        CartFrangment.total.setText(NumberFormat.getNumberInstance().format(total));
    }
    @Override
    public int getItemCount() {
        return listCart.size();
    }

    @Override
    public long getItemId(int i) {
        return listCart.get(i).getId();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product item = listCart.get(position);
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
        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", item);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
        holder.plus.setOnClickListener(view -> {
            MainActivity.counts.set(position, counts.get(position) + 1);
            counts.set(position, counts.get(position) + 1);
            notifyItemChanged(position);
        });
        holder.minus.setOnClickListener(view -> {
            int value = counts.get(position);
            if (value > 0) {
                MainActivity.counts.set(position, value-1);
                counts.set(position, value-1);
                notifyItemChanged(position);
            }
        });
        holder.close.setOnClickListener(view -> {
            MainActivity.counts.remove(position);
            MainActivity.listCart.remove(position);
            counts.remove(position);
            listCart.remove(position);
            notifyItemRemoved(position);
        });
        holder.count.setText(String.valueOf(counts.get(position)));
        double sum = item.getPrice() * counts.get(position);
        holder.sumPrice.setText(NumberFormat.getNumberInstance().format((sum)));
        updateTotal();

    }
    @NonNull
    @Override
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_product_cart, parent, false);
        return new ViewHolder(view);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, close;
        TextView brandName, price, sumPrice, count, minus, plus ;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pc_img);
            close = itemView.findViewById(R.id.pc_close);
            brandName = itemView.findViewById(R.id.pc_phoneName);
            price = itemView.findViewById(R.id.pc_pricePhone);
            sumPrice = itemView.findViewById(R.id.pc_sumPrice);
            count = itemView.findViewById(R.id.pc_count);
            minus = itemView.findViewById(R.id.minusCart);
            plus = itemView.findViewById(R.id.plusCart);
        }
    }
}
