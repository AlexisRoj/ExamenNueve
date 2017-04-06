package com.innovagenesis.aplicaciones.android.examennueve.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.adapters.RecyclerViewAdapaterAU;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.estudiantes.EstudiantesAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.fragments.AsignaturaFragment;
import com.innovagenesis.aplicaciones.android.examennueve.fragments.EstudiantesFragment;
import com.innovagenesis.aplicaciones.android.examennueve.fragments.TareasFragment;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.UsuariosAsigna;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EstudiantesAsyncTask.mDesplegarEstudiantes {

    SharedPreferences preferences;
    int contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(DiccionarioDatos.PREFERENCE_LOGIN, MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            try {
                new EstudiantesAsyncTask(this).execute(new URL(DiccionarioDatos.URL_SERVICIO_ASIGNA));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


        } else if (id == R.id.nav_estudiante) {
            // Se envía la solicitud asincronica de estudiantes
        } else if (id == R.id.nav_tareas) {
            // Se envía la solicitud asincronica de tareas
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
    public void DesplegarEstudiantes(ArrayList<UsuariosAsigna> listaUsuarios) {

        contenedor = R.id.contenedor;
        Fragment fragment = AsignaturaFragment.newInstances(listaUsuarios);

        //mListarDonantes(listaUsuarios);
        mInstanciarFragment(contenedor,fragment).commit();
    }

    public void mListarDonantes(List<UsuariosAsigna> usuariosAsigna) {
        /** Llena el recyclerView en activity*/
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerViewAdapaterAU adapter = new RecyclerViewAdapaterAU(this,usuariosAsigna);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
