package com.yrg.ecokitchen.models;

import java.io.Serializable;

public class Donations implements Serializable {
    private String id, institution;
    private String[] slot, category;
    private int amount;
    private long date;
    private boolean present;

    public Donations() {
        id = institution = "";
        slot = category = new String[] {};
        amount = 0;
        date = 0;
        present = false;
    }

    public Donations(String a, String b, long c, String[] d, String[] e, int f, int g) {
        this.id = a;
        this.institution = b;
        this.date = c;
        this.slot = d;
        this.category = e;
        this.amount = f;
        this.present = (g == 1) ? true : false;
    }

    public boolean isPresent() {
        return present;
    }

    public int getAmount() {
        return amount;
    }

    public long getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getInstitution() {
        return institution;
    }

    public String[] getCategory() {
        return category;
    }

    public String[] getSlot() {
        return slot;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public void setSlot(String[] slot) {
        this.slot = slot;
    }
}
