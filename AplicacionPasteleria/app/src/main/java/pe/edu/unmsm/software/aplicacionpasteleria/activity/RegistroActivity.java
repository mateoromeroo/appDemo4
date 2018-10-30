package pe.edu.unmsm.software.aplicacionpasteleria.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Usuario;

public class RegistroActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private static final String TAG = "RegistroActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Button btnRegistrar;


    RequestQueue rq;
    JsonRequest jrq;

    EditText txtDNI,txtApellido,txtName,txtDireccion,txtDistrito ,txtCelular , txtCorreo,txtPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // falta agregar dni y probar la conxion

        txtDNI = (EditText) findViewById(R.id.dniRegistro);
        txtApellido = (EditText) findViewById(R.id.apellRegistro);
        txtName = (EditText) findViewById(R.id.nomRegistro);
        txtDireccion = (EditText) findViewById(R.id.direcRegistro);
        txtDistrito = (EditText) findViewById(R.id.distrRegistro);
        mDisplayDate = (TextView)findViewById(R.id.fech_NacRegistro);
        txtCelular = (EditText) findViewById(R.id.celuRegistro);
        txtCorreo = (EditText) findViewById(R.id.correoRegistro);
        txtPw = (EditText) findViewById(R.id.contrRegistro);
        btnRegistrar = (Button)findViewById(R.id.btnregRegistro);

        rq = Volley.newRequestQueue(this);

        // DATE
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistroActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: "+month+"/"+day+"/"+year);
                String date = month+"/"+day+"/"+year;
                mDisplayDate.setText(date);
            }
        };
        // FIN DATE
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });


    }

    private void registrar() {

        String url = "https://diegosv10.000webhostapp.com/registrar.php?dni="+txtDNI.getText().toString()+
                "&ape="+txtApellido.getText().toString()+
                "&name="+txtName.getText().toString()+
                "&dir="+txtDireccion.getText().toString()+
                "&fechN="+mDisplayDate.getText().toString()+
                "&cel="+txtCelular.getText().toString()+
                "&correo="+txtCorreo.getText().toString()+
                "&pw="+txtPw.getText().toString();


        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"No se registró usuario",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Se registró usuario "+txtName.getText().toString(),Toast.LENGTH_SHORT).show();
         // Error idkw
        ComunicacionFragments.user.setDni(txtDNI.toString());
        ComunicacionFragments.user.setNombre(txtName.toString());
        ComunicacionFragments.user.setApellido(txtApellido.toString());
        ComunicacionFragments.user.setCorreo(txtCorreo.toString());
        ComunicacionFragments.user.setCelular(txtCelular.toString());
        ComunicacionFragments.user.setDireccion(txtDireccion.toString());
        ComunicacionFragments.user.setDistrito(txtDistrito.toString());
        ComunicacionFragments.user.setPassword(txtPw.toString());
        //ComunicacionFragments.user.setFechaNacimiento(new Date(mDisplayDate.toString()));

        irVentanaInicio();
    }

    private void irVentanaInicio() {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }
}
