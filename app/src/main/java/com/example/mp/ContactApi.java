package com.example.mp;

import com.google.gson.annotations.SerializedName;

public class ContactApi {

    private String model;

    //our text is supposed to be the staff value so we put thiis
    //@SerializedName("pk")
    private String pk;

    private Contact fields;

    public String getModel() {
        return model;
    }

    public String getPk() {
        return pk;
    }

    public Contact getFields() {
        return fields;
    }
}
