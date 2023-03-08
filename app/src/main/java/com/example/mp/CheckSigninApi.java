package com.example.mp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckSigninApi implements Parcelable {

    private String model;

    //our text is supposed to be the staff value so we put thiis
    @SerializedName("pk")
    private String pk;

    private Staff fields;

    protected CheckSigninApi(Parcel in) {
        model = in.readString();
        pk = in.readString();
        fields = in.readParcelable(Staff.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeString(pk);
        dest.writeParcelable(fields, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckSigninApi> CREATOR = new Creator<CheckSigninApi>() {
        @Override
        public CheckSigninApi createFromParcel(Parcel in) {
            return new CheckSigninApi(in);
        }

        @Override
        public CheckSigninApi[] newArray(int size) {
            return new CheckSigninApi[size];
        }
    };

    public String getModel() {
        return model;
    }

    public String getPk() { return pk; }

    public Staff getFields() {
        return fields;
    }
}
