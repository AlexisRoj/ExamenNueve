package com.innovagenesis.aplicaciones.android.examennueve.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.test.ProviderTestCase2;

import com.innovagenesis.aplicaciones.android.examennueve.activities.MainActivity;

import java.util.HashMap;

/**
 * Clase que administra el provedor de contenidos
 * Created by alexi on 16/04/2017.
 */

public class ProvedorContenidosTareas extends ContentProvider {

    private SQLiteDatabase db = null;
    static final String DATABASE_NAME = "db2";
    static final String TABLE_NAME_TAREAS = "tbl_tareas";
    static final int DATABASE_VERSION = 1;
    static final String Sentencia = " CREATE TABLE " + TABLE_NAME_TAREAS
            + " (id_tarea INTEGER PRIMARY KEY, "
            + " nom_tarea TEXT NOT NULL, "
            + " asigna_tarea TEXT NOT NULL, "
            + " estud_tarea TEXT NOT NULL, "
            + " nota_tarea INTEGER NOT NULL);";
    public static final String id_tarea = "id_tarea";
    public static final String nom_tarea = "nom_tarea";
    public static final String asingna_tarea = "asigna_tarea";
    public static final String estud_tarea = "estud_tarea";
    public static final String nota_tarea = "nota_tarea";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    static final String NOMBREPROVIDER = "com.innovagenesis.aplicaciones.android.examennueve" +
            ".provider.ProvedorContenidosTareas";
    static final String URL = "content://" + NOMBREPROVIDER + "/cte";
    public static final Uri CONTENEDORURI = Uri.parse(URL);
    private static HashMap<String, String> values;

    BaseDatos dbHelper = null;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NOMBREPROVIDER, "cte", uriCode);
        uriMatcher.addURI(NOMBREPROVIDER, "cte/*", uriCode);
    }


    /**
     * Se crea la base que crea la tabla de tareas
     */

    private static class BaseDatos extends SQLiteOpenHelper {

        BaseDatos(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Sentencia);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TAREAS);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {

        Context context = getContext();
        dbHelper = new BaseDatos(context);
        db = dbHelper.getWritableDatabase();
        if (db != null){
            // Borra la tabla de la base de datos cuando inicia la aplicación
            // para descargar de nuevo el contenido del service
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TAREAS);
            db.execSQL(Sentencia);
            return true;
        }
        return false;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME_TAREAS);

        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Uri No soportada " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = id_tarea;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/cte";
            default:
                throw new IllegalArgumentException("Uri no valida: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {

        if (MainActivity.cantCiclos == 0){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TAREAS);
            db.execSQL(Sentencia);
        }
        long rowID = db.insert(TABLE_NAME_TAREAS, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENEDORURI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Error a agregar el registro " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int entero= 0;
        switch (uriMatcher.match(uri)){
            case uriCode:
                entero= db.delete(TABLE_NAME_TAREAS,selection,selectionArgs);
                break;
            default:
                throw  new IllegalArgumentException("Uri desconocida ");
        }

        return entero;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_NAME_TAREAS, values, selection, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("URI desconocidad " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
