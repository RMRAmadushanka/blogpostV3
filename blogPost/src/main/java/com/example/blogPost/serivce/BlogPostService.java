package com.example.blogPost.serivce;


import com.example.blogPost.entity.BlogPostEntity;
import com.example.blogPost.repository.BlogPostRepository;
import com.example.blogPost.repository.SubscriptionPlanRepository;
import com.example.blogPost.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public Mono<BlogPostEntity> createBlogPost(BlogPostEntity postRequest) {
        Long userId = postRequest.getUserId();
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    return subscriptionPlanRepository.findById(user.getSubscriptionId())
                            .switchIfEmpty(Mono.error(new RuntimeException("Subscription plan not found")))
                            .flatMap(plan -> {
                                return blogPostRepository.countByUserId(userId)
                                        .flatMap(count -> {
                                            if (count >= plan.getMaxBlogPosts()) {
                                                return Mono.error(new RuntimeException("Blog post limit reached for your subscription"));
                                            }
                                            postRequest.setViews(0);
                                            postRequest.setLikes(0);
                                            return blogPostRepository.save(postRequest);
                                        });
                            });
                });
    }
}
