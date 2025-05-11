package com.example.blogapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class DetailArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_article);

        // Apply system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                }
        );

        // Set up toolbar as ActionBar with Up button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Load article by ID
        int id = getIntent().getIntExtra("ARTICLE_ID", -1);
        if (id == -1) {
            finish();
            return;
        }

        BlogDbHelper db = new BlogDbHelper(this);
        Article article = db.getArticleById(id);
        if (article == null) {
            finish();
            return;
        }

        // Populate views
        toolbar.setTitle(article.getTitre());

        TextView tvTitle   = findViewById(R.id.tvDetailTitre);
        TextView tvAuthor  = findViewById(R.id.tvDetailAuthor);
        TextView tvDate    = findViewById(R.id.tvDetailDate);
        TextView tvContent = findViewById(R.id.tvDetailContenu);

        tvTitle.setText(article.getTitre());
        tvAuthor.setText("Écrit par John Doe");     // or article.getAuteur()
        tvDate.setText("21 Novembre 2022");          // or article.getDateFormatted()
        tvContent.setText(article.getContenu());
    }
}
