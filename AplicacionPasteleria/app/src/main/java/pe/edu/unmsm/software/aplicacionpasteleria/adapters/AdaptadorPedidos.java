package pe.edu.unmsm.software.aplicacionpasteleria.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Pedido;

public class AdaptadorPedidos extends
        RecyclerView.Adapter<AdaptadorPedidos.PedidosViewHolder> implements View.OnClickListener {

    List<Pedido> listaPedido;
    private View.OnClickListener listener;

    public AdaptadorPedidos(List<Pedido> listaPedido) {
        this.listaPedido = listaPedido;
    }


    @Override
    public PedidosViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new AdaptadorPedidos.PedidosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder holder, int position) {
        holder.txtCodigo.setText(""+listaPedido.get(position).getId());
        holder.txtEstado.setText(""+listaPedido.get(position).getEstado());
        holder.txtFecha.setText(listaPedido.get(position).getFechaEntrega().toString());
        if (listaPedido.get(position).getEstado() == 1) {
            holder.imagen.setImageResource(R.drawable.paquete_abierto);
        } else {
            holder.imagen.setImageResource(R.drawable.paquete_cerrado);
        }
    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{

        TextView txtCodigo,txtFecha,txtEstado;
        ImageView imagen;

        public PedidosViewHolder(View itemView) {
            super(itemView);
            txtCodigo = (TextView) itemView.findViewById(R.id.codigoPedido);
            txtFecha = (TextView) itemView.findViewById(R.id.fechaEntregaPedido);
            txtEstado = (TextView) itemView.findViewById(R.id.estadoPedido);
            imagen = (ImageView) itemView.findViewById(R.id.imgPedidos);
        }
    }
}
