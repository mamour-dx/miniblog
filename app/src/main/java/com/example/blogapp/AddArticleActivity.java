package com.example.blogapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddArticleActivity extends AppCompatActivity {

    private EditText etTitre, etContenu;
    private Button btnSave;
    private BlogDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Active le mode Edge-to-Edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_article);

        // Ajuste le padding pour les system bars (status bar, nav bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // --- Initialisation des vues ---
        etTitre   = findViewById(R.id.editTextTitre);
        etContenu = findViewById(R.id.editTextContenu);
        btnSave   = findViewById(R.id.btnSave);

        // --- Helper SQLite ---
        dbHelper = new BlogDbHelper(this);

        // --- Listener du bouton Enregistrer ---
        btnSave.setOnClickListener(v -> {
            String titre   = etTitre.getText().toString().trim();
            String contenu = etContenu.getText().toString().trim();

            // Validation basique
            if (titre.isEmpty() || contenu.isEmpty()) {
                Toast.makeText(this,
                        "Les deux champs sont obligatoires",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Insertion en base
            long newId = dbHelper.addArticle(titre, contenu);
            if (newId != -1) {
                Toast.makeText(this,
                        "Article ajouté (ID=" + newId + ")",
                        Toast.LENGTH_SHORT).show();
                // Retour à MainActivity
                finish();
            } else {
                Toast.makeText(this,
                        "Erreur lors de l'ajout",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
