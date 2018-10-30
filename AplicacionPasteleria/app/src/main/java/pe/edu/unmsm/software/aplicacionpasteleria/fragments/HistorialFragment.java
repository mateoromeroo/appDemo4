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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.Canasta;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.adapters.AdaptadorArticulos;
import pe.edu.unmsm.software.aplicacionpasteleria.adapters.AdaptadorPedidos;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Pedido;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Pedido> listaPedidos;
    private JsonObjectRequest jrq;
    private RequestQueue rq;
    private RecyclerView recyclerPedidos;
    private ProgressDialog dialog;
    private DetallePedidoFragment detallePedidoFragment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialFragment newInstance(String param1, String param2) {
        HistorialFragment fragment = new HistorialFragment();
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
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        rq = Volley.newRequestQueue(getContext());
        listaPedidos=new ArrayList<>();

        recyclerPedidos = (RecyclerView) view.findViewById(R.id.idRecyclerPedidos);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerPedidos.setHasFixedSize(true);

        cargarWebService();

        return view;
    }

    private void cargarWebService() {
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Consultando Pedidos");
        dialog.show();

        //ComunicacionFragments.user.getDni()
        String url="https://diegosv10.000webhostapp.com/listaPedidosUsuario.php?dni="+ComunicacionFragments.user.getDni();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Pedido pedido = null;
        JSONArray json=response.optJSONArray("pedidos");

        try {

            for (int i=0;i<json.length();i++){
                pedido = new Pedido();
                JSONObject jsonObject=null;
                jsonObject = json.getJSONObject(i);


                /////
                pedido.setId(jsonObject.optInt("id_pedidos"));
                pedido.setDireccion(jsonObject.optString("direccion_envio"));
                //casteo de fecha posible error ?
                String dateStr = jsonObject.getString("fecha_orden");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate = sdf.parse(dateStr);
                pedido.setFechaOrden(birthDate);

                dateStr = jsonObject.getString("fecha_entrega");
                birthDate = sdf.parse(dateStr);
                pedido.setFechaEntrega(birthDate);
                /////
                pedido.setHoraEntrega(jsonObject.optString("hora_entrega"));
                pedido.setEstado(jsonObject.optInt("estado"));
                pedido.setDniCliente(jsonObject.optString("Clientes_dni_cliente"));
                pedido.setIdCupon(jsonObject.optInt("Cupones_id_cupones"));
                listaPedidos.add(pedido);
            }
            dialog.hide();
            AdaptadorPedidos adapter=new AdaptadorPedidos(listaPedidos);

            adapter.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ComunicacionFragments.pedido = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(v));
                    detallePedidoFragment = new DetallePedidoFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.screen_area,detallePedidoFragment).addToBackStack(null).commit();
                }
            });

            recyclerPedidos.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            dialog.hide();
        } catch (ParseException e) {
            e.printStackTrace();
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
