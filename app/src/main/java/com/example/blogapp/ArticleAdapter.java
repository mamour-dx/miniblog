package com.example.blogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private final Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    /** Met à jour la liste d’articles et rafraîchit la vue */
    public void updateList(List<Article> newArticles) {
        this.articles = newArticles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article art = articles.get(position);
        holder.tvTitre.setText(art.getTitre());
        // Afficher les 50 premiers caractères du contenu
        String preview = art.getContenu().length() > 50
                ? art.getContenu().substring(0, 50) + "…"
                : art.getContenu();
        holder.tvPreview.setText(preview);

        // Clic sur l’item → détail
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailArticleActivity.class);
            i.putExtra("ARTICLE_ID", art.getId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    /** ViewHolder interne pour item_article.xml */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitre, tvPreview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre   = itemView.findViewById(R.id.tvItemTitre);
            tvPreview = itemView.findViewById(R.id.tvItemPreview);
        }
    }
}
