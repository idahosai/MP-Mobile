package com.example.mp;

import android.os.Parcel;
import android.os.Parcelable;

public class Staff implements Parcelable {
    private String userid;
    private String username;
    private String firstname;
    private String lastname;
    private String emailaddress;
    private String industry;

    protected Staff(Parcel in) {
        userid = in.readString();
        username = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        emailaddress = in.readString();
        industry = in.readString();
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailaddress() { return emailaddress; }

    public String getIndustry() {
        return industry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userid);
        parcel.writeString(username);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(emailaddress);
        parcel.writeString(industry);
    }
}
