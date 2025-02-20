package org.example.sprowal_charles_newspage_capstone.service;

import org.example.sprowal_charles_newspage_capstone.model.Blog;
import org.example.sprowal_charles_newspage_capstone.model.User;
import org.example.sprowal_charles_newspage_capstone.repository.BlogRepository;
import org.example.sprowal_charles_newspage_capstone.repository.UserRepository;
import org.example.sprowal_charles_newspage_capstone.dto.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found"));
    }

    public Blog createBlog(BlogDTO blogDTO, String username) {
        // Find the authenticated user
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Convert DTO to Entity
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(user);
        
        // Save and return the blog
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, Blog blogDetails, String username) {
        Blog blog = getBlogById(id);

        // Check if the user is the author
        if (!blog.getAuthor().getUsername().equals(username)) {
            throw new IllegalStateException("User not authorized to update this blog");
        }

        blog.setTitle(blogDetails.getTitle());
        blog.setContent(blogDetails.getContent());

        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id, String username) {
        Blog blog = getBlogById(id);

        // Check if the user is the author
        if (!blog.getAuthor().getUsername().equals(username)) {
            throw new IllegalStateException("User not authorized to delete this blog");
        }

        blogRepository.delete(blog);
    }

    public List<Blog> getBlogsByUser(Long userId) {
        return blogRepository.findByAuthorId(userId);
    }
}