package com.example.altriaphone.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altriaphone.Adapter.AdapterPhone;
import com.example.altriaphone.Adapter.GridSpacingItemDecoration;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListProductsActivity extends AppCompatActivity {
    String typeShow;
    Button search, newOld, filter, showMore, hide;
    ImageView back;
    Spinner price, priceLevel;
    RecyclerView recyclerView;
    boolean isNewest = true, canMore = true;
    String priceSelected = "Không chọn", priceLevelSelected = "Tất cả";
    Intent intent;
    ArrayList<Product> products = new ArrayList<>(), filterProducts = new ArrayList<>();
    AdapterPhone adapterPhone;
    TextView numProducts;
    Context context;
    int NUM_SHOW = 20;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listproducts);
        context = this;
        back = findViewById(R.id.back);
        search = findViewById(R.id.search);
        newOld = findViewById(R.id.btn_new_old);
        filter = findViewById(R.id.btn_filter);
        recyclerView = findViewById(R.id.grid_view);
        price = findViewById(R.id.spinner_price);
        priceLevel = findViewById(R.id.spinner_price_level);
        showMore = findViewById(R.id.show_more);
        hide = findViewById(R.id.hide);
        numProducts = findViewById(R.id.notification);

        // set mặc định cho spinner
        price.setSelection(0);
        priceLevel.setSelection(0);

        intent = getIntent();
        typeShow = intent.getStringExtra("type");
        adapterPhone = new AdapterPhone(filterProducts, this, NUM_SHOW);
        settingShow();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, true));
        recyclerView.setAdapter(adapterPhone);

        // Xử lý sự kiện
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListProductsActivity.this, SearchActivity.class);
                if (typeShow.equals("brand")){
                    it.putExtra("type_search","brand");
                    it.putExtra("listID", intent.getIntegerArrayListExtra("listID"));
                } else if (typeShow.equals("all")) {
                    it.putExtra("type_search","all");
                }
                else {
                    it.putExtra("type_search","search_result");
                    ArrayList<Integer> lID = new ArrayList<>();
                    for(Product item : filterProducts){
                        lID.add(item.getId());
                    }
                    it.putExtra("listID", lID);
                }
                startActivity(it);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newOld.setText("MỚI");
        newOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNewest = !isNewest;
                if (isNewest){
                    newOld.setText("MỚI");
                }
                else {
                    newOld.setText("CŨ");
                }
            }
        });
        price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                priceSelected = "Không chọn";
            }
        });
        priceLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceLevelSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                priceLevelSelected = "Tất cả";
            }
        });
        hide.setVisibility(View.GONE);
        if (NUM_SHOW >= products.size()){
            showMore.setVisibility(View.GONE);
        }
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNumDataShow(NUM_SHOW+20);
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNumDataShow(20);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSortAndFilter();
            }
        });
    }
    public void sortListPhone(String fieldName,boolean increase) {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product item1, Product item2) {
                try {
                    Field field = Product.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Comparable value1 = (Comparable) field.get(item1);
                    Comparable value2 = (Comparable) field.get(item2);
                    return (increase ? 1 : -1) * value1.compareTo(value2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public void doSortAndFilter(){
        sortListPhone("announced", newOld.getText().toString().equals("CŨ"));
        if (!priceSelected.equals("Không chọn")){
            sortListPhone("price", priceSelected.equals("Thấp đến Cao"));
        }
        filterProducts.clear();
        double start = 0.0, end = 500000000.0;
        switch (priceLevelSelected){
            case "Dưới 2 triệu":
                start = 0.0;
                end = 2000000.0;
                break;
            case "2 - 5 triệu":
                start = 2000000.0;
                end = 5000000.0;
                break;
            case "5 - 10 triệu":
                start = 5000000.0;
                end = 10000000.0;
                break;
            case "Trên 10 triệu":
                start = 10000000.0;
                break;
        }
        for (Product item : products){
            double price_item = item.getPrice();
            if (price_item >= start && price_item < end){
                filterProducts.add(item);
            }
        }
        if (filterProducts.size() == 0){
            numProducts.setText("Không tìm thấy sản phẩm");
        }
        else{
            numProducts.setText(Integer.toString(filterProducts.size())+" sản phẩm");
        }
        adapterPhone.notifyDataSetChanged();
    }
    public void updateNumDataShow(int newValue) {
        NUM_SHOW = newValue;
        if (NUM_SHOW >= filterProducts.size()){
            showMore.setVisibility(View.GONE);
        }
        else if (filterProducts.size() > 20){
            showMore.setVisibility(View.VISIBLE);
        }
        if (NUM_SHOW > 20){
            hide.setVisibility(View.VISIBLE);
        }
        else hide.setVisibility(View.GONE);

        adapterPhone.setNumShow(NUM_SHOW);
        adapterPhone.notifyDataSetChanged();
    }
    void settingShow(){
        products.clear();
        switch (typeShow) {
            case "brand":
                ArrayList<Integer> listID = intent.getIntegerArrayListExtra("listID");
                for (Integer id: listID){
                    for (Product item : MainActivity.itemList) {
                        if (item.getId() == id){
                            products.add(item);
                        }
                    }
                }
                break;
            case "all":
                products.addAll(MainActivity.itemList);
                break;
            case "search_result":
                products.addAll(SearchActivity.resultSearch);
        }
        for (Product item: products){
            filterProducts.add(item);
        }
        numProducts.setText(Integer.toString(filterProducts.size())+" sản phẩm");
        adapterPhone.notifyDataSetChanged();
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
