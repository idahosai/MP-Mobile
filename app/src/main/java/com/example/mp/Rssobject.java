package com.example.mp;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Rssobject implements Parcelable {
    public String status;
    public Feed feed;
    public List<Item> items;


    public Rssobject(String status, Feed feed, List<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    protected Rssobject(Parcel in) {
        status = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<Rssobject> CREATOR = new Creator<Rssobject>() {
        @Override
        public Rssobject createFromParcel(Parcel in) {
            return new Rssobject(in);
        }

        @Override
        public Rssobject[] newArray(int size) {
            return new Rssobject[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeTypedList(items);
    }
}

