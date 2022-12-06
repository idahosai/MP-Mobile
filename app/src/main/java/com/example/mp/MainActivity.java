package com.example.mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    //hold the variable of recyclerview id
    /*
    RecyclerView recycler_view;
    EmailAdapter adapter;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        recycler_view = findViewById(R.id.recycler_view);
        setRecyclerView();
        */


        //drawerLayout= findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //from current video
        setSupportActionBar(toolbar);



        //from current video
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }

    /*
    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmailAdapter(this, getList());
        recycler_view.setAdapter(adapter);


    }
    */
/*
    private List<Email> getList(){
        List<Email> email_list = new ArrayList<>();

        email_list.add(new Email("1", "birthday boy", "Active", "RSS Broadcast", "375", 7.00,
                "6.99", "null", "1",
                "Aug 20,2022 2:01PM", "Jan 03, 2022 4:09PM", 0.2, 5.0, 1.0, 0.6,
                "welcome contacts", 1, 2, 1, 1,
                1, 1, 1));

        email_list.add(new Email("2", "holliday boy", "Publised", "A Broadcast", "278", 7.00,
                "6.99", "null", "2",
                "Feb 21,2022 3:01PM", "Sep 05, 2022 5:09PM", 0.2, 5.0, 1.0, 0.6,
                "welcome contacts", 2, 1, 2, 2,
                2, 1, 2));


        return email_list;
    }
*/
    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.email:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WorkflowFragment()).commit();
                break;
            case R.id.importcontacts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ImportmanuallyFragment()).commit();
                break;
            case R.id.face:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forwardtoinbox:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);


        //to select the item after we click it
        return true;
    }

    //from other video

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}