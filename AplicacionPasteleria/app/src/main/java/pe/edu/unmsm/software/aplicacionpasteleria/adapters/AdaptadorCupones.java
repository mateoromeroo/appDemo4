package pe.edu.unmsm.software.aplicacionpasteleria.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Cupones;

public class AdaptadorCupones extends
        RecyclerView.Adapter<AdaptadorCupones.CuponesViewHolder> {

    List<Cupones> listaCupones;

    public AdaptadorCupones(List<Cupones> listaCupones) {
        this.listaCupones = listaCupones;
    }

    @Override
    public CuponesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.cupones_list_image,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new CuponesViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(CuponesViewHolder holder, int position) {
        holder.txtNombre.setText(listaCupones.get(position).getNombre().toString());
        holder.txtCantidad.setText(""+listaCupones.get(position).getCantidad());
        holder.txtDescripcion.setText(""+listaCupones.get(position).getDescripcion());
        holder.txtDescuento.setText(""+listaCupones.get(position).getDescuento());
        holder.txtCodigo.setText(""+listaCupones.get(position).getCodigo());

        if (listaCupones.get(position).getImagen()!=null){
            holder.imagen.setImageBitmap(listaCupones.get(position).getImagen());
        }else{
            holder.imagen.setImageResource(R.drawable.img_base);
        }
    }

    @Override
    public int getItemCount() {
        return listaCupones.size();
    }


    public class CuponesViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre,txtCantidad,txtDescuento, txtDescripcion, txtCodigo;
        ImageView imagen;

        public CuponesViewHolder(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.nombreCupon);
            txtCantidad = (TextView) itemView.findViewById(R.id.cantidadCupon);
            txtDescuento = (TextView) itemView.findViewById(R.id.descuentoCupon);
            txtDescripcion = (TextView) itemView.findViewById(R.id.descripcionCupon);
            txtCodigo = (TextView) itemView.findViewById(R.id.codigoCupon);
            imagen = (ImageView) itemView.findViewById(R.id.imagenCupon);
        }
    }

}
