package com.example.altriaphone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.altriaphone.Adapter.AdapterSearchPhone;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    SearchView search;
    RecyclerView recyclerView;
    AdapterSearchPhone adapter;
    public static ArrayList<Product> resultSearch = new ArrayList<>();
    String typeSearch = "all";
    ImageView back;
    TextView noti;
    Intent iten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView_search);
        noti = findViewById(R.id.notifi);

        iten = getIntent();
        typeSearch = iten.getStringExtra("type_search");
        if (typeSearch.equals("all")){
            search.setQueryHint("Tìm tất cả điện thoại");
        }
        else if (typeSearch.equals("brand")){
            String brandname = "";
            boolean found = false;
            ArrayList<Integer> listID = iten.getIntegerArrayListExtra("listID");
            for (Integer id : listID) {
                for (Product product : MainActivity.itemList) {
                    if (product.getId() == id) {
                        brandname = product.getBrand();
                        found = true;
                        break;
                    }
                }
                if (found){
                    break;
                }
            }
            if (!brandname.equals("")){
                search.setQueryHint("Tìm điện thoại " + brandname);
            }

        } else if (typeSearch.equals("search_result")) {
            search.setQueryHint("Tìm điện thoại nào?");
        }
        noti.setVisibility(View.GONE);

        back.setOnClickListener(view -> finish());
        search.clearFocus();
        search.setIconified(false);
        search.requestFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                if (!resultSearch.isEmpty()){
                    Intent intent = new Intent(SearchActivity.this, ListProductsActivity.class);
                    intent.putExtra("type", "search_result");
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterSearchPhone(resultSearch, this, 10);
        recyclerView.setAdapter(adapter);
    }
    private void filterList(String newText){
        resultSearch.clear();
        noti.setVisibility(View.VISIBLE);
        if (newText.equals("")){
            noti.setVisibility(View.GONE);
        }
        if (typeSearch.equals("all")) {
            for (Product product : MainActivity.itemList) {
                String nameProduct = (product.getBrand() + " " + product.getName()).toLowerCase();
                if (nameProduct.contains(newText.toLowerCase())) {
                    resultSearch.add(product);
                }
            }
        }
        else if (typeSearch.equals("brand") || typeSearch.equals("search_result")) {
            ArrayList<Integer> listID = iten.getIntegerArrayListExtra("listID");
            for (Integer id : listID){
                for (Product product : MainActivity.itemList){
                    if (product.getId() == id) {
                        String nameProduct = (product.getBrand() + " " + product.getName()).toLowerCase();
                        if (nameProduct.contains(newText.toLowerCase())) {
                            resultSearch.add(product);
                        }
                    }
                }
            }
        }
        if (resultSearch.isEmpty()){
            noti.setText("No product found :(");
            adapter.setFilterList(new ArrayList<Product>());
        }
        else{
            noti.setText(String.valueOf(resultSearch.size())+" products found");
            adapter.setFilterList(resultSearch);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        this.getSupportActionBar().hide();
        search = findViewById(R.id.search);
        search.requestFocus();
    }
    @Override
    public void onStop() {
        super.onStop();
        this.getSupportActionBar().show();
        resultSearch.clear();
    }
}