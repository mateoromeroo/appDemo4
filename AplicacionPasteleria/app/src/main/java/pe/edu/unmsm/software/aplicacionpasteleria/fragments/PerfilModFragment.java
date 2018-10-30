package pe.edu.unmsm.software.aplicacionpasteleria.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.activity.RegistroActivity;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class PerfilModFragment extends Fragment {


    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    RequestQueue queue;
    EditText txtApellido,txtName,txtDireccion,txtDistrito ,txtCelular , txtCorreo,txtPw, txtDNI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_mod, container, false);
        queue = Volley.newRequestQueue(getContext());
        txtApellido =  view.findViewById(R.id.apellModPerfil);
        txtName =  view.findViewById(R.id.nomModPerfil);
        txtDireccion = view.findViewById(R.id.direcModPerfil);
        txtDistrito =  view.findViewById(R.id.distrModPerfil);
        mDisplayDate =  view.findViewById(R.id.fech_NacModPerfil);
        txtCelular =  view.findViewById(R.id.celuModPerfil);
        txtCorreo =  view.findViewById(R.id.correoModPerfil);
        txtPw =   view.findViewById(R.id.contrModPerfil);
        txtDNI = view.findViewById(R.id.dniModPerfil);

        // DATE
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
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
                //Log.d(TAG, "onDateSet: mm/dd/yyyy: "+month+"/"+day+"/"+year);
                String date = month+"/"+day+"/"+year;
                mDisplayDate.setText(date);
            }
        };


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.btnGuarModPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        view.findViewById(R.id.btnCancelarModPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Retrocediendo", Toast.LENGTH_LONG).show();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.screen_area,new PerfilFragment());
                fr.commit();
            }
        });
    }

    private void cargarWebService() {
        String url = "https://diegosv10.000webhostapp.com/actualizarPerfil.php";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonBody = new JSONObject();
            // Estos son los datos que se usan pero no me salió :c sry
            jsonBody.put("apellido", txtApellido.toString());
            jsonBody.put("nombre", txtName.toString());
            jsonBody.put("dni", txtDNI.toString());
            jsonBody.put("direccion", txtDireccion.toString());
            jsonBody.put("distrito", txtDistrito.toString());
            jsonBody.put("fecha_nacimiento", mDisplayDate.toString());
            jsonBody.put("celular", txtCelular.toString());
            /////////////////////////// falta imagen eso aún no entiendo donde estan (preguntar a lusho)
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    // pasar los datos al usuario conectado para que se actualice en visualizar perfil
                    ComunicacionFragments.user.setApellido(txtApellido.toString());
                    ComunicacionFragments.user.setNombre(txtName.toString());
                    ComunicacionFragments.user.setDni(txtDNI.toString());
                    ComunicacionFragments.user.setDireccion(txtDireccion.toString());
                    ComunicacionFragments.user.setDistrito(txtDistrito.toString());
                    ComunicacionFragments.user.setFechaNacimiento(new Date(mDisplayDate.toString()));
                    ComunicacionFragments.user.setCelular(txtCelular.toString());

                    Toast.makeText(getActivity(), "Cambios Guardados", Toast.LENGTH_LONG).show();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.screen_area,new PerfilFragment());
                    fr.commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
