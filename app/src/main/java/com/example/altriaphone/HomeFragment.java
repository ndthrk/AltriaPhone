package com.example.altriaphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.altriaphone.Activity.ListProductsActivity;
import com.example.altriaphone.Activity.SearchActivity;
import com.example.altriaphone.Adapter.AdapterBrand;
import com.example.altriaphone.Adapter.AdapterPhone;
import com.example.altriaphone.Adapter.GridSpacingItemDecoration;
import com.example.altriaphone.Object.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class HomeFragment extends Fragment implements View.OnClickListener{

    public View root;

    RecyclerView recyclerView;
    Button search;
    TextView tvXemThem, nameUser;
    ViewFlipper viewFlipper;
    LinearLayout samsung, apple, xiaomi, oppo, vivo;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.grid_view);
        search = root.findViewById(R.id.search);
        tvXemThem = root.findViewById(R.id.home_xemthem);
        viewFlipper = root.findViewById(R.id.home_noibat);
        samsung = root.findViewById(R.id.brand_samsung);
        apple = root.findViewById(R.id.brand_apple);
        xiaomi = root.findViewById(R.id.brand_xiaomi);
        oppo = root.findViewById(R.id.brand_oppo);
        vivo = root.findViewById(R.id.brand_vivo);
        nameUser = root.findViewById(R.id.user_name);

        if (MainActivity.user != null){
            nameUser.setText(MainActivity.user.getName());
        }

        viewFlipper.setFlipInterval(3142); // chuyển hình ảnh
        viewFlipper.setInAnimation(getContext(), R.anim.slide_out_left);
        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.startFlipping();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, true));
        recyclerView.setAdapter(MainActivity.itemAdapter);

        // Xử lý sự kiện
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), SearchActivity.class);
                it.putExtra("type_search","all");
                getContext().startActivity(it);
            }
        });
        tvXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListProductsActivity.class);
                intent.putExtra("type", "all");
                getContext().startActivity(intent);
            }
        });
        samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID", MainActivity.brands.get("Samsung"));
                getContext().startActivity(intent);
            }
        });
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID", MainActivity.brands.get("Apple"));
                getContext().startActivity(intent);
            }
        });
        xiaomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID", MainActivity.brands.get("Xiaomi"));
                getContext().startActivity(intent);
            }
        });
        oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID", MainActivity.brands.get("Oppo"));
                getContext().startActivity(intent);
            }
        });
        vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListProductsActivity.class);
                intent.putExtra("type", "brand");
                intent.putExtra("listID", MainActivity.brands.get("vivo"));
                getContext().startActivity(intent);
            }
        });
        return root;
    }
    @Override
    public void onClick(View view) {
    }
     @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}

