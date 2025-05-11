package com.example.blogapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BlogDbHelper extends SQLiteOpenHelper {
    // Version et nom de la base
    private static final String DATABASE_NAME = "blog.db";
    private static final int DATABASE_VERSION = 1;

    // Nom de table et colonnes
    public static final String TABLE_ARTICLES = "articles";
    public static final String COL_ID      = "id";
    public static final String COL_TITRE   = "titre";
    public static final String COL_CONTENU = "contenu";

    public BlogDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table articles
        String CREATE_TABLE = "CREATE TABLE " + TABLE_ARTICLES + " ("
                + COL_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITRE   + " TEXT NOT NULL, "
                + COL_CONTENU + " TEXT NOT NULL"
                + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Pour l’instant, on supprime et on recrée (simple)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    /**
     * Insère un nouvel article dans la base et retourne son ID.
     */
    public long addArticle(String titre, String contenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITRE, titre);
        values.put(COL_CONTENU, contenu);
        long newRowId = db.insert(TABLE_ARTICLES, null, values);
        db.close();
        return newRowId;
    }

    /**
     * Retourne la liste de tous les articles, du plus récent au plus ancien.
     */
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { COL_ID, COL_TITRE, COL_CONTENU };
        Cursor cursor = db.query(
                TABLE_ARTICLES,
                columns,
                null, null, null, null,
                COL_ID + " DESC"
        );

        if (cursor.moveToFirst()) {
            do {
                int    id      = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String titre   = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITRE));
                String contenu = cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENU));
                articles.add(new Article(id, titre, contenu));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return articles;
    }

    /**
     * Retourne un article spécifique identifié par son ID, ou null si introuvable.
     */
    public Article getArticleById(int idRecherche) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns       = { COL_ID, COL_TITRE, COL_CONTENU };
        String   selection     = COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(idRecherche) };

        Cursor cursor = db.query(
                TABLE_ARTICLES,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        Article article = null;
        if (cursor.moveToFirst()) {
            int    id      = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String titre   = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITRE));
            String contenu = cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENU));
            article = new Article(id, titre, contenu);
        }
        cursor.close();
        db.close();
        return article;
    }
}
