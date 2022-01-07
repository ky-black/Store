package com.example.shop.Model;

import java.io.Serializable;

public class ProductModel implements Serializable
{
    private String id;
    private String product_name;
    private String price;
    private String picture;
    private String description;
    private String id_product_type;


    public ProductModel(String id, String product_name, String price, String picture, String description, String id_product_type) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.id_product_type = id_product_type;
    }

    public ProductModel() {
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getId_product_type ()
    {
        return id_product_type;
    }

    public void setId_product_type (String id_product_type)
    {
        this.id_product_type = id_product_type;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getPicture ()
    {
        return picture;
    }

    public void setPicture (String picture)
    {
        this.picture = picture;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [price = "+price+", id_product_type = "+id_product_type+", description = "+description+", id = "+id+", product_name = "+product_name+", picture = "+picture+"]";
    }
}