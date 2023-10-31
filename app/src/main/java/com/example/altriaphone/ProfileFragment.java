package com.example.altriaphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.altriaphone.Activity.ListInvoiceActivity;
import com.example.altriaphone.Activity.LoginActivity;
import com.example.altriaphone.Activity.PaymentActivity;

import java.text.DecimalFormat;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    public View root;
    TextView username, name, money, phone, email, address;
    ImageView edit, payment, delivered;
    Button logout, about, changePass, okCPass, noCPass, okCInfo, noCInfo;
    EditText curPass, newPass, cfNewPass,
        eName, ePhone, eEmail, eAddress;
    LinearLayout editLayout, cPassLayout;
    NestedScrollView mainLayout;
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        mapping();
        defaultSetting();

        // EditLayout:
        edit.setOnClickListener(view -> {
            mainLayout.setVisibility(View.GONE);
            cPassLayout.setVisibility(View.GONE);
            editLayout.setVisibility(View.VISIBLE);
        });
        okCInfo.setOnClickListener(view -> {
            if (eName.getText().toString().equals("") || ePhone.getText().toString().equals("") || eEmail.getText().toString().equals("") || eAddress.getText().toString().equals("")){
                Toast.makeText(getContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            }
            else{
                MainActivity.user.setName(eName.getText().toString());
                MainActivity.user.setPhone(ePhone.getText().toString());
                MainActivity.user.setEmail(eEmail.getText().toString());
                MainActivity.user.setAddress(eAddress.getText().toString());
                defaultSetting();
            }
        });
        noCInfo.setOnClickListener(view -> defaultSetting());
        // Change Password Layout:
        changePass.setOnClickListener(view -> {
            mainLayout.setVisibility(View.GONE);
            cPassLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
        });
        okCPass.setOnClickListener(view -> {
            if (!curPass.getText().toString().equals(MainActivity.user.getPassword())){
                showNotification("Mật khẩu hiện tại không chính xác");
            }
            else if (newPass.getText().toString().equals(MainActivity.user.getPassword())){
                showNotification("Vẫn là mật khẩu cũ");
            }
            else if (!newPass.getText().toString().equals(cfNewPass.getText().toString())){
                showNotification("Mật khẩu xác nhận sai!");
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn thay đổi mật khẩu thành "+ newPass.getText().toString()+ " không?");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    MainActivity.user.setPassword(newPass.getText().toString());
                    mainLayout.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);
                    cPassLayout.setVisibility(View.GONE);
                });
                builder.setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss());
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        noCPass.setOnClickListener(view -> defaultSetting());

        // Đăng xuất
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            CLEAR_ALL();
            getContext().startActivity(intent);
        });

        // Thanh toán
        payment.setOnClickListener(view -> startActivity(new Intent(getContext(), PaymentActivity.class)));
        // Đơn hàng đã mua
        delivered.setOnClickListener(view -> startActivity(new Intent(getContext(), ListInvoiceActivity.class)));
        // About
        about.setOnClickListener(view -> showAbout());
        return root;
    }
    void showAbout(){
        String mess = getString(R.string.aboutme);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("About me and App");
        builder.setMessage(mess);
        builder.setNegativeButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void showNotification(String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage(mess);
        builder.setNegativeButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void defaultSetting(){
        mainLayout.setVisibility(View.VISIBLE);
        editLayout.setVisibility(View.GONE);
        cPassLayout.setVisibility(View.GONE);

        username.setText(MainActivity.user.getUsername());
        name.setText(MainActivity.user.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        money.setText(decimalFormat.format(MainActivity.user.getMoney()));
        phone.setText(MainActivity.user.getPhone());
        email.setText(MainActivity.user.getEmail());
        address.setText(MainActivity.user.getAddress());

        eName.setText(MainActivity.user.getName());
    }
    void mapping(){
        edit = root.findViewById(R.id.edit_info);
        payment = root.findViewById(R.id.payment);
        delivered = root.findViewById(R.id.invoice);

        logout = root.findViewById(R.id.log_out);
        about = root.findViewById(R.id.about);
        changePass = root.findViewById(R.id.changePassword);
        okCPass = root.findViewById(R.id.btn_OK_changePass);
        noCPass = root.findViewById(R.id.btn_cancel_changePass);
        okCInfo = root.findViewById(R.id.btn_OK_edit);
        noCInfo = root.findViewById(R.id.btn_cancel_edit);

        cPassLayout = root.findViewById(R.id.changePassLayout);
        editLayout = root.findViewById(R.id.editLayout);
        mainLayout = root.findViewById(R.id.linearLayout);

        username = root.findViewById(R.id.username);
        name = root.findViewById(R.id.name);
        money = root.findViewById(R.id.money);
        phone = root.findViewById(R.id.phone);
        email = root.findViewById(R.id.email);
        address = root.findViewById(R.id.address);

        curPass = root.findViewById(R.id.ped_current_password);
        newPass = root.findViewById(R.id.ped_newPass);
        cfNewPass = root.findViewById(R.id.pred_confirm_newPass);

        eName = root.findViewById(R.id.ped_name);
        ePhone = root.findViewById(R.id.ped_phone);
        eEmail = root.findViewById(R.id.ped_email);
        eAddress = root.findViewById(R.id.ped_address);
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
