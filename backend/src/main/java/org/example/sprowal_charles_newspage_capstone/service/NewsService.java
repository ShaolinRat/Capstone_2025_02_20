package org.example.sprowal_charles_newspage_capstone.service;

import org.example.sprowal_charles_newspage_capstone.model.Article;
import org.example.sprowal_charles_newspage_capstone.model.TopNewsResponse;
import org.example.sprowal_charles_newspage_capstone.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {

    @Value("${newsapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ArticleRepository articleRepository;

    @Autowired
    public NewsService(RestTemplate restTemplate, ArticleRepository articleRepository) {
        this.restTemplate = restTemplate;
        this.articleRepository = articleRepository;
    }

//    public void populateArticles() {
//        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;
//        TopNewsResponse response = restTemplate.getForObject(url, TopNewsResponse.class);
//
//        if (response != null && response.getArticles() != null) {
//            List<Article> articles = response.getArticles().stream()
//                    .map(article -> new Article(
//                            article.getSourceApiId(),
//                            article.getTitle(),
//                            article.getDescription(),
//                            article.getUrl(),
//                            article.getUrlToImage(),
//                            article.getContent(),
//                            article.getPublishedAt()
//                    ))
//                    .toList();
//
//            // Save all articles to the database
//            articleRepository.saveAll(articles);
//        }
//    }

    public void populateArticles() {
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;
        TopNewsResponse response = restTemplate.getForObject(url, TopNewsResponse.class);

        if (response != null && response.getArticles() != null) {
            List<Article> articles = response.getArticles().stream()
                    .map(article -> {
                        // Determine the sourceApiId based on whether id is null or not
                        String sourceApiId = article.getSource() != null
                                ? (article.getSource().getId() != null
                                ? article.getSource().getId()
                                : article.getSource().getName().toLowerCase().replace(" ", "-"))
                                : "unknown-source"; // Default value if source is null

                        String description = article.getDescription();
                        int descriptionLength = description != null ? description.length() : 0;

                        System.out.println("Description length for article '" + article.getTitle() + "': " + descriptionLength);


                        return new Article(
                                sourceApiId,
                                article.getTitle(),
                                article.getDescription(),
                                article.getUrl(),
                                article.getUrlToImage(),
                                article.getContent(),
                                article.getPublishedAt()
                        );
                    })
                    .toList();

            // Save all articles to the database
            articleRepository.saveAll(articles);
        }
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}