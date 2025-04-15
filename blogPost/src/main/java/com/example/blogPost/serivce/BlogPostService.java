package com.example.blogPost.serivce;


import com.example.blogPost.entity.BlogPostEntity;
import com.example.blogPost.repository.BlogPostRepository;
import com.example.blogPost.repository.SubscriptionPlanRepository;
import com.example.blogPost.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    public Flux<BlogPostEntity> getAllBlogPost(){
        return Flux.fromIterable(blogPostRepository.findAll().toIterable());
    }

    public Mono<String> getPremiumContentAccess (Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    if(user.getSubscriptionId() == null){
                        return Mono.error(new RuntimeException("Subscription plan not found"));
                    }
                    return subscriptionPlanRepository.findById(user.getSubscriptionId())
                            .switchIfEmpty(Mono.error(new RuntimeException("Subscription plan not found")))
                            .flatMap(plan -> {
                                if(plan.isCanAccessPremiumContent()){
                                    return Mono.error(new RuntimeException("Can access premium content"));
                                } else if (!plan.isCanAccessPremiumContent() && plan.getMaxViewsPerDay() > 0){
                                    return Mono.just("Primium content is limited. You can view limited content today");
                                } else {
                                    return Mono.error(new RuntimeException("Can not access premium content"));
                                }
                            });
                });
    }
}
