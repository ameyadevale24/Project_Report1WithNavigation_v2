package com.example.a300288675.project_report1;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }




        //this piece of code is to add data for testing purpose only--------------------------------------------------------
        /*DatabaseHelper databaseHelper = new DatabaseHelper(this);

        if(!databaseHelper.checkUserExists("user1"))
            databaseHelper.addUser("user1","f","l","e","2","user1");

        databaseHelper.addMonthlyIncomeTracking("user1",11, 2018,900.0);

        databaseHelper.addTransaction("user1",1,"2018-11-01",10,"");
        databaseHelper.addTransaction("user1",1,"2018-11-01",10,"");
        databaseHelper.addTransaction("user1",1,"2018-11-02",15,"");
        databaseHelper.addTransaction("user1",1,"2018-11-02",15,"");
        databaseHelper.addTransaction("user1",1,"2018-11-03",14,"");
        databaseHelper.addTransaction("user1",1,"2018-11-03",28,"");

        Calendar cal = Calendar.getInstance();
        cal.set(2018, 11-1, 01);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        Log.e("check this date here  "," date is "+format1.format(cal.getTime()));
        //-------------------------------------------------------------------------------------------------------------------
        */



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_goals:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GoalsFragment()).commit();
                break;
            case R.id.nav_reports:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReportsFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}

