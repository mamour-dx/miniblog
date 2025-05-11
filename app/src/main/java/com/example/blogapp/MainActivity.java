package com.example.blogapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BlogDbHelper dbHelper;
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Active le mode Edge-to-Edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste le padding pour les systemBars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Initialisation de la base et du RecyclerView ---
        dbHelper = new BlogDbHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ArticleAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // --- FAB pour ajouter un article ---
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddArticleActivity.class));
        });

        // Charge la liste au démarrage
        loadArticles();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 🔄 À chaque retour (après ajout), on rafraîchit la liste
        loadArticles();
    }

    /**
     * Étape 5.4 : relit tous les articles en base et met à jour l'adaptateur
     */
    private void loadArticles() {
        List<Article> list = dbHelper.getAllArticles();
        adapter.updateList(list);
    }
}
