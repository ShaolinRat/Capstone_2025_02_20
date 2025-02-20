package org.example.sprowal_charles_newspage_capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsArticle {

    @JsonProperty("source")
    private Source source; // Refer to nested source class below

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("url")
    private String url;

    @JsonProperty("urlToImage")
    private String urlToImage;

    @JsonProperty("content")
    private String content;

    @JsonProperty("publishedAt")
    private LocalDateTime publishedAt;

    public String getSourceApiId() {
        return source != null ? source.getId() : null;
    }

    // Inner class to represent the source of the article
    @Data
    @NoArgsConstructor
    public static class Source {
        @JsonProperty("id")
        private String id;
        private String name;
    }
}