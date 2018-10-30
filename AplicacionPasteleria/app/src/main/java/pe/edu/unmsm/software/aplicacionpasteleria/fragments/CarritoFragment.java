package pe.edu.unmsm.software.aplicacionpasteleria.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.Canasta;
import pe.edu.unmsm.software.aplicacionpasteleria.Utils.ComunicacionFragments;
import pe.edu.unmsm.software.aplicacionpasteleria.adapters.AdaptadorArticulos;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Producto;


public class CarritoFragment extends Fragment {


    //Botones
    View vista;

    private RecyclerView recyclerProductos;
    private ProgressDialog dialog;
    private ArrayList<ArticuloVo> listaProductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_carrito, container, false);
        listaProductos=new ArrayList<>();

        recyclerProductos = (RecyclerView) vista.findViewById(R.id.idRecyclerCarrito);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerProductos.setHasFixedSize(true);

        Iterator it = Canasta.getIterador();

        while (it.hasNext()){
            ArticuloVo producto = (ArticuloVo) it.next();
            producto.setCantidad(producto.getCantidadCarrito());
            listaProductos.add(producto);
        }
        AdaptadorArticulos adapter=new AdaptadorArticulos(listaProductos);
        recyclerProductos.setAdapter(adapter);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnConfirCompraCarrito).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Canasta.tamanho() == 0) {
                    Toast.makeText(getContext(),"La canasta está vacía, compra >:V",Toast.LENGTH_SHORT).show();
                } else {
                    cargarWebService();
                }

            }
        });

        view.findViewById(R.id.vaciarCarrito).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                Canasta.vaciar();

                fr.replace(R.id.screen_area,new CarritoFragment());
                fr.commit();
                Toast.makeText(getActivity(), "Se vació carrito", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cargarWebService() {
        String url = "https://diegosv10.000webhostapp.com/comprarCarrito.php";
        // Datos a almacenar
        String direccionEnvio = ComunicacionFragments.user.getDireccion();
        Date fechaHoy = Calendar.getInstance().getTime();
        Date feechaEntrega = new Date(fechaHoy.getYear(),fechaHoy.getMonth(),fechaHoy.getDate()+7);
        String horaEntrega = String.valueOf(fechaHoy.getHours());
        int estado = 0;
        ComunicacionFragments.user.getDni();
        // Cupones ? no entiendo (preguntar a lusho)
        // Esta parte no entendi mucho de lo que me dijo gershon creo que los id de todos los productos si es así
        // wardar en un string toda la cadena de id de la canasta :
        Iterator it = Canasta.getIterador();
        String cad = "";
        while (it.hasNext()) {
            // en este articulo extraen lo que necesite
            ArticuloVo articulo = (ArticuloVo) it.next();
            cad += articulo.getId() + ";"; // Seria así ? ;-;
        }
        // Esos sería todo creo ?

        /* esto va en el onResponse
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            Canasta.vaciar();

            fr.replace(R.id.screen_area,new ConfirmarFragment());
            fr.commit();
            Toast.makeText(getActivity(), "Se compró", Toast.LENGTH_LONG).show();
        */
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
