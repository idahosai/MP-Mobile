package com.example.mp;

public class Segment {


    Integer id;
    String name;
    String dateone;
    String datetwo;
    String dateofcreation;

    public Segment(String name, String dateone, String datetwo, String dateofcreation) {
        this.name = name;
        this.dateone = dateone;
        this.datetwo = datetwo;
        this.dateofcreation = dateofcreation;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateone() {
        return dateone;
    }

    public String getDatetwo() {
        return datetwo;
    }

    public String getDateofcreation() {
        return dateofcreation;
    }
}
