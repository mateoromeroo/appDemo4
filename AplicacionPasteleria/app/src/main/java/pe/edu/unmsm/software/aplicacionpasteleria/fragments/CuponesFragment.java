package pe.edu.unmsm.software.aplicacionpasteleria.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.adapters.AdaptadorArticulos;
import pe.edu.unmsm.software.aplicacionpasteleria.adapters.AdaptadorCupones;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Cupones;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CuponesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CuponesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuponesFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Cupones> listaCupones;
    private JsonObjectRequest jrq;
    private RequestQueue rq;
    private RecyclerView recyclerCupones;
    private ProgressDialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CuponesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CuponesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CuponesFragment newInstance(String param1, String param2) {
        CuponesFragment fragment = new CuponesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cupones, container, false);
        rq = Volley.newRequestQueue(getContext());
        listaCupones=new ArrayList<>();

        recyclerCupones = (RecyclerView) view.findViewById(R.id.idRecyclerCupones);
        recyclerCupones.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerCupones.setHasFixedSize(true);

        cargarWebService();

        return view;
    }

    private void cargarWebService() {

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Cargando cupones");
        dialog.show();

        String url="https://diegosv10.000webhostapp.com/listarCupones.php";
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Algo salió mal :'v",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Cupones cupon=null;

        JSONArray json=response.optJSONArray("datos");

        try {

            for (int i=0;i<json.length();i++){
                cupon=new Cupones();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                cupon.setId(jsonObject.optInt("id_cupones"));
                cupon.setNombre(jsonObject.optString("nombre_cupon"));
                cupon.setDescripcion(jsonObject.optString("detalle_cupon"));
                cupon.setCodigo(jsonObject.optString("codigo_cupon"));
                cupon.setDato(jsonObject.optString("img_cupon"));
                cupon.setCantidad(jsonObject.optInt("cantidad_cupon"));
                cupon.setDescuento(jsonObject.optDouble("descuento"));
                listaCupones.add(cupon);
            }
            dialog.hide();
            AdaptadorCupones adapter=new AdaptadorCupones(listaCupones);

            recyclerCupones.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            dialog.hide();
        }
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
