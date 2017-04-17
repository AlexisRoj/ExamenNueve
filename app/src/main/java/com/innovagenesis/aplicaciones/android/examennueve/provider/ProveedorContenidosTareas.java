package com.innovagenesis.aplicaciones.android.examennueve.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import java.util.HashMap;

/**
 * Clase que administra el provedor de contenidos
 * Created by alexi on 16/04/2017.
 */

public class ProveedorContenidosTareas extends android.content.ContentProvider {

    private SQLiteDatabase db = null;
    static final String DATABASE_NAME = "db";
    static final String TABLE_NAME = "tbl_tareas";
    static final int DATABASE_VERSION = 1;
    static final String Sentencia = " CREATE TABLE " + TABLE_NAME
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
            ".provider.ProveedorContenidosTareas";
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
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL(Sentencia);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {

        Context context = getContext();
        dbHelper = new BaseDatos(context);

        dbHelper.close();
        if (db != null){
            return true;
        }
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
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
        long rowID = db.insert(TABLE_NAME, "", values);
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
                entero= db.delete(TABLE_NAME,selection,selectionArgs);
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
                count = db.update(TABLE_NAME, values, selection, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("URI desconocidad " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
