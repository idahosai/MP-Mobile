package com.example.mp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BlocksFragment extends Fragment {


    private Button goemailcontent_btn;
    EditText emailcontentbox_txt;
    ImageView imageforscore_img;
    ImageView imageforscore2_img;

    private Button fetchimage_btn;
    private Button scoreimage_btn;
    private Button fetchimage2_btn;
    private Button scoreimage2_btn;
    private EditText imageurl_edttxt;
    private EditText imageurl2_edttxt;

    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    Handler mainHandler2 = new Handler();
    ProgressDialog progressDialog2;


    /*
    RecyclerView recycler_view3;
    private List<DataModel> mList;
    private ItemBlockAdapter adapter;
    */
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_blocks, container,false);



        imageurl_edttxt = view.findViewById(R.id.imageurl_edttxt);

        imageforscore_img = view.findViewById(R.id.imageforscore_img);
        imageforscore2_img = view.findViewById(R.id.imageforscore2_img);

        fetchimage_btn = view.findViewById(R.id.fetchimage_btn);
        scoreimage_btn = view.findViewById(R.id.scoreimage_btn);

        imageurl2_edttxt = view.findViewById(R.id.imageurl2_edttxt);

        fetchimage2_btn = view.findViewById(R.id.fetchimage2_btn);
        scoreimage2_btn = view.findViewById(R.id.scoreimage2_btn);

        emailcontentbox_txt = view.findViewById(R.id.emailcontentbox_txt);

        goemailcontent_btn = view.findViewById(R.id.goemailcontent_btn);

        fetchimage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = imageurl_edttxt.getText().toString();
                new FetchImage(url).start();

            }});

        fetchimage2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = imageurl2_edttxt.getText().toString();
                new FetchImage2(url).start();
            }});


        goemailcontent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                String txt = emailcontentbox_txt.getText().toString();
                StringBuffer body = new StringBuffer("<html><br>");
                String blockaction = "";
                int here = 0;

                for (int i=0;i<txt.length();i++){
                    body.append(txt.charAt(i));

                    if (txt.charAt(i) == '{' && (txt.charAt(i+1) == '{') && (txt.charAt(i+2)== 'r') && (txt.charAt(i+3) == 's') && (txt.charAt(i+4) == 's') && (txt.charAt(i+5) == '}') && (txt.charAt(i+6) == '}')){
                        //get text of rss block, break it down then put it into here

                    }

                    else if(txt.charAt(i) == '{' && (txt.charAt(i+1) == '{') && (txt.charAt(i+2)== 'i') && (txt.charAt(i+3)== 'm') && (txt.charAt(i+4)== 'a') && (txt.charAt(i+5)== 'g') && (txt.charAt(i+6)== 'e') && (txt.charAt(i+7)== '1') && (txt.charAt(i+8)== '}') && (txt.charAt(i+9)== '}')){
                        body.append("<img src=\""+imageurl_edttxt.getText().toString()+"\"  /><br>");
                        blockaction = "image";
                    }
                    else if(txt.charAt(i) == '{' && (txt.charAt(i+1) == '{') && (txt.charAt(i+2)== 'i') && (txt.charAt(i+3)== 'm') && (txt.charAt(i+4)== 'a') && (txt.charAt(i+5)== 'g') && (txt.charAt(i+6)== 'e') && (txt.charAt(i+7)== '2') && (txt.charAt(i+8)== '}') && (txt.charAt(i+9)== '}')){
                        body.append("<img src=\""+imageurl2_edttxt.getText().toString()+"\"  /><br>");
                        blockaction = "image";
                    }
                    else if(txt.charAt(i) == '{' && (txt.charAt(i+1) == '{') && txt.charAt(i+2)== 'c' && (txt.charAt(i+3)== '_') && (txt.charAt(i+4)== 'f') && (txt.charAt(i+5)== 'i') && (txt.charAt(i+6)== 'r') && (txt.charAt(i+7)== 's') && (txt.charAt(i+8)== 't') && (txt.charAt(i+9)== 'n') && (txt.charAt(i+10)== 'a') && (txt.charAt(i+11)== 'm') && (txt.charAt(i+12)== 'e') && (txt.charAt(i+13)== '}') && (txt.charAt(i+14)== '}')){

                        //i dont change it here but when im at the appropriate place to change it, i do
                        //get firstname of all emails in the list and do individual email sends
                    }
                    else if (true){
                        if(blockaction == "image"){
                            here = 10;
                            blockaction = "null";
                        }

                        if(here == 0){
                            body.append(txt.charAt(i));
                        }
                        else{
                            here = here -1;
                        }

                    }






                }
                //loop through the string and remove all {}} words
                body.append("</html>");
                emailcontentbox_txt.setText(body.toString());

                for (int i=0;i<body.toString().length();i++){
                    //loop through the string and remove all {}} words
                }

                */






                String getValue0= getArguments().getString("number of articles");
                String getValue= getArguments().getString("send frequency");
                String getValue2= getArguments().getString("days selected");
                String getValue3= getArguments().getString("subscriber segment");
                String getValue4= getArguments().getString("time");
                Boolean getValue5= getArguments().getBoolean("send automatically");

                String getValue6= getArguments().getString("name of rss");
                String getValue7= getArguments().getString("subjectline");
                Rssobject getValue8= (Rssobject) getArguments().getParcelable("rss object list");
                String getValue9= getArguments().getString("feed url");
                String getValue10= getArguments().getString("post rss block");


                Fragment ldf = new CreateworkflowFragment();
                Bundle args = new Bundle();
                args.putString("number of articles", getValue0);
                args.putString("send frequency", getValue);
                args.putString("days selected", getValue2);
                args.putString("subscriber segment",getValue3);
                args.putString("time",getValue4);
                args.putBoolean("send automatically",getValue5);

                args.putString("name of rss", getValue6);
                args.putString("subjectline", getValue7);
                args.putParcelable("rss object list",getValue8);
                args.putString("feed url", getValue9);
                args.putString("post rss block", getValue10);

                args.putString("image1",imageurl_edttxt.getText().toString());
                args.putString("image2",imageurl2_edttxt.getText().toString());
                args.putString("email content",emailcontentbox_txt.getText().toString());

                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();




            }});

        /*
        //what you do when you do recyclerview
        recycler_view3 = view.findViewById(R.id.recyclerView3);
        recycler_view3.setHasFixedSize(true);
        recycler_view3.setLayoutManager(new LinearLayoutManager(recycler_view3.getContext()));

        mList = new ArrayList<>();

        List<String> nestedList1 = new ArrayList();
        nestedList1.add("Image");
        nestedList1.add("Text");
        nestedList1.add("padding");
        nestedList1.add("RSS");
        //main list
        mList.add(new DataModel(nestedList1, "Section 1"));
        mList.add(new DataModel(nestedList1, "Section 2"));
        mList.add(new DataModel(nestedList1, "Section 3"));
        mList.add(new DataModel(nestedList1, "Section 4"));
        mList.add(new DataModel(nestedList1, "Section 5"));
        mList.add(new DataModel(nestedList1, "Section 6"));
        mList.add(new DataModel(nestedList1, "Section 7"));
        mList.add(new DataModel(nestedList1, "Section 8"));

        adapter = new ItemBlockAdapter(mList);
        recycler_view3.setAdapter(adapter);

        */


        String getValue= getArguments().getString("send frequency");
        String getValue2= getArguments().getString("days selected");
        String getValue3= getArguments().getString("subscriber segment");
        String getValue4= getArguments().getString("time");
        Boolean getValue5= getArguments().getBoolean("send automatically");

        String getValue6= getArguments().getString("name of rss");
        String getValue7= getArguments().getString("subjectline");
        Rssobject getValue8= (Rssobject) getArguments().getParcelable("rss object list");
        String getValue9= getArguments().getString("feed url");
        String getValue10= getArguments().getString("post rss block");



        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        System.out.println(getValue);
        System.out.println(getValue2);
        System.out.println(getValue3);
        System.out.println(getValue4);
        System.out.println(getValue5);
        System.out.println(getValue6);
        System.out.println(getValue7);
        System.out.println(getValue8);
        System.out.println("JJJJJJJ");
        //i must click the item not the checkbox to get this to work
        List<Item> df = getValue8.getItems();
        for (int i = 0; i < df.size(); i++) {
            System.out.println(df.get(i).selected);
            System.out.println(df.get(i).link);

        }
        System.out.println("JJJJJJJ");
        System.out.println(getValue9);
        System.out.println(getValue10);
        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");




        return view;
    }

    private void setRecyclerView() {
        //recycler_view3.setHasFixedSize(true);
        //recycler_view3.setLayoutManager(new LinearLayoutManager(recycler_view3.getContext()));
        //adapter = new EmailAdapter(recycler_view3.getContext(), getList());
        //recycler_view3.setAdapter(adapter);
    }


    class FetchImage extends Thread{

        String url;
        Bitmap bitmap;

        FetchImage(String url){
            this.url = url;
        }

        @Override
        public void run(){


            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(getView().getContext());
                    progressDialog.setMessage("Getting your pic....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });

            InputStream inputStream = null;
            try {
                //inputStream = new java.net.URL(url).openStream();
                inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();

                    }
                    imageforscore_img.setImageBitmap(bitmap);
                }
            });
        }

    }



    class FetchImage2 extends Thread{

        String url;
        Bitmap bitmap;

        FetchImage2(String url){
            this.url = url;
        }

        @Override
        public void run(){


            mainHandler2.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog2 = new ProgressDialog(getView().getContext());
                    progressDialog2.setMessage("Getting your pic....");
                    progressDialog2.setCancelable(false);
                    progressDialog2.show();

                }
            });

            InputStream inputStream = null;
            try {
                //inputStream = new java.net.URL(url).openStream();
                inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler2.post(new Runnable() {
                @Override
                public void run() {

                    if(progressDialog2.isShowing()){
                        progressDialog2.dismiss();

                    }
                    imageforscore2_img.setImageBitmap(bitmap);
                }
            });
        }

    }



}
