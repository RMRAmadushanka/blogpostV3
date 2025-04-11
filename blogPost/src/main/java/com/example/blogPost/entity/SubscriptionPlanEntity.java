package com.example.blogPost.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("subscription_plan")
public class SubscriptionPlanEntity {
    @Id
    private Long id;
    private String planName;
    private String description;
    private int maxBlogPosts;
    private boolean canAccessPremiumContent;
    private int maxViewsPerDay;
    private boolean isAdFree;
    private String subscriptionCode;
}
