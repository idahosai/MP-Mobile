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

import org.jetbrains.annotations.NotNull;

public class ImportmanuallyFragment extends Fragment {


    Button importmanually_btn;
    Button predictions_btn;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_importmanually, container,false);


        /*
        CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
        String content = "";
        content += "model: " + checkSigninApi.getModel() + "\n";
        content += "pk: " + checkSigninApi.getPk() + "\n";
        content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
        System.out.println("*****B******" + content);
        */


        importmanually_btn = view.findViewById(R.id.importmanually_btn);

        predictions_btn = view.findViewById(R.id.predictions_btn);

        importmanually_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                String content = "";
                content += "model: " + checkSigninApi.getModel() + "\n";
                content += "pk: " + checkSigninApi.getPk() + "\n";
                content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                System.out.println("*****B******" + content);

                Fragment ldf = new ListcustomfeilditemFragment();
                Bundle args = new Bundle();
                args.putParcelable("thestaff",checkSigninApi);
                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                /*
                Fragment fragment = new ListcustomfeilditemFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */


            }});

        return view;
    }

}
