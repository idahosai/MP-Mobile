package com.example.mp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
//import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import java.security.Security;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
*/

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
//iggy added this to do rss


public class EmailmakerFragment extends Fragment {

    //private Button sendemail_btn;
    private Button namerssgo_btn;
    private Button feedurlgo_btn;
    private Button subjectlinego_btn;

    private EditText enterfeedname_edttxt;
    private EditText enterfeedurl_edttxt;
    //private EditText maxnumberofposts_edttxt;
    private EditText subjectline_edttxt;
    private EditText postblockstyle_edttxt;

    private CheckBox saveasdraft_chk;

    private Button designrssbroadcast_btn;

    View vie;


    //Toolbar toolbar;
    RecyclerView recyclerView2;
    Rssobject rssobject;
    //RSS link
    //private final String rss_link="http://rss.cnn.com/rss/edition.rss";
    private String rss_link="http://rss.cnn.com/rss/edition.rss";
    //https://rss2json.com/#rss_url=https%3A%2F%2Ftechcrunch.com%2Ffeed%2F
    private final String rss_to_json_api = "https://api.rss2json.com/v1/api.json?rss_url=";


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_emailmaker, container,false);

        vie = view;

        //toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        //toolbar.setTitle("News");

        //setSupportActionBar(toolbar);
        recyclerView2 = (RecyclerView)view.findViewById(R.id.recycler_view2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(recyclerView2.getContext(), LinearLayoutManager.VERTICAL, false));



        //loadRSS();

        //sendemail_btn = (Button) view.findViewById(R.id.sendemail_btn);

        namerssgo_btn = (Button) view.findViewById(R.id.namerssgo_btn);
        feedurlgo_btn = (Button) view.findViewById(R.id.feedurlgo_btn);
        subjectlinego_btn = (Button) view.findViewById(R.id.subjectlinego_btn);
        designrssbroadcast_btn = (Button) view.findViewById(R.id.designrssbroadcast_btn);

        saveasdraft_chk = (CheckBox) view.findViewById(R.id.saveasdraft_chk);

        enterfeedname_edttxt = (EditText) view.findViewById(R.id.enterfeedname_edttxt);
        enterfeedurl_edttxt = (EditText) view.findViewById(R.id.enterfeedurl_edttxt);
        //maxnumberofposts_edttxt = (EditText) view.findViewById(R.id.maxnumberofposts_edttxt);
        subjectline_edttxt = (EditText) view.findViewById(R.id.subjectline_edttxt);

        postblockstyle_edttxt = view.findViewById(R.id.postblockstyle_edttxt);

        feedurlgo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rss_link = enterfeedurl_edttxt.getText().toString();
                loadRSS();
            }});

        designrssbroadcast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Fragment fragment = new BlocksFragment();
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

                String getValue0= getArguments().getString("number of articles");
                String getValue= getArguments().getString("send frequency");
                String getValue2= getArguments().getString("days selected");
                String getValue3= getArguments().getString("subscriber segment");
                String getValue4= getArguments().getString("time");
                Boolean getValue5= getArguments().getBoolean("send automatically");
                System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
                System.out.println(getValue0);
                System.out.println(getValue);
                System.out.println(getValue2);
                System.out.println(getValue3);
                System.out.println(getValue4);
                System.out.println(getValue5);
                System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");


                Fragment ldf = new BlocksFragment();
                Bundle args = new Bundle();
                args.putParcelable("thestaff",checkSigninApi);
                args.putString("number of articles", getValue0);
                args.putString("send frequency", getValue);
                args.putString("days selected", getValue2);
                args.putString("subscriber segment",getValue3);
                args.putString("time",getValue4);
                args.putBoolean("send automatically",getValue5);

                args.putString("name of rss", enterfeedname_edttxt.getText().toString());
                args.putString("subjectline", subjectline_edttxt.getText().toString());
                args.putParcelable("rss object list",rssobject);
                args.putString("feed url", enterfeedurl_edttxt.getText().toString());
                args.putString("post rss block", postblockstyle_edttxt.getText().toString());

                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();




            }});

        /*
        sendemail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("*********************************************************************");
                //System.out.println(readRSS("http://rss.cnn.com/rss/edition.rss"));
                ExecuteTaskInBackround executeTaskInBackround = new ExecuteTaskInBackround();
                executeTaskInBackround.execute();
            }
        });
        */


        /*
        String getValue= getArguments().getString("send frequency");
        String getValue2= getArguments().getString("days selected");
        String getValue3= getArguments().getString("subscriber segment");
        System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
        System.out.println(getValue);
        System.out.println(getValue2);
        System.out.println(getValue3);
        System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
        */


        return view;

    }

    private void loadRSS() {
        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(recyclerView2.getContext());


            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait ...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssobject = new Gson().fromJson(s,Rssobject.class);

                FeedAdapter adapter = new FeedAdapter(rssobject,recyclerView2.getContext());
                recyclerView2.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        };

        StringBuilder url_get_data = new StringBuilder(rss_to_json_api);
        url_get_data.append(rss_link);
        loadRSSAsync.execute(url_get_data.toString());

    }

    /*
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        getMenuInflater().inflate(R.menu.drawer_menu)
        super.onCreateOptionsMenu(menu, inflater);
    }
    */
    public static String readRSS(String urlAddress){

        String stress = null;

        try {
            URL rssUrl = new URL(urlAddress);

            HttpURLConnection urlConnection = (HttpURLConnection) rssUrl.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder ab = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null){
                    ab.append(line);
                    System.out.println("here+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println(line);
                    System.out.println("here*********************************************************");
                }
                stress = ab.toString();
                System.out.println("-------------------------------------");
                System.out.println(ab);
                urlConnection.disconnect();
            }

            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String sourceCode = "";
            String line;
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                System.out.println("here+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println(line);
                System.out.println("here*********************************************************");
                if (line.contains("<title>")) {
                    System.out.println(line);
                    int firstPos = line.indexOf("<title>");
                    String temp = line.substring(firstPos);
                    temp = temp.replace("<title>", "");
                    int lastPos = temp.indexOf("</title>");
                    temp = temp.substring(0, lastPos);
                    sourceCode += temp + "\n";
                }



                //int titleEndIndex = 0;
                //int titleStartIndex = 0;
                //while (titleStartIndex >= 0) {
                //    titleStartIndex = line.indexOf("<title>", titleEndIndex);
                //    if (titleStartIndex >= 0) {
                //        titleEndIndex = line.indexOf("</title>", titleStartIndex);
                //        sourceCode += line.substring(titleStartIndex + "<title>".length(), titleEndIndex) + "\n";
                //    }
                //}

            }
            in.close();
            return sourceCode;
            */
        }catch(MalformedURLException uo){
            System.out.println("Malformed URL");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong reading the contents" + e);
        }

        return null;


    }






    public class ExecuteTaskInBackround2 extends AsyncTask<Void,Void,Void> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {


            System.out.println(readRSS("http://rss.cnn.com/rss/edition.rss"));



            return null;
        }
    }

    public class ExecuteTaskInBackround extends AsyncTask<Void,Void,Void> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {

            //
            String mailFrom = "ae30c81fcfa156";
            //
            //db1462228c8403
            //jswvkouhgohcwgli
            //khbvitqhipuvtqyp
            String password = "khbvitqhipuvtqyp";
            String host = "smtp.mailtrap.io";
            String port = "2525";


            // message info
            //String mailTo = "YOUR_RECIPIENT";
            String mailTo = "idahosai@sheridancollege.ca";
            String subject = "Test e-mail with inline images";
            StringBuffer body
                    = new StringBuffer("<html>This message contains two inline images.<br>");
            body.append("The first image is a chart:<br>");
            //body.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
            body.append("The second one is a cube:<br>");
            //body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
            body.append("<img src=\"https://images.pexels.com/photos/1097456/pexels-photo-1097456.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1\" width=\"15%\" height=\"15%\" /><br>");
            body.append("End of message.");
            body.append("</html>");

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
                send(host, port, mailFrom, password, mailTo,
                        subject, body.toString(), inlineImages);
                System.out.println("Email sent.***********************************************************************");
            } catch (Exception ex) {
                //Toast.makeText(vie.getContext(), "here"+ex, Toast.LENGTH_SHORT).show();
                System.out.println("Could not send email.***************************************************************");
                System.out.println("" + ex);
                ex.printStackTrace();
            }

            return null;
        }
    }





    public static void send(String host, String port,
                     final String userName, final String password, String toAddress,
                     String subject, String htmlBody,
                     Map<String, String> mapInlineImages)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        //properties.put("mail.user", userName);
        //properties.put("mail.smtp.user", userName);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // from https://stackoverflow.com/questions/1990454/using-javamail-to-connect-to-gmail-smtp-server-ignores-specified-port-and-tries-t
        //properties.put("mail.smtp.debug", "true");
        //properties.put("mail.smtp.socketFactory.port", port);
        //properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.put("mail.smtp.socketFactory.fallback", "false");
        //below will re move the following error javax.mail.MessagingException Could not convert socket to TLS
        properties.put("mail.smtp.starttls.enable", "false");
        //
        //properties.put("mail.password", password);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);




        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        //msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // just added this. this is the problem
        //msg.setText("Hey there, \n look my email!");

        // creates message part

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





/*
    public static void main(String[] args) {
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        //String mailFrom = "YOUR_EMAIL";
        //String password = "YOUR_PASSWORD";
        String mailFrom = "iidahosa@bell.net";
        String password = "igbinosa3";

        // message info
        //String mailTo = "YOUR_RECIPIENT";
        String mailTo = "idahosai@sheridancollege.ca";
        String subject = "Test e-mail with inline images";
        StringBuffer body
                = new StringBuffer("<html>This message contains two inline images.<br>");
        body.append("The first image is a chart:<br>");
        body.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
        body.append("The second one is a cube:<br>");
        body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
        body.append("End of message.");
        body.append("</html>");

        // inline images
        Map<String, String> inlineImages = new HashMap<String, String>();
        inlineImages.put("image1", "E:/Test/chart.png");
        inlineImages.put("image2", "E:/Test/cube.jpg");

        try {
            send(host, port, mailFrom, password, mailTo,
                    subject, body.toString(), inlineImages);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
*/


}
