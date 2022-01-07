package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.Model.AccountModule;
import com.example.shop.R;
import com.example.shop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {
    Button btn_Login;
    EditText et_UserName, et_Password;
    TextView txt_forgotPassword, txt_gotoRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        catchOnEvent();
    }
    private void catchOnEvent() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccount(new CallBackAccount() {

                    @Override
                    public void onResponse(AccountModule account) {
                        if (account != null){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            MainActivity.account = account;
                            startActivity(intent);
                        }else et_Password.setError("Tài Khoản hoặc mật khẩu không chính xác!");
                    }
                });

            }
        });
        txt_gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }

    public interface CallBackAccount{
        void onResponse(AccountModule account);
    }

    private void checkAccount(CallBackAccount callBackAccount) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Cast to json
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", et_UserName.getText().toString().trim());
            jsonBody.put("password", et_Password.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();


        //Create req

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlCheckUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AccountModule tmp = null;
                if (response.length() > 2)
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObjectAccount = jsonArray.getJSONObject(0);
                        tmp = new AccountModule(jsonObjectAccount.getString("id"),
                                jsonObjectAccount.getString("username"),
                                jsonObjectAccount.getString("password"),
                                jsonObjectAccount.getString("name"),
                                jsonObjectAccount.getString("gmail"),
                                jsonObjectAccount.getString("phone"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                callBackAccount.onResponse(tmp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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

        btn_Login = findViewById(R.id.btn_Login);
        et_UserName = findViewById(R.id.et_UserName);
        et_Password = findViewById(R.id.et_Password);
        txt_forgotPassword = findViewById(R.id.txt_forgotPassword);
        txt_gotoRegister = findViewById(R.id.txt_gotoRegister);

    }
}