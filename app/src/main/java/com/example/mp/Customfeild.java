package com.example.mp;

public class Customfeild {

    Integer id;
    String name;
    Integer customfeildintvalue;
    String customfeildstringvalue;
    //i need to check the data type of this latter
    //is it Date or String
    String dateofcreation;
    String lastcustomfeildupdate;

    public Customfeild(Integer id, String name, Integer customfeildintvalue, String customfeildstringvalue, String dateofcreation, String lastcustomfeildupdate) {
        this.id = id;
        this.name = name;
        this.customfeildintvalue = customfeildintvalue;
        this.customfeildstringvalue = customfeildstringvalue;
        this.dateofcreation = dateofcreation;
        this.lastcustomfeildupdate = lastcustomfeildupdate;
    }
    public Customfeild(Integer id, String name, String customfeildstringvalue, String dateofcreation, String lastcustomfeildupdate) {
        this.id = id;
        this.name = name;
        this.customfeildstringvalue = customfeildstringvalue;
        this.dateofcreation = dateofcreation;
        this.lastcustomfeildupdate = lastcustomfeildupdate;
    }
    public Customfeild(String name, String customfeildstringvalue, String dateofcreation, String lastcustomfeildupdate) {

        this.name = name;
        this.customfeildstringvalue = customfeildstringvalue;
        this.dateofcreation = dateofcreation;
        this.lastcustomfeildupdate = lastcustomfeildupdate;
    }

    public Customfeild(String name, Integer customfeildintvalue, String customfeildstringvalue, String dateofcreation, String lastcustomfeildupdate) {

        this.name = name;
        this.customfeildintvalue = customfeildintvalue;
        this.customfeildstringvalue = customfeildstringvalue;
        this.dateofcreation = dateofcreation;
        this.lastcustomfeildupdate = lastcustomfeildupdate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCustomfeildintvalue() {
        return customfeildintvalue;
    }

    public String getCustomfeildstringvalue() {
        return customfeildstringvalue;
    }

    public String getDateofcreation() {
        return dateofcreation;
    }

    public String getLastcustomfeildupdate() {
        return lastcustomfeildupdate;
    }
}
