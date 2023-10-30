package com.example.altriaphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.altriaphone.Activity.LoginActivity;
import com.example.altriaphone.Object.User;

import java.text.DecimalFormat;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    public View root;
    TextView username, name, money, phone, address;
    ImageView edit, payment, logout;
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        edit = root.findViewById(R.id.edit);
        payment = root.findViewById(R.id.payment);
        logout = root.findViewById(R.id.logout);
        username = root.findViewById(R.id.username);
        name = root.findViewById(R.id.name);
        money = root.findViewById(R.id.money);
        phone = root.findViewById(R.id.phone);
        address = root.findViewById(R.id.address);

        logout.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            CLEAR_ALL();
            getContext().startActivity(intent);
        });
        username.setText(MainActivity.user.getUsername());
        name.setText(MainActivity.user.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        money.setText(decimalFormat.format(MainActivity.user.getMoney()));
        phone.setText(MainActivity.user.getPhone());
        address.setText("Hanoi");
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
    public void CLEAR_ALL(){

    }
}
