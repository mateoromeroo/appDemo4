package com.demo4.grupo9.aplicacionvirtual.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.demo4.grupo9.aplicacionvirtual.Activitys.Clases.User;
import com.demo4.grupo9.aplicacionvirtual.Activitys.Interfaces.WeatherService;
import com.demo4.grupo9.aplicacionvirtual.R;


import java.util.List;


import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);



        final EditText email = findViewById(R.id.inputemail);
        final EditText password = findViewById(R.id.inputpassword);

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

        //------------------------------------------------------------

        Call<List<User>> usersCall = service.getUsers();

        usersCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    users = response.body();
               }else {
                    Toast.makeText(MainActivity.this,"Hubo un problema al cargar los datos",Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });



        //-----------------------------------------------------

        CardView card = findViewById(R.id.cardView);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {             // pasa a la vista ver peliculas
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                    User user = UsuarioCorrecto(email.getText().toString(), password.getText().toString(), users);
                    if (user != null){
                        Intent intent = new Intent(v.getContext(), ActivityShowFilms.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this,"email o password incorrecto",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
                }


            }
        });

        TextView textView =  findViewById(R.id.Notienecuenta);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {             // pasa a la vista crearcuenta
                Intent intent = new Intent(v.getContext(), CreateActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    public User UsuarioCorrecto (String email, String password, List<User> userlist){
        if (users != null){
            for (int i=0; i< userlist.size(); i++){
                if (userlist.get(i).getEmail().equals(email)){
                    if (userlist.get(i).getPassword().equals(password)){
                        return userlist.get(i);
                    }
                }
            }
        }
        return null;
    }


}
