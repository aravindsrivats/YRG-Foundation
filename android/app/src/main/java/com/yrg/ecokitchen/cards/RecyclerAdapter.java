package com.yrg.ecokitchen.cards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yrg.ecokitchen.R;
import com.yrg.ecokitchen.models.Institutions;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<CardHolder> {

    private List<Institutions> institutions;
    private RecyclerClickListener listener;

    public RecyclerAdapter(List<Institutions> palettes, RecyclerClickListener cl) {
        this.listener = cl;
        this.institutions = new ArrayList<>();
        this.institutions.addAll(palettes);
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.institution_card, viewGroup, false);

        return new CardHolder(item, listener);
    }

    @Override
    public void onBindViewHolder(CardHolder cardHolder, int i) {
        Institutions institution = institutions.get(i);
        cardHolder.name.setText(institution.getName());
        cardHolder.address.setText(institution.getAddress());
        cardHolder.image.setImageResource(R.drawable.card_default);
    }

    @Override
    public int getItemCount() {
        return institutions.size();
    }

    public void updateList(List<Institutions> newlist) {
        institutions.clear();
        institutions.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
