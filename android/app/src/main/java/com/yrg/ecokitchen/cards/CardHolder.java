package com.yrg.ecokitchen.cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yrg.ecokitchen.R;

public class CardHolder extends RecyclerView.ViewHolder {

    protected ImageView image;
    protected TextView name, address;
    protected CardView card;

    public CardHolder(View item) {
        super(item);
        image = (ImageView) item.findViewById(R.id.institution_image);
        name = (TextView) item.findViewById(R.id.institution_name);
        address = (TextView) item.findViewById(R.id.institution_address);
        card = (CardView) item;
    }

}