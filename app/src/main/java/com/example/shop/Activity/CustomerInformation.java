package com.example.shop.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.Model.AccountModule;
import com.example.shop.R;
import com.example.shop.ultil.CheckInfo;
import com.example.shop.ultil.Server;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CustomerInformation extends AppCompatActivity {
    Button btn_accept;
    TextInputLayout til_Name, til_Email,til_Phone;
    AccountModule account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        mapping();
        catchButtonEvent();
    }

    private void getAccount() {
        account.setId(null);
        account.setUsername(getIntent().getExtras().getString("UserName"));
        account.setPassword(getIntent().getExtras().getString("Password"));
        account.setName(til_Name.getEditText().getText().toString().trim());
        account.setGmail(til_Email.getEditText().getText().toString());

        account.setPhone(til_Phone.getEditText().getText().toString());
    }

    private void catchButtonEvent() {
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataInput(til_Name.getEditText().getText().toString().trim(),til_Email.getEditText().getText().toString().trim(),til_Phone.getEditText().getText().toString().trim())){
                    getAccount();
                    putUserToSever(account);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    MainActivity.account = account;
                    startActivity(intent);
                }
            }
        });
    }
    private String getJSONObj(AccountModule account){

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", null);
            jsonBody.put("username", account.getUsername());
            jsonBody.put("password", account.getPassword());
            jsonBody.put("name", account.getName());
            jsonBody.put("gmail", account.getGmail());
            jsonBody.put("phone", account.getPhone());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonBody.toString();
    }

    private boolean checkDataInput(String name, String email, String phone) {
        boolean kt = true;
        if (name.length() <= 0){
            til_Name.setError("Vui lòng nhập tên của bạn!");
            kt = false;
        }else til_Name.setError("");

        if (email.length() <= 0){
            til_Name.setError("Vui lòng nhập tên của bạn!");
            kt = false;
        }else if (email.length() > 0 && !CheckInfo.isValidEmail(email)){
            til_Email.setError("Địa chỉ email không chính xác!");
            kt = false;
        } else til_Email.setError("");

        if (phone.length() <= 0){
            til_Name.setError("Vui lòng nhập tên của bạn!");
            kt = false;
        }else til_Name.setError("");

        return kt;
    }

    private void putUserToSever(AccountModule account) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String requestBody = getJSONObj(account);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlPostUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }

    private void mapping() {
        btn_accept = findViewById(R.id.btn_accept);
        til_Name = findViewById(R.id.til_Name);
        til_Email = findViewById(R.id.til_Email);
        til_Phone = findViewById(R.id.til_Phone);
        account = new AccountModule();
    }
}