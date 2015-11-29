package com.yrg.ecokitchen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yrg.ecokitchen.R;
import com.yrg.ecokitchen.cards.RecyclerAdapter;
import com.yrg.ecokitchen.models.Institutions;

import java.util.ArrayList;
import java.util.List;

public class InstitutionsFragment extends Fragment {
    private List<Institutions> institutions;

    public InstitutionsFragment() {
    }
public static InstitutionsFragment newInstance(ArrayList<Institutions> list) {
        InstitutionsFragment fragment = new InstitutionsFragment();
        Bundle args = new Bundle();
        args.putSerializable("institutions", list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            institutions = new ArrayList<>();
            institutions.addAll((ArrayList<Institutions>) getArguments().getSerializable("institutions"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cv = inflater.inflate(R.layout.fragment_institutions, container, false);
        RecyclerView recyclerView = (RecyclerView) cv.findViewById(R.id.recyclerList);
        LinearLayoutManager linearLM = new LinearLayoutManager(getActivity());
        linearLM.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLM);
        recyclerView.setAdapter(new RecyclerAdapter(institutions));
        return cv;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
