package com.example.altriaphone;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altriaphone.Activity.ListProductsActivity;
import com.example.altriaphone.Activity.PaymentActivity;
import com.example.altriaphone.Adapter.AdapterPhoneCart;
import com.example.altriaphone.Object.Product;

import java.util.ArrayList;

public class CartFrangment extends Fragment implements View.OnClickListener{
    public View root;
    Button order, confirm, cancel;
    ImageView editInfo;
    LinearLayout layoutEdit;
    EditText editName, editPhone, editAdd;
    RecyclerView recyclerView;
    AdapterPhoneCart adapterPhoneCart;
    ArrayList<Product> listCart = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();
    TextView infoNamePhone, address;
    public static TextView sub, total, discount;
    LinearLayout summary;
    @Override
    public void onClick(View view) {
    }
    public CartFrangment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_cart, container, false);
        infoNamePhone = root.findViewById(R.id.cart_namePhone);
        address = root.findViewById(R.id.cart_address);
        sub = root.findViewById(R.id.cart_subtotal);
        total = root.findViewById(R.id.cart_total);
        discount = root.findViewById(R.id.cart_discount);
        order = root.findViewById(R.id.cart_order);
        recyclerView = root.findViewById(R.id.cart_rv);
        summary = root.findViewById(R.id.cart_summary);
        editInfo = root.findViewById(R.id.card_edit);
        layoutEdit = root.findViewById(R.id.layout_edit);
        editName = root.findViewById(R.id.edit_name);
        editPhone = root.findViewById(R.id.edit_phone);
        editAdd = root.findViewById(R.id.edit_address);
        confirm = root.findViewById(R.id.btn_OK);
        cancel = root.findViewById(R.id.btn_cancel);

        layoutEdit.setVisibility(View.GONE);
        infoNamePhone.setText(MainActivity.user.getName() + " | " + MainActivity.user.getPhone());
        address.setText(MainActivity.user.getAddress());

        editInfo.setOnClickListener(view -> {
            layoutEdit.setVisibility(View.VISIBLE);
            editName.setText(MainActivity.user.getName());
            editPhone.setText(MainActivity.user.getPhone());
        } );
        confirm.setOnClickListener(view -> {
            String name = editName.getText().toString(),
                phone = editPhone.getText().toString(),
                addr = editAdd.getText().toString();
            if (name.equals("") || phone.equals("") ||addr.equals("")){
                Toast.makeText(getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
            }
            else {
                infoNamePhone.setText(name+" | " + phone);
                address.setText(addr);
                layoutEdit.setVisibility(View.GONE);
            }
        });
        cancel.setOnClickListener(view -> layoutEdit.setVisibility(View.GONE));
        order.setOnClickListener(view -> {
            String namephone = infoNamePhone.getText().toString().replace(" | ",""),
                addr = address.getText().toString();
            double totalPay = 0;
            try {
                totalPay = Double.valueOf(total.getText().toString().replace(".", ""));
            } catch (Exception e){
                totalPay = Double.valueOf(total.getText().toString().replace(",", ""));
            }
            if (namephone.equals("") ||addr.equals("")){
                Toast.makeText(getContext(), "Không được để trống thông tin!", Toast.LENGTH_SHORT).show();
            }
            else if (totalPay == 0) {
                Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
            } else{
                doOrder();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPhoneCart = new AdapterPhoneCart(listCart, counts, getContext());
        LOAD_CART();
        recyclerView.setAdapter(adapterPhoneCart);

        return root;
    }
    void doOrder(){

        double totalPay = 0;
        try {
            totalPay = Double.valueOf(total.getText().toString().replace(",", ""));
        } catch (Exception e){
            totalPay = Double.valueOf(total.getText().toString().replace(".", ""));
        }
        double money = MainActivity.user.getMoney();
        if (money < totalPay){
            showNeedPayment();
        }
        else{
            Thanhtoan(money, totalPay);
        }
    }
    void Thanhtoan(double money, double totalPay){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn chắc chắn mua không?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            for (int i = 0; i < MainActivity.listCart.size(); i++){
                MainActivity.listDelivered.add(MainActivity.listCart.get(i));
                MainActivity.count_Delivered.add(MainActivity.counts.get(i));
                MainActivity.add_invoice.add(address.getText().toString());
                MainActivity.id_invoice.add(MainActivity.lastIDInvoice);
                String[] splitS = infoNamePhone.getText().toString().split(" \\| ");
                MainActivity.name_invoice.add(splitS[0]);
                MainActivity.phone_invoice.add(splitS[1]);

                MainActivity.lastIDInvoice += 1;
                MainActivity.counts.set(i, 0);
            }
            MainActivity.user.setMoney(money - totalPay);
            showOrderSuccess();
        });
        builder.setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void showOrderSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Thanh toán thành công");
        builder.setNegativeButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showNeedPayment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn cần nạp thêm tiền.");
        builder.setPositiveButton("OK", (dialog, which) -> {
            startActivity(new Intent(getContext(), PaymentActivity.class));
            dialog.dismiss();
        });
        builder.setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void LOAD_CART(){
        for (int i = 0; i < MainActivity.listCart.size(); i++){
            listCart.add(MainActivity.listCart.get(i));
            counts.add(MainActivity.counts.get(i));
        }
        adapterPhoneCart.notifyDataSetChanged();
    }
    void updateCart(){
        adapterPhoneCart.notifyDataSetChanged();
        if (MainActivity.listCart.isEmpty()){
            summary.setVisibility(View.GONE);
        }
        else{
            summary.setVisibility(View.VISIBLE);
        }
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
