package com.example.shop.Model;

public class ProductTypeModel {
    public int id;
    public String product_type_name;
    public String picture;

    public ProductTypeModel(int id, String product_type_name, String picture) {
        this.id = id;
        this.product_type_name = product_type_name;
        this.picture = picture;
    }

    public ProductTypeModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_type_name() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name = product_type_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
