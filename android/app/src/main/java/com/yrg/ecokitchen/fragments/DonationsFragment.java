package com.yrg.ecokitchen.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yrg.ecokitchen.R;
import com.yrg.ecokitchen.cards.RecyclerDonationsAdapter;
import com.yrg.ecokitchen.models.Donations;

import java.util.ArrayList;
import java.util.List;

public class DonationsFragment extends Fragment {
    private List<Donations> donations;

    public DonationsFragment() {
    }

    public static DonationsFragment newInstance(ArrayList<Donations> list) {
        DonationsFragment fragment = new DonationsFragment();
        Bundle args = new Bundle();
        args.putSerializable("donations", list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            donations = new ArrayList<>();
            donations.addAll((ArrayList<Donations>) getArguments().getSerializable("donations"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cv = inflater.inflate(R.layout.fragment_donations, container, false);
        RecyclerView recyclerView = (RecyclerView) cv.findViewById(R.id.recyclerList);
        LinearLayoutManager linearLM = new LinearLayoutManager(getActivity());
        linearLM.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLM);
        recyclerView.setAdapter(new RecyclerDonationsAdapter(getActivity().getApplicationContext(), donations));
        return cv;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
