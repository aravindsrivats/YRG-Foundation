package com.yrg.ecokitchen.cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yrg.ecokitchen.R;

public class DonationHolder extends RecyclerView.ViewHolder {

    protected ImageView status;
    protected TextView name, amount, date;
    protected CardView card;

    public DonationHolder(View item) {
        super(item);
        status = (ImageView) item.findViewById(R.id.status_image);
        name = (TextView) item.findViewById(R.id.institution_name);
        amount = (TextView) item.findViewById(R.id.donation_amount);
        date = (TextView) item.findViewById(R.id.donation_date);
        card = (CardView) item;
    }

}