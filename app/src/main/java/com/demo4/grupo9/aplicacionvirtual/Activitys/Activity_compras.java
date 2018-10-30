package com.demo4.grupo9.aplicacionvirtual.Activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.Compra;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.Movie;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.User;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Interfaces.WeatherService;
import com.demo4.grupo9.aplicacionvirtual.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_compras extends AppCompatActivity {

    private User user;
    private Movie movie;
    private int dia,mes,ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_compras);

        user = getIntent().getExtras().getParcelable("usuario2");
        movie = getIntent().getExtras().getParcelable("pelicula2");

        TextView nombre = findViewById(R.id.textViewName);
        TextView duracion = findViewById(R.id.textViewDuration);
        TextView descripcion = findViewById(R.id.textViewDescription);
        final TextView fecha = findViewById(R.id.textViewDate);
        ImageView imagen = findViewById(R.id.imagenPortada);
        final TextView viewfech = findViewById(R.id.viewFecha);


        final Spinner precios = findViewById(R.id.SpinnerPrecio);
        final Spinner horas = findViewById(R.id.SpinnerHora);
        final Spinner salas = findViewById(R.id.SpinnerSala);
        final Spinner lugares = findViewById(R.id.SpinnerLugar);

        Button volver = findViewById(R.id.bttnvolver);
        Button comprar = findViewById(R.id.bttncomprar);

        ArrayAdapter<CharSequence> adapterPrecios = ArrayAdapter.createFromResource(this,R.array.priceoptions, android.R.layout.simple_spinner_item);
        precios.setAdapter(adapterPrecios);

        ArrayAdapter<CharSequence> adapterHoras = ArrayAdapter.createFromResource(this,R.array.horasoptions, android.R.layout.simple_spinner_item);
        horas.setAdapter(adapterHoras);

        ArrayAdapter<CharSequence> adapterSalas = ArrayAdapter.createFromResource(this,R.array.salasoptions, android.R.layout.simple_spinner_item);
        salas.setAdapter(adapterSalas);

        ArrayAdapter<CharSequence> adapterLugares = ArrayAdapter.createFromResource(this,R.array.lugaresoptions, android.R.layout.simple_spinner_item);
        lugares.setAdapter(adapterLugares);

        nombre.setText(nombre.getText().toString()+ movie.getName());
        duracion.setText(duracion.getText().toString()+ movie.getDuration());
        descripcion.setText(descripcion.getText().toString()+ movie.getDescription());
        fecha.setText(fecha.getText().toString()+ movie.getDate());
        Picasso.with(this).load(movie.getPhoto()).fit().into(imagen);

        viewfech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get((Calendar.MONTH));
                ano = c.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_compras.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        viewfech.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },dia,mes,ano);
               // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!precios.getSelectedItem().toString().isEmpty() && !horas.getSelectedItem().toString().isEmpty()
                        && !salas.getSelectedItem().toString().isEmpty() && !lugares.getSelectedItem().toString().isEmpty()
                        && !viewfech.getText().toString().equals("Seleccionar fecha")){

                    //--------------------Adaptador retrofit -------------------------------

                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://206.189.184.16/api/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    WeatherService service = retrofit.create(WeatherService.class);

                    //-----------------------------------------------------------------------

                    Call<ResponseBody> callableResponse = service.addCompras(user.getId(),movie.getId(),Integer.parseInt(precios.getSelectedItem().toString()),viewfech.getText().toString(),
                            horas.getSelectedItem().toString(),lugares.getSelectedItem().toString(),salas.getSelectedItem().toString());
//

//                     Call<ResponseBody> callableResponse = service.addCompras(1, 2,10,"2018-10-05","09:00","San Miguel","05");


                    callableResponse.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try{
                                Toast.makeText(Activity_compras.this, response.body().string(), Toast.LENGTH_SHORT).show();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(Activity_compras.this,"Error al enviar",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(Activity_compras.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
                }

                onBackPressed();
            }
        });
    }
}
