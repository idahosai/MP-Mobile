package com.example.mp;

public class Email {

    String id;
    String name;
    String status;
    String type;
    String numberofcontactssentto;
    Double totalearnings;
    String averageearningsperclick;
    String attachedstatsforautomation_id;
    String attachedstatsforautomationbroadcast_id;
    String dateofcreation;
    String senddateandtime;
    Double openrate;
    Double unsubscribers;
    Double clickrate;
    Double averageuserrating;
    String subjecttitle;
    Integer maxrowdown;
    Integer maxcolumnright;
    Integer attachedemailimage_id;
    Integer attachedemailtextmakeup_id;
    Integer attachedemaildiscussion_id;
    Integer hasbeenrated;
    Integer attachedemail_id;


    public Email(String id, String name, String status, String type, String numberofcontactssentto, Double totalearnings,
                 String averageearningsperclick, String attachedstatsforautomation_id, String attachedstatsforautomationbroadcast_id,
                 String dateofcreation, String senddateandtime, Double openrate, Double unsubscribers, Double clickrate, Double averageuserrating,
                 String subjecttitle, Integer maxrowdown, Integer maxcolumnright, Integer attachedemailimage_id, Integer attachedemailtextmakeup_id,
                 Integer attachedemaildiscussion_id, Integer hasbeenrated, Integer attachedemail_id){

        this.id = id;
        this.name = name;
        this.status = status;
        this.type = type;
        this.numberofcontactssentto = numberofcontactssentto;
        this.totalearnings = totalearnings;
        this.averageearningsperclick = averageearningsperclick;
        this.attachedstatsforautomation_id = attachedstatsforautomation_id;
        this.attachedstatsforautomationbroadcast_id = attachedstatsforautomationbroadcast_id;
        this.dateofcreation = dateofcreation;
        this.senddateandtime = senddateandtime;
        this.openrate = openrate;
        this.unsubscribers = unsubscribers;
        this.clickrate = clickrate;
        this.averageuserrating = averageuserrating;
        this.subjecttitle = subjecttitle;
        this.maxrowdown = maxrowdown;
        this.maxcolumnright = maxcolumnright;
        this.attachedemailimage_id = attachedemailimage_id;
        this.attachedemailtextmakeup_id = attachedemailtextmakeup_id;
        this.attachedemaildiscussion_id = attachedemaildiscussion_id;
        this.hasbeenrated = hasbeenrated;
        this.attachedemail_id = attachedemail_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getNumberofcontactssentto() {
        return numberofcontactssentto;
    }

    public Double getTotalearnings() {
        return totalearnings;
    }

    public String getAverageearningsperclick() {
        return averageearningsperclick;
    }

    public String getAttachedstatsforautomation_id() {
        return attachedstatsforautomation_id;
    }

    public String getAttachedstatsforautomationbroadcast_id() {
        return attachedstatsforautomationbroadcast_id;
    }

    public String getDateofcreation() {
        return dateofcreation;
    }

    public String getSenddateandtime() {
        return senddateandtime;
    }

    public Double getOpenrate() {
        return openrate;
    }

    public Double getUnsubscribers() {
        return unsubscribers;
    }

    public Double getClickrate() {
        return clickrate;
    }

    public Double getAverageuserrating() {
        return averageuserrating;
    }

    public String getSubjecttitle() {
        return subjecttitle;
    }

    public Integer getMaxrowdown() {
        return maxrowdown;
    }

    public Integer getMaxcolumnright() {
        return maxcolumnright;
    }

    public Integer getAttachedemailimage_id() {
        return attachedemailimage_id;
    }

    public Integer getAttachedemailtextmakeup_id() {
        return attachedemailtextmakeup_id;
    }

    public Integer getAttachedemaildiscussion_id() {
        return attachedemaildiscussion_id;
    }

    public Integer getHasbeenrated() {
        return hasbeenrated;
    }

    public Integer getAttachedemail_id() {
        return attachedemail_id;
    }
}
