package com.example.shop.ultil;

public class Server {
    public static String localHost = "192.168.100.175";
    public static String ProductTypeLink = "http://"+localHost+"/server/getloaisp.php";
//    public static String DuongDan = "http://"+localHost+":3000/producttype";
    public static String LatestProductLinks = "http://"+localHost+"/server/getproduct.php";
//    public static String DuongDanSanPhamMoiNhat = "http://"+localHost+":3000/productsnew";

    public static String ProductLink = "http://"+localHost+"/server/getproductbytype.php?page=";
    public static String CustomerInformationLink = "http://"+localHost+"/server/thongtinkhachhang.php";
    public static String OrderDetailsLink = "http://"+localHost+"/server/chitietdonhang.php";

    public static String urlCheckUser = "http://"+localHost+":3000/account";
    public static String urlCheckUserByUserName = "http://"+localHost+":3000/account/";
    public static String urlPostUser = "http://"+localHost+":3000/account/insert";
    public static String urlGetAllProduct = "http://"+localHost+":3000/products";
    public static String urlGetProductType = "http://"+localHost+":3000/product_type";
    public static String urlGetProductByIDType = "http://"+localHost+":3000/products/";
    public static String urlPostCart = "http://"+localHost+":3000/cart";
    public static String urlGetCartByIdAccount = "http://"+localHost+":3000/cart/";
    public static String urlDeteleCartByIdAccount = "http://"+localHost+":3000/cart/";
    public static String urlDeleteCartById = "http://"+localHost+":3000/api/cart/";
    public static String urlPostOrder = "http://"+localHost+":3000/order";



}
