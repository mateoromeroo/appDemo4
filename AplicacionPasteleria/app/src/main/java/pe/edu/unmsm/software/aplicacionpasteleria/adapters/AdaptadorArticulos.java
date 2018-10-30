package pe.edu.unmsm.software.aplicacionpasteleria.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;

import pe.edu.unmsm.software.aplicacionpasteleria.R;

public class AdaptadorArticulos extends
        RecyclerView.Adapter<AdaptadorArticulos.ArticulosViewHolder> implements View.OnClickListener{

    List<ArticuloVo> listaProductos;
    private View.OnClickListener listener;

    public AdaptadorArticulos(List<ArticuloVo> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public ArticulosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.productos_list_image,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new ArticulosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ArticulosViewHolder holder, int position) {
        holder.txtNombre.setText(listaProductos.get(position).getNombre().toString());
        holder.txtPrecio.setText(""+listaProductos.get(position).getPrecioUnitario());
        holder.txtStock.setText(""+listaProductos.get(position).getCantidad());

        if (listaProductos.get(position).getImagen()!=null){
            holder.imagen.setImageBitmap(listaProductos.get(position).getImagen());
        }else{
            holder.imagen.setImageResource(R.drawable.img_base);
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre,txtStock,txtPrecio;
        ImageView imagen;

        public ArticulosViewHolder(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.idNombre);
            txtStock = (TextView) itemView.findViewById(R.id.idStock);
            txtPrecio = (TextView) itemView.findViewById(R.id.idPrecio);
            imagen = (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
