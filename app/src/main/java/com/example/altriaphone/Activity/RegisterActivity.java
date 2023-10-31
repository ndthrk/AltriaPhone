package com.example.altriaphone.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.User;
import com.example.altriaphone.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText user, password, phone, ten;
    ImageView imageAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.register_user);
        password = findViewById(R.id.register_pass);
        ten = findViewById(R.id.register_ten);
        register = findViewById(R.id.btn_register);
        phone = findViewById(R.id.register_phone);
        imageAdd = findViewById(R.id.cardview_avatar);

        register.setOnClickListener(v -> {
            if (user.getText().toString().equals("") || password.getText().toString().equals("") || ten.getText().toString().equals("") || phone.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Vui lòng thêm đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                DangKi();
            }
        });
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
    private void DangKi() {
        String urlAPI = "https://ndthrk.000webhostapp.com/LTMB/API/users_infomation.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,
                response -> {
                    boolean tontai = false;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject itemObject = response.getJSONObject(i);
                            String usern =  itemObject.getString("username");
                            if (usern.equals(user.getText().toString())){
                                tontai = true;
                                Toast.makeText(RegisterActivity.this, "Tài khoản đã có người đăng ký", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!tontai){
                        Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        User u = new User(response.length(),user.getText().toString(),password.getText().toString(), ten.getText().toString(),"",phone.getText().toString(), 0.0, "");
                        bundle.putSerializable("user", u);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, error -> Toast.makeText(RegisterActivity.this, "Lỗi khi tải dữ liệu từ API", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
