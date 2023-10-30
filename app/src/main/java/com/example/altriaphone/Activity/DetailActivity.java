package com.example.altriaphone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.altriaphone.CartFrangment;
import com.example.altriaphone.MainActivity;
import com.example.altriaphone.Object.Product;
import com.example.altriaphone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    Product product;
    TextView size, resolution, picMc, picSc, ramRom, chip, battery, os, name, price,
            d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12;
    ImageView image, back, share;
    Button addCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        size = findViewById(R.id.size);
        resolution = findViewById(R.id.resolution);
        picMc = findViewById(R.id.pic_mc);
        picSc = findViewById(R.id.pic_sc);
        ramRom = findViewById(R.id.ram_rom);
        chip = findViewById(R.id.chipset);
        battery = findViewById(R.id.battery);
        os = findViewById(R.id.os);
        name = findViewById(R.id.name_product);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        addCart = findViewById(R.id.add_to_cart);
        back = findViewById(R.id.img_back);
        share = findViewById(R.id.img_share);
        d1 = findViewById(R.id.dt_brand);
        d2 = findViewById(R.id.dt_an);
        d3 = findViewById(R.id.dt_size);
        d4 = findViewById(R.id.dt_res);
        d5 = findViewById(R.id.dt_os);
        d6 = findViewById(R.id.dt_chip);
        d7 = findViewById(R.id.dt_rr);
        d8 = findViewById(R.id.dt_mc);
        d9 = findViewById(R.id.dt_sc);
        d10 = findViewById(R.id.dt_cs);
        d11 = findViewById(R.id.dt_j);
        d12 = findViewById(R.id.dt_battery);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product = (Product) extras.getSerializable("product");
            setAll();
        }
        back.setOnClickListener(view -> finish());
        share.setOnClickListener(view -> shareProductLink());
        addCart.setOnClickListener(view -> {
            doAddCart();
            Toast.makeText(DetailActivity.this, "Đã thêm vào giỏ hàng ✅" , Toast.LENGTH_SHORT).show();
        });
    }
    void doAddCart(){
        boolean isNew = true;
        for (int i = 0; i < MainActivity.listCart.size(); i++){
            if (MainActivity.listCart.get(i).getId() == product.getId()){
                MainActivity.counts.set(i, MainActivity.counts.get(i) + 1);
                isNew = false;
                break;
            }
        }
        if (isNew) {
            MainActivity.listCart.add(product);
            MainActivity.counts.add(1);
        }
    }
    private void shareProductLink() {
        String productLink = product.getLink();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, productLink);

        PackageManager pm = getPackageManager();
        if (shareIntent.resolveActivity(pm) != null) {
            shareIntent.setPackage("com.facebook.katana");
            startActivity(shareIntent);
        } else {
            Toast.makeText(this, "Ứng dụng Facebook không được cài đặt.", Toast.LENGTH_SHORT).show();
        }
    }
    void setAll(){
        Picasso.get().load(product.getImg()).into(image);
        String name_ = product.getName(),
                brand_ = product.getBrand(),
                os_ = product.getOs(),
                resolution_ = product.getResolution() + " pixels",
                chip_ = product.getChipset(),
                card_slot = product.getCard_slot(),
                ramRom_ = product.getRam()+" "+product.getRom(),
                nMc = String.valueOf(product.getNum_of_mc()) + " camera",
                nSc = String.valueOf(product.getNum_of_sc()) + " camera",
                picMc_ = product.getPic_mc() + " MP",
                picSc_ = product.getPic_sc() + " MP",
                jack = product.getJack(),
                size_ = String.valueOf(product.getSize()) + " inches",
                battery_ = product.getBattery() + "mAh",
                area =String.valueOf(product.getScreenarea()) + " cm²",
                ppi = String.valueOf(product.getPpi()) + " ppi";
        Date date = product.getAnnounced();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
        String announced_ = format.format(date);

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        price.setText(decimalFormat.format(product.getPrice()));
        if (!brand_.equals("Apple")){
            name_ = brand_ + " " + name_;
        }
        size.setText(size_);
        resolution.setText(resolution_);
        picMc.setText(picMc_);
        picSc.setText(picSc_);
        ramRom.setText(ramRom_);
        chip.setText(chip_);
        battery.setText(battery_);
        os.setText(os_);
        name.setText(name_);

        d1.setText(brand_);
        d2.setText(announced_);
        d3.setText(size_ +", "+area);
        d4.setText(resolution_ + ", " + ppi);
        d5.setText(os_);
        d6.setText(chip_);
        d7.setText(ramRom_);
        d8.setText(nMc+", "+picMc_);
        d9.setText(nSc+", "+picSc_);
        d10.setText(card_slot);
        d11.setText(jack);
        d12.setText(battery_);
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