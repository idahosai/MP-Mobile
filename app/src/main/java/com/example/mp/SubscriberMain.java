package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class SubscriberMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    //hold the variable of recyclerview id
    /*
    RecyclerView recycler_view;
    EmailAdapter adapter;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribermain);



        //receive
        User userApi = (User) getIntent().getParcelableExtra("user");
        String content = "";
        //content += "model: " + userApi.getModel() + "\n";
        //content += "pk: " + userApi.getPk() + "\n";
        //content += "fields: " + userApi.getFields().getUsername() + "\n\n";
        System.out.println("*****A******" + content);


        /*
        recycler_view = findViewById(R.id.recycler_view);
        setRecyclerView();
        */


        //drawerLayout= findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        //from current video
        setSupportActionBar(toolbar);



        //from current video
        drawerLayout = findViewById(R.id.drawer_layout2);

        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.homesubscriber);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        User userApi = (User) getIntent().getParcelableExtra("user");
        String content = "";
        //content += "model: " + userApi.getModel() + "\n";
        //content += "pk: " + userApi.getPk() + "\n";
        //content += "fields: " + userApi.getFields().getUsername() + "\n\n";
        System.out.println("*****A******" + content);



        switch (item.getItemId()){
            case R.id.homesubscriber:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                        new HomeFragment()).commit();
                break;
            case R.id.chatbotsubscriber:
                /*
                Fragment ldf3 = new WorkflowFragment();
                Bundle args3 = new Bundle();
                args3.putParcelable("user",userApi);
                ldf3.setArguments(args3);

                FragmentManager fragmentManager3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container2, ldf3);
                fragmentTransaction3.commit();
                */
                break;
            case R.id.chatsystem:
                /*
                Fragment ldf = new ImportmanuallyFragment();
                Bundle args = new Bundle();
                args.putParcelable("user",userApi);
                ldf.setArguments(args);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container2, ldf);
                fragmentTransaction.commit();
                */
                break;
            case R.id.spaceholder1subscriber:
                /*
                Fragment ldf2 = new SubscribersFragment();
                Bundle args2 = new Bundle();
                args2.putParcelable("user",userApi);
                ldf2.setArguments(args2);

                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container2, ldf2);
                fragmentTransaction2.commit();
                */
                break;


            case R.id.spaceholder2subscriber:
                Intent intentMain = new Intent(SubscriberMain.this , LoginActivity.class);
                startActivity(intentMain);
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
