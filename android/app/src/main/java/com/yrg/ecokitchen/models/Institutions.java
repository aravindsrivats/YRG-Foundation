package com.yrg.ecokitchen.models;

import java.io.Serializable;

public class Institutions implements Serializable {
    private String id, name, address;
    private String[] category;
    private int capacity;

    public Institutions() {
        id = "_any";
        name = "Any";
        address = "Any Institution";
        category = new String[] {"All Categories"};
        capacity = 1000;
    }

    public Institutions(String a, String b, String c, String[] d, int e) {
        this.id = a;
        this.name = b;
        this.address = c;
        this.category = d;
        this.capacity = e;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public String[] getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }
}

