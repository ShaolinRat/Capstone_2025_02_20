package org.example.sprowal_charles_newspage_capstone.repository;

import org.example.sprowal_charles_newspage_capstone.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByAuthorId(Long userId);
    List<Blog> findByTitleContaining(String title);
}