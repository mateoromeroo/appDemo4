package pe.edu.unmsm.software.aplicacionpasteleria;

import android.view.inputmethod.CursorAnchorInfo;

import org.junit.Test;

import java.util.Iterator;

import pe.edu.unmsm.software.aplicacionpasteleria.Utils.Canasta;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.ArticuloVo;
import pe.edu.unmsm.software.aplicacionpasteleria.entidades.Producto;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        System.out.println(Canasta.tamanho());

        ArticuloVo prod1 = new ArticuloVo();
        prod1.setId(1);
        prod1.setNombre("cepillo");
        Canasta.agregar(prod1);

        if (Canasta.get(3) == null) {
            System.out.println("muestra esto");
        }
        System.out.println(Canasta.get(1));

        ArticuloVo prod2 = new ArticuloVo();
        prod2.setId(2);
        prod2.setNombre("esocba");

        ArticuloVo prod3 = new ArticuloVo();
        prod3.setId(3);
        prod3.setNombre("taladro");

        Canasta.agregar(prod1);
        Canasta.agregar(prod2);
        Canasta.agregar(prod3);
        Canasta.agregar(prod2);
        Canasta.agregar(prod1);

        Iterator it = Canasta.getIterador();

        System.out.println(Canasta.tamanho());

        while (it.hasNext()) {
            ArticuloVo pr = (ArticuloVo) it.next();
            System.out.println(pr.getId() + " " + pr.getCantidadCarrito());
        }
    }
}