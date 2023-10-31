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


import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.User;
import com.example.altriaphone.R;

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
        Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        String ID = "10";
        User u = new User(ID,user.getText().toString(),password.getText().toString(), ten.getText().toString(),"",phone.getText().toString(), 0.0, "");
        bundle.putSerializable("user", u);
        intent.putExtras(bundle);
        startActivity(intent);
        startActivity(intent);
    }
}
