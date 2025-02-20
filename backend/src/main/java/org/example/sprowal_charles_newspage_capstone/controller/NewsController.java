package org.example.sprowal_charles_newspage_capstone.controller;

import org.example.sprowal_charles_newspage_capstone.model.Article;
import org.example.sprowal_charles_newspage_capstone.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/populate")
    public String populateArticles() {
        newsService.populateArticles();
        return "Articles have been populated.";
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        // Assuming you have a method in NewsService to get all articles
        return newsService.getAllArticles();
    }
}