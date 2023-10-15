package com.example.mp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {


    //its a list were getting
    @GET("checksigninapi/")
    Call<List<CheckSigninApi>> getCheckSigninApis(@Query("username") String username, @Query("password") String password);

    //@POST("savecontactapi/")
    //Call<List<ContactApi>> createContact(@Body Contact contact);


    @POST("CreateContactView/")
    @FormUrlEncoded
    Call<List<ContactApi>> createContact(
            //@Field("arrCustomfeilds") ArrayList<Object> arrCustomfeilds,
            @Field("datejoined") String datejoined,
            @Field("notes") String notes,
            @Field("emailaddress") String emailaddress,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("jobtitle") String jobtitle,
            @Field("company") String company,
            @Field("mobilephone") String mobilephone,
            @Field("workphone") String workphone,
            @Field("country") String country,
            @Field("stateprovince") String stateprovince,
            @Field("city") String city,
            @Field("address") String address,
            @Field("zip") String zip,
            @Field("website") String website,

            @Field("addmethod") String addmethod,
            @Field("staffpk") String staffpk
    );

    @POST("CreateCustomfeildView/")
    @FormUrlEncoded
    Call<List<ContactApi>> createCustomfeild(
            //@Field("arrCustomfeilds") ArrayList<Object> arrCustomfeilds,
            @Field("contactpk") String contactpk,
            @Field("name") String name,
            @Field("customfeildintvalue") Integer customfeildintvalue,
            @Field("customfeildstringvalue") String customfeildstringvalue,
            @Field("dateofcreation") String dateofcreation,
            @Field("lastcustomfeildupdate") String lastcustomfeildupdate,
            @Field("staffpk") String staffpk

    );

    @GET("CreateCustomfeild2View/")
    Call<List<Customfeild>> getCustomfeildApis(@Query("staffpk") String staffpk);


    @POST("CreateCustomfeild2View/")
    @FormUrlEncoded
    Call<List<Customfeild>> getCustomfeildApis(
            @Field("id") String contactpk,
            @Field("name") String name,
            @Field("customfeildstringvalue") String customfeildstringvalue,
            @Field("dateofcreation") String dateofcreation,
            @Field("lastcustomfeildupdate") String lastcustomfeildupdate

    );

    @GET("CreateContact2View/")
    Call<List<Contact>> getContactApis(@Query("staffpk") String staffpk);


    @GET("CreateSegmentView/")
    Call<List<Segment>> getSegmentApis(@Query("staffpk") String staffpk);

    @POST("CreateSegmentView/")
    @FormUrlEncoded
    Call<List<Segment>> getSegmentApis(
            @Field("name") String name,
            @Field("dateone") String dateone,
            @Field("datetwo") String datetwo,
            @Field("dateofcreation") String dateofcreation,
            @Field("staffpk") String staffpk

    );


    @GET("CreateContact3View/")
    Call<List<Contact>> getDateContactApis(
            @Query("date1") String date1,
            @Query("date2") String date2,
            @Query("staffpk") String staffpk

    );

    @POST("CreateSegmentIdToContactView/")
    @FormUrlEncoded
    Call<List<Contact>> getSegmentIdToContactApis(
            @Field("id") String id
    );

    @GET("GetIsRegisteredEmailApis/")
    Call<List<Contact>> getIsRegisteredEmailApis(
            @Query("emailaddress") String emailaddress

    );

    @POST("CreateRegisterAccountApis/")
    @FormUrlEncoded
    Call<Staff> getRegisterAccountApis(
            @Field("username") String username,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("emailaddress") String emailaddress,
            @Field("password") String password,
            @Field("industry") String industry
    );

    @GET("GetRecoverPasswordApis/")
    Call<Staff> getRecoverPasswordApis(@Query("emailaddress") String emailaddress);


}
