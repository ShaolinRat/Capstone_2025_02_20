package org.example.sprowal_charles_newspage_capstone.repository;

import org.example.sprowal_charles_newspage_capstone.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //find articles by title:
    List<Article> findByTitleContaining(String titleFragment);

    //find by published date:
    List<Article> findByPublishedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    //find by sourceApiId:
    Article findBySourceApiId(String sourceApiId);
}
