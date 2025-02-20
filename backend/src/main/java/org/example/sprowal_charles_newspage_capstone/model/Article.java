package org.example.sprowal_charles_newspage_capstone.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceApiId; // Assuming NewsAPI provides a unique identifier for each article

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description; // Optional, might not always be available from NewsAPI

    @Column(nullable = false)
    private String url; // Link to the original article

    private String urlToImage; // Optional image URL

    @Column(columnDefinition = "TEXT")
    private String content; // Full content of the article if you're storing it

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt; // When this article was saved to the database

    // Constructor without id for creating new articles
    public Article(String sourceApiId, String title, String description, String url, String urlToImage, String content, LocalDateTime publishedAt) {
        this.sourceApiId = sourceApiId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
        this.publishedAt = publishedAt;
        this.fetchedAt = LocalDateTime.now(); // Set fetched time at creation
    }
}