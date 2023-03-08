package com.example.mp;

public class Contact {

    String status;
    //json will set this to null when sending to api
    Integer id;

    Double lifetimevalue;
    //i need to check the data type of this latter
    //is it Date or String
    String datejoined;
    String notes;
    String emailaddress;
    String firstname;
    String lastname;
    String jobtitle;
    String company;
    String mobilephone;
    String workphone;
    String country;
    String stateprovince;
    String city;
    String address;
    String zip;
    String website;
    String stopmethod;
    String confirmquestionmark;
    String addmethod;
    String signupsource;
    String totalreviewsleft;
    String lastemailratingdone;

    public Contact(String status, Integer id, Double lifetimevalue, String datejoined, String notes, String emailaddress, String firstname, String lastname, String jobtitle, String company, String mobilephone, String workphone, String country, String stateprovince, String city, String address, String zip, String website, String stopmethod, String confirmquestionmark, String addmethod, String signupsource, String totalreviewsleft, String lastemailratingdone) {
        this.status = status;
        this.id = id;
        this.lifetimevalue = lifetimevalue;
        this.datejoined = datejoined;
        this.notes = notes;
        this.emailaddress = emailaddress;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobtitle = jobtitle;
        this.company = company;
        this.mobilephone = mobilephone;
        this.workphone = workphone;
        this.country = country;
        this.stateprovince = stateprovince;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.website = website;
        this.stopmethod = stopmethod;
        this.confirmquestionmark = confirmquestionmark;
        this.addmethod = addmethod;
        this.signupsource = signupsource;
        this.totalreviewsleft = totalreviewsleft;
        this.lastemailratingdone = lastemailratingdone;
    }

    public Contact(Double lifetimevalue, String datejoined, String emailaddress, String firstname, String lastname) {
        this.lifetimevalue = lifetimevalue;
        this.datejoined = datejoined;
        this.emailaddress = emailaddress;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    //for not sending id to post api
    public Contact(String datejoined, String notes, String emailaddress, String firstname, String lastname, String jobtitle, String company, String mobilephone, String workphone, String country, String stateprovince, String city, String address, String zip, String website, String addmethod, String signupsource) {
        this.datejoined = datejoined;
        this.notes = notes;
        this.emailaddress = emailaddress;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobtitle = jobtitle;
        this.company = company;
        this.mobilephone = mobilephone;
        this.workphone = workphone;
        this.country = country;
        this.stateprovince = stateprovince;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.website = website;
        this.addmethod = addmethod;
        this.signupsource = signupsource;

    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Double getLifetimevalue() {
        return lifetimevalue;
    }

    public String getDatejoined() {
        return datejoined;
    }

    public String getNotes() {
        return notes;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public String getCompany() {
        return company;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public String getWorkphone() {
        return workphone;
    }

    public String getCountry() {
        return country;
    }

    public String getStateprovince() {
        return stateprovince;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getWebsite() {
        return website;
    }

    public String getStopmethod() {
        return stopmethod;
    }

    public String getConfirmquestionmark() {
        return confirmquestionmark;
    }

    public String getAddmethod() {
        return addmethod;
    }

    public String getSignupsource() {
        return signupsource;
    }

    public String getTotalreviewsleft() {
        return totalreviewsleft;
    }

    public String getLastemailratingdone() {
        return lastemailratingdone;
    }
}
