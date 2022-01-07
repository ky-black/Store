package com.example.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.shop.Adapter.CartAdapter;
import com.example.shop.Model.AccountModule;
import com.example.shop.Model.CartModel;
import com.example.shop.R;
import com.example.shop.ultil.MySingleton;
import com.example.shop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    SwipeMenuListView lv_GioHang;
    TextView txt_ThongBao ;
    static EditText et_GiaTriGH;
    Button btn_TiepTucMuaHang,btn_MuaHang;
    Toolbar toolBarGH;
    CartAdapter gioHangAdapter;
//    ArrayList<CartModel> arrayListCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        anhXa();
        ActionToolBar();
        CheckListView();
        EventUltil();
        SwipeMenuCreator();
        EventButton();


    }


    public interface CallBackCart{
        void onResponse();
    }

    private void postCartToServer(int position, CartModel cart, CallBackCart callBackCart) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String requestBody = getJSONObj(cart);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlPostCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 2){
//                    MainActivity.arrayListGioHang.get(position).setId(response);
                }
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

    private String getJSONObj(CartModel cart){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", cart.getId());
            jsonBody.put("id_account", MainActivity.account.getId());
            jsonBody.put("id_product", cart.getId_product());
            jsonBody.put("quantily", cart.getQuantily());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonBody.toString();
    }

    private void EventButton() {
        btn_TiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.arrayListGioHang.size() > 0){
                    for (int i = 0; i < MainActivity.arrayListGioHang.size(); i++){
                        postCartToServer(i,MainActivity.arrayListGioHang.get(i), new CallBackCart() {
                            @Override
                            public void onResponse() {

                            }
                        });

                    }
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn_MuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayListGioHang.size() > 0 ){
                    deteleCartByIDAccount(MainActivity.account.getId().toString(), new CallBack() {
                        @Override
                        public void onResponse(Boolean status) {
                            for (int i = 0; i < MainActivity.arrayListGioHang.size(); i++){
                                postDataOrderToServer(MainActivity.arrayListGioHang.get(i), new CallBack() {
                                    @Override
                                    public void onResponse(Boolean status) {
                                    }
                                });
                            }
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),"Giỏ hàng của bạn đang trống!",Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void postDataOrderToServer(CartModel cart, CallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id_order", null);
            jsonBody.put("id_account", MainActivity.account.getId());
            jsonBody.put("id_product", cart.getId_product());
            jsonBody.put("quantily", cart.getQuantily());
            jsonBody.put("total_money", cart.getPrice());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlPostOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 2){

                }
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

    private void deteleCartByIDAccount(String IDAccount, CallBack callBack) {
        String url = Server.urlDeteleCartByIdAccount + IDAccount;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.DELETE,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 2)
                    callBack.onResponse(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }

    private void SwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lv_GioHang.setMenuCreator(creator);

        lv_GioHang.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deleteCartByID(MainActivity.arrayListGioHang.get(position).getId() + "", new CallBack() {
                            @Override
                            public void onResponse(Boolean status) {
                                MainActivity.arrayListGioHang.remove(position);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                                if (MainActivity.arrayListGioHang.size() <=0) {
                                    txt_ThongBao.setVisibility(View.VISIBLE);
                                }else txt_ThongBao.setVisibility(View.INVISIBLE);
                            }
                        });

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
    public interface CallBack{
        void onResponse(Boolean status);
    }
    private void deleteCartByID(String ID, CallBack callBack) {
        String url = Server.urlDeleteCartById + ID;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.DELETE,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 2)
                    callBack.onResponse(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }

    public static void EventUltil() {
        long tongTien = 0;
        for (int i =0; i < MainActivity.arrayListGioHang.size(); i++){
            tongTien += MainActivity.arrayListGioHang.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        et_GiaTriGH.setText(decimalFormat.format(tongTien)+" Đ");
    }

    private void CheckListView() {
        if (MainActivity.arrayListGioHang.size() <=0){
            gioHangAdapter.notifyDataSetChanged();
            txt_ThongBao.setVisibility(View.VISIBLE);
            lv_GioHang.setVisibility(View.INVISIBLE);
        }else{
            gioHangAdapter.notifyDataSetChanged();
            txt_ThongBao.setVisibility(View.INVISIBLE);
            lv_GioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolBarGH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarGH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayListGioHang.size() > 0){
                    for (int i = 0; i < MainActivity.arrayListGioHang.size(); i++){
                        postCartToServer(i,MainActivity.arrayListGioHang.get(i), new CallBackCart() {
                            @Override
                            public void onResponse() {
                            }
                        });

                    }
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        lv_GioHang = findViewById(R.id.lv_GioHang);
        txt_ThongBao = findViewById(R.id.txt_ThongBao);
        et_GiaTriGH = findViewById(R.id.et_GiaTriGH);
        btn_TiepTucMuaHang = findViewById(R.id.btn_TiepTucMuaHang);
        btn_MuaHang = findViewById(R.id.btn_MuaHang);
        toolBarGH = findViewById(R.id.toolBarGH);
        gioHangAdapter = new CartAdapter(Cart.this, MainActivity.arrayListGioHang);
        lv_GioHang.setAdapter(gioHangAdapter);

    }
}