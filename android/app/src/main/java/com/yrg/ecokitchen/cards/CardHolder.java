package com.yrg.ecokitchen.cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yrg.ecokitchen.R;
import com.yrg.ecokitchen.models.Institutions;

public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ImageView image;
    protected TextView name, address;
    protected CardView card;
    protected RecyclerClickListener clicklisten;

    public CardHolder(View item, RecyclerClickListener cl) {
        super(item);
        clicklisten = cl;
        image = (ImageView) item.findViewById(R.id.institution_image);
        name = (TextView) item.findViewById(R.id.institution_name);
        address = (TextView) item.findViewById(R.id.institution_address);
        card = (CardView) item;
        card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clicklisten.recyclerViewListClicked(v, this.getLayoutPosition());

    }
}