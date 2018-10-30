package com.demo4.grupo9.aplicacionvirtual.Activitys.Clases;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String name;
    private String duration;
    private String description;
    private String date;
    private String photo;

    public Movie(int id, String name, String duration, String description, String date, String photo) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.date = date;
        this.photo = photo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        duration = in.readString();
        description = in.readString();
        date = in.readString();
        photo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(duration);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(photo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

//public class Movie implements Parcelable {
//
//    private int id;
//    private String name;
//    private String duration;
//    private String description;
//    private String date;
//    private String photo;
//
//    public Movie(int id, String name, String duration, String description, String date, String photo){
//        this.setId(id);
//        this.setName(name);
//        this.setDuration(duration);
//        this.setDescription(description);
//        this.setDate(date);
//        this.setPhoto(photo);
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDuration() {
//        return duration;
//    }
//
//    public void setDuration(String duration) {
//        this.duration = duration;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }
//
//    protected Movie(Parcel in) {
//        id = in.readInt();
//        name = in.readString();
//        duration = in.readString();
//        description = in.readString();
//        date = in.readString();
//        photo = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeString(duration);
//        dest.writeString(description);
//        dest.writeString(date);
//        dest.writeString(photo);
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
//        @Override
//        public Movie createFromParcel(Parcel in) {
//            return new Movie(in);
//        }
//
//        @Override
//        public Movie[] newArray(int size) {
//            return new Movie[size];
//        }
//    };
//}
