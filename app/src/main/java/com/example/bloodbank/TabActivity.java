package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TabActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private Toolbar mToolbar ;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
       // String data = getIntent().getStringExtra("map");
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true ;
    }
    requestActivity requestActivity=new requestActivity();
    campaignActivity campaignActivity=new campaignActivity();
    reservationActivity reservationActivity=new reservationActivity();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.navigation_home).setIcon(R.drawable.request);
        menu.findItem(R.id.navigation_map).setIcon(R.drawable.campaign);
        menu.findItem(R.id.navigation_notification).setIcon(R.drawable.reservation);
        switch (item.getItemId()) {
            case R.id.navigation_home:
                item.setIcon(R.drawable.request_fill);
                getSupportFragmentManager().beginTransaction().
                        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.container, requestActivity).commit();
                return true;
            case R.id.navigation_map:
                item.setIcon(R.drawable.campaign_fill);
                getSupportFragmentManager().beginTransaction().
                        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.container, campaignActivity).commit();
                return true;
            case R.id.navigation_notification:
                item.setIcon(R.drawable.fill_reservation);
                getSupportFragmentManager().beginTransaction().
                        setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                        replace(R.id.container, reservationActivity).commit();
                return true;
        }
        return false;
    }
}