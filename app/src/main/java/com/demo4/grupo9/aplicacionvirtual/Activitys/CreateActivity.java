package com.demo4.grupo9.aplicacionvirtual.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo4.grupo9.aplicacionvirtual.Activitys.Interfaces.WeatherService;
import com.demo4.grupo9.aplicacionvirtual.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_activuty);



        final EditText textDNI = findViewById(R.id.editText4);
        final EditText textName = findViewById(R.id.editText5);
        final EditText textLastname = findViewById(R.id.editText6);
        final EditText textEmail = findViewById(R.id.editText8);
        final EditText textPassword = findViewById(R.id.editText9);


        Button btn1 = findViewById(R.id.buttonagregar);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (!textDNI.getText().toString().isEmpty() && !textName.getText().toString().isEmpty() && !textLastname.getText().toString().isEmpty()
                        && !textEmail.getText().toString().isEmpty() && !textPassword.getText().toString().isEmpty()){

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


                    Call<ResponseBody> callableResponse = service.addUsers(Integer.parseInt(textDNI.getText().toString()),textName.getText().toString(),textLastname.getText().toString(),textEmail.getText().toString(),textPassword.getText().toString());
                    callableResponse.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                Toast.makeText(CreateActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CreateActivity.this,"Error al enviar",Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);


                }else {
                    Toast.makeText(CreateActivity.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button btn2 = findViewById(R.id.buttonvolver);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), MainActivity.class);
//                startActivityForResult(intent, 0);

                onBackPressed();
            }
        });
    }
}
