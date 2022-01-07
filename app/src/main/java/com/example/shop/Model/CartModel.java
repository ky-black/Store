package com.example.shop.Model;

public class CartModel {
    private String id;
    private int id_product;
    private String product_name;
    private long price;
    private String picture;
    private int quantily;

    public CartModel() {
    }

    public CartModel(String id, int id_product, String product_name, long price, String picture, int quantily) {
        this.id = id;
        this.id_product = id_product;
        this.product_name = product_name;
        this.price = price;
        this.picture = picture;
        this.quantily = quantily;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getQuantily() {
        return quantily;
    }

    public void setQuantily(int quantily) {
        this.quantily = quantily;
    }
}