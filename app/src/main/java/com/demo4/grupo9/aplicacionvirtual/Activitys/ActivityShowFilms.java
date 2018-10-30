package com.demo4.grupo9.aplicacionvirtual.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.Adaptador;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.Movie;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.User;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Interfaces.WeatherService;
import com.demo4.grupo9.aplicacionvirtual.R;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityShowFilms extends AppCompatActivity {

    private User user;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_films);

        user = getIntent().getExtras().getParcelable("usuario");     //

        //----------------- Adaptador retrofit ---------------------//

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.184.16/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        //------------------------------------------------------------//

        mRecyclerView =  findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setHasFixedSize(true);  // Cuando tiene seguridad de que el recylcer view no crecera mas
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Call<List<Movie>> filmscall = service.getFilms();



        filmscall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()){
                    List<Movie> movies = response.body();
                    mAdapter = new Adaptador(movies, R.layout.recycler_view_item, new Adaptador.OnitemClickListener() {
                        @Override

                        public void onItemClick(Movie movie, int position) {
                            Intent intent = new Intent(ActivityShowFilms.this, Activity_compras.class);
                            intent.putExtra("usuario2", user);
                            intent.putExtra("pelicula2", movie);
                            startActivity(intent);

                        }
                    });
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    Toast.makeText(ActivityShowFilms.this,"Hubo un problema al cargar los datos",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(ActivityShowFilms.this, "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });





















    }

}
