package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CategorySelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategories;
    private CategoryAdapter categoryAdapter;
    private List<Category> categories;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        initializeViews();
        setupCategories();
        setupRecyclerView();
    }

    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
    }

    private void setupCategories() {
        categories = new ArrayList<>();

        // Tambahkan kategori "Semua" untuk memainkan semua soal
        categories.add(new Category("Semua", "Mainkan semua kategori", "🎯", "#4CAF50"));

        // Tambahkan kategori berdasarkan soal yang ada
        categories.add(new Category("Teknologi", "Soal seputar teknologi dan programming", "💻", "#2196F3"));
        categories.add(new Category("Umum", "Pengetahuan umum dan geografi", "🌍", "#FF9800"));
        categories.add(new Category("Matematika", "Soal-soal matematika dasar", "🔢", "#9C27B0"));
        categories.add(new Category("Sejarah", "Peristiwa bersejarah dunia", "📚", "#795548"));
        categories.add(new Category("Olahraga", "Dunia olahraga dan atlet", "⚽", "#F44336"));
    }

    private void setupRecyclerView() {
        categoryAdapter = new CategoryAdapter(categories, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                startQuizWithCategory(category.getName());
            }
        });

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private void startQuizWithCategory(String categoryName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selected_category", categoryName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Kembali ke splash atau keluar dari aplikasi
        super.onBackPressed();
    }
}