package com.demo4.grupo9.aplicacionvirtual.Activitys.Interfaces;

import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.Compra;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.Movie;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WeatherService {
    @GET("v1/movies/")
    Call<List<Movie>> getFilms();

    @GET("v1/")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST ("v1/")
    Call<ResponseBody> addUsers(
            @Field("DNI") int DNI,
            @Field("name") String name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("v1/csale/")
    Call<ResponseBody> addCompras(
            @Field("User") int iduser,
            @Field("Movie") int idmovie,
            @Field("price") int precio,
            @Field("date") String fecha,
            @Field("time") String hora,
            @Field("place") String lugar,
            @Field("room_cinema") String sala

    );

//    @POST("v1/csale/")
//    Call<ResponseBody> addCompras(@Body Compra compra);





}
