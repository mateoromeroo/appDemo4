package pe.edu.unmsm.software.aplicacionpasteleria.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Usuario;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Button btnIniciarSesion;
    RequestQueue rq;
    JsonRequest jrq;
    EditText txtID, txtPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtID = findViewById(R.id.correoLogin);
        txtPw = findViewById(R.id.contrLogin);

        rq = Volley.newRequestQueue(this);

        //Dando valores a las variables
        btnIniciarSesion = (Button)findViewById(R.id.btnIniciarLogin);

        //Ingresar a la pantalla Inicio
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSession();
            }
        });
    }

    private void irVentanaInicio() {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }

    private void iniciarSession() {
        String url = "https://diegosv10.000webhostapp.com/login.php?user="+txtID.getText().toString()+"&pw="+
                txtPw.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"No se encontró usuario",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Se encontró usuario "+txtID.getText().toString(),Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            ComunicacionFragments.user.setDni(jsonObject.optString("dni_cliente"));
            ComunicacionFragments.user.setNombre(jsonObject.optString("nombre"));
            ComunicacionFragments.user.setApellido(jsonObject.optString("apellido"));
            ComunicacionFragments.user.setDireccion(jsonObject.optString("direccion"));
            ComunicacionFragments.user.setDistrito(jsonObject.optString("distrito"));
            String dateStr = jsonObject.getString("fecha_nacimiento");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(dateStr);
            ComunicacionFragments.user.setFechaNacimiento(birthDate);
            ComunicacionFragments.user.setCelular(jsonObject.optString("celular"));
            ComunicacionFragments.user.setFoto(jsonObject.optString("foto_usuario"));
            ComunicacionFragments.user.setCorreo(jsonObject.optString("correo"));
            ComunicacionFragments.user.setPassword(jsonObject.optString("pasword"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        irVentanaInicio();
    }
}
