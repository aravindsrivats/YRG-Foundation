package com.yrg.ecokitchen.cards;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yrg.ecokitchen.R;
import com.yrg.ecokitchen.db.InstitutionsBase;
import com.yrg.ecokitchen.models.Donations;
import com.yrg.ecokitchen.models.Institutions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerDonationsAdapter extends RecyclerView.Adapter<DonationHolder> {

    private List<Donations> donations;
    private InstitutionsBase idb;
    private Context context;
    public RecyclerDonationsAdapter(Context ctx, List<Donations> palettes) {
        context = ctx;
        idb = new InstitutionsBase(context);
        idb.open();
        this.donations = new ArrayList<>();
        this.donations.addAll(palettes);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        idb.close();
    }

    @Override
    public DonationHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.donation_card, viewGroup, false);

        return new DonationHolder(item);
    }

    @Override
    public void onBindViewHolder(DonationHolder cardHolder, int i) {
        Donations donation = donations.get(i);
        Institutions inst = idb.getInstitution(donation.getInstitution());
        cardHolder.name.setText(inst.getName());
        cardHolder.amount.setText("Rs. " + donation.getAmount());
        cardHolder.date.setText(formatDate(donation.getDate()));
        cardHolder.status.setImageResource(R.drawable.status_image);
    }

    private String formatDate(long time) {
        DateFormat sf = new SimpleDateFormat("dd MMM, yyyy");
        return sf.format(new Date(time));
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public void updateList(List<Donations> newlist) {
        donations.clear();
        donations.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
