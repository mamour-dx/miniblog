package com.example.blogapp;

public class Article {
    private int id;
    private String titre;
    private String contenu;

    public Article(int id, String titre, String contenu) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getContenu() { return contenu; }
}
