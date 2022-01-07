package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.R;
import com.example.shop.ultil.MySingleton;
import com.example.shop.ultil.Server;

public class Register extends AppCompatActivity {
    Button btn_SignUp;
    EditText et_UserName,et_Password, et_Re_enterPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mapping();
        catchOnEvent();
    }
    private boolean checkInformation() {
        boolean tmp = true;

        if (!et_Password.getText().toString().trim().equals(et_Re_enterPassword.getText().toString().trim())){
            et_Re_enterPassword.setError("Mật khẩu không khớm");
            et_Re_enterPassword.setText("");
            tmp = false;
        }
        return tmp;
    }

    private void catchOnEvent() {
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAccount(new CallBackAccount() {
                    @Override
                    public void onResponse(Boolean status) {

                        if (!status){
                            et_UserName.setError("Tên đăng nhập đã tồn tại");
                            et_UserName.setText("");
                        }

                        if (status && checkInformation()){
                            Intent intent = new Intent(getApplicationContext(), CustomerInformation.class);
                            intent.putExtra("UserName", et_UserName.getText().toString().trim());
                            intent.putExtra("Password", et_Password.getText().toString().trim());
                            startActivity(intent);
                        }


                    }
                });

            }
        });
    }
    public interface CallBackAccount{
        void onResponse(Boolean status);
    }
    private void CheckAccount(CallBackAccount callBackAccount) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Server.urlCheckUserByUserName + et_UserName.getText().toString().trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBackAccount.onResponse(response.length() > 2 ? false : true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Loi gi " + error, Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void mapping() {
        btn_SignUp = findViewById(R.id.btn_SignUp);
        et_UserName = findViewById(R.id.et_UserName);
        et_Password = findViewById(R.id.et_Password);
        et_Re_enterPassword = findViewById(R.id.et_Re_enterPassword);

    }
}