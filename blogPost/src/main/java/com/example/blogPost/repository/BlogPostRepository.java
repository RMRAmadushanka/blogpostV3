package com.example.blogPost.repository;

import com.example.blogPost.entity.BlogPostEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BlogPostRepository extends ReactiveCrudRepository<BlogPostEntity, Long> {
    Mono<Long> countByUserId(Long userId);
}