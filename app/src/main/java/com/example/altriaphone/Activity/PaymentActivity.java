package com.example.altriaphone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.altriaphone.MainActivity;
import com.example.altriaphone.R;

public class PaymentActivity extends AppCompatActivity {

    TextView content;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        back = findViewById(R.id.pm_back);
        content = findViewById(R.id.pm_noiDung);

        back.setOnClickListener(view -> finish());
        content.setText("NAPTIEN_"+ MainActivity.user.getUsername());
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