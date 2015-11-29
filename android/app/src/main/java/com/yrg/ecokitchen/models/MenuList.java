package com.yrg.ecokitchen.models;

public class MenuList {
    public String title;
    public boolean isGroupHeader = false;

    public MenuList(String title, boolean isheader) {
        this.title = title;
        this.isGroupHeader = isheader;
    }
}
