package com.yrg.ecokitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.launcher);
        setSupportActionBar(toolbar);

        settings = getSharedPreferences("yrgprefs", Context.MODE_PRIVATE);
        boolean registered = settings.getBoolean("registered", false);
        if (registered) {
            startActivity(new Intent(this, MyDonations.class));
            this.finish();
        }
        setContentView(R.layout.login);
        Button register = (Button) findViewById(R.id.register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name);
                EditText email = (EditText) findViewById(R.id.email);
                EditText phone = (EditText) findViewById(R.id.phone);
                registerDonor(name.getText().toString(), email.getText().toString(), phone.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerDonor(final String name, final String email, final String phone) {
        String url = "http://192.168.122.166:8000/api/donors";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("Register Response", jsonResponse.toString());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("registered", true);
                    editor.putString("donorid", jsonResponse.getString("id"));
                    editor.putString("name", jsonResponse.getString("name"));
                    editor.putString("email", jsonResponse.getString("email"));
                    editor.putString("phone", jsonResponse.getString("phone"));
                    editor.commit();
                    startActivity(new Intent(MainActivity.this, MyDonations.class));
                    MainActivity.this.finish();

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
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
