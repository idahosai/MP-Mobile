package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkflowFragment extends Fragment {

    //hold the variable of recyclerview id
    RecyclerView recycler_view;
    EmailAdapter adapter;
    private Button rssautomation_btn;
    private Button broadcast_btn;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //use the view to work with the fragments
        View view = inflater.inflate(R.layout.fragment_workflow, container,false);

        //return inflater.inflate(R.layout.fragment_workflow, container,false);

        recycler_view = view.findViewById(R.id.recycler_view);
        setRecyclerView();

        rssautomation_btn = view.findViewById(R.id.rssautomation_btn);
        broadcast_btn = view.findViewById(R.id.broadcast_btn);

        rssautomation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                Intent intentMain = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intentMain);
                //createNewStudentAuth();
                //createNewStudentAccount(44);

            }
        });
        return view;
    }




    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(recycler_view.getContext()));
        adapter = new EmailAdapter(recycler_view.getContext(), getList());
        recycler_view.setAdapter(adapter);
    }
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


}
