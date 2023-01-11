package com.example.mp;

public class Attachedcustomfeild {

    Integer id;
    //i need to check the data type of this latter
    //is it Date or String
    String dateofattachement;
    Integer contactid;
    Integer customfeildid;

    public Attachedcustomfeild(Integer id, String dateofattachement, Integer contactid, Integer customfeildid) {
        this.id = id;
        this.dateofattachement = dateofattachement;
        this.contactid = contactid;
        this.customfeildid = customfeildid;
    }

    public Integer getId() {
        return id;
    }

    public String getDateofattachement() {
        return dateofattachement;
    }

    public Integer getContactid() {
        return contactid;
    }

    public Integer getCustomfeildid() {
        return customfeildid;
    }
}
