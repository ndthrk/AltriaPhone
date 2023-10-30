package com.example.altriaphone;

import static com.example.altriaphone.MainActivity.itemList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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


public class BrandFragment extends Fragment implements View.OnClickListener{

    public View root;
    RecyclerView recyclerView;
    public BrandFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_brands, container, false);
        recyclerView = root.findViewById(R.id.list_view);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(MainActivity.adapterBrand);

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

