package com.yrg.ecokitchen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yrg.ecokitchen.cards.RecyclerAdapter;
import com.yrg.ecokitchen.db.InstitutionsBase;
import com.yrg.ecokitchen.models.Institutions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Donate extends AppCompatActivity {

    private InstitutionsBase idb;
    private RecyclerAdapter recyclerAdapter;
    private List<Institutions> inslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager linearLM = new LinearLayoutManager(this);
        linearLM.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLM);

        idb = new InstitutionsBase(this);
        idb.open();

        inslist = idb.getInstitutions();

        recyclerAdapter = new RecyclerAdapter(inslist);
        recyclerView.setAdapter(recyclerAdapter);

        if(inslist.size() == 0) {
            fetchInstitutions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        idb.close();
    }

    private void fetchInstitutions() {
        String url = "http://192.168.122.166:8000/api/institutions";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject institution = jsonResponse.getJSONObject(i);
                        Log.d("Institutions", institution.toString());
                        idb.addInstitution(institution.getString("id"), institution.getString("name"), institution.getString("address"), institution.getString("category"), institution.getInt("capacity"));
                        inslist.add(new Institutions(institution.getString("id"), institution.getString("name"), institution.getString("address"), institution.getString("category").split(","), institution.getInt("capacity")));
                    }
                    recyclerAdapter.updateList(inslist);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(getRequest);
    }

}
