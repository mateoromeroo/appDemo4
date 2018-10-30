package pe.edu.unmsm.software.aplicacionpasteleria.activity;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import pe.edu.unmsm.software.aplicacionpasteleria.R;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.CarritoFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.CatalogoFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.CerrarSesionFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.ConfirmarFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.CuponesFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.DetallePedidoFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.HistorialFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.InicioFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.PerfilFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.PerfilModFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.UbicanosFragment;
import pe.edu.unmsm.software.aplicacionpasteleria.fragments.detalleProducto;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CarritoFragment.OnFragmentInteractionListener,
        CatalogoFragment.OnFragmentInteractionListener,
        CerrarSesionFragment.OnFragmentInteractionListener,
        CuponesFragment.OnFragmentInteractionListener,
        HistorialFragment.OnFragmentInteractionListener,
        InicioFragment.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        UbicanosFragment.OnFragmentInteractionListener,
        ConfirmarFragment.OnFragmentInteractionListener,
        detalleProducto.OnFragmentInteractionListener,
        PerfilModFragment.OnFragmentInteractionListener,
        DetallePedidoFragment.OnFragmentInteractionListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        * Agregar para que inicie con el primer fragment
        * */
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.screen_area, new InicioFragment());
        ft.commit();
        navigationView.setCheckedItem(R.id.inInicio);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;

        if (id == R.id.inInicio) {
            miFragment = new InicioFragment();
        } else if (id == R.id.perInicio) {
            miFragment = new PerfilFragment();
        } else if (id == R.id.carInicio) {
            miFragment = new CarritoFragment();
        } else if (id == R.id.hisPeInicio) {
            miFragment = new HistorialFragment();
        } else if (id == R.id.catInicio) {
            miFragment = new CatalogoFragment();
        } else if (id == R.id.cupInicio) {
            miFragment = new CuponesFragment();
        } else if (id == R.id.ubiInicio) {
            miFragment = new UbicanosFragment();
        }else if (id == R.id.cerrInicio) {
            miFragment = new CerrarSesionFragment();
        }
        if(miFragment != null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, miFragment);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
