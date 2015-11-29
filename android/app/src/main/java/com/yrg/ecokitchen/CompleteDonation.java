package com.yrg.ecokitchen;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yrg.ecokitchen.db.DonationsBase;
import com.yrg.ecokitchen.models.Donations;
import com.yrg.ecokitchen.models.Institutions;
import com.yrg.ecokitchen.view.MultiSelectionSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CompleteDonation extends AppCompatActivity {

    EditText pickdate, amount;
    Donations donation;
    Institutions inst;
    SharedPreferences settings;
    MultiSelectionSpinner slot, addons, category;
    CardView card;
    DonationsBase dbd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_donation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.launcher);
        setSupportActionBar(toolbar);

        settings = getSharedPreferences("yrgprefs", Context.MODE_PRIVATE);
        donation = new Donations();

        dbd = new DonationsBase(this);
        dbd.open();

        card = (CardView) findViewById(R.id.selected_institution);

        pickdate = (EditText) findViewById(R.id.pickDate);
        amount = (EditText) findViewById(R.id.amount);

        inst = (Institutions) getIntent().getSerializableExtra("institution");
        donation.setInstitution(inst.getId());

        ImageView cover = (ImageView) card.findViewById(R.id.institution_image);
        TextView name = (TextView) card.findViewById(R.id.institution_name);
        TextView address = (TextView)  card.findViewById(R.id.institution_address);

        name.setText(inst.getName());
        address.setText(inst.getAddress());
        cover.setImageResource(R.drawable.card_default);
        slot = (MultiSelectionSpinner) findViewById(R.id.slot_spin);
        addons = (MultiSelectionSpinner) findViewById(R.id.addon_spin);
        category = (MultiSelectionSpinner) findViewById(R.id.category_spin);

        fetchMeals();
        fetchAddons();
        String[] cat = inst.getCategory();
        category.setItems(cat);

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerFragment().show(getFragmentManager(), "Pick a Date");
            }
        });

        Button add = (Button) findViewById(R.id.setup_donate);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDonation();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbd.close();
    }

    public void updateDate(int year, int month, int date) throws Exception{
        String str_date = date + "-" + month + "-" + year;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateObj = (Date) formatter.parse(str_date);
        donation.setDate(dateObj.getTime());
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            EditText date = (EditText) getActivity().findViewById(R.id.pickDate);
            date.setText(day + "/" + (month+1) + "/" + year);
            try {
                ((CompleteDonation) getActivity()).updateDate(year, month, day);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchMeals() {
        String url = "http://192.168.122.166:8000/api/meals";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json = new JSONArray(response);
                            if(json.length() == 0) {
                                addons.setVisibility(View.INVISIBLE);
                            }
                            else {
                                String[] slots = new String[json.length()];
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject meal = json.getJSONObject(i);
                                    JSONArray content = meal.getJSONArray("contents");
                                    slots[i] = meal.getString("name") + " (" + content.getString(0) + ")";
                                }
                                slot.setItems(slots);
                            }
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

    private void fetchAddons() {
        String url = "http://192.168.122.166:8000/api/addons";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Addons", response);
                            JSONArray json = new JSONArray(response);
                            if(json.length() == 0) {
                                addons.setVisibility(View.INVISIBLE);
                            }
                            else {
                                String[] slots = new String[json.length()];
                                for (int i = 0; i < json.length(); i++) {
                                    slots[i] = json.getJSONObject(i).getString("name");
                                }
                                addons.setItems(slots);
                            }
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

    private void addDonation() {
        String url = "http://192.168.122.166:8000/api/donations";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.d("Donation Response", json.toString());
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            Date dateObj = new Date();
                            try {
                                dateObj = formatter.parse(json.getString("date"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dbd.addDonation(json.getString("id"), json.getString("institution"), dateObj.getTime(), json.getString("slot"), json.getString("category"), json.getInt("amount"), json.getBoolean("present"));
                            Toast.makeText(CompleteDonation.this, "Thanks for donating to our cause", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CompleteDonation.this, MyDonations.class));
                            CompleteDonation.this.finish();
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
                params.put("donorid", settings.getString("donorid", ""));
                params.put("institution", inst.getId());
                params.put("date", donation.getDate()+"");
                params.put("slot", slot.getSelectedItemsAsString());
                params.put("category", category.getSelectedItemsAsString());
                params.put("addons", addons.getSelectedItemsAsString());
                params.put("amount", amount.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
