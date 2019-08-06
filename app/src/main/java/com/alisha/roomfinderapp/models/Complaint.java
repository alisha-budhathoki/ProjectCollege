package com.alisha.roomfinderapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Complaint implements Parcelable {
    public static final Creator<Complaint> CREATOR = new Creator<Complaint>() {
        @Override
        public Complaint createFromParcel(Parcel in) {
            return new Complaint(in);
        }

        @Override
        public Complaint[] newArray(int size) {
            return new Complaint[size];
        }
    };
    public String id;
    public String post_id;
    public String user_id;
    public String username;
    public String description;

    public Complaint(String id, String post_id, String user_id, String username, String description) {
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.username = username;
        this.description = description;
    }

    public Complaint() {
    }

    protected Complaint(Parcel in) {
        id = in.readString();
        post_id = in.readString();
        user_id = in.readString();
        username = in.readString();
        description = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(post_id);
        parcel.writeString(user_id);
        parcel.writeString(username);
        parcel.writeString(description);
    }
}
