package com.example.api.Repository;

import com.example.api.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Float> {
}

