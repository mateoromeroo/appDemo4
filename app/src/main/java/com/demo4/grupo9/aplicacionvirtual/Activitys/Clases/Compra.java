package com.demo4.grupo9.aplicacionvirtual.Activitys.Clases;

public class Compra {

    private User User;
    private Movie Movie;

    private int price;
    private String date;
    private String time;
    private String place;
    private String room_cinema;


    public Compra(User user, Movie movie, int price, String date, String time, String place, String room_cinema) {
        this.User = user;
        this.Movie = movie;
        this.price = price;
        this.date = date;
        this.time = time;
        this.place = place;
        this.room_cinema = room_cinema;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }

    public Movie getMovie() {
        return Movie;
    }

    public void setMovie(Movie movie) {
        this.Movie = movie;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRoom_cinema() {
        return room_cinema;
    }

    public void setRoom_cinema(String room_cinema) {
        this.room_cinema = room_cinema;
    }
}
