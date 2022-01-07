package com.example.shop.Model;

import java.io.Serializable;

public class AccountModule implements Serializable
{


    private String id;
    private String username;
    private String password;
    private String name;
    private String gmail;
    private String phone;

    public AccountModule(String id, String username, String password, String name, String gmail, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gmail = gmail;
        this.phone = phone;
    }

    public AccountModule() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail ()
    {
        return gmail;
    }

    public void setGmail (String gmail)
    {
        this.gmail = gmail;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gmail = "+gmail+", password = "+password+", phone = "+phone+", id = "+id+", username = "+username+"]";
    }
}