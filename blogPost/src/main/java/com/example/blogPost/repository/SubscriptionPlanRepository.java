package com.example.blogPost.repository;

import com.example.blogPost.entity.SubscriptionPlanEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPlanRepository extends ReactiveCrudRepository<SubscriptionPlanEntity, Long> {}
