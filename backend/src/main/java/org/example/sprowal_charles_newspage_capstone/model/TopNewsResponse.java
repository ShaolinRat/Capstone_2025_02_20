package org.example.sprowal_charles_newspage_capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopNewsResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("totalResults")
    private Integer totalResults;

    @JsonProperty("articles")
    private List<NewsArticle> articles;
}