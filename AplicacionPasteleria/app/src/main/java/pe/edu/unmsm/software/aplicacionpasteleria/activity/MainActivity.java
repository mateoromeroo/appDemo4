package pe.edu.unmsm.software.aplicacionpasteleria.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pe.edu.unmsm.software.aplicacionpasteleria.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonRegistrarse;
    private Button buttonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dando valores a las variables
        buttonIniciarSesion = (Button)findViewById(R.id.btnIniciarMain);
        buttonRegistrarse = (Button)findViewById(R.id.btnRegMain);

        //Ingresar a la pantalla Login
        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irVentanaIniciarSesion();
            }
        });

        //Ingresar a la pantalla Registro
        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irVentanaRegistrar();
            }
        });


    }

    public void irVentanaRegistrar(){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }

    public void irVentanaIniciarSesion(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
