package org.example.sprowal_charles_newspage_capstone.controller;

import org.example.sprowal_charles_newspage_capstone.model.Blog;
import org.example.sprowal_charles_newspage_capstone.service.BlogService;
import org.example.sprowal_charles_newspage_capstone.dto.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public Blog getBlog(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public Blog createBlog(@RequestBody BlogDTO blogDTO, Authentication authentication) {
        return blogService.createBlog(blogDTO, authentication.getName());
    }

    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody Blog blog, Authentication authentication) {
        return blogService.updateBlog(id, blog, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id, Authentication authentication) {
        blogService.deleteBlog(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<Blog> getUserBlogs(@PathVariable Long userId) {
        return blogService.getBlogsByUser(userId);
    }
}