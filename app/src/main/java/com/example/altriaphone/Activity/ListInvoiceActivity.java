package com.example.altriaphone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.altriaphone.Adapter.AdapterInvoice;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;

import java.util.ArrayList;

public class ListInvoiceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterInvoice adapter;
    ArrayList<Product> listInvoice = new ArrayList<>();
    ArrayList<Integer> IDs = new ArrayList<>(),
                    quantities = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>(),
            phones = new ArrayList<>(),
            addresses = new ArrayList<>();
    TextView noti;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_invoice);

        back = findViewById(R.id.backImage);
        noti = findViewById(R.id.thongbaoInvoice);
        recyclerView = findViewById(R.id.danhsachRV);

        back.setOnClickListener(view -> finish());
        if (MainActivity.listDelivered.isEmpty()){
            noti.setText("Không có đơn hàng nào :(");
        }
        else{
            noti.setVisibility(View.GONE);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterInvoice(listInvoice, IDs, quantities, names,phones, addresses, this);
        LOAD_INVOICE();
        recyclerView.setAdapter(adapter);
    }
    void LOAD_INVOICE(){
        for (int i = 0; i < MainActivity.listDelivered.size(); i++){
            listInvoice.add(MainActivity.listDelivered.get(i));
            IDs.add(MainActivity.id_invoice.get(i));
            quantities.add(MainActivity.count_Delivered.get(i));
            names.add(MainActivity.name_invoice.get(i));
            phones.add(MainActivity.phone_invoice.get(i));
            addresses.add(MainActivity.add_invoice.get(i));
        }
        adapter.notifyDataSetChanged();
    }
        @Override
    public void onResume() {
        super.onResume();
        this.getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        this.getSupportActionBar().show();
    }
}