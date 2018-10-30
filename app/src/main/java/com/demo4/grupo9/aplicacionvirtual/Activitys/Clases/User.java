package com.demo4.grupo9.aplicacionvirtual.Activitys.Clases;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id;
    private int DNI;
    private String name;
    private String last_name;
    private String email;
    private String password;


    public User(int id,int DNI, String name, String last_name, String email, String password){
        this.id = id;
        this.DNI = DNI;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected User(Parcel in) {
        id = in.readInt();
        DNI = in.readInt();
        name = in.readString();
        last_name = in.readString();
        email = in.readString();
        password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(DNI);
        dest.writeString(name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(password);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
