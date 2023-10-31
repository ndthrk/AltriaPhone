package com.example.altriaphone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.altriaphone.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

public class ForgotPassActivity extends AppCompatActivity {

    EditText user, email;
    TextView noti;
    Button send;
    boolean tontai = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        user = findViewById(R.id.fg_user);
        email = findViewById(R.id.fg_email);
        noti = findViewById(R.id.fg_not_success);
        send = findViewById(R.id.fg_send);

        send.setOnClickListener(view -> {
        String urlAPI = "https://ndthrk.000webhostapp.com/LTMB/API/users_infomation.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject itemObject = response.getJSONObject(i);
                            String usern =  itemObject.getString("username");
                            String mail = itemObject.getString("mail");
                            if (usern.equals(user.getText().toString())){
                                tontai = true;
                                if (mail.equals(email.getText().toString())){
                                    noti.setText("");
                                    Toast.makeText(ForgotPassActivity.this, "Gửi mật khẩu mới thành công", Toast.LENGTH_SHORT).show();
                                    new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 2000);
                                }
                                else{
                                    noti.setText("Email không chính xác");
                                }
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!tontai){
                        noti.setText("Không tồn tại tài khoản " + user.getText().toString());
                    }
                }, error -> Toast.makeText(ForgotPassActivity.this, "Lỗi khi tải dữ liệu từ API", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(jsonArrayRequest);
        });
    }
    public static void sendEmail(String recipientEmail) {
        return;
//        final String username = "your_email@gmail.com";
//        final String password = "your_password";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject("Mật khẩu mới");
//            String newPassword = generateNewPassword(); // Tạo mật khẩu mới
//            message.setText("Mật khẩu mới của bạn là: " + newPassword);
//
//            Transport.send(message);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }

    public static String generateNewPassword() {
        // Tạo mật khẩu mới ngẫu nhiên
        // Implement logic để tạo mật khẩu mới ở đây
        return "new_password";
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