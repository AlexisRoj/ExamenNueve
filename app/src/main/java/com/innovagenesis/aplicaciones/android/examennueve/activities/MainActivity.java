package com.innovagenesis.aplicaciones.android.examennueve.activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.adapters.RecyclerViewAdapterTarea;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.ActualizarTareaAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.InsertarTareaAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.ListarAsignaturaAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.ListarTareasAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.ListarUsuarioAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.dialogos.DialogoAgregarTareas;
import com.innovagenesis.aplicaciones.android.examennueve.fragments.AsignaturaFragment;
import com.innovagenesis.aplicaciones.android.examennueve.fragments.EstudiantesFragment;
import com.innovagenesis.aplicaciones.android.examennueve.fragments.TareasFragment;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.UsuariosAsigna;
import com.innovagenesis.aplicaciones.android.examennueve.provider.ProveedorContenidosTareas;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ListarAsignaturaAsyncTask.mDesplegarEstudiantes,
        ListarUsuarioAsyncTask.mDesplegarUsuario,
        ListarTareasAsyncTask.mDesplegarTareas,
        RecyclerViewAdapterTarea.mEditarElementoRecycler,
        DialogoAgregarTareas.DatosGuardarTarea {

    private SharedPreferences preferences;
    private int contenedor = R.id.contenedor;
    private String jsonAsignatura;
    private Toolbar toolbar;
    private Bundle args = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle(getString(R.string.app_name));
        toolbar.setSubtitle("Inicio");

        preferences = getSharedPreferences(DiccionarioDatos.PREFERENCE_LOGIN, MODE_PRIVATE);

        /*  Se llena el provider cuando inicia la primera vez la aplicación*/
        mLLenarProvider();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new ListarAsignaturaAsyncTask(MainActivity.this, 2).execute(
                            new URL(DiccionarioDatos.URL_SERVICIO_ASIGNA));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Metodo encargado de realizar el llamado del proceso de llenar el provider
     */
    public void mLLenarProvider() {
        try {
            new ListarTareasAsyncTask(this, 2).execute(new URL(DiccionarioDatos.URL_SERVICIO_TAREA));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.bprrarPreference) {
            // Encargado de borrar la preference
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(DiccionarioDatos.nombreUsuario);
            editor.remove(DiccionarioDatos.passUsuario);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_asignatura) {
            // Se envía la solicitud asincronica de asignaturas
            // El segundo parametro del constructor indica la accion si va a llenar
            // el spinner o el recycler. 1 = Recycler, 2 = Spinner
            try {
                new ListarAsignaturaAsyncTask(this, 1).execute(new URL(DiccionarioDatos.URL_SERVICIO_ASIGNA));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


        } else if (id == R.id.nav_estudiante) {
            // Se envía la solicitud asincronica de estudiantes
            try {
                new ListarUsuarioAsyncTask(this, 1).execute(new URL(DiccionarioDatos.URL_SERVICIO_USUARIO));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_tareas) {
            // Se envía la solicitud asincronica de tareas

            try {
                new ListarTareasAsyncTask(this, 1).execute(new URL(DiccionarioDatos.URL_SERVICIO_TAREA));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Método encargado de hacer la carga de todos los fragmentos
     */
    @SuppressLint("CommitTransaction")
    private FragmentTransaction mInstanciarFragment(int contenedor, Fragment fragment) {

        return getSupportFragmentManager().beginTransaction()
                .replace(contenedor, fragment);
    }

    @Override
    public void DesplegarAsignaturaRecycler(ArrayList<UsuariosAsigna> listaAsignatura) {
        // Carga los elementos que van ser enviados al dialogo de agregar tarea
        // Uso para el spinner
        Fragment fragment = AsignaturaFragment.newInstances(listaAsignatura);
        mInstanciarFragment(contenedor, fragment).commit();
        toolbar.setSubtitle(getString(R.string.asignatura));
    }

    @Override
    public void DesplegarAsignaturaDialogo(String jsonAsigna) {

        // Reserva el resultado de la primer consulta ejecutada desde el boton
        // y ejecuta segunda consulta para llenar spinner
        jsonAsignatura = jsonAsigna;

        try {
            new ListarUsuarioAsyncTask(this, 2).execute(new URL(DiccionarioDatos.URL_SERVICIO_USUARIO));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DesplegarUsuarioRecycler(ArrayList<UsuariosAsigna> listaUsuarios) {

        Fragment fragment = EstudiantesFragment.newInstance(listaUsuarios);
        mInstanciarFragment(contenedor, fragment).commit();
        toolbar.setSubtitle(getString(R.string.estudiante));
    }

    String jsonEstudiante;

    @Override
    public void DesplegarUsuarioDialogo(String jsonEstud) {
    /* Despliega el dialogo de agregar usuario*/
        DialogoAgregarTareas dialogoAgregarTareas =
                DialogoAgregarTareas.newInstance(jsonEstud, jsonAsignatura, this);
        dialogoAgregarTareas.setArguments(args);
        dialogoAgregarTareas.show(getSupportFragmentManager(), DialogoAgregarTareas.TAG_DIALOGO);
    }

    @Override
    public void DesplegarTareaRecycler(ArrayList<Tareas> listarTareasAsyncTasks) {
        // Llama al fragment de tareas para y envia la lista al recyclerView

        Fragment fragment = TareasFragment.newInstance(listarTareasAsyncTasks);
        mInstanciarFragment(contenedor, fragment).commit();
        toolbar.setSubtitle(getString(R.string.tareas));
    }

    /**
     * Encargado de llenar el content provider de tareas
     */
    @Override
    public void LlenarProviderTareas(String jsonTarea) {

        if (jsonTarea != null) {

            try {
                JSONArray jsonArray = new JSONArray(jsonTarea);

                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues valores = new ContentValues();
                    valores.put(ProveedorContenidosTareas.id_tarea, jsonArray.getJSONObject(i)
                            .getInt(DiccionarioDatos.idTarea));
                    valores.put(ProveedorContenidosTareas.nom_tarea, jsonArray.getJSONObject(i)
                            .getString(DiccionarioDatos.nomTarea));
                    valores.put(ProveedorContenidosTareas.asingna_tarea, jsonArray.getJSONObject(i)
                            .getString(DiccionarioDatos.nomAsignaTarea));
                    valores.put(ProveedorContenidosTareas.estud_tarea, jsonArray.getJSONObject(i)
                            .getString(DiccionarioDatos.nomUsuarioTarea));
                    valores.put(ProveedorContenidosTareas.nota_tarea, jsonArray.getJSONObject(i)
                            .getInt(DiccionarioDatos.notaTarea));
                    getContentResolver().insert(ProveedorContenidosTareas.CONTENEDORURI, valores);
                }
                Toast.makeText(getApplicationContext(),
                        "Nuevo registro ingresado " + ProveedorContenidosTareas.CONTENEDORURI,
                        Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void EditarElementoRecycler(Bundle bundle) {
        /*Ejecuta la lectura del json para
        enviarlo a rellenar el spinner*/

        args = bundle;
        try {
            new ListarAsignaturaAsyncTask(MainActivity.this, 2).execute(
                    new URL(DiccionarioDatos.URL_SERVICIO_ASIGNA));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /** Encargado de gestionar la accion de guardar o actualizar*/
    @Override
    public void GuardarTarea(Tareas tareas, Boolean nuevaTarea) {
        if (nuevaTarea) {
            try {
                new InsertarTareaAsyncTask(MainActivity.this, tareas)
                        .execute(new URL(DiccionarioDatos.URL_SERVICIO_TAREA));
                /** Seccion del provedor de contenido*/
                mLLenarProvider();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new ActualizarTareaAsyncTask(MainActivity.this, tareas)
                        .execute(new URL(DiccionarioDatos.URL_SERVICIO_TAREA + tareas.getIdTarea()));
                /** Seccion del provedor de contenido*/
                mLLenarProvider();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        try {
            new ListarTareasAsyncTask(this, 1).execute(new URL(DiccionarioDatos.URL_SERVICIO_TAREA));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
