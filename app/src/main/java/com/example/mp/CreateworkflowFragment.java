
package com.example.mp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
//import androidx.appcompat.app.AppCompatActivity;

public class CreateworkflowFragment extends Fragment {

    //hold the variable of recyclerview id
    RecyclerView recycler_view;
    EmailAdapter adapter;


    private ImageButton trigger_imgbtn;
    private ImageButton delay_imgbtn;
    private ImageButton condition_imgbtn;
    private ImageButton action_imgbtn;
    private ImageButton email_imgbtn;

    private Button automationon_btn;

    private int countchecked = 0;
    int holder = 0;
    double rholder = 0.00;
    int groupcount = 0;
    int maxcount = 0;

    List<List<String>> groupedsend = new ArrayList<List<String>>();
    List<List<String>> groupedsend1 = new ArrayList<List<String>>();
    ArrayList<String> insidelist= new ArrayList<>();
    List<List<String>> groupedlenover1 = new ArrayList<List<String>>();

    List<List<String>> newestgrouplenover1 = new ArrayList<List<String>>();
    List<Integer> storedfordelete = new ArrayList<>();

    List<String> emaillist;

    String emailid;


    Timer timer = new Timer();
    //when our timer reaches a certain point
    // its gonna implement the timer function there
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            System.out.println("                                      ");
            System.out.println("                                      ");
            System.out.println("                                      ");
            System.out.println("                                      ");
            System.out.println("                                      ");
            System.out.println("sent email at correct time interval");
            //3
            ExecuteTaskInBackround3 executeTaskInBackround3 = new ExecuteTaskInBackround3();
            executeTaskInBackround3.execute();

            /*
            //temp change
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            */
        }
    };


    Calendar calendar = new GregorianCalendar();
    //calendar.set(2022, 11, 28);
    //calendar2.add(Calendar.DATE, 50)
    //i will remove this
    //timer.scheduleAtFixedRate(task, calendar2.getTime(),8640000);//=7 days
    Date datecalender = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    //System.out.println(formatter.format(datecalender));
    //System.out.println(datecalender.getMonth());
    //System.out.println(datecalender.getDate());
    //System.out.println("*********************************************");


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //use the view to work with the fragments
        View view = inflater.inflate(R.layout.fragment_createworkflow, container,false);

        //return inflater.inflate(R.layout.fragment_workflow, container,false);

        recycler_view = view.findViewById(R.id.recycler_view);

        trigger_imgbtn = (ImageButton) view.findViewById(R.id.trigger_imgbtn);
        delay_imgbtn = (ImageButton) view.findViewById(R.id.delay_imgbtn);
        condition_imgbtn = (ImageButton) view.findViewById(R.id.condition_imgbtn);
        action_imgbtn = (ImageButton) view.findViewById(R.id.action_imgbtn);
        email_imgbtn = (ImageButton) view.findViewById(R.id.email_imgbtn);

        automationon_btn = view.findViewById(R.id.automationon_btn);


        email_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                //Intent intentMain = new Intent(view.getContext() , EmailmakerActivity.class);
                //startActivity(intentMain);
                //createNewStudentAuth();
                //createNewStudentAccount(44);

                /*
                Fragment fragment = new EmailmakerFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */
            }
        });

        automationon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //below
                //below was just recently added on 2023-10-04
                CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                //checkSigninApi.getPk()

                String getValue3 = getArguments().getString("subscriber segment");
                System.out.println(getValue3);

                String[] arrOfStr = getValue3.split("\\.");
                //Escaping a regex is done by \, but in Java, \ is written as \\).
                System.out.println(Arrays.toString(arrOfStr));

                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                System.out.println("hereeeeeeeeeeee5");

                Date datecalender = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(formatter.format(datecalender));

                //System.out.println(Arrays.toString(new String[]{arrOfStr[0]}));
                System.out.println("--------------------------------------------------");
                //System.out.println(arrOfStr[0].toString());
                int countings = 0;
                String idsegmentholder = "0";

                //List<String> al = Arrays.asList(arrOfStr);

                //List<String> arrayList = new ArrayList<>();
                //Collections.addAll(arrayList, arrOfStr);

                //System.out.println(arrayList);

                for (String s : arrOfStr) {
                    System.out.println(s);
                    if (countings == 0){
                        idsegmentholder = s;
                    }
                    countings = countings + 1;
                }
                //this code below is the problem as it doesn't show what it is
                System.out.println("idsegmentholder value is " + idsegmentholder);
                //Call<List<Contact>> call8 = jsonPlaceHolderApi.getSegmentIdToContactApis(arrOfStr[0].toString());
                Call<List<Contact>> call8 = jsonPlaceHolderApi.getSegmentIdToContactApis(idsegmentholder);
                //Call<List<Contact>> call8 = jsonPlaceHolderApi.getSegmentIdToContactApis("9");
                emaillist = new ArrayList<>();

                call8.enqueue(new Callback<List<Contact>>() {
                    @Override
                    public void onResponse(Call<List<Contact>> call8, Response<List<Contact>> response) {
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
                        List<Contact> contactApis = response.body();
                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+contactApis);
                        System.out.println("***********"+contactApis.size());
                        //this should only have 1 lenth
                        //if(customfeildApis.size() == 1) {
                        //    for (Customfeild customfeildApi : customfeildApis) {
                        //        String content = "";
                        //        content += "A name: " + customfeildApi.getName() + "\n";
                        //        content += "A dateofcreation: " + customfeildApi.getDateofcreation() + "\n";
                        //        content += "A lastcustomfeildupdate: " + customfeildApi.getLastcustomfeildupdate() + "\n\n";
                        //        arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                        //        System.out.println("***********" + content);
                        //    }
                        //}

                        //adapter.contact_list.clear();
                        for (Contact contactApi : contactApis) {
                            String content = "";
                            //content += "A8 first name: " + contactApi.getFirstname() + "\n";
                            //content += "A8 date joined: " + contactApi.getDatejoined() + "\n";
                            content += "A8 email: " + contactApi.getEmailaddress() + "\n\n";
                            //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                            System.out.println("***********" + content);
                            emaillist.add(contactApi.getEmailaddress());
                            System.out.println("********email list = "+emaillist.toString());
                            System.out.println("********email list size= "+String.valueOf(emaillist.size()));
                        }

                        //adapter.contact_list.add(new Contact(h,"Jan 06,2022 6:01PM","update1@bell.net","update1","here"));
                        //adapter.contact_list.add(new Contact(t,"Jan 06, 2022 6:09PM","update2@bell.net","update2","here"));
                        //adapter.notifyDataSetChanged();

                        Retrofit retrofit2 = new Retrofit.Builder()
                                //has to have "http://" or it wont work
                                .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        JsonPlaceHolderApi jsonPlaceHolderApi2 = retrofit.create(JsonPlaceHolderApi.class);

                        String getValue6 = getArguments().getString("name of rss");
                        String getValue7 = getArguments().getString("subjectline");
                        String emaillistcount = String.valueOf(emaillist.size());
                        String thedateofficial = formatter.format(datecalender).toString();

                        Call<Email> call9 = jsonPlaceHolderApi.getEmailApis(checkSigninApi.getPk(),getValue6, emaillistcount,thedateofficial,getValue7);
                        call9.enqueue(new Callback<Email>() {
                            @Override
                            public void onResponse(Call<Email> call9, Response<Email> response) {
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
                                Email emailApis = response.body();
                                System.out.println("************"+response.body().toString());
                                System.out.println("*****emailApis******"+emailApis);

                                emailid = emailApis.getId().toString();



                            }
                            @Override
                            public void onFailure(Call<Email> call9, Throwable t) {

                                System.out.println("****onfailure****"+t.getMessage());
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<List<Contact>> call8, Throwable t) {

                        System.out.println("********"+t.getMessage());
                    }
                });


                //above


                //



                //





                String getValue = getArguments().getString("send frequency");
                System.out.println("2going into setupforsendddddddddddddddddddddddddddddddddd");

                setupforsend();



                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");



                if (getValue == "Everyday") {

                    /*
                    try {
                        sendtoquartzqueue();
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                    */
                    everydayselected();

                    //datecalender = new Date();
                    //String[] arrOfStr = getValue4.split(":");
                    //datecalender.setHours(Integer.parseInt("08"));
                    //datecalender.setMinutes(Integer.parseInt("25"));
                    //System.out.println(formatter.format(datecalender));
                    //System.out.println("time:" + datecalender.getTime());
                    //System.out.println("date" + datecalender.getDate());
                    //System.out.println("******************yo***************************");
                    //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days


                } else if (getValue == "Week") {
                    System.out.println("24");

                    weeksselected();

                }



            }
        });



        return view;
    }






    /*
    private ImageButton trigger_imgbtn;
    private ImageButton delay_imgbtn;
    private ImageButton condition_imgbtn;
    private ImageButton action_imgbtn;
    private ImageButton email_imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createworkflow);
        trigger_imgbtn = (ImageButton) findViewById(R.id.trigger_imgbtn);
        delay_imgbtn = (ImageButton) findViewById(R.id.delay_imgbtn);
        condition_imgbtn = (ImageButton) findViewById(R.id.condition_imgbtn);
        action_imgbtn = (ImageButton) findViewById(R.id.action_imgbtn);
        email_imgbtn = (ImageButton) findViewById(R.id.email_imgbtn);
        email_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);
                Intent intentMain = new Intent(CreateworkflowActivity.this , RegisterActivity.class);
                startActivity(intentMain);
                //createNewStudentAuth();
                //createNewStudentAccount(44);
            }
        });
    }
    */

    public class ExecuteTaskInBackround3 extends AsyncTask<Void,Void,Void> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {

            int passthrough = 0;

            int temppositioni = 0;

            String combinedgetValue10 = "";

            boolean getridof = false;

            String holdnewgetValue10 = "";
            String getValue10 = getArguments().getString("post rss block");
            holdnewgetValue10 = getValue10.toString();

            System.out.println("groupedlenover: "+groupedlenover1);
            if (groupedlenover1.size() != 0) {


                //loop through over 1 len then remove it from list
                //******this is only gonna capture for the the last in line for groupedlenover1*********
                //******i need to set i to 0 to properly get the first one******
                //**** this is always gonna run actually cus of the set up*****
                for (int i = 0; i < groupedlenover1.size(); i++) {
                    //**** this is always gonna run actually cus of the set up*****
                    if (groupedlenover1.get(i).size() > 1) {
                        //for each len, add to the string the item by structure if the block

                        if (passthrough == 0) {
                            //do what i have to do with the groupedlenover1{[],[]} then
                            temppositioni = i;
                            newestgrouplenover1.clear();
                            //[[link,link]]
                            newestgrouplenover1.add(groupedlenover1.get(i));

                            //{[],[]}
                            int sizeofnewestgrouplenover1 = newestgrouplenover1.get(0).size();

                            //String combinedgetValue10 = "";
                            //[[link,link]]
                            for (int u = 0; u < newestgrouplenover1.get(0).size(); u++) {
                                //getting the inside of {[link]}
                                //newestgrouplenover1.get(0).get(u);

                                System.out.println("this should be the link:" + newestgrouplenover1.get(0).get(u));
                                //find the rssobject with thesame link
                                Rssobject getValue8 = (Rssobject) getArguments().getParcelable("rss object list");
                                List<Item> items = getValue8.getItems();
                                for (int g = 0; g < items.size(); g++) {
                                    if (items.get(g).selected == true && items.get(g).link == newestgrouplenover1.get(0).get(u)) {
                                        //items.get(g).author;
                                        //find rss post block and based on whats in that replace it with the real value
                                        //String getValue10 = getArguments().getString("post rss block");
                                        //String holdnewgetValue10 = "";
                                        holdnewgetValue10 = "";
                                        getValue10 = getArguments().getString("post rss block");
                                        holdnewgetValue10 = getValue10.toString();

                                        System.out.println("holdnewgetValue10:" + holdnewgetValue10);

                                        if (getValue10.contains("{{author}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{author}}", items.get(g).author);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{pubDate}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{pubDate}}", items.get(g).pubDate);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{title}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{title}}", items.get(g).title);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{link}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{link}}", items.get(g).link);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{description}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{description}}", items.get(g).description);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        //combinedgetValue10.concat(getValue10);
                                        combinedgetValue10 = combinedgetValue10.concat("\n");
                                        combinedgetValue10 = combinedgetValue10.concat(holdnewgetValue10);
                                    }

                                }
                                // change string that will be sent as email

                            }

                            //then
                            //groupedlenover1.remove(i);

                            passthrough = 1;

                            getridof = true;
                        }


                        //getridof = true;

                    } else if (groupedlenover1.get(i).size() == 1) {
                        if (passthrough == 0) {
                            //do what i have to do with the groupedlenover1{[],[]} then
                            temppositioni = i;
                            newestgrouplenover1.clear();
                            //[[link,link]]
                            newestgrouplenover1.add(groupedlenover1.get(i));

                            //{[],[]}
                            int sizeofnewestgrouplenover1 = newestgrouplenover1.get(0).size();

                            //String combinedgetValue10 = "";
                            //[[link,link]]
                            for (int u = 0; u < newestgrouplenover1.get(0).size(); u++) {
                                //getting the inside of {[link]}
                                //newestgrouplenover1.get(0).get(u);

                                System.out.println("this should be the link:" + newestgrouplenover1.get(0).get(u));
                                //find the rssobject with thesame link
                                Rssobject getValue8 = (Rssobject) getArguments().getParcelable("rss object list");
                                List<Item> items = getValue8.getItems();
                                for (int g = 0; g < items.size(); g++) {
                                    if (items.get(g).selected == true && items.get(g).link == newestgrouplenover1.get(0).get(u)) {
                                        //items.get(g).author;
                                        //find rss post block and based on whats in that replace it with the real value
                                        //String getValue10 = getArguments().getString("post rss block");
                                        //String holdnewgetValue10 = "";
                                        holdnewgetValue10 = "";
                                        getValue10 = getArguments().getString("post rss block");
                                        holdnewgetValue10 = getValue10.toString();

                                        System.out.println("holdnewgetValue10:" + holdnewgetValue10);

                                        if (getValue10.contains("{{author}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{author}}", items.get(g).author);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{pubDate}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{pubDate}}", items.get(g).pubDate);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{title}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{title}}", items.get(g).title);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{link}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{link}}", items.get(g).link);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        if (getValue10.contains("{{description}}")) {
                                            holdnewgetValue10 = holdnewgetValue10.replace("{{description}}", items.get(g).description);
                                            System.out.println("holdnewgetValue10:" + holdnewgetValue10);
                                        }
                                        //combinedgetValue10.concat(getValue10);
                                        combinedgetValue10 = combinedgetValue10.concat("\n");
                                        combinedgetValue10 = combinedgetValue10.concat(holdnewgetValue10);
                                    }

                                }
                                // change string that will be sent as email

                            }

                            //then
                            //groupedlenover1.remove(i);

                            passthrough = 1;

                            getridof = true;
                            //temp change.  maybe i get rid of this for now
                            //task.cancel();
                            timer.cancel();
                        }


                        //getridof = true;

                        //cancel timer that calls this function again when reached
                        //timer.cancel();
                        //this is not gonna run
                    } else if (groupedlenover1.get(i).size() < 1) {

                    }

                    /*
                    if (getridof == true) {
                        System.out.println("      groupedlenover:    " + groupedlenover1);
                        groupedlenover1.remove(temppositioni);
                        System.out.println("      groupedlenover:    " + groupedlenover1);
                        getridof = false;
                    }
                    */

                }


                if (getridof == true) {
                    System.out.println("      groupedlenover:    " + groupedlenover1);
                    groupedlenover1.remove(temppositioni);
                    System.out.println("      groupedlenover:    " + groupedlenover1);
                    getridof = false;
                }

                //System.out.println("groupedlenover: "+groupedlenover1);
                //groupedlenover1.remove(temppositioni);
                //System.out.println("groupedlenover: "+groupedlenover1);

                //replace the normal content string part with the rss block part
                //combinedgetValue10

                holdnewgetValue10 = "";

                /*
                //why does it run this
                System.out.println("groupedsend1: "+groupedsend1);
                if(groupedlenover1.size() == 0){
                    //loop through single len then remove them all
                    //[[link],[link],[link]]
                    for (int i = 0; i < groupedsend1.size(); i++) {


                        for (int u = 0; u < groupedsend1.get(i).size(); u++){


                            System.out.println("this should be the link:"+groupedsend1.get(i));

                            //find the rssobject with thesame link
                            Rssobject getValue8 = (Rssobject) getArguments().getParcelable("rss object list");
                            List<Item> items = getValue8.getItems();
                            for(int g=0; g<items.size(); g++){
                                if(items.get(g).selected == true && items.get(g).link == groupedsend1.get(i).get(u)){
                                    //items.get(g).author;
                                    //find rss post block and based on whats in that replace it with the real value
                                    //String getValue10 = getArguments().getString("post rss block");

                                    holdnewgetValue10 = "";
                                    getValue10 = getArguments().getString("post rss block");
                                    holdnewgetValue10 = getValue10.toString();

                                    //String holdnewgetValue10 = "";

                                    if(getValue10.contains("{{author}}")){
                                        holdnewgetValue10 = holdnewgetValue10.replace("{{author}}",items.get(g).author);
                                    }
                                    if(getValue10.contains("{{pubDate}}")){
                                        holdnewgetValue10 = holdnewgetValue10.replace("{{pubDate}}",items.get(g).pubDate);
                                    }
                                    if(getValue10.contains("{{title}}")){
                                        holdnewgetValue10 = holdnewgetValue10.replace("{{title}}",items.get(g).title);
                                    }
                                    if(getValue10.contains("{{link}}")){
                                        holdnewgetValue10 = holdnewgetValue10.replace("{{link}}",items.get(g).link);
                                    }
                                    if(getValue10.contains("{{description}}")){
                                        holdnewgetValue10 = holdnewgetValue10.replace("{{description}}",items.get(g).description);
                                    }
                                    //combinedgetValue10.concat(getValue10);
                                    combinedgetValue10 = combinedgetValue10.concat("\n");
                                    combinedgetValue10 = combinedgetValue10.concat(holdnewgetValue10);
                                }

                            }

                        }

                    }
                    //cancel timer that calls this function again when reached
                    timer.cancel();
                }

                */
                System.out.println("this RSS Post block is:"+combinedgetValue10);

                //get content box string and put the rss block where it belongs

                String combinedgetValue13 = "";
                String holdnewgetValue13 = "";
                String getValue13 = getArguments().getString("email content");
                holdnewgetValue13 = getValue13.toString();
                String getValue11 = getArguments().getString("image1");
                String getValue12 = getArguments().getString("image2");
                //String holdnewgetValue10 = "";
                if(getValue13.contains("{{rss}}")){
                    holdnewgetValue13 = holdnewgetValue13.replace("{{rss}}",combinedgetValue10);
                }
                if(getValue13.contains("{{image1}}")){
                    //

                    holdnewgetValue13 = holdnewgetValue13.replace("{{image1}}"," <img src=\'"+getValue11.toString()+"\' width=\'"+"15%"+"\' height=\'"+"15%"+"\' /> ");
                }
                if(getValue13.contains("{{image2}}")){
                    holdnewgetValue13 = holdnewgetValue13.replace("{{image2}}"," <img src=\'"+getValue12.toString()+"\' width=\'"+"15%"+"\' height=\'"+"15%"+"\' /> ");
                }
                //combinedgetValue10.concat(getValue10);
                System.out.println("combinedgetValue13 is:"+combinedgetValue13);
                combinedgetValue13 = combinedgetValue13.concat(holdnewgetValue13);



                //
                /*
                String mailFrom = "ae30c81fcfa156";
                String password = "db1462228c8403";
                String host = "smtp.mailtrap.io";
                String port = "2525";
                */
                //String port = "2525";

                //this is a trail to see what works
                String mailFrom = "igbinosaidahosai@gmail.com";
                //String password = "Iggyboy4$";
                //nsvafjkawqiwzbqe
                //jswvkouhgohcwgli
                //khbvitqhipuvtqyp
                String password = "khbvitqhipuvtqyp";
                String host = "smtp.gmail.com";
                //String port = "465";
                String port = "587";
                //192.168.2.220
                //192.168.2.1
                //String mailTo = "idahosai@sheridancollege.ca";

                // message info
                //String mailTo = "YOUR_RECIPIENT";


                String mailTo = "idahosai@sheridancollege.ca";
                String getValue7 = getArguments().getString("subjectline");
                String subject = getValue7.toString();
                StringBuffer body
                        = new StringBuffer("<html>"+combinedgetValue13.toString());
                //body.append("The first image is a chart:<br>");
                //body.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
                //body.append("The second one is a cube:<br>");
                //body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");

                //the below code line works
                //body.append("<img src=\'"+"https://images.pexels.com/photos/1097456/pexels-photo-1097456.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"+"\' />");

                //body.append("<img src=\"https://images.pexels.com/photos/1097456/pexels-photo-1097456.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1\" width=\"15%\" height=\"15%\" /><br>");
                //body.append("End of message.");

                //body.append("</html>");
                //body.append(" <img src=\'"+getValue12.toString()+"\' width=\'\"+\"15%\"+\"\\' height=\\'\"+\"15%\"+\"\\' /> </html>");
                body.append(" <img src=\'"+"http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/trackopenemails/"+emailid.toString()+"\' width=\'"+"1%"+"\' height=\'"+"1%"+"\' /> </html>");

                System.out.println("body is:"+body.toString());

                // inline images
                Map<String, String> inlineImages = new HashMap<String, String>();
                //Path p = Paths.get("C:\\Users\\idah\\AndroidStudioProjects\\MP\\app\\src\\main\\res\\drawable\\email.png");
                //Path p2 = Paths.get("C:\\Users\\idah\\AndroidStudioProjects\\MP\\app\\src\\main\\res\\drawable\\action.png");
                //String file = p.getFileName().toString();
                //String file2 = p2.getFileName().toString();
                //inlineImages.put("image1", "C:\\Users\\nidah\\AndroidStudioProjects\\MP\\app\\src\\main\\res\\drawable\\action.png");
                //inlineImages.put("image2", "C:\\Users\\nidah\\AndroidStudioProjects\\MP\\app\\src\\main\\res\\drawable\\action.png");
                //inlineImages.put("image1", "C:/forsend.png");
                //inlineImages.put("image2", "C:/Users/nidah/AndroidStudioProjects/MP/app/src/main/res/drawable/email.png");

                try {


                    for(String email : emaillist) {
                        send2(host, port, mailFrom, password, email,
                                subject, body.toString(), inlineImages, emaillist);
                    }

                    //send2(host, port, mailFrom, password, mailTo,
                    //                        subject, body.toString(), inlineImages, emaillist);

                    System.out.println("Email sent.***********************************************************************");
                } catch (Exception ex) {
                    //Toast.makeText(vie.getContext(), "here"+ex, Toast.LENGTH_SHORT).show();
                    System.out.println("Could not send email.***************************************************************");
                    System.out.println("" + ex);
                    ex.printStackTrace();
                }
                //temp change maybe add this here
                return null;

            } else {
                timer.cancel();
                return null;

            }
            //return null;
        }
    }



    public void send2(String host, String port,
                            final String userName, final String password, String toAddress,
                            String subject, String htmlBody,
                            Map<String, String> mapInlineImages, List<String> emaillist)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        //properties.put("mail.user", userName);
        //properties.put("mail.smtp.user", userName);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        //this one works
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);

        //properties.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.host","smtp.gmail.com");
        //properties.put("mail.smtp.port","587");

        //properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // from https://stackoverflow.com/questions/1990454/using-javamail-to-connect-to-gmail-smtp-server-ignores-specified-port-and-tries-t
        //properties.put("mail.smtp.debug", "true");
        //properties.put("mail.smtp.socketFactory.port", port);
        //properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.put("mail.smtp.socketFactory.fallback", "false");
        //below will re move the following error javax.mail.MessagingException Could not convert socket to TLS

        //i will temprarily remove this to see if it works
        //this one works
        //properties.put("mail.smtp.starttls.enable", "false");

        //properties.put("mail.password", password);
        /*
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });
        */


        // creates a new session with an authenticator
        /*
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);
        */

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
        //Session session = Session.getDefaultInstance(properties, auth);


        //Session session = Session.getDefaultInstance(properties, new GMailAuthenticator("xxxxx@gmail.com", "xxxxx"));
        //session.setDebug(true);



        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        /*
        InternetAddress [] toAddresses = new InternetAddress[emaillist.size()];
        int counter = 0;
        for(String email : emaillist) {
            toAddresses[counter] = new InternetAddress(email.trim());
            counter++;
        }
        */

        //msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // just added this. this is the problem
        //msg.setText("Hey there, \n look my email!");

        // creates message part
        //add header


        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        //multipart.addBodyPart(messageBodyPart);


        // adds inline image attachments

        /*
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();
            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    File imageFile = new File(imageFilePath);
                    if(imageFile.canRead()){
                        System.out.println("it existssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                    }
                    else{
                        System.out.println(imageFilePath + " ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                        System.out.println(new File(imageFilePath).getAbsoluteFile());
                    }
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(imagePart);
            }
        }
        */

        //MimeBodyPart imagePart = new MimeBodyPart();
        /*
        String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
                "C:/Users/nidah/Downloads/forsend.png");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        messageBodyPart.setDisposition(MimeBodyPart.INLINE);
        */
        //add image
        multipart.addBodyPart(messageBodyPart);
        //msg.setContent(mul);
        //


        msg.setContent(multipart);

        //msg.setContent("<h1>Attached Image</h1>", "text/html");
        Transport.send(msg);



    }



    public void setupforsend() {

        String getValue0 = getArguments().getString("number of articles");
        String getValue = getArguments().getString("send frequency");
        String getValue2 = getArguments().getString("days selected");
        String getValue3 = getArguments().getString("subscriber segment");
        String getValue4 = getArguments().getString("time");
        Boolean getValue5 = getArguments().getBoolean("send automatically");

        String getValue6 = getArguments().getString("name of rss");
        String getValue7 = getArguments().getString("subjectline");
        Rssobject getValue8 = (Rssobject) getArguments().getParcelable("rss object list");
        String getValue9 = getArguments().getString("feed url");
        String getValue10 = getArguments().getString("post rss block");

        String getValue11 = getArguments().getString("image1");
        String getValue12 = getArguments().getString("image2");
        String getValue13 = getArguments().getString("email content");

        List<Item> myitems = getValue8.getItems();


        for (int i = 0; i < myitems.size(); i++) {
            //System.out.println(df.get(i).selected);
            //System.out.println(df.get(i).link);
            if (myitems.get(i).selected == true) {
                countchecked = countchecked + 1;
            }

        }

        System.out.println("***************************setupforsend********************************");

        System.out.println("*countchecked" + countchecked);
        System.out.println("*getvalue0" + getValue0);

        //****the number of articles cant be zero***
        if (countchecked > Integer.parseInt(getValue0)) {
            holder = countchecked / Integer.parseInt(getValue0);

            rholder = countchecked % Integer.parseInt(getValue0);
            System.out.println("***************************jojo********************************");
            //whole number
            System.out.println(Math.floor(holder));
            //just the remainder
            System.out.println(rholder);
            //if a remainder exist
            if (rholder != 0.00) {
                //gather Integer.parseInt(getValue0) together
                // Math.floor(holder) times then do it by max number of true selected items
                // in existance remaining
                List<Item> d = getValue8.getItems();

                //List<List<String>> groupedsend = new ArrayList<List<String>>();
                //ArrayList<String> insidelist= new ArrayList<>();
                for (int i = 0; i < myitems.size(); i++) {
                    //System.out.println(df.get(i).selected);
                    //System.out.println(df.get(i).link);

                    if (myitems.get(i).selected == true) {
                        insidelist.add(myitems.get(i).link);
                        groupcount = groupcount + 1;

                        if (groupcount == Integer.parseInt(getValue0)) {
                            groupedsend.add(insidelist);
                            //jk.clear();
                            System.out.println("insidelist:" + insidelist);
                            insidelist = new ArrayList<>();
                            groupcount = 0;
                            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            System.out.println("insidelist A:" + insidelist);
                            System.out.println("groupedsend: " + groupedsend);
                            maxcount = maxcount + 1;
                        } else if (maxcount == Math.floor(holder)) {
                            groupedsend.add(insidelist);
                            System.out.println("groupedsend B" + groupedsend);
                        }
                    }

                }

                //see whats inside my arrays
                for (int i = 0; i < groupedsend.size(); i++) {
                    System.out.println("groupedsend.get(i) :" + groupedsend.get(i));
                    for (int o = 0; o < groupedsend.get(i).size(); o++) {
                        System.out.println("groupedsend.get(i).get(o) :" + groupedsend.get(i).get(o));
                    }
                }

            } else {


                //temp change
                //i need to recheck this
                //gather Integer.parseInt(getValue0) together
                // Math.floor(holder) times
                //I NEED TO FILL THIS AREA COMPLEATELY WITH CODE
                for (int i = 0; i < myitems.size(); i++) {
                    //System.out.println(df.get(i).selected);
                    //System.out.println(df.get(i).link);

                    if (myitems.get(i).selected == true) {
                        insidelist.add(myitems.get(i).link);
                        groupcount = groupcount + 1;
                        //if group count - number of articles
                        if (groupcount == Integer.parseInt(getValue0)) {
                            groupedsend.add(insidelist);
                            //jk.clear();
                            System.out.println("insidelist:" + insidelist);
                            insidelist = new ArrayList<>();
                            groupcount = 0;
                            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            System.out.println("insidelist A:" + insidelist);
                            System.out.println("groupedsend: " + groupedsend);
                            maxcount = maxcount + 1;
                        } else if (maxcount == Math.floor(holder)) {
                            groupedsend.add(insidelist);
                            System.out.println("groupedsend B" + groupedsend);
                        }
                    }

                }


            }
        } else if (countchecked == Integer.parseInt(getValue0)) {
            //send all the checked boxes at once
            //I NEED TO FILL THIS AREA COMPLEATELY WITH CODE
            holder = countchecked / Integer.parseInt(getValue0);

            rholder = countchecked % Integer.parseInt(getValue0);
            System.out.println("***************************jojo********************************");
            //whole number
            System.out.println(Math.floor(holder));
            //just the remainder
            System.out.println(rholder);
            //if a remainder exist
            for (int i = 0; i < myitems.size(); i++) {
                //System.out.println(df.get(i).selected);
                //System.out.println(df.get(i).link);

                if (myitems.get(i).selected == true) {
                    insidelist.add(myitems.get(i).link);
                    groupcount = groupcount + 1;
                    //if group count - number of articles
                    if (groupcount == Integer.parseInt(getValue0)) {
                        groupedsend.add(insidelist);
                        //jk.clear();
                        System.out.println("insidelist:" + insidelist);
                        insidelist = new ArrayList<>();
                        groupcount = 0;
                        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                        System.out.println("insidelist A:" + insidelist);
                        System.out.println("groupedsend: " + groupedsend);
                        maxcount = maxcount + 1;
                        //this doesn't get called
                    } else if (maxcount == Math.floor(holder)) {
                        groupedsend.add(insidelist);
                        System.out.println("groupedsend B" + groupedsend);
                    }
                }

            }


        } else if(countchecked < Integer.parseInt(getValue0)){

            /*
            holder = countchecked / Integer.parseInt(getValue0);

            rholder = countchecked % Integer.parseInt(getValue0);
            System.out.println("***************************jojo********************************");
            //whole number
            System.out.println(Math.floor(holder));
            //just the remainder
            System.out.println(rholder);
            //if a remainder exist
            for (int i = 0; i < myitems.size(); i++) {
                //System.out.println(df.get(i).selected);
                //System.out.println(df.get(i).link);

                if (myitems.get(i).selected == true) {
                    insidelist.add(myitems.get(i).link);
                    groupcount = groupcount + 1;
                    //if group count - number of articles
                    if (groupcount == Integer.parseInt(getValue0)) {
                        groupedsend.add(insidelist);
                        //jk.clear();
                        System.out.println("insidelist:" + insidelist);
                        insidelist = new ArrayList<>();
                        groupcount = 0;
                        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                        System.out.println("insidelist A:" + insidelist);
                        System.out.println("groupedsend: " + groupedsend);
                        //maxcount = maxcount + 1;
                    }
                        //this doesn't need to be called again
                    //} else if (maxcount == Math.floor(holder)) {
                    //    groupedsend.add(insidelist);
                    //    System.out.println("groupedsend B" + groupedsend);
                    //}
                }

            }
            */


            holder = countchecked / Integer.parseInt(getValue0);

            rholder = countchecked % Integer.parseInt(getValue0);
            System.out.println("***************************jojo********************************");
            //whole number
            System.out.println(Math.floor(holder));
            //just the remainder
            System.out.println(rholder);
            //if a remainder exist
            if (rholder != 0.00) {
                //gather Integer.parseInt(getValue0) together
                // Math.floor(holder) times then do it by max number of true selected items
                // in existance remaining
                List<Item> d = getValue8.getItems();

                //List<List<String>> groupedsend = new ArrayList<List<String>>();
                //ArrayList<String> insidelist= new ArrayList<>();

                int actualarticlesselected = 0;
                for (int i = 0; i < myitems.size(); i++) {
                    //System.out.println(df.get(i).selected);
                    //System.out.println(df.get(i).link);

                    if (myitems.get(i).selected == true) {
                        actualarticlesselected = actualarticlesselected + 1;
                    }
                }

                for (int i = 0; i < myitems.size(); i++) {
                    //System.out.println(df.get(i).selected);
                    //System.out.println(df.get(i).link);

                    if (myitems.get(i).selected == true) {
                        insidelist.add(myitems.get(i).link);
                        groupcount = groupcount + 1;

                        if (groupcount == actualarticlesselected) {
                            groupedsend.add(insidelist);
                            //jk.clear();
                            System.out.println("insidelist:" + insidelist);
                            //insidelist = new ArrayList<>();
                            groupcount = 0;
                            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            System.out.println("insidelist A:" + insidelist);
                            System.out.println("groupedsend: " + groupedsend);
                            //maxcount = maxcount + 1;
                        }
                    }

                }

                //see whats inside my arrays
                for (int i = 0; i < groupedsend.size(); i++) {
                    System.out.println("groupedsend.get(i) :" + groupedsend.get(i));
                    for (int o = 0; o < groupedsend.get(i).size(); o++) {
                        System.out.println("groupedsend.get(i).get(o) :" + groupedsend.get(i).get(o));
                    }
                }

            } else {
                //gather Integer.parseInt(getValue0) together
                // Math.floor(holder) times
                //I NEED TO FILL THIS AREA COMPLEATELY WITH CODE

                int actualarticlesselected = 0;
                for (int i = 0; i < myitems.size(); i++) {
                    //System.out.println(df.get(i).selected);
                    //System.out.println(df.get(i).link);

                    if (myitems.get(i).selected == true) {
                        actualarticlesselected = actualarticlesselected + 1;
                    }
                }

                for (int i = 0; i < myitems.size(); i++) {
                    //System.out.println(df.get(i).selected);
                    //System.out.println(df.get(i).link);

                    if (myitems.get(i).selected == true) {
                        insidelist.add(myitems.get(i).link);
                        groupcount = groupcount + 1;
                        //if group count - number of articles
                        if (groupcount == actualarticlesselected) {
                            groupedsend.add(insidelist);
                            //jk.clear();
                            System.out.println("insidelist:" + insidelist);
                            //insidelist = new ArrayList<>();
                            groupcount = 0;
                            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            System.out.println("insidelist A:" + insidelist);
                            System.out.println("groupedsend: " + groupedsend);
                            //maxcount = maxcount + 1;
                        }
                    }

                }
            }
        }

        //loop through list and remove len over 1 from it
        System.out.println("groupedsend G :"+groupedsend);
        for (int i = 0; i < groupedsend.size(); i++) {
            if (groupedsend.get(i).size() > 0) {
                groupedlenover1.add(groupedsend.get(i));
                System.out.println("groupedsend G1 :"+groupedsend);
                //storedfordelete.add(i);

                //store it up for future delete
                //groupedsend.remove(i);
                //System.out.println("groupedsend G1done :"+groupedsend);
            }
            /*
            if (groupedsend.get(i).size() > 1) {
                groupedlenover1.add(groupedsend.get(i));
                System.out.println("groupedsend G1 :"+groupedsend);
                //storedfordelete.add(i);

                //store it up for future delete
                //groupedsend.remove(i);
                //System.out.println("groupedsend G1done :"+groupedsend);
            }
            if (groupedsend.get(i).size() == 1) {
                //groupedlenover1.add(groupedsend.get(i));
                //groupedsend1 only exist if groupedsend.get(i).size() == 1, it is it at size 1 so [[]]
                groupedsend1.add(groupedsend.get(i));
                System.out.println("groupedsend1 G1 :"+groupedsend1);

                //store it up for future delete
                //groupedsend.remove(i);
                //System.out.println("groupedsend G1done :"+groupedsend);
            }
            */
        }
        //groupedsend1.clear();

        System.out.println("groupedsend G2 :"+groupedsend);
        /*
        for(int w = storedfordelete.size(); w > 0; w--){
            groupedsend.remove(storedfordelete.get(w-1));
        }
        */
        System.out.println("groupedsend G2done :"+groupedsend);

        System.out.println("groupedsend G4 :"+groupedsend);


    }



    public void everydayselected() {

        String getValue0 = getArguments().getString("number of articles");
        String getValue = getArguments().getString("send frequency");
        String getValue2 = getArguments().getString("days selected");
        String getValue3 = getArguments().getString("subscriber segment");
        String getValue4 = getArguments().getString("time");
        Boolean getValue5 = getArguments().getBoolean("send automatically");

        String getValue6 = getArguments().getString("name of rss");
        String getValue7 = getArguments().getString("subjectline");
        Rssobject getValue8 = (Rssobject) getArguments().getParcelable("rss object list");
        String getValue9 = getArguments().getString("feed url");
        String getValue10 = getArguments().getString("post rss block");

        String getValue11 = getArguments().getString("image1");
        String getValue12 = getArguments().getString("image2");
        String getValue13 = getArguments().getString("email content");
        //january
        if (calendar.get(Calendar.MONTH) == 0) {
            System.out.println("1");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }

        //febuary
        if (calendar.get(Calendar.MONTH) == 1) {
            System.out.println("3");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 day
        }

        //march
        if (calendar.get(Calendar.MONTH) == 2) {
            System.out.println("5");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }

        //april
        if (calendar.get(Calendar.MONTH) == 3) {
            System.out.println("7");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 day
        }

        //may
        if (calendar.get(Calendar.MONTH) == 4) {
            System.out.println("9");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }
        //june
        if (calendar.get(Calendar.MONTH) == 5) {
            System.out.println("11");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }

        //july
        if (calendar.get(Calendar.MONTH) == 6) {
            System.out.println("13");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }


        //august
        if (calendar.get(Calendar.MONTH) == 7) {
            System.out.println("15");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }


        //september
        if (calendar.get(Calendar.MONTH) == 8) {
            System.out.println("17");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            

        }


        //october
        if (calendar.get(Calendar.MONTH) == 9) {
            System.out.println("19");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
        }


        //november
        if (calendar.get(Calendar.MONTH) == 10) {
            System.out.println("21");
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("*********************************************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//=7 days
        }
        if (calendar.get(Calendar.MONTH) == 11) {
            System.out.println("23");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            System.out.println("**********************A***********************");
            timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days

        }
    }



    public void weeksselected(){

        String getValue0 = getArguments().getString("number of articles");
        String getValue = getArguments().getString("send frequency");
        String getValue2 = getArguments().getString("days selected");
        String getValue3 = getArguments().getString("subscriber segment");
        String getValue4 = getArguments().getString("time");
        Boolean getValue5 = getArguments().getBoolean("send automatically");

        String getValue6 = getArguments().getString("name of rss");
        String getValue7 = getArguments().getString("subjectline");
        Rssobject getValue8 = (Rssobject) getArguments().getParcelable("rss object list");
        String getValue9 = getArguments().getString("feed url");
        String getValue10 = getArguments().getString("post rss block");

        String getValue11 = getArguments().getString("image1");
        String getValue12 = getArguments().getString("image2");
        String getValue13 = getArguments().getString("email content");

        //january
        if (calendar.get(Calendar.MONTH) == 0 ) {
            System.out.println("25");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }

        //febuary
        if (calendar.get(Calendar.MONTH) == 1 ) {
            System.out.println("26");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


        //march
        if (calendar.get(Calendar.MONTH) == 2 ) {
            System.out.println("27");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }

        //April
        if (calendar.get(Calendar.MONTH) == 3 ) {
            System.out.println("28");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


        //may
        if (calendar.get(Calendar.MONTH) == 4 ) {
            System.out.println("29");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


        //june
        if (calendar.get(Calendar.MONTH) == 5 ) {
            System.out.println("30");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


        //july
        if (calendar.get(Calendar.MONTH) == 6 ) {
            System.out.println("31");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }

        //August
        if (calendar.get(Calendar.MONTH) == 7 ) {
            System.out.println("32");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }

        //September
        if (calendar.get(Calendar.MONTH) == 8 ) {
            System.out.println("33");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }



        //october
        if (calendar.get(Calendar.MONTH) == 9 ) {
            System.out.println("34");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


        //November
        if (calendar.get(Calendar.MONTH) == 10 ) {
            System.out.println("35");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


        //december
        if (calendar.get(Calendar.MONTH) == 11 ) {
            System.out.println("36");
            //calendar.set(Calendar.MONTH, 0);
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH));
            //to get the sunday thats appropriate i do this
            //calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH)-10);//finally

            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            System.out.println("day of week:" + calendar.DAY_OF_WEEK + "m " + calendar.get(calendar.DAY_OF_WEEK) + "k" + Calendar.WEDNESDAY);

            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println("were in saturday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("wewewewe");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    System.out.println("wewewewe");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                System.out.println("were in friday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {

                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                System.out.println("were in thursday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {

                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                System.out.println("were in wednesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");


                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    System.out.println("!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    System.out.println("!!!!");
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    System.out.println("!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    System.out.println("!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                System.out.println("were in tuesday");
                if (getValue2 == "Sunday") {
                    System.out.println("!!!!!!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Monday") {
                    System.out.println("!!");
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Tuesday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");
                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                System.out.println("were in monday");
                if (getValue2 == "Sunday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            if (calendar.get(calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println("were in sunday");
                if (getValue2 == "Sunday") {
                    //datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SATURDAY);
                    //same day so no extra
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH));
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Monday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.SUNDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Tuesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.MONDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Wednesday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.TUESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Thursday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.WEDNESDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Friday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.THURSDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
                if (getValue2 == "Saturday") {
                    datecalender.setDate(calendar.get(calendar.DAY_OF_MONTH) + Calendar.FRIDAY);
                    System.out.println(formatter.format(datecalender));
                    System.out.println(datecalender.getMonth());
                    System.out.println(datecalender.getDate());
                    System.out.println("*********************************************");

                }
            }
            System.out.println("day of month:" + calendar.DAY_OF_MONTH + "  m " + calendar.get(calendar.DAY_OF_MONTH));
            String[] arrOfStr = getValue4.split(":");
            datecalender.setHours(Integer.parseInt(arrOfStr[0]));
            datecalender.setMinutes(Integer.parseInt(arrOfStr[1]));
            System.out.println(formatter.format(datecalender));
            System.out.println("time:" + datecalender.getTime());
            System.out.println("date" + datecalender.getDate());
            //calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrOfStr[0]));
            //calendar.set(Calendar.MINUTE, Integer.parseInt(arrOfStr[1]));
            //System.out.println(calendar.getTime());
            //timer.scheduleAtFixedRate(task, calendar.getTime(),8640000);//=7 days
            System.out.println("**********************A***********************");
            //timer.scheduleAtFixedRate(task, datecalender, 8640000);//1 days
            //timer.scheduleAtFixedRate(task, datecalender,1000);//=7 days
            //timer.scheduleAtFixedRate(task, calendar.getTime(),6000);//=6 seconds
            timer.scheduleAtFixedRate(task, calendar.getTime(), 60480000);//=7 days
        }


    }



}
