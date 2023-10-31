package com.example.altriaphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.altriaphone.Adapter.AdapterBrand;
import com.example.altriaphone.Adapter.AdapterPhone;
import com.example.altriaphone.Adapter.AdapterPhoneCart;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.Object.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private boolean doubleClick = false;
     public static ArrayList<Product> itemList = new ArrayList<>(),
                                    listCart = new ArrayList<>(),
                                    listDelivered = new ArrayList<>();
    public static ArrayList<String> arrBrand, name_invoice = new ArrayList<>(),
                                            phone_invoice = new ArrayList<>(),
                                            add_invoice = new ArrayList<>();
    public static ArrayList<Integer> counts = new ArrayList<>(),
                                    count_Delivered = new ArrayList<>(),
                                    id_invoice = new ArrayList<>();
    public static Map<String, ArrayList<Integer>> brands = new HashMap<>();
    public static AdapterPhone itemAdapter;
    public static AdapterBrand adapterBrand;
    public BottomNavigationView bottomNav;
    ProgressBar progressBar;
    int newPosition, startingPosition=-1;
    Fragment fragment;
    public static User user = new User();
    public static int lastIDInvoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
        }

        String [] arr = getResources().getStringArray(R.array.phone_brands);
        arrBrand = new ArrayList<>(Arrays.asList(arr));
        bottomNav = findViewById(R.id.bottom_nav);
        progressBar = findViewById(R.id.progressBar);

        itemAdapter = new AdapterPhone(MainActivity.itemList, this, 10);
        adapterBrand = new AdapterBrand(MainActivity.brands, arrBrand,this);

        if (itemList.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            LOAD_ALL_DATA(() -> {
                progressBar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new HomeFragment())
                        .commit();
            });
        }
        else{
            LOAD_CART();
            LOAD_INVOICE();
            getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new HomeFragment())
                        .commit();
        }

        bottomNav.setOnItemSelectedListener(item ->  {
            fragment = null;
            newPosition = 0;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_trangchu) {
                fragment = new HomeFragment();
                newPosition = 1;
            } else if (itemId == R.id.nav_danhmuc) {
                fragment = new BrandFragment();
                newPosition = 2;
            } else if (itemId == R.id.nav_giohang) {
                fragment = new CartFrangment();
                newPosition = 3;
            } else if (itemId == R.id.nav_nguoidung) {
                fragment = new ProfileFragment();
                newPosition = 4;
            }
            return loadFragment();
        });
    }

    void doLoadFragment(){
        if (startingPosition > newPosition) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();
            }
            if (startingPosition < newPosition){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_out_right, R.anim.slide_in_left)
                        .replace(R.id.nav_host_fragment, fragment)
                        .commit();
            }
            startingPosition = newPosition;
    }
    private boolean loadFragment() {
        if (fragment != null) {
            doLoadFragment();
            return true;
        }
        return false;
    }
    public void setMainFragment(String pos){
        if (pos.equals("1")) {
            fragment = new HomeFragment();
            newPosition = 1;
        } else if (pos.equals("2")) {
            fragment = new BrandFragment();
            newPosition = 2;
        } else if (pos.equals("3")) {
            fragment = new CartFrangment();
            newPosition = 3;
        } else if (pos.equals("4")) {
            fragment = new ProfileFragment();
            newPosition = 4;
        }
        doLoadFragment();
    }
    public static void updateNumDataShow(int newValue) {
        itemAdapter.setNumShow(newValue);
        itemAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        if (doubleClick)
            finish();
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        doubleClick = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
            }
        }, 2000);
    }
    void LOAD_ALL_DATA(Runnable callback) {
        LOAD_PHONE(() -> {
            LOAD_CART();
            LOAD_INVOICE();
            callback.run();
        });
    }

    void LOAD_INVOICE(){
        String urlAPI = "https://ndthrk.000webhostapp.com/LTMB/API/invoices.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,
            response -> {
            listDelivered.clear();
            count_Delivered.clear();
            id_invoice.clear();
            name_invoice.clear();
            phone_invoice.clear();
            add_invoice.clear();
            lastIDInvoice = response.length();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject itemObject = response.getJSONObject(i);
                        int id_user = itemObject.getInt("ID_user");
                        if (id_user == user.getID()){
                            int id_phone = itemObject.getInt("ID_phone");
                            int quantity = itemObject.getInt("quantity");
                            int id = itemObject.getInt("ID");
                            String name = itemObject.getString("name"),
                                    phone = itemObject.getString("phone"),
                                    addr = itemObject.getString("address");
                            for (Product product : itemList){
                                if (product.getId() == id_phone){
                                    listDelivered.add(product);
                                    count_Delivered.add(quantity);
                                    id_invoice.add(id);
                                    name_invoice.add(name);
                                    phone_invoice.add(phone);
                                    add_invoice.add(addr);
                                    break;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> Toast.makeText(MainActivity.this, "Lỗi khi tải dữ liệu từ API", Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(MainActivity.this).add(jsonArrayRequest);
    }
    void LOAD_CART(){
        String urlAPI = "https://ndthrk.000webhostapp.com/LTMB/API/carts.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,
            response -> {
                listCart.clear();
                counts.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject itemObject = response.getJSONObject(i);
                        int id_user = itemObject.getInt("ID_user");
                        if (id_user == user.getID()){
                            int id_phone = itemObject.getInt("ID_phone");
                            int quantity = itemObject.getInt("quantity");
                            for (Product product : itemList){
                                if (product.getId() == id_phone){
                                    listCart.add(product);
                                    counts.add(quantity);
                                    break;
                                }
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> Toast.makeText(MainActivity.this, "Lỗi khi tải dữ liệu từ API", Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(MainActivity.this).add(jsonArrayRequest);
    }
    void LOAD_PHONE(Runnable callback){

            String urlAPI = "https://ndthrk.000webhostapp.com/LTMB/API/list_phones.json";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,
                    response -> {
                        callback.run();
                        itemList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject itemObject = response.getJSONObject(i);

                                int id = itemObject.getInt("id");
                                String name = itemObject.getString("name");
                                String brand = itemObject.getString("brand");
                                String announced = itemObject.getString("announced");
                                String resolution = itemObject.getString("resolution");
                                String os = itemObject.getString("os");
                                String chipset = itemObject.getString("chipset");
                                String card_slot = itemObject.getString("card_slot");
                                String rom = itemObject.getString("rom");
                                String ram = itemObject.getString("ram");
                                int num_of_mc = itemObject.getInt("num_of_mc");
                                String pic_mc = itemObject.getString("pic_mc");
                                int num_of_sc = itemObject.getInt("num_of_sc");
                                String pic_sc = itemObject.getString("pic_sc");
                                String jack = itemObject.getString("jack");
                                String battery = itemObject.getString("battery");
                                double size = itemObject.getDouble("size");
                                double price = itemObject.getDouble("price");
                                double screenarea = itemObject.getDouble("screenarea");
                                int ppi = itemObject.getInt("ppi");
                                String link = itemObject.getString("link");
                                String img = itemObject.getString("img");

                                Product item = new Product(id,name,brand,announced,size,resolution,os,chipset,card_slot,rom,ram,num_of_mc,pic_mc,num_of_sc, pic_sc,jack,battery,price,screenarea,ppi,link,img);
                                itemList.add(item);

                                brands.putIfAbsent(brand, new ArrayList<>());
                                brands.get(brand).add(id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        sortListPhone("announced", false);
                        itemAdapter.notifyDataSetChanged();
                        adapterBrand.notifyDataSetChanged();
                    }, error -> Toast.makeText(MainActivity.this, "Lỗi khi tải dữ liệu từ API", Toast.LENGTH_SHORT).show());
            Volley.newRequestQueue(MainActivity.this).add(jsonArrayRequest);
    }

    public void sortListPhone(final String fieldName, final boolean increase) {
        Collections.sort(itemList, (item1, item2) -> {
            try {
                Field field = Product.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Comparable value1 = (Comparable) field.get(item1);
                Comparable value2 = (Comparable) field.get(item2);
                return (increase ? 1 : -1) * value1.compareTo(value2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        itemAdapter.notifyDataSetChanged();
    }

}