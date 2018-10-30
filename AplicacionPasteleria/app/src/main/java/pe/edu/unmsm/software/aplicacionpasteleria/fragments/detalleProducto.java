package pe.edu.unmsm.software.aplicacionpasteleria.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.Canasta;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Producto;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link detalleProducto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link detalleProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detalleProducto extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView txtDescripcion;
    private EditText txtCantidad;
    private ImageView imageDescripcion;
    private Button agregarACarro;
    private Button comprarAhora;
    private JsonObjectRequest jrq;
    private RequestQueue rq;
    Producto producto = null;

    public detalleProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detalleProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static detalleProducto newInstance(String param1, String param2) {
        detalleProducto fragment = new detalleProducto();
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
        View view = inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        rq = Volley.newRequestQueue(getContext());

        txtDescripcion = view.findViewById(R.id.descripcionId);
        txtCantidad = view.findViewById(R.id.cantidadComprarProducto);
        imageDescripcion = view.findViewById(R.id.imagenDetalleId);
        agregarACarro = view.findViewById(R.id.btnAgregarCarrito);
        comprarAhora = view.findViewById(R.id.btnComprarAhoraProducto);

        int idProducto;

        idProducto = ComunicacionFragments.idProducto;
        cargarWebService(String.valueOf(idProducto));

        agregarACarro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!txtCantidad.getText().toString().equals("")) {
                    if (Canasta.get(producto.getId()) != null) {
                        if (Canasta.get(producto.getId()).getCantidadCarrito()
                                + Integer.parseInt(txtCantidad.getText().toString())
                                <= Canasta.get(producto.getId()).getCantidad()) {
                            guardarProducto();
                            Toast.makeText(getContext(),"Se agrego al carrito",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"Demasiados productos",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (Integer.parseInt(txtCantidad.getText().toString()) <= producto.getStock()) {
                            guardarProducto();
                            Toast.makeText(getContext(),"Se agrego al carrito",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"Demasiados productos",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(),"Elija cantidad de productos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        comprarAhora.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!txtCantidad.getText().toString().equals("")) {
                    if (Canasta.get(producto.getId()) != null) {
                        if (Canasta.get(producto.getId()).getCantidadCarrito()
                                + Integer.parseInt(txtCantidad.getText().toString())
                                <= Canasta.get(producto.getId()).getCantidad()) {
                            guardarProducto();
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            fr.replace(R.id.screen_area,new CarritoFragment());
                            fr.commit();
                        } else {
                            Toast.makeText(getContext(),"Demasiados productos",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (Integer.parseInt(txtCantidad.getText().toString()) <= producto.getStock()) {
                            guardarProducto();
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            fr.replace(R.id.screen_area,new CarritoFragment());
                            fr.commit();
                        } else {
                            Toast.makeText(getContext(),"Demasiados productos",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(),"Elija cantidad de productos",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void guardarProducto() {
        ArticuloVo articuloVo = new ArticuloVo();
        articuloVo.setCantidad(producto.getStock());
        articuloVo.setId(producto.getId());
        articuloVo.setNombre(producto.getNombre_producto());
        articuloVo.setDato(producto.getDato());
        articuloVo.setPrecioUnitario(producto.getPrecio());
        Canasta.agregar(articuloVo, Integer.parseInt(txtCantidad.getText().toString()));
    }

    private void cargarWebService(String idProducto) {
        String url="https://diegosv10.000webhostapp.com/productoPorID.php?id="+idProducto;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"algo salio mal :c",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"milagro salio bien :'D",Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            producto = new Producto();
            producto.setId(jsonObject.optInt("id"));
            producto.setNombre_producto(jsonObject.optString("nombre_producto"));
            producto.setDescripcion(jsonObject.optString("descripcion"));
            producto.setStock(jsonObject.optInt("stock"));
            producto.setPrecio(jsonObject.optDouble("precio"));
            producto.setCalorias(jsonObject.optInt("calorias"));
            producto.setGrasas(jsonObject.optDouble("grasas"));
            producto.setCarbohidratos(jsonObject.optDouble("carbohidratos"));
            producto.setProteinas(jsonObject.optDouble("proteinas"));
            producto.setDato(jsonObject.optString("imagen"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (producto.getImagen()!=null){
            imageDescripcion.setImageBitmap(producto.getImagen());
        }else{
            imageDescripcion.setImageResource(R.drawable.img_base);
        }

        txtDescripcion.setText(producto.getNombre_producto() + "\n"
                            + producto.getPrecio() + "\n"
                            + producto.getStock() + "\n"
                            + producto.getDescripcion() + "\n");
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
