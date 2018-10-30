package pe.edu.unmsm.software.aplicacionpasteleria.Utils;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Producto;

public class Canasta {
    private static HashMap<Integer,ArticuloVo> canasta = new HashMap<>();

    public static void agregar(ArticuloVo producto) {
        if (canasta.containsKey(producto.getId())) {
            ArticuloVo producto1 = canasta.get(producto.getId());
            producto1.setCantidadCarrito(producto1.getCantidadCarrito()+1);
        } else {
            producto.setCantidadCarrito(1);
            canasta.put(producto.getId(),producto);
        }
    }

    public static void agregar(ArticuloVo producto, int cantidad) {
        if (canasta.containsKey(producto.getId())) {
            ArticuloVo producto1 = canasta.get(producto.getId());
            producto1.setCantidadCarrito(producto.getCantidadCarrito()+cantidad);
        } else {
            producto.setCantidadCarrito(cantidad);
            canasta.put(producto.getId(),producto);
        }
    }

    public static void eliminar(String idProducto) {
        if (canasta.containsKey(idProducto)) {
            canasta.remove(idProducto);
        }
    }

    public static void vaciar() {
        canasta.clear();
    }

    public static int tamanho() {
        return canasta.size();
    }

    public static ArticuloVo get(int id) {
        return canasta.get(id);
    }

    public static Iterator getIterador() {
        return canasta.values().iterator();
    }
}
