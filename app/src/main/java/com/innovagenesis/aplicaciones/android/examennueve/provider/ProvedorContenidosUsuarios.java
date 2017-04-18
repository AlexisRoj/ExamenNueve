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
import java.util.HashMap;

/**
 *
 * Created by alexi on 17/04/2017.
 */

public class ProvedorContenidosUsuarios extends ContentProvider {

    private SQLiteDatabase db = null;
    static final String DATABASE_NAME = "db";
    static final String TABLE_NAME_USUARIO = "tbl_usuario";
    static final int DATABASE_VERSION = 1;
    static final String Sentencia = " CREATE TABLE " + TABLE_NAME_USUARIO
            + " (id_usuario INTEGER PRIMARY KEY, "
            + " nom_usuario TEXT NOT NULL, "
            + " pass_usuario TEXT NOT NULL, "
            + " rol_user INTEGER NOT NULL);";
    public static final String id_usuario = "id_usuario";
    public static final String nom_usuario = "nom_usuario";
    public static final String pass_usuario = "pass_usuario";
    public static final String rol_user = "rol_user";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    static final String NOMBREPROVIDER = "com.innovagenesis.aplicaciones.android.examennueve" +
            ".provider.ProvedorContenidosUsuarios";
    static final String URL = "content://" + NOMBREPROVIDER + "/cte";
    public static final Uri CONTENEDORURI = Uri.parse(URL);
    private static HashMap<String, String> values;

    BaseDatos dbHelper = null;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NOMBREPROVIDER, "cte", uriCode);
        uriMatcher.addURI(NOMBREPROVIDER, "cte/*", uriCode);
    }


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

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USUARIO);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new ProvedorContenidosUsuarios.BaseDatos(context);

        db = dbHelper.getWritableDatabase();
        if (db != null){
            // Borra la tabla de la base de datos cuando inicia la aplicación
            // para descargar de nuevo el contenido del service
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USUARIO);
            db.execSQL(Sentencia);
            return true;
        }
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME_USUARIO);

        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Uri No soportada " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = pass_usuario;
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
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME_USUARIO, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENEDORURI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Error a agregar el registro " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
