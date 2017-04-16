package com.innovagenesis.aplicaciones.android.examennueve.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Clase que administra el provedor de contenidos
 * Created by alexi on 16/04/2017.
 */

public class ProveedorContenidos extends android.content.ContentProvider{

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "db";
    static final String TABLE_NAME = "tbl_tareas";
    static final int DATABASE_VERSION = 1;
    static final String Sentencia = " CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " Texto TEXT NOT NULL);";
    static final String id = "id";
    static final String Texto = "Texto";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    static final String NOMBREPROVIDER = "com.example.nextu.almacenamiento.ProviderContent";
    static final String URL = "content://" + NOMBREPROVIDER + "/cte";
    static final Uri CONTENEDORURI = Uri.parse(URL);
    private static HashMap<String, String> values;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NOMBREPROVIDER, "cte", uriCode);
        uriMatcher.addURI(NOMBREPROVIDER, "cte/*", uriCode);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
