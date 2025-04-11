package com.example.blogPost.controller;

import com.example.blogPost.entity.BlogPostEntity;
import com.example.blogPost.serivce.BlogPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final BlogPostService blogPostService;

    public BlogController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/blog-posts")
    public Mono<BlogPostEntity> createPost(@RequestBody BlogPostEntity blogPost) {
        return blogPostService.createBlogPost(blogPost);
    }



}
