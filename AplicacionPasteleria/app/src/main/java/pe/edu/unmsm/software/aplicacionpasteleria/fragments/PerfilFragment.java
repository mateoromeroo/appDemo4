package pe.edu.unmsm.software.aplicacionpasteleria.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONObject;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;


public class PerfilFragment extends Fragment {


    private OnFragmentInteractionListener mListener;


    private RequestQueue rq;
    private JsonRequest jrq;

    /*Mis parámetros */
    EditText apellidos;
    EditText nombres;
    EditText direccion;
    EditText distrito;
    EditText fechaNac;
    EditText celular;
    EditText correo;
    EditText contra;
    Button modiDatos;
    Button cambiarDatos;
    Button modificarDatos;


    TextView txtApellido,txtName,txtDireccion,txtDistrito,txtfechaNac ,txtCelular , txtCorreo;

    ///////////////Agregue
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnModPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.screen_area,new PerfilModFragment());
                fr.commit();
                Toast.makeText(getActivity(), "Ingres a PerfilModFragment", Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.btnpPhotoPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "CambiandoFOTOPeril", Toast.LENGTH_LONG).show();
            }
        });

    }
    ///////////////Finnn


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        rq = Volley.newRequestQueue(getContext());

        txtApellido =  view.findViewById(R.id.apellPerfil);
        txtName =  view.findViewById(R.id.nomPerfil);
        txtDireccion = view.findViewById(R.id.direcPerfil);
        txtDistrito =  view.findViewById(R.id.distrPerfil);
        txtfechaNac =  view.findViewById(R.id.fech_NacPerfil);
        txtCelular =  view.findViewById(R.id.celuPerfil);
        txtCorreo =  view.findViewById(R.id.correoPerfil);

        txtApellido.setText(ComunicacionFragments.user.getApellido());
        txtName.setText(ComunicacionFragments.user.getNombre());
        txtDireccion.setText(ComunicacionFragments.user.getDireccion());
        txtDistrito.setText(ComunicacionFragments.user.getDistrito());
        txtfechaNac.setText(ComunicacionFragments.user.getFechaNacimiento().toString());
        txtCelular.setText(ComunicacionFragments.user.getCelular());
        txtCorreo.setText(ComunicacionFragments.user.getCorreo());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
