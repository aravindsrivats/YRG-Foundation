package com.yrg.ecokitchen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.yrg.ecokitchen.db.DonationsBase;
import com.yrg.ecokitchen.fragments.ContentFragment;
import com.yrg.ecokitchen.fragments.DonationsFragment;
import com.yrg.ecokitchen.fragments.EmptyFragment;
import com.yrg.ecokitchen.models.Donations;
import com.yrg.ecokitchen.models.MenuList;

import java.util.ArrayList;
import java.util.List;

public class MyDonations extends AppCompatActivity {
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private MenuAdapter lists;
    private FrameLayout mainLayout;
    private ActionBarDrawerToggle drawlerToggle;
    private DonationsBase dbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.launcher);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dbd = new DonationsBase(this);
        dbd.open();

        drawerList = (ListView)findViewById(R.id.navList);
        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        lists = new MenuAdapter(this, getMenuList());
        drawerList.setAdapter(lists);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuList item = (MenuList) parent.getAdapter().getItem(position);
                updateFragment(item.title);
                drawerLayout.closeDrawer(drawerList);
            }
        });
        setupDrawer();

        updateFragment("My Donations");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyDonations.this, Donate.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbd.close();
    }

    private void setupDrawer() {
        drawlerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        drawlerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawlerToggle);
    }

    public void updateFragment(String item) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment frag = new EmptyFragment();
        if(item.equals("My Donations")) {
            ArrayList<Donations> dons = dbd.getDonations();
            frag = DonationsFragment.newInstance(dons);
        }
        else {
            frag = ContentFragment.newInstance(item);
        }
        setTitle(item);
        ft.replace(R.id.mainLayout, frag);
        ft.commit();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawlerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawlerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawlerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<MenuList> getMenuList() {
        ArrayList<MenuList> list = new ArrayList<>();
        list.add(new MenuList("Actions", true));
        list.add(new MenuList("My Donations", false));
        list.add(new MenuList("Support", true));
        list.add(new MenuList("About Us", false));
        list.add(new MenuList("Contact", false));
        return list;
    }

}
