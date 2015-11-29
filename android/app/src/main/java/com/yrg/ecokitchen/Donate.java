package com.yrg.ecokitchen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yrg.ecokitchen.cards.RecyclerAdapter;
import com.yrg.ecokitchen.cards.RecyclerClickListener;
import com.yrg.ecokitchen.db.InstitutionsBase;
import com.yrg.ecokitchen.models.Institutions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Donate extends AppCompatActivity implements RecyclerClickListener {

    private InstitutionsBase idb;
    private RecyclerAdapter recyclerAdapter;
    private List<Institutions> inslist;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.launcher);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager linearLM = new LinearLayoutManager(this);
        linearLM.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLM);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        idb = new InstitutionsBase(this);
        idb.open();

        inslist = idb.getInstitutions();

        recyclerAdapter = new RecyclerAdapter(inslist, this);
        recyclerView.setAdapter(recyclerAdapter);

        if(inslist.size() == 0) {
            fetchInstitutions();
        }
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchInstitutions();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
                        idb.addInstitution(institution.getString("id"), institution.getString("name"), institution.getString("address"), institution.getJSONArray("category").getString(0), institution.getInt("capacity"));
                    }
                    inslist = idb.getInstitutions();
                    recyclerAdapter.updateList(inslist);
                    swipeContainer.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                swipeContainer.setRefreshing(false);
            }
        });
        Volley.newRequestQueue(this).add(getRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_skip) {
            Institutions institution = new Institutions();
            Intent i = new Intent(Donate.this, CompleteDonation.class);
            Bundle b = new Bundle();
            b.putSerializable("institution", institution);
            i.putExtra("institution", institution);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void recyclerViewListClicked(View v, int position) {
        Log.d("clicked", position + "");
        Institutions institution = inslist.get(position);
        Intent i = new Intent(Donate.this, CompleteDonation.class);
        Bundle b = new Bundle();
        b.putSerializable("institution", institution);
        i.putExtra("institution", institution);
        startActivity(i);

    }
}
