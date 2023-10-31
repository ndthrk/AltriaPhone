package com.example.altriaphone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.User;
import com.example.altriaphone.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextView forgot, register, wrong;
    Button login;
    EditText user, password;
    String usernameText, passwordText;
    private boolean doubleClick = false, success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        success = false;

        user = findViewById(R.id.login_user);
        password = findViewById(R.id.login_pass);
        register = findViewById(R.id.text_register);
        login = findViewById(R.id.btn_login);
        forgot = findViewById(R.id.forgot_password);
        wrong = findViewById(R.id.not_success);

        register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        login.setOnClickListener(v -> {
            usernameText = user.getText().toString().trim();
            passwordText =password.getText().toString().trim();
            DangNhap();
        });
    }

    private void DangNhap() {
        String urlAPI = "https://ndthrk.000webhostapp.com/LTMB/API/users_infomation.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject itemObject = response.getJSONObject(i);
                            String id = itemObject.getString("ID");
                            String usern =  itemObject.getString("username");
                            String pass =  itemObject.getString("password");
                            String name =  itemObject.getString("name");
                            double money =  itemObject.getDouble("money");
                            String phone =  itemObject.getString("phonenumber");
                            String mail = itemObject.getString("mail");
                            String addr = itemObject.getString("address");

                            if (usern.equals(usernameText) && pass.equals(passwordText)) {
                                success = true;
                                wrong.setText("");
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                User user = new User(id, usern, pass, name,mail, phone, money, addr);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!success){
                        wrong.setText("Thông tin tài khoản hoặc mật khẩu không chính xác!");
                    }
                }
            }, error -> Toast.makeText(LoginActivity.this, "Lỗi khi tải dữ liệu từ API", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleClick) {
            super.onBackPressed();
            finishAffinity(); // Đóng tất cả các Activity trong ứng dụng
            System.exit(0); // Kết thúc quá trình ứng dụng
        } else {
            Toast.makeText(this, "Nhấn Back một lần nữa để thoát", Toast.LENGTH_SHORT).show();
            doubleClick = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleClick = false;
                }
            }, 1500);
        }
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

