package com.example.mp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

                /*
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();


                // Create new fragment and transaction
                Fragment newFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, newFragment);
                //transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
                */

                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                    //Intent intentMain = new Intent(view.getContext() , CreateworkflowActivity.class);
                    //view.getContext().startActivity(intentMain);

                //createNewStudentAuth();
                //createNewStudentAccount(44);


                /*
                Fragment fragment = new CreateworkflowFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */

                CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                String content = "";
                content += "model: " + checkSigninApi.getModel() + "\n";
                content += "pk: " + checkSigninApi.getPk() + "\n";
                content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                System.out.println("*****B******" + content);

                Fragment ldf = new TriggerFragment();
                Bundle args = new Bundle();
                args.putParcelable("thestaff",checkSigninApi);
                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                /*
                Fragment fragment = new TriggerFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */

            }
        });







        return view;
    }



    /*
    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(recycler_view.getContext()));
        adapter = new EmailAdapter(recycler_view.getContext(), getList());
        recycler_view.setAdapter(adapter);
    }
    private List<Email> getList(){
        List<Email> email_list = new ArrayList<>();

        email_list.add(new Email(1,"birthday boy", 0, "Aug 20,2022 2:01PM","welcome contacts",0));
        email_list.add(new Email(2,"holliday boy", 0, "Feb 21,2022 3:01PM","welcome contacts",0));
        return email_list;
    }
    */
    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(recycler_view.getContext()));
        getList();
        //adapter = new EmailAdapter(recycler_view.getContext(), );
        //recycler_view.setAdapter(adapter);
    }
    private void getList(){
        //List<Email> email_list = new ArrayList<>();

        //email_list.add(new Email(1,"birthday boy", 0, "Aug 20,2022 2:01PM","welcome contacts",0));
        //email_list.add(new Email(2,"holliday boy", 0, "Feb 21,2022 3:01PM","welcome contacts",0));
        //return email_list;

        List<Email> email_list = new ArrayList<>();

        CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
        //System.out.println("checkSigninApi.getPk()=" + checkSigninApi.getPk());

        Retrofit retrofit = new Retrofit.Builder()
                //has to have "http://" or it wont work
                .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        System.out.println("hereeeeeeeeeeee4");


        //Call<List<Contact>> call4 = jsonPlaceHolderApi.getContactApis(checkSigninApi.getPk());
        Call<List<Email>> call4 = jsonPlaceHolderApi.getEmailsApis(checkSigninApi.getPk());
        call4.enqueue(new Callback<List<Email>>() {
            @Override
            public void onResponse(Call<List<Email>> call4, Response<List<Email>> response) {
                if(!response.isSuccessful()){
                    try {
                        System.out.println("Code: " + response.code() +""+ response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                System.out.println("*");
                //its a list of whatever is inside
                List<Email> emailApis = response.body();
                System.out.println("************"+response.body().toString());
                System.out.println("***********"+emailApis);
                System.out.println("***********"+emailApis.size());

                for (Email emailApi : emailApis) {
                    String content = "";
                    content += "A4 first name: " + emailApi.getName() + "\n";
                    content += "A4 dateofcreation: " + emailApi.getDateofcreation() + "\n";
                    //content += "A4 last name: " + emailApi.getLastname() + "\n\n";
                    //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                    email_list.add(new Email(emailApi.getId(),emailApi.getName(),emailApi.getNumberofcontactssentto(),emailApi.getDateofcreation(),emailApi.getSubjecttitle(),emailApi.getOpens()));
                    System.out.println("***********" + content);
                }


                adapter = new EmailAdapter(recycler_view.getContext(), email_list);
                recycler_view.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Email>> call4, Throwable t) {

                System.out.println("********"+t.getMessage());
            }
        });
    }


}
